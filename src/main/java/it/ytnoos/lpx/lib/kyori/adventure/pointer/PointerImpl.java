package it.ytnoos.lpx.lib.kyori.adventure.pointer;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class PointerImpl<T> implements Pointer<T> {

    private final Class<T> type;

    private final Key key;

    PointerImpl(Class<T> classValue, Key key2) {
        this.type = classValue;
        this.key = key2;
    }

    @NotNull
    @Override
    public Class<T> type() {
        return this.type;
    }

    @NotNull
    @Override
    public Key key() {
        return this.key;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            PointerImpl pointerImpl = (PointerImpl) result;
            return this.type.equals(pointerImpl.type) && this.key.equals(pointerImpl.key);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = this.type.hashCode();
        return 31 * value + this.key.hashCode();
    }
}
