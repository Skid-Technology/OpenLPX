package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public interface VirtualComponentRenderer<C> {

    @UnknownNullability
    ComponentLike apply(@NotNull C c);

    @NotNull
    default String fallbackString() {
        return "";
    }
}
