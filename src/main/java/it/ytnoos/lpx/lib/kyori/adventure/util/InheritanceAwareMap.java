package it.ytnoos.lpx.lib.kyori.adventure.util;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;

import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InheritanceAwareMap<C, V> {

    @NotNull
    static <K, E> InheritanceAwareMap<K, E> empty() {
        return InheritanceAwareMapImpl.EMPTY;
    }

    static <K, E> InheritanceAwareMap.@NotNull Builder<K, E> builder() {
        return new InheritanceAwareMapImpl.BuilderImpl();
    }

    static <K, E> InheritanceAwareMap.@NotNull Builder<K, E> builder(
            InheritanceAwareMap<? extends K, ? extends E> inheritanceAwareMap) {
        return new InheritanceAwareMapImpl.BuilderImpl().putAll(inheritanceAwareMap);
    }

    boolean containsKey(@NotNull Class<? extends C> classValue);

    @Nullable
    V get(@NotNull Class<? extends C> classValue);

    @CheckReturnValue
    @NotNull
    InheritanceAwareMap<C, V> with(@NotNull Class<? extends C> classValue, @NotNull V v);

    @CheckReturnValue
    @NotNull
    InheritanceAwareMap<C, V> without(@NotNull Class<? extends C> classValue);

    interface Builder<C, V> extends AbstractBuilder<InheritanceAwareMap<C, V>> {

        @NotNull
        InheritanceAwareMap.Builder<C, V> strict(boolean matches);

        @NotNull
        InheritanceAwareMap.Builder<C, V> put(@NotNull Class<? extends C> classValue, @NotNull V v);

        @NotNull
        InheritanceAwareMap.Builder<C, V> remove(@NotNull Class<? extends C> classValue);

        @NotNull
        InheritanceAwareMap.Builder<C, V> putAll(
                @NotNull InheritanceAwareMap<? extends C, ? extends V> inheritanceAwareMap);
    }
}
