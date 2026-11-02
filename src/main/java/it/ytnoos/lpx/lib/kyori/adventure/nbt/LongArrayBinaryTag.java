package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

import java.util.PrimitiveIterator.OfLong;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

@NonExtendable
public interface LongArrayBinaryTag extends ArrayBinaryTag, Iterable<Long> {

    @NotNull
    static LongArrayBinaryTag longArrayBinaryTag(long @NotNull ... value) {
        return new LongArrayBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static LongArrayBinaryTag of(long @NotNull ... value) {
        return new LongArrayBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<LongArrayBinaryTag> type() {
        return BinaryTagTypes.LONG_ARRAY;
    }

    long @NotNull [] value();

    int size();

    long get(int value);

    @NotNull
    OfLong iterator();

    @NotNull
    java.util.Spliterator.OfLong spliterator();

    @NotNull
    LongStream stream();

    void forEachLong(@NotNull LongConsumer longConsumer);
}
