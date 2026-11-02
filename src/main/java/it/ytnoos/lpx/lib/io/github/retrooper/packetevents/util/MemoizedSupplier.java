package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

@NullMarked
@Internal
public final class MemoizedSupplier<T> implements Supplier<T> {

    @Nullable private Supplier<T> delegate;

    @MonotonicNonNull private volatile T value;

    public MemoizedSupplier(Supplier<T> supplier) {
        this.delegate = supplier;
    }

    @Override
    public T get() {
        T t = this.value;
        if (this.value == null) {
            synchronized (this) {
                t = this.value;
                if (this.value == null) {
                    assert this.delegate != null;
                    this.value = (T) (t = this.delegate.get());
                    this.delegate = null;
                }
            }
        }
        return (T) t;
    }
}
