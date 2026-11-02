package it.ytnoos.lpx.lib.kyori.adventure.key;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;
import it.ytnoos.lpx.lib.kyori.examination.string.StringExaminer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

final class KeyedValueImpl<T> implements Examinable, KeyedValue<T> {

    private final Key key;

    private final T value;

    KeyedValueImpl(Key key2, T t) {
        this.key = key2;
        this.value = (T) t;
    }

    @NotNull
    @Override
    public Key key() {
        return this.key;
    }

    @NotNull
    @Override
    public T value() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            KeyedValueImpl keyedValueImpl = (KeyedValueImpl) result;
            return this.key.equals(keyedValueImpl.key) && this.value.equals(keyedValueImpl.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value2 = this.key.hashCode();
        return 31 * value2 + this.value.hashCode();
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("key", this.key), ExaminableProperty.of("value", this.value));
    }

    @Override
    public String toString() {
        return this.examine(StringExaminer.simpleEscaping());
    }
}
