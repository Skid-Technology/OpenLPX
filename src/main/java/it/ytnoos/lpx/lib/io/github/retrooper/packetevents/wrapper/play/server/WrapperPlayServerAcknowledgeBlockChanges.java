package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerAcknowledgeBlockChanges
        extends PacketWrapper<WrapperPlayServerAcknowledgeBlockChanges> {

    private int sequence;

    public WrapperPlayServerAcknowledgeBlockChanges(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerAcknowledgeBlockChanges(int sequence) {
        super(PacketType.Play.Server.ACKNOWLEDGE_BLOCK_CHANGES);
        this.sequence = sequence;
    }

    @Override
    public void read() {
        this.sequence = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.sequence);
    }

    public void copy(
            WrapperPlayServerAcknowledgeBlockChanges wrapperPlayServerAcknowledgeBlockChanges) {
        this.sequence = wrapperPlayServerAcknowledgeBlockChanges.sequence;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
