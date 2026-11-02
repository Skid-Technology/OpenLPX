package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientPickItemFromEntity
        extends PacketWrapper<WrapperPlayClientPickItemFromEntity> {

    private int entityId;

    private boolean includeData;

    public WrapperPlayClientPickItemFromEntity(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPickItemFromEntity(int entityId, boolean includeData) {
        super(PacketType.Play.Client.PICK_ITEM_FROM_ENTITY);
        this.entityId = entityId;
        this.includeData = includeData;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.includeData = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeBoolean(this.includeData);
    }

    public void copy(WrapperPlayClientPickItemFromEntity wrapperPlayClientPickItemFromEntity) {
        this.entityId = wrapperPlayClientPickItemFromEntity.entityId;
        this.includeData = wrapperPlayClientPickItemFromEntity.includeData;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public boolean isIncludeData() {
        return this.includeData;
    }

    public void setIncludeData(boolean includeData) {
        this.includeData = includeData;
    }
}
