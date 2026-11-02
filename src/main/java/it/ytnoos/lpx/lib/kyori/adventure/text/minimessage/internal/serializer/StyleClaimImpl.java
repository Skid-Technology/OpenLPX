package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

class StyleClaimImpl<V> implements StyleClaim<V> {

    private final String claimKey;

    private final Function<Style, V> lens;

    private final Predicate<V> filter;

    private final BiConsumer<V, TokenEmitter> emitable;

    StyleClaimImpl(
            String claimKey,
            Function<Style, @Nullable V> function,
            Predicate<V> predicate,
            BiConsumer<V, TokenEmitter> biConsumer) {
        this.claimKey = claimKey;
        this.lens = function;
        this.filter = predicate;
        this.emitable = biConsumer;
    }

    @NotNull
    @Override
    public String claimKey() {
        return this.claimKey;
    }

    @Nullable
    @Override
    public Emitable apply(@NotNull Style style) {
        V v;
        if ((v = this.lens.apply(style)) != null && this.filter.test((V) v)) {
            Object result2 = v;
            return result -> this.emitable.accept((V) result2, result);
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.claimKey);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof StyleClaimImpl)) {
            return false;
        }
        StyleClaimImpl styleClaimImpl = (StyleClaimImpl) result;
        return Objects.equals(this.claimKey, styleClaimImpl.claimKey);
    }
}
