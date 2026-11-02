package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface StringBinaryTag extends BinaryTag {

    @NotNull
    static StringBinaryTag stringBinaryTag(@NotNull String text) {
        return new StringBinaryTagImpl(text);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static StringBinaryTag of(@NotNull String text) {
        return new StringBinaryTagImpl(text);
    }

    @NotNull
    @Override
    default BinaryTagType<StringBinaryTag> type() {
        return BinaryTagTypes.STRING;
    }

    @NotNull
    String value();
}
