package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface ListTagSetter<R, T extends BinaryTag> {

    @NotNull
    R add(T t);

    @NotNull
    R add(Iterable<? extends T> iterable);
}
