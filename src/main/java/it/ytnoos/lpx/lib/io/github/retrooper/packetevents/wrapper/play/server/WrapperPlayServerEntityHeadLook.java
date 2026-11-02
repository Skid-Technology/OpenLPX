package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEntityHeadLook
        extends PacketWrapper<WrapperPlayServerEntityHeadLook> {

    private int entityID;

    private float headYaw;

    public WrapperPlayServerEntityHeadLook(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityHeadLook(int entityID, float headYaw) {
        super(PacketType.Play.Server.ENTITY_HEAD_LOOK);
        this.entityID = entityID;
        this.headYaw = headYaw;
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        this.headYaw = this.readByte() / 0.7111111F;
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        this.writeByte((int) (this.headYaw * 0.7111111F));
    }

    public void copy(WrapperPlayServerEntityHeadLook wrapperPlayServerEntityHeadLook) {
        this.entityID = wrapperPlayServerEntityHeadLook.entityID;
        this.headYaw = wrapperPlayServerEntityHeadLook.headYaw;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }
}
