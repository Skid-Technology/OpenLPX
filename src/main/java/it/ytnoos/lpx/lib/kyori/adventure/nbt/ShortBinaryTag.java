package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface ShortBinaryTag extends NumberBinaryTag {

    @NotNull
    static ShortBinaryTag shortBinaryTag(short value) {
        return new ShortBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static ShortBinaryTag of(short value) {
        return new ShortBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<ShortBinaryTag> type() {
        return BinaryTagTypes.SHORT;
    }

    short value();
}
