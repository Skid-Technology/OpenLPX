package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface EndBinaryTag extends BinaryTag {

    @NotNull
    static EndBinaryTag endBinaryTag() {
        return EndBinaryTagImpl.INSTANCE;
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static EndBinaryTag get() {
        return EndBinaryTagImpl.INSTANCE;
    }

    @NotNull
    @Override
    default BinaryTagType<EndBinaryTag> type() {
        return BinaryTagTypes.END;
    }
}
