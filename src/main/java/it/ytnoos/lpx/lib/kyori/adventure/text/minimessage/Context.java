package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NonExtendable
public interface Context {

    @Nullable
    Pointered target();

    @NotNull
    Pointered targetOrThrow();

    @NotNull
    <T extends Pointered> T targetAsType(@NotNull Class<T> classValue);

    @NotNull
    Component deserialize(@NotNull String text);

    @NotNull
    Component deserialize(@NotNull String text, @NotNull TagResolver tagResolver);

    @NotNull
    Component deserialize(@NotNull String text, @NotNull TagResolver... tagResolver);

    @NotNull
    ParsingException newException(@NotNull String text, @NotNull ArgumentQueue argumentQueue);

    @NotNull
    ParsingException newException(@NotNull String text);

    @NotNull
    ParsingException newException(
            @NotNull String text,
            @Nullable Throwable throwable,
            @NotNull ArgumentQueue argumentQueue);

    boolean emitVirtuals();
}
