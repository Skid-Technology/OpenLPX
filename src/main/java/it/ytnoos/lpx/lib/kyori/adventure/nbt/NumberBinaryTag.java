package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface NumberBinaryTag extends BinaryTag {

    @NotNull
    @Override
    BinaryTagType<? extends NumberBinaryTag> type();

    byte byteValue();

    double doubleValue();

    float floatValue();

    int intValue();

    long longValue();

    short shortValue();

    @NotNull
    Number numberValue();
}
