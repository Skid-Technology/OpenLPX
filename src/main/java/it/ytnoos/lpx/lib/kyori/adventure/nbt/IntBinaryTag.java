package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface IntBinaryTag extends NumberBinaryTag {

    @NotNull
    static IntBinaryTag intBinaryTag(int value) {
        return new IntBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static IntBinaryTag of(int value) {
        return new IntBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<IntBinaryTag> type() {
        return BinaryTagTypes.INT;
    }

    int value();
}
