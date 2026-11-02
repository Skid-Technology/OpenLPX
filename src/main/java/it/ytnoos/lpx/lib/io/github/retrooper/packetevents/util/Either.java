package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public class Either<L, R> {

    @Nullable private final L left;

    @Nullable private final R right;

    private Either(@Nullable L l, @Nullable R r) {
        this.left = (L) l;
        this.right = (R) r;
    }

    public static <L, R> Either<L, R> createLeft(L l) {
        return new Either<>((L) l, null);
    }

    public static <L, R> Either<L, R> createRight(R r) {
        return new Either<>(null, (R) r);
    }

    public static <T> T unwrap(Either<T, ? extends T> either) {
        return (T) (either.left != null ? either.left : either.right);
    }

    public Object get() {
        return this.left != null ? this.left : this.right;
    }

    public <T> T map(Function<L, T> function, Function<R, T> function2) {
        return (T) (this.left != null ? function.apply(this.left) : function2.apply(this.right));
    }

    public boolean isLeft() {
        return this.left != null;
    }

    @Nullable
    public L getLeft() {
        return this.left;
    }

    public boolean isRight() {
        return this.right != null;
    }

    @Nullable
    public R getRight() {
        return this.right;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof Either)) {
            return false;
        }
        Either either = (Either) result;
        return !Objects.equals(this.left, either.left)
                ? false
                : Objects.equals(this.right, either.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.left, this.right);
    }
}
