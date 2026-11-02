package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@Renderer(text = "String.valueOf(this.value) + \"s\"", hasChildren = "false")
final class ShortBinaryTagImpl extends AbstractBinaryTag implements ShortBinaryTag {

    private final short value;

    ShortBinaryTagImpl(short value) {
        this.value = value;
    }

    @Override
    public short value() {
        return this.value;
    }

    @Override
    public byte byteValue() {
        return (byte) (this.value & 255);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public short shortValue() {
        return this.value;
    }

    @NotNull
    @Override
    public Number numberValue() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ShortBinaryTagImpl shortBinaryTagImpl = (ShortBinaryTagImpl) result;
            return this.value == shortBinaryTagImpl.value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Short.hashCode(this.value);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
