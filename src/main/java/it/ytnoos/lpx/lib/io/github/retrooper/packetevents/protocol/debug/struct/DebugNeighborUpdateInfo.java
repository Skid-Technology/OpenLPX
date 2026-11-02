package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugNeighborUpdateInfo {

    private final Vector3i pos;

    public DebugNeighborUpdateInfo(Vector3i vector3i) {
        this.pos = vector3i;
    }

    public static DebugNeighborUpdateInfo read(PacketWrapper<?> packetWrapper) {
        Vector3i vector3i = packetWrapper.readBlockPosition();
        return new DebugNeighborUpdateInfo(vector3i);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, DebugNeighborUpdateInfo debugNeighborUpdateInfo) {
        packetWrapper.writeBlockPosition(debugNeighborUpdateInfo.pos);
    }

    public Vector3i getPos() {
        return this.pos;
    }
}
