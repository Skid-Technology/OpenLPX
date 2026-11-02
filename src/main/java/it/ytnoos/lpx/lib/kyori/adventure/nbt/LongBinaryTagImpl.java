package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@Renderer(text = "String.valueOf(this.value) + \"l\"", hasChildren = "false")
final class LongBinaryTagImpl extends AbstractBinaryTag implements LongBinaryTag {

    private final long value;

    LongBinaryTagImpl(long value) {
        this.value = value;
    }

    @Override
    public long value() {
        return this.value;
    }

    @Override
    public byte byteValue() {
        return (byte) (this.value & 255L);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return (float) this.value;
    }

    @Override
    public int intValue() {
        return (int) this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public short shortValue() {
        return (short) (this.value & 65535L);
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
            LongBinaryTagImpl longBinaryTagImpl = (LongBinaryTagImpl) result;
            return this.value == longBinaryTagImpl.value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
