package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerChunkBatchEnd extends PacketWrapper<WrapperPlayServerChunkBatchEnd> {

    private int batchSize;

    public WrapperPlayServerChunkBatchEnd(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChunkBatchEnd(int batchSize) {
        super(PacketType.Play.Server.CHUNK_BATCH_END);
        this.batchSize = batchSize;
    }

    @Override
    public void read() {
        this.batchSize = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.batchSize);
    }

    public void copy(WrapperPlayServerChunkBatchEnd wrapperPlayServerChunkBatchEnd) {
        this.batchSize = wrapperPlayServerChunkBatchEnd.batchSize;
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
