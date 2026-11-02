package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEntityMovement
        extends PacketWrapper<WrapperPlayServerEntityMovement> {

    private int entityId;

    public WrapperPlayServerEntityMovement(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityMovement(int entityId) {
        super(PacketType.Play.Server.ENTITY_MOVEMENT);
        this.entityId = entityId;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
    }

    public void copy(WrapperPlayServerEntityMovement wrapperPlayServerEntityMovement) {
        this.entityId = wrapperPlayServerEntityMovement.entityId;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
