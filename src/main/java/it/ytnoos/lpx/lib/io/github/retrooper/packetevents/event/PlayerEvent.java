package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

@NullMarked
public interface PlayerEvent {

    @NullUnmarked
    @UnknownNullability
    <T> T getPlayer();
}
