package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUpdateViewPosition
        extends PacketWrapper<WrapperPlayServerUpdateViewPosition> {

    private int chunkX;

    private int chunkZ;

    public WrapperPlayServerUpdateViewPosition(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateViewPosition(int chunkX, int chunkZ) {
        super(PacketType.Play.Server.UPDATE_VIEW_POSITION);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public void read() {
        this.chunkX = this.readVarInt();
        this.chunkZ = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.chunkX);
        this.writeVarInt(this.chunkZ);
    }

    public void copy(WrapperPlayServerUpdateViewPosition wrapperPlayServerUpdateViewPosition) {
        this.chunkX = wrapperPlayServerUpdateViewPosition.chunkX;
        this.chunkZ = wrapperPlayServerUpdateViewPosition.chunkZ;
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
