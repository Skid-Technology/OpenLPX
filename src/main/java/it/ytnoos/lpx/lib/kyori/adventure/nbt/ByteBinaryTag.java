package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface ByteBinaryTag extends NumberBinaryTag {

    ByteBinaryTag ZERO = new ByteBinaryTagImpl((byte) 0);

    ByteBinaryTag ONE = new ByteBinaryTagImpl((byte) 1);

    @NotNull
    static ByteBinaryTag byteBinaryTag(byte value) {
        if (value == 0) {
            return ZERO;
        } else {
            return value == 1 ? ONE : new ByteBinaryTagImpl(value);
        }
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static ByteBinaryTag of(byte value) {
        return byteBinaryTag(value);
    }

    @NotNull
    @Override
    default BinaryTagType<ByteBinaryTag> type() {
        return BinaryTagTypes.BYTE;
    }

    byte value();
}
