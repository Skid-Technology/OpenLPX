package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

@NonExtendable
public interface VirtualComponent extends TextComponent {

    @NotNull
    Class<?> contextType();

    @NotNull
    VirtualComponentRenderer<?> renderer();
}
