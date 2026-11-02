package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class StandardTags {

    private static final TagResolver ALL =
            TagResolver.builder()
                    .resolvers(
                            HoverTag.RESOLVER,
                            ClickTag.RESOLVER,
                            ColorTagResolver.INSTANCE,
                            KeybindTag.RESOLVER,
                            TranslatableTag.RESOLVER,
                            TranslatableFallbackTag.RESOLVER,
                            InsertionTag.RESOLVER,
                            FontTag.RESOLVER,
                            DecorationTag.RESOLVER,
                            GradientTag.RESOLVER,
                            RainbowTag.RESOLVER,
                            ResetTag.RESOLVER,
                            NewlineTag.RESOLVER,
                            TransitionTag.RESOLVER,
                            SelectorTag.RESOLVER,
                            ScoreTag.RESOLVER,
                            NbtTag.RESOLVER,
                            PrideTag.RESOLVER,
                            ShadowColorTag.RESOLVER,
                            SpriteTag.RESOLVER,
                            SequentialHeadTag.RESOLVER)
                    .build();

    private StandardTags() {}

    @NotNull
    public static TagResolver decorations(@NotNull TextDecoration textDecoration) {
        return Objects.requireNonNull(
                DecorationTag.RESOLVERS.get(textDecoration),
                "No resolver found for decoration (this should not be possible?)");
    }

    @NotNull
    public static TagResolver decorations() {
        return DecorationTag.RESOLVER;
    }

    @NotNull
    public static TagResolver color() {
        return ColorTagResolver.INSTANCE;
    }

    @NotNull
    public static TagResolver hoverEvent() {
        return HoverTag.RESOLVER;
    }

    @NotNull
    public static TagResolver clickEvent() {
        return ClickTag.RESOLVER;
    }

    @NotNull
    public static TagResolver keybind() {
        return KeybindTag.RESOLVER;
    }

    @NotNull
    public static TagResolver sequentialHead() {
        return SequentialHeadTag.RESOLVER;
    }

    @NotNull
    public static TagResolver translatable() {
        return TranslatableTag.RESOLVER;
    }

    @NotNull
    public static TagResolver translatableFallback() {
        return TranslatableFallbackTag.RESOLVER;
    }

    @NotNull
    public static TagResolver insertion() {
        return InsertionTag.RESOLVER;
    }

    @NotNull
    public static TagResolver font() {
        return FontTag.RESOLVER;
    }

    @NotNull
    public static TagResolver gradient() {
        return GradientTag.RESOLVER;
    }

    @NotNull
    public static TagResolver rainbow() {
        return RainbowTag.RESOLVER;
    }

    public static TagResolver transition() {
        return TransitionTag.RESOLVER;
    }

    @NotNull
    public static TagResolver reset() {
        return ResetTag.RESOLVER;
    }

    @NotNull
    public static TagResolver newline() {
        return NewlineTag.RESOLVER;
    }

    @NotNull
    public static TagResolver selector() {
        return SelectorTag.RESOLVER;
    }

    @NotNull
    public static TagResolver score() {
        return ScoreTag.RESOLVER;
    }

    @NotNull
    public static TagResolver nbt() {
        return NbtTag.RESOLVER;
    }

    @NotNull
    public static TagResolver pride() {
        return PrideTag.RESOLVER;
    }

    @NotNull
    public static TagResolver shadowColor() {
        return ShadowColorTag.RESOLVER;
    }

    @NotNull
    public static TagResolver sprite() {
        return SpriteTag.RESOLVER;
    }

    @NotNull
    public static TagResolver defaults() {
        return ALL;
    }

    static Set<String> names(String... text) {
        return new HashSet<>(Arrays.asList(text));
    }
}
