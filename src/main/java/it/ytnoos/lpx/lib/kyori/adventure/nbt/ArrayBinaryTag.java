package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface ArrayBinaryTag extends BinaryTag {

    @NotNull
    @Override
    BinaryTagType<? extends ArrayBinaryTag> type();
}
