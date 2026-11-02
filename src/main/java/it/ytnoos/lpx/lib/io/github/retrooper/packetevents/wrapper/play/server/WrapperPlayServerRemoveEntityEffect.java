package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerRemoveEntityEffect
        extends PacketWrapper<WrapperPlayServerRemoveEntityEffect> {

    private int entityId;

    private PotionType potionType;

    public WrapperPlayServerRemoveEntityEffect(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerRemoveEntityEffect(int entityId, PotionType potionType2) {
        super(PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
        this.entityId = entityId;
        this.potionType = potionType2;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        int value;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18_2)) {
            value = this.readVarInt();
        } else {
            value = this.readByte();
        }
        this.potionType = PotionTypes.getById(value, this.serverVersion);
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18_2)) {
            this.writeVarInt(this.potionType.getId(this.serverVersion.toClientVersion()));
        } else {
            this.writeByte(this.potionType.getId(this.serverVersion.toClientVersion()));
        }
    }

    public void copy(WrapperPlayServerRemoveEntityEffect wrapperPlayServerRemoveEntityEffect) {
        this.entityId = wrapperPlayServerRemoveEntityEffect.entityId;
        this.potionType = wrapperPlayServerRemoveEntityEffect.potionType;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public PotionType getPotionType() {
        return this.potionType;
    }

    public void setPotionType(PotionType potionType2) {
        this.potionType = potionType2;
    }
}
