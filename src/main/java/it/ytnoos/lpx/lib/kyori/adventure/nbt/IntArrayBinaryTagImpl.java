package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator.OfInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Renderer(
        text = "\"int[\" + this.value.length + \"]\"",
        childrenArray = "this.value",
        hasChildren = "this.value.length > 0")
final class IntArrayBinaryTagImpl extends ArrayBinaryTagImpl implements IntArrayBinaryTag {

    final int[] value;

    IntArrayBinaryTagImpl(int... value2) {
        this.value = Arrays.copyOf(value2, value2.length);
    }

    @Override
    public int @NotNull [] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public int get(int value2) {
        checkIndex(value2, this.value.length);
        return this.value[value2];
    }

    @Override
    @NotNull
    public OfInt iterator() {
        return new OfInt() {

            private int index;

            @Override
            public boolean hasNext() {
                return this.index < IntArrayBinaryTagImpl.this.value.length - 1;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    return IntArrayBinaryTagImpl.this.value[this.index++];
                }
            }
        };
    }

    @Override
    @NotNull
    public java.util.Spliterator.OfInt spliterator() {
        return ((java.util.Spliterator.OfInt) Arrays.spliterator(this.value));
    }

    @NotNull
    @Override
    public IntStream stream() {
        return Arrays.stream(this.value);
    }

    @Override
    public void forEachInt(@NotNull IntConsumer intConsumer) {
        int value2 = 0;
        for (int value3 = this.value.length; value2 < value3; value2++) {
            intConsumer.accept(this.value[value2]);
        }
    }

    static int[] value(IntArrayBinaryTag intArrayBinaryTag) {
        return intArrayBinaryTag instanceof IntArrayBinaryTagImpl
                ? ((IntArrayBinaryTagImpl) intArrayBinaryTag).value
                : intArrayBinaryTag.value();
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            IntArrayBinaryTagImpl intArrayBinaryTagImpl = (IntArrayBinaryTagImpl) result;
            return Arrays.equals(this.value, intArrayBinaryTagImpl.value);
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
