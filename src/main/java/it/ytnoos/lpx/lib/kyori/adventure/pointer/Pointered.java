package it.ytnoos.lpx.lib.kyori.adventure.pointer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Optional;
import java.util.function.Supplier;

public interface Pointered {

    @NotNull
    default <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        return this.pointers().get(pointer);
    }

    @Contract("_, null -> _; _, !null -> !null")
    @Nullable
    default <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T t) {
        return this.pointers().getOrDefault(pointer, (T) t);
    }

    @UnknownNullability
    default <T> T getOrDefaultFrom(
            @NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> supplier) {
        return this.pointers().getOrDefaultFrom(pointer, supplier);
    }

    @NotNull
    default Pointers pointers() {
        return Pointers.empty();
    }
}
