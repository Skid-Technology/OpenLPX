package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerChunkBatchBegin
        extends PacketWrapper<WrapperPlayServerChunkBatchBegin> {

    public WrapperPlayServerChunkBatchBegin(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChunkBatchBegin() {
        super(PacketType.Play.Server.CHUNK_BATCH_BEGIN);
    }
}
