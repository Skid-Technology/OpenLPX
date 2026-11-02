package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.value.ValueType;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

final class OptionImpl<V> implements Option<V> {

    private final String id;

    private final ValueType<V> type;

    @Nullable private final V defaultValue;

    OptionImpl(String id, ValueType<V> valueType, @Nullable V v) {
        this.id = id;
        this.type = valueType;
        this.defaultValue = (V) v;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public ValueType<V> valueType() {
        return this.type;
    }

    @Override
    @Nullable
    public V defaultValue() {
        return this.defaultValue;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            OptionImpl optionImpl = (OptionImpl) result;
            return Objects.equals(this.id, optionImpl.id)
                    && Objects.equals(this.type, optionImpl.type);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.type);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "{id="
                + this.id
                + ",type="
                + this.type
                + ",defaultValue="
                + this.defaultValue
                + 125;
    }
}
