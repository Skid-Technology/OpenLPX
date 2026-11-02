package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface DoubleBinaryTag extends NumberBinaryTag {

    @NotNull
    static DoubleBinaryTag doubleBinaryTag(double value) {
        return new DoubleBinaryTagImpl(value);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static DoubleBinaryTag of(double value) {
        return new DoubleBinaryTagImpl(value);
    }

    @NotNull
    @Override
    default BinaryTagType<DoubleBinaryTag> type() {
        return BinaryTagTypes.DOUBLE;
    }

    double value();
}
