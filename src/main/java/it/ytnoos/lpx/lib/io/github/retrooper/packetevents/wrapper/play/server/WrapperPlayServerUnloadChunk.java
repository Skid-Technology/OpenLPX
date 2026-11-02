package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUnloadChunk extends PacketWrapper<WrapperPlayServerUnloadChunk> {

    private int chunkX;

    private int chunkZ;

    public WrapperPlayServerUnloadChunk(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUnloadChunk(int chunkX, int chunkZ) {
        super(PacketType.Play.Server.UNLOAD_CHUNK);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            long value = this.readLong();
            this.chunkX = getChunkX(value);
            this.chunkZ = getChunkZ(value);
        } else {
            this.chunkX = this.readInt();
            this.chunkZ = this.readInt();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            this.writeLong(getChunkKey(this.chunkX, this.chunkZ));
        } else {
            this.writeInt(this.chunkX);
            this.writeInt(this.chunkZ);
        }
    }

    public void copy(WrapperPlayServerUnloadChunk wrapperPlayServerUnloadChunk) {
        this.chunkX = wrapperPlayServerUnloadChunk.chunkX;
        this.chunkZ = wrapperPlayServerUnloadChunk.chunkZ;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }
}
