package it.ytnoos.lpx.lib.kyori.adventure.pointer;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Optional;
import java.util.function.Supplier;

@NonExtendable
public interface Pointers extends Buildable<Pointers, Pointers.Builder> {

    @Contract(pure = true)
    @NotNull
    static Pointers empty() {
        return PointersImpl.EMPTY;
    }

    @Contract(pure = true)
    @NotNull
    static Pointers.Builder builder() {
        return new PointersImpl.BuilderImpl();
    }

    @NotNull
    <T> Optional<T> get(@NotNull Pointer<T> pointer);

    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
        return this.<T>get(pointer).orElse((T) t);
    }

    @UnknownNullability
    default <T> T getOrDefaultFrom(
            @NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
        return this.<T>get(pointer).orElseGet(supplier);
    }

    <T> boolean supports(@NotNull Pointer<T> pointer);

    @NonExtendable
    interface Builder extends AbstractBuilder<Pointers>, Buildable.Builder<Pointers> {

        @Contract("_, _ -> this")
        @NotNull
        default <T> Pointers.Builder withStatic(@NotNull Pointer<T> pointer, @Nullable T t) {
            return this.withDynamic(pointer, () -> (T) ((Object) t));
        }

        @Contract("_, _ -> this")
        @NotNull
        <T> Pointers.Builder withDynamic(
                @NotNull Pointer<T> pointer, @NotNull Supplier<@Nullable T> supplier);
    }
}
