package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public class WrapperPlayServerDamageEvent extends PacketWrapper<WrapperPlayServerDamageEvent> {

    private int entityId;

    private DamageType sourceType;

    private int sourceCauseId;

    private int sourceDirectId;

    @Nullable private Vector3d sourcePosition;

    public WrapperPlayServerDamageEvent(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDamageEvent(
            int entityId,
            DamageType damageType,
            int sourceCauseId,
            int sourceDirectId,
            @Nullable Vector3d vector3d) {
        super(PacketType.Play.Server.DAMAGE_EVENT);
        this.entityId = entityId;
        this.sourceType = damageType;
        this.sourceCauseId = sourceCauseId;
        this.sourceDirectId = sourceDirectId;
        this.sourcePosition = vector3d;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.sourceType = this.readMappedEntity(DamageTypes.getRegistry());
        this.sourceCauseId = this.readVarInt();
        this.sourceDirectId = this.readVarInt();
        this.sourcePosition = this.readOptional(Vector3d::read);
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeMappedEntity(this.sourceType);
        this.writeVarInt(this.sourceCauseId);
        this.writeVarInt(this.sourceDirectId);
        this.writeOptional(this.sourcePosition, Vector3d::write);
    }

    public void copy(WrapperPlayServerDamageEvent wrapperPlayServerDamageEvent) {
        this.entityId = wrapperPlayServerDamageEvent.entityId;
        this.sourceType = wrapperPlayServerDamageEvent.sourceType;
        this.sourceCauseId = wrapperPlayServerDamageEvent.sourceCauseId;
        this.sourceDirectId = wrapperPlayServerDamageEvent.sourceDirectId;
        this.sourcePosition = wrapperPlayServerDamageEvent.sourcePosition;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public DamageType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(DamageType damageType) {
        this.sourceType = damageType;
    }

    public int getSourceCauseId() {
        return this.sourceCauseId;
    }

    public void setSourceCauseId(int sourceCauseId) {
        this.sourceCauseId = sourceCauseId;
    }

    public int getSourceDirectId() {
        return this.sourceDirectId;
    }

    public void setSourceDirectId(int sourceDirectId) {
        this.sourceDirectId = sourceDirectId;
    }

    @Nullable
    public Vector3d getSourcePosition() {
        return this.sourcePosition;
    }

    public void setSourcePosition(@Nullable Vector3d vector3d) {
        this.sourcePosition = vector3d;
    }
}
