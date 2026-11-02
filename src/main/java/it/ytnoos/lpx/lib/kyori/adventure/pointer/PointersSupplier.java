package it.ytnoos.lpx.lib.kyori.adventure.pointer;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@NonExtendable
public interface PointersSupplier<T> {

    @NotNull
    static <T> PointersSupplier.Builder<T> builder() {
        return new PointersSupplierImpl.BuilderImpl<>();
    }

    @NotNull
    Pointers view(@NotNull T t);

    <P> boolean supports(@NotNull Pointer<P> pointer);

    @Nullable
    <P> Function<? super T, P> resolver(@NotNull Pointer<P> pointer);

    @NonExtendable
    interface Builder<T> extends AbstractBuilder<PointersSupplier<T>> {

        @Contract("_ -> this")
        @NotNull
        PointersSupplier.Builder<T> parent(@Nullable PointersSupplier<? super T> pointersSupplier);

        @Contract("_, _ -> this")
        @NotNull
        <P> PointersSupplier.Builder<T> resolving(
                @NotNull Pointer<P> pointer, @NotNull Function<T, P> function);
    }
}
