package it.ytnoos.lpx.lib.kyori.adventure.pointer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

final class PointersImpl implements Pointers {

    static final Pointers EMPTY =
            new Pointers() {

                @NotNull
                @Override
                public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
                    return Optional.empty();
                }

                @Override
                public <T> boolean supports(@NotNull Pointer<T> pointer) {
                    return false;
                }

                public Pointers.@NotNull Builder toBuilder() {
                    return new PointersImpl.BuilderImpl();
                }

                @Override
                public String toString() {
                    return "EmptyPointers";
                }
            };

    private final Map<Pointer<?>, Supplier<?>> pointers;

    PointersImpl(@NotNull PointersImpl.BuilderImpl builderImpl) {
        this.pointers = new HashMap<>(builderImpl.pointers);
    }

    @NotNull
    @Override
    public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        Objects.requireNonNull(pointer, "pointer");
        Supplier<?> supplier;
        return (supplier = this.pointers.get(pointer)) == null
                ? Optional.empty()
                : Optional.ofNullable((T) supplier.get());
    }

    @Override
    public <T> boolean supports(@NotNull Pointer<T> pointer) {
        Objects.requireNonNull(pointer, "pointer");
        return this.pointers.containsKey(pointer);
    }

    public Pointers.@NotNull Builder toBuilder() {
        return new PointersImpl.BuilderImpl(this);
    }

    static final class BuilderImpl implements Pointers.Builder {

        private final Map<Pointer<?>, Supplier<?>> pointers;

        BuilderImpl() {
            this.pointers = new HashMap<>();
        }

        BuilderImpl(@NotNull PointersImpl pointersImpl) {
            this.pointers = new HashMap<>(pointersImpl.pointers);
        }

        @NotNull
        @Override
        public <T> Pointers.Builder withDynamic(
                @NotNull Pointer<T> pointer, @NotNull Supplier<@Nullable T> supplier) {
            this.pointers.put(
                    Objects.requireNonNull(pointer, "pointer"),
                    Objects.requireNonNull(supplier, "value"));
            return this;
        }

        @NotNull
        public Pointers build() {
            return new PointersImpl(this);
        }
    }
}
