package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ChunkWaypointInfo implements WaypointInfo {

    private final int chunkX;

    private final int chunkZ;

    public ChunkWaypointInfo(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Internal
    public static ChunkWaypointInfo read(PacketWrapper<?> packetWrapper) {
        return new ChunkWaypointInfo(packetWrapper.readVarInt(), packetWrapper.readVarInt());
    }

    @Internal
    public static void write(PacketWrapper<?> packetWrapper, WaypointInfo waypointInfo) {
        ChunkWaypointInfo chunkWaypointInfo = (ChunkWaypointInfo) waypointInfo;
        packetWrapper.writeVarInt(chunkWaypointInfo.chunkX);
        packetWrapper.writeVarInt(chunkWaypointInfo.chunkZ);
    }

    @Override
    public WaypointInfo.Type getType() {
        return WaypointInfo.Type.CHUNK;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }
}
