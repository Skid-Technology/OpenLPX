package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface ByteArrayBinaryTag extends ArrayBinaryTag, Iterable<Byte> {

    @NotNull
    static ByteArrayBinaryTag byteArrayBinaryTag(byte @NotNull ... value) {
        return new ByteArrayBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static ByteArrayBinaryTag of(byte @NotNull ... value) {
        return new ByteArrayBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<ByteArrayBinaryTag> type() {
        return BinaryTagTypes.BYTE_ARRAY;
    }

    byte @NotNull [] value();

    int size();

    byte get(int value);
}
