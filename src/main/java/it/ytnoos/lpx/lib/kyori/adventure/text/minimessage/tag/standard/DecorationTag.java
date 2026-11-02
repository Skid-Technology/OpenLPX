package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class DecorationTag {

    public static final String REVERT = "!";

    static final Map<TextDecoration, TagResolver> RESOLVERS =
            Stream.of(
                            resolvers(TextDecoration.OBFUSCATED, "obf"),
                            resolvers(TextDecoration.BOLD, "b"),
                            resolvers(TextDecoration.STRIKETHROUGH, "st"),
                            resolvers(TextDecoration.UNDERLINED, "u"),
                            resolvers(TextDecoration.ITALIC, "em", "i"))
                    .collect(
                            Collectors.toMap(
                                    Entry::getKey,
                                    result ->
                                            result.getValue().collect(TagResolver.toTagResolver()),
                                    (result, result2) ->
                                            TagResolver.builder()
                                                    .resolver(result)
                                                    .resolver(result2)
                                                    .build(),
                                    LinkedHashMap::new));

    static final TagResolver RESOLVER = TagResolver.resolver(RESOLVERS.values());

    static Entry<TextDecoration, Stream<TagResolver>> resolvers(
            TextDecoration textDecoration, @Nullable String text2, @NotNull String... text3) {
        String text4 = TextDecoration.NAMES.key(textDecoration);
        HashSet<String> values;
        (values = new HashSet()).add(text4);
        if (text2 != null) {
            values.add(text2);
        }
        Collections.addAll(values, text3);
        BiFunction<ArgumentQueue, Context, Tag> biFunction =
                (result, result2) -> create(textDecoration, ((ArgumentQueue) result), result2);
        Stream<TagResolver> stream2 =
                Stream.of(
                        SerializableResolver.claimingStyle(
                                values,
                                biFunction,
                                claim(
                                        textDecoration,
                                        (result2, result3) ->
                                                emit(
                                                        text4,
                                                        text2 == null ? text4 : text2,
                                                        result2,
                                                        result3))));
        Stream<String> stream3 = values.stream();
        return new SimpleImmutableEntry<>(
                textDecoration,
                Stream.concat(
                        stream2,
                        stream3.map(
                                result ->
                                        TagResolver.resolver(
                                                "!" + result, createNegated(textDecoration)))));
    }

    private DecorationTag() {}

    static Tag create(TextDecoration textDecoration, ArgumentQueue argumentQueue, Context context) {
        boolean matches = !argumentQueue.hasNext() || !argumentQueue.pop().isFalse();
        return Tag.styling(textDecoration.withState(matches));
    }

    static Tag createNegated(TextDecoration textDecoration) {
        return Tag.styling(textDecoration.withState(false));
    }

    @NotNull
    static StyleClaim<TextDecoration.State> claim(
            @NotNull TextDecoration textDecoration,
            @NotNull BiConsumer<TextDecoration.State, TokenEmitter> biConsumer) {
        Objects.requireNonNull(textDecoration, "decoration");
        String text = "decoration_" + TextDecoration.NAMES.key(textDecoration);
        return StyleClaim.claim(
                text,
                result -> result.decoration(textDecoration),
                result2 -> result2 != TextDecoration.State.NOT_SET,
                biConsumer);
    }

    static void emit(
            @NotNull String text,
            @NotNull String text2,
            TextDecoration.@NotNull State state,
            @NotNull TokenEmitter tokenEmitter) {
        if (state == TextDecoration.State.FALSE) {
            tokenEmitter.tag("!" + text);
        } else {
            tokenEmitter.tag(text);
        }
    }
}
