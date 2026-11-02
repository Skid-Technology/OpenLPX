package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Emitable {

    void emit(@NotNull TokenEmitter tokenEmitter);

    @Nullable
    default Component substitute() {
        return null;
    }
}
