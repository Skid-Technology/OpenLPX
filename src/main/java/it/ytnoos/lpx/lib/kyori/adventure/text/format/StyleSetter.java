package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.util.ARGBLike;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NonExtendable
public interface StyleSetter<T extends StyleSetter<?>> {

    @NotNull
    T font(@Nullable Key key);

    @NotNull
    T color(@Nullable TextColor textColor);

    @NotNull
    T colorIfAbsent(@Nullable TextColor textColor);

    @NotNull
    T shadowColor(@Nullable ARGBLike aRGBLike);

    @NotNull
    T shadowColorIfAbsent(@Nullable ARGBLike aRGBLike);

    @NotNull
    default T decorate(@NotNull TextDecoration textDecoration) {
        return this.decoration(textDecoration, TextDecoration.State.TRUE);
    }

    @NotNull
    default T decorate(@NotNull TextDecoration... textDecoration) {
        EnumMap enumMap = new EnumMap<>(TextDecoration.class);
        int value = 0;
        for (int value2 = textDecoration.length; value < value2; value++) {
            enumMap.put(textDecoration[value], TextDecoration.State.TRUE);
        }
        return ((T) this.decorations(enumMap));
    }

    @NotNull
    default T decoration(@NotNull TextDecoration textDecoration, boolean matches) {
        return this.decoration(textDecoration, TextDecoration.State.byBoolean(matches));
    }

    @NotNull
    T decoration(@NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

    @NotNull
    T decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

    @NotNull
    T decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries);

    @NotNull
    default T decorations(@NotNull Set<TextDecoration> values, boolean matches) {
        Stream<it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration> stream2 =
                values.stream();
        Function<TextDecoration, TextDecoration> function = Function.identity();
        return this.decorations(
                stream2.collect(
                        Collectors.toMap(
                                function, result -> TextDecoration.State.byBoolean(matches))));
    }

    @NotNull
    T clickEvent(@Nullable ClickEvent clickEvent2);

    @NotNull
    T hoverEvent(@Nullable HoverEventSource<?> hoverEventSource);

    @NotNull
    T insertion(@Nullable String text);
}
