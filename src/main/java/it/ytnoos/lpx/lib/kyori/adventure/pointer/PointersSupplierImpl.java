package it.ytnoos.lpx.lib.kyori.adventure.pointer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

final class PointersSupplierImpl<T> implements PointersSupplier<T> {

    @Nullable private final PointersSupplier<? super T> parent;

    private final Map<Pointer<?>, Function<T, ?>> resolvers;

    PointersSupplierImpl(@NotNull PointersSupplierImpl.BuilderImpl<T> builderImpl) {
        this.parent = builderImpl.parent;
        this.resolvers = new HashMap<>(builderImpl.resolvers);
    }

    @NotNull
    @Override
    public Pointers view(@NotNull T t) {
        return new PointersSupplierImpl.ForwardingPointers<>(t, this);
    }

    @Override
    public <P> boolean supports(@NotNull Pointer<P> pointer) {
        if (this.resolvers.containsKey(Objects.requireNonNull(pointer, "pointer"))) {
            return true;
        } else {
            return this.parent == null ? false : this.parent.supports(pointer);
        }
    }

    @Nullable
    @Override
    public <P> Function<? super T, P> resolver(@NotNull Pointer<P> pointer) {
        Function<? super T, P> function;
        if ((function =
                        (Function<? super T, P>)
                                (Function<?, ?>)
                                        this.resolvers.get(
                                                Objects.requireNonNull(pointer, "pointer")))
                != null) {
            return function;
        } else {
            return this.parent == null ? null : this.parent.resolver(pointer);
        }
    }

    static final class BuilderImpl<T> implements PointersSupplier.Builder<T> {

        @Nullable private PointersSupplier<? super T> parent = null;

        private final Map<Pointer<?>, Function<T, ?>> resolvers = new HashMap<>();

        @NotNull
        @Override
        public PointersSupplier.Builder<T> parent(
                @Nullable PointersSupplier<? super T> pointersSupplier) {
            this.parent = pointersSupplier;
            return this;
        }

        @NotNull
        @Override
        public <P> PointersSupplier.Builder<T> resolving(
                @NotNull Pointer<P> pointer, @NotNull Function<T, P> function) {
            this.resolvers.put(pointer, function);
            return this;
        }

        @NotNull
        public PointersSupplier<T> build() {
            return new PointersSupplierImpl<>(this);
        }
    }

    static final class ForwardingPointers<U> implements Pointers {

        private final U instance;

        private final PointersSupplierImpl<U> supplier;

        ForwardingPointers(@NotNull U u, @NotNull PointersSupplierImpl<U> pointersSupplierImpl) {
            this.instance = (U) u;
            this.supplier = pointersSupplierImpl;
        }

        @NotNull
        @Override
        public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
            Function function;
            PointersSupplier pointersSupplier;
            if ((function = this.supplier.resolvers.get(Objects.requireNonNull(pointer, "pointer")))
                            == null
                    && (pointersSupplier = this.supplier.parent) != null) {
                function = pointersSupplier.resolver(pointer);
            }
            return function == null
                    ? Optional.empty()
                    : Optional.ofNullable((T) function.apply(this.instance));
        }

        @Override
        public <T> boolean supports(@NotNull Pointer<T> pointer) {
            return this.supplier.supports(pointer);
        }

        public Pointers.@NotNull Builder toBuilder() {
            Pointers.Builder builder2 =
                    this.supplier.parent == null
                            ? Pointers.builder()
                            : this.supplier.parent.view(this.instance).toBuilder();
            for (Entry<it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointer<?>, Function<U, ?>> entry :
                    this.supplier.resolvers.entrySet()) {
                Pointer pointer2 = (Pointer) entry.getKey();
                builder2.withDynamic(
                        pointer2,
                        () -> (Object) ((Function) entry.getValue()).apply(this.instance));
            }
            return builder2;
        }
    }
}
