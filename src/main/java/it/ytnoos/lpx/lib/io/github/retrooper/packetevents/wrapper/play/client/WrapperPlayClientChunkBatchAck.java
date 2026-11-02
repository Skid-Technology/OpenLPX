package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientChunkBatchAck extends PacketWrapper<WrapperPlayClientChunkBatchAck> {

    private float desiredChunksPerTick;

    public WrapperPlayClientChunkBatchAck(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChunkBatchAck(float desiredChunksPerTick) {
        super(PacketType.Play.Client.CHUNK_BATCH_ACK);
        this.desiredChunksPerTick = desiredChunksPerTick;
    }

    @Override
    public void read() {
        this.desiredChunksPerTick = this.readFloat();
    }

    @Override
    public void write() {
        this.writeFloat(this.desiredChunksPerTick);
    }

    public void copy(WrapperPlayClientChunkBatchAck wrapperPlayClientChunkBatchAck) {
        this.desiredChunksPerTick = wrapperPlayClientChunkBatchAck.desiredChunksPerTick;
    }

    public float getDesiredChunksPerTick() {
        return this.desiredChunksPerTick;
    }

    public void setDesiredChunksPerTick(float desiredChunksPerTick) {
        this.desiredChunksPerTick = desiredChunksPerTick;
    }
}
