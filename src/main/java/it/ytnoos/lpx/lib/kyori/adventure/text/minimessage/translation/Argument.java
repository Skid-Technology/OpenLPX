package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgumentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.TagPattern;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Argument {

    private Argument() {}

    @NotNull
    public static ComponentLike bool(@TagPattern @NotNull String text, boolean matches) {
        return argument(text, TranslationArgument.bool(matches));
    }

    @NotNull
    public static ComponentLike numeric(@TagPattern @NotNull String text, @NotNull Number number) {
        return argument(text, TranslationArgument.numeric(number));
    }

    @Deprecated
    @NotNull
    public static ComponentLike numeric(@TagPattern @NotNull String text, @NotNull String text2) {
        return string(text, text2);
    }

    @NotNull
    public static ComponentLike string(@TagPattern @NotNull String text2, @NotNull String text3) {
        return argument(text2, TranslationArgument.component(Component.text(text3)));
    }

    @NotNull
    public static ComponentLike component(
            @TagPattern @NotNull String text, @NotNull ComponentLike componentLike) {
        return argument(text, TranslationArgument.component(componentLike));
    }

    @NotNull
    public static ComponentLike argument(
            @TagPattern @NotNull String text,
            @NotNull TranslationArgumentLike translationArgumentLike) {
        return argument(
                text,
                Objects.requireNonNull(translationArgumentLike, "argument")
                        .asTranslationArgument());
    }

    @NotNull
    public static ComponentLike argument(
            @TagPattern @NotNull String text, @NotNull TranslationArgument translationArgument) {
        return Component.virtual(
                Void.class,
                new MiniMessageTranslatorArgument<>(
                        text, Objects.requireNonNull(translationArgument, "argument")));
    }

    @NotNull
    public static ComponentLike tag(@TagPattern @NotNull String text, @NotNull Tag tag2) {
        return Component.virtual(
                Void.class,
                new MiniMessageTranslatorArgument<>(text, Objects.requireNonNull(tag2, "tag")));
    }

    @NotNull
    public static ComponentLike tagResolver(@NotNull TagResolver... tagResolver2) {
        return tagResolver(TagResolver.resolver(tagResolver2));
    }

    @NotNull
    public static ComponentLike tagResolver(@NotNull Iterable<TagResolver> iterable) {
        return tagResolver(TagResolver.resolver(iterable));
    }

    @NotNull
    public static ComponentLike tagResolver(@NotNull TagResolver tagResolver2) {
        return Component.virtual(
                Void.class,
                new MiniMessageTranslatorArgument<>(
                        "unused", Objects.requireNonNull(tagResolver2, "tagResolver")));
    }

    @NotNull
    public static ComponentLike target(@NotNull Pointered pointered) {
        return Component.virtual(
                Void.class,
                new MiniMessageTranslatorTarget(Objects.requireNonNull(pointered, "target")));
    }
}
