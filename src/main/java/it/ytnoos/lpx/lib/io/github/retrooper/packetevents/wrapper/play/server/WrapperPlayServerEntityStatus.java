package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEntityStatus extends PacketWrapper<WrapperPlayServerEntityStatus> {

    private int entityID;

    private int status;

    public WrapperPlayServerEntityStatus(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityStatus(int entityID, int status) {
        super(PacketType.Play.Server.ENTITY_STATUS);
        this.entityID = entityID;
        this.status = status;
    }

    @Override
    public void read() {
        this.entityID = this.readInt();
        this.status = this.readByte();
    }

    @Override
    public void write() {
        this.writeInt(this.entityID);
        this.writeByte(this.status);
    }

    public void copy(WrapperPlayServerEntityStatus wrapperPlayServerEntityStatus) {
        this.entityID = wrapperPlayServerEntityStatus.entityID;
        this.status = wrapperPlayServerEntityStatus.status;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
