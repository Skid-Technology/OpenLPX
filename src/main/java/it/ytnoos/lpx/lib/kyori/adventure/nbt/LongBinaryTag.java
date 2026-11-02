package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface LongBinaryTag extends NumberBinaryTag {

    @NotNull
    static LongBinaryTag longBinaryTag(long value) {
        return new LongBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static LongBinaryTag of(long value) {
        return new LongBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<LongBinaryTag> type() {
        return BinaryTagTypes.LONG;
    }

    long value();
}
