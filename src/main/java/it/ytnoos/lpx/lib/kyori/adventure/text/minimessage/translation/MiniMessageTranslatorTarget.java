package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponentRenderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

final class MiniMessageTranslatorTarget implements VirtualComponentRenderer<Void> {

    private final Pointered pointered;

    MiniMessageTranslatorTarget(@NotNull Pointered pointered2) {
        this.pointered = pointered2;
    }

    @NotNull
    Pointered pointered() {
        return this.pointered;
    }

    @UnknownNullability
    public ComponentLike apply(@NotNull Void voidValue) {
        return null;
    }
}
