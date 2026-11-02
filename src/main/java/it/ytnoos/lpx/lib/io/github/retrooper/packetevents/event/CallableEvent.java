package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface CallableEvent {

    default void call(PacketListenerCommon packetListenerCommon) {
        packetListenerCommon.onPacketEventExternal((PacketEvent) this);
    }
}
