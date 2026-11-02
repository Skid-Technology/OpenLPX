package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

@Renderer(
        text = "\"byte[\" + this.value.length + \"]\"",
        childrenArray = "this.value",
        hasChildren = "this.value.length > 0")
final class ByteArrayBinaryTagImpl extends ArrayBinaryTagImpl implements ByteArrayBinaryTag {

    final byte[] value;

    ByteArrayBinaryTagImpl(byte[] values) {
        this.value = Arrays.copyOf(values, values.length);
    }

    @Override
    public byte @NotNull [] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public byte get(int value2) {
        checkIndex(value2, this.value.length);
        return this.value[value2];
    }

    static byte[] value(ByteArrayBinaryTag byteArrayBinaryTag) {
        return byteArrayBinaryTag instanceof ByteArrayBinaryTagImpl
                ? ((ByteArrayBinaryTagImpl) byteArrayBinaryTag).value
                : byteArrayBinaryTag.value();
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ByteArrayBinaryTagImpl byteArrayBinaryTagImpl = (ByteArrayBinaryTagImpl) result;
            return Arrays.equals(this.value, byteArrayBinaryTagImpl.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {

            private int index;

            @Override
            public boolean hasNext() {
                return this.index < ByteArrayBinaryTagImpl.this.value.length - 1;
            }

            public Byte next() {
                return ByteArrayBinaryTagImpl.this.value[this.index++];
            }
        };
    }
}
