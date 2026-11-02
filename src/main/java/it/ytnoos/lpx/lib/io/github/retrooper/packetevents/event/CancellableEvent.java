package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

public interface CancellableEvent {

    boolean isCancelled();

    void setCancelled(boolean matches);
}
