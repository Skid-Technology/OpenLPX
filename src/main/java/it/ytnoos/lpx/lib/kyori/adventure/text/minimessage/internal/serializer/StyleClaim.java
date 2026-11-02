package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface StyleClaim<V> {

    @NotNull
    static <T> StyleClaim<T> claim(
            @NotNull String text,
            @NotNull Function<Style, @Nullable T> function,
            @NotNull BiConsumer<T, TokenEmitter> biConsumer) {
        return claim(text, function, result -> true, biConsumer);
    }

    @NotNull
    static <T> StyleClaim<T> claim(
            @NotNull String text,
            @NotNull Function<Style, @Nullable T> function,
            @NotNull Predicate<T> predicate,
            @NotNull BiConsumer<T, TokenEmitter> biConsumer) {
        return new StyleClaimImpl(
                Objects.requireNonNull(text, "claimKey"),
                Objects.requireNonNull(function, "lens"),
                Objects.requireNonNull(predicate, "filter"),
                Objects.requireNonNull(biConsumer, "emitable"));
    }

    @NotNull
    String claimKey();

    @Nullable
    Emitable apply(@NotNull Style style);
}
