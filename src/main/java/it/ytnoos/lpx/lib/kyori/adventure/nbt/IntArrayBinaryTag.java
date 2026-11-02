package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

import java.util.PrimitiveIterator.OfInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@NonExtendable
public interface IntArrayBinaryTag extends ArrayBinaryTag, Iterable<Integer> {

    @NotNull
    static IntArrayBinaryTag intArrayBinaryTag(int @NotNull ... value) {
        return new IntArrayBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static IntArrayBinaryTag of(int @NotNull ... value) {
        return new IntArrayBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<IntArrayBinaryTag> type() {
        return BinaryTagTypes.INT_ARRAY;
    }

    int @NotNull [] value();

    int size();

    int get(int value);

    @NotNull
    OfInt iterator();

    @NotNull
    java.util.Spliterator.OfInt spliterator();

    @NotNull
    IntStream stream();

    void forEachInt(@NotNull IntConsumer intConsumer);
}
