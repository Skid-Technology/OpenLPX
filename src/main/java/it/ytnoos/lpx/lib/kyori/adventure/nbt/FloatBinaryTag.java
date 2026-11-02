package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface FloatBinaryTag extends NumberBinaryTag {

    @NotNull
    static FloatBinaryTag floatBinaryTag(float value) {
        return new FloatBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static FloatBinaryTag of(float value) {
        return new FloatBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<FloatBinaryTag> type() {
        return BinaryTagTypes.FLOAT;
    }

    float value();
}
