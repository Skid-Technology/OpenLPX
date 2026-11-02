package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator.OfLong;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Renderer(
        text = "\"long[\" + this.value.length + \"]\"",
        childrenArray = "this.value",
        hasChildren = "this.value.length > 0")
final class LongArrayBinaryTagImpl extends ArrayBinaryTagImpl implements LongArrayBinaryTag {

    final long[] value;

    LongArrayBinaryTagImpl(long[] values) {
        this.value = Arrays.copyOf(values, values.length);
    }

    @Override
    public long @NotNull [] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public long get(int value2) {
        checkIndex(value2, this.value.length);
        return this.value[value2];
    }

    @Override
    @NotNull
    public OfLong iterator() {
        return new OfLong() {

            private int index;

            @Override
            public boolean hasNext() {
                return this.index < LongArrayBinaryTagImpl.this.value.length - 1;
            }

            @Override
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    return LongArrayBinaryTagImpl.this.value[this.index++];
                }
            }
        };
    }

    @Override
    @NotNull
    public java.util.Spliterator.OfLong spliterator() {
        return ((java.util.Spliterator.OfLong) Arrays.spliterator(this.value));
    }

    @NotNull
    @Override
    public LongStream stream() {
        return Arrays.stream(this.value);
    }

    @Override
    public void forEachLong(@NotNull LongConsumer longConsumer) {
        int value2 = 0;
        for (int value3 = this.value.length; value2 < value3; value2++) {
            longConsumer.accept(this.value[value2]);
        }
    }

    static long[] value(LongArrayBinaryTag longArrayBinaryTag) {
        return longArrayBinaryTag instanceof LongArrayBinaryTagImpl
                ? ((LongArrayBinaryTagImpl) longArrayBinaryTag).value
                : longArrayBinaryTag.value();
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            LongArrayBinaryTagImpl longArrayBinaryTagImpl = (LongArrayBinaryTagImpl) result;
            return Arrays.equals(this.value, longArrayBinaryTagImpl.value);
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
}
