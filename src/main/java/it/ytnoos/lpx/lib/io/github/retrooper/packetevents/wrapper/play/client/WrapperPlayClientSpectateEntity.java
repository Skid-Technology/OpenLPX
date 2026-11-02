package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class WrapperPlayClientSpectateEntity
        extends PacketWrapper<WrapperPlayClientSpectateEntity> {

    @Nullable private Integer entityId;

    public WrapperPlayClientSpectateEntity(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSpectateEntity(int value) {
        this(Integer.valueOf(value));
    }

    public WrapperPlayClientSpectateEntity(@Nullable Integer entityId) {
        super(PacketType.Play.Client.SPECTATE_ENTITY);
        this.entityId = entityId;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_2)) {
            this.entityId = this.readNullableVarInt();
        } else {
            this.entityId = this.readVarInt();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_2)) {
            this.writeNullableVarInt(this.entityId);
        } else {
            this.writeVarInt(this.entityId != null ? this.entityId : 0);
        }
    }

    public void copy(WrapperPlayClientSpectateEntity wrapperPlayClientSpectateEntity) {
        this.entityId = wrapperPlayClientSpectateEntity.entityId;
    }

    public boolean hasEntityId() {
        return this.entityId != null;
    }

    public int getEntityId() {
        return this.entityId != null ? this.entityId : 0;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setEntityId(@Nullable Integer entityId) {
        this.entityId = entityId;
    }
}
