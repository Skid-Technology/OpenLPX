package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugGameEventListenerInfo {

    private final int listenerRadius;

    public DebugGameEventListenerInfo(int listenerRadius) {
        this.listenerRadius = listenerRadius;
    }

    public static DebugGameEventListenerInfo read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        return new DebugGameEventListenerInfo(value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, DebugGameEventListenerInfo debugGameEventListenerInfo) {
        packetWrapper.writeVarInt(debugGameEventListenerInfo.listenerRadius);
    }

    public int getListenerRadius() {
        return this.listenerRadius;
    }
}
