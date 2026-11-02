package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public class WrapperPlayServerFacePlayer extends PacketWrapper<WrapperPlayServerFacePlayer> {

    private WrapperPlayServerFacePlayer.EntitySection aimUnit;

    private Vector3d targetPosition;

    @Nullable private WrapperPlayServerFacePlayer.TargetEntity targetEntity;

    public WrapperPlayServerFacePlayer(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerFacePlayer(
            WrapperPlayServerFacePlayer.EntitySection entitySection,
            Vector3d vector3d,
            @Nullable WrapperPlayServerFacePlayer.TargetEntity targetEntity2) {
        super(PacketType.Play.Server.FACE_PLAYER);
        this.aimUnit = entitySection;
        this.targetPosition = vector3d;
        this.targetEntity = targetEntity2;
    }

    @Override
    public void read() {
        this.aimUnit = WrapperPlayServerFacePlayer.EntitySection.getById(this.readVarInt());
        this.targetPosition = new Vector3d(this.readDouble(), this.readDouble(), this.readDouble());
        this.targetEntity =
                this.readOptional(
                        result ->
                                new WrapperPlayServerFacePlayer.TargetEntity(
                                        result.readVarInt(),
                                        WrapperPlayServerFacePlayer.EntitySection.getById(
                                                result.readVarInt())));
    }

    @Override
    public void write() {
        this.writeVarInt(this.aimUnit.getId());
        this.writeDouble(this.targetPosition.getX());
        this.writeDouble(this.targetPosition.getY());
        this.writeDouble(this.targetPosition.getZ());
        this.writeOptional(
                this.targetEntity,
                (result, result2) -> {
                    result.writeVarInt(result2.getEntityId());
                    result.writeVarInt(result2.getEntitySection().getId());
                });
    }

    public void copy(WrapperPlayServerFacePlayer wrapperPlayServerFacePlayer) {
        this.aimUnit = wrapperPlayServerFacePlayer.aimUnit;
        this.targetPosition = wrapperPlayServerFacePlayer.targetPosition;
        this.targetEntity = wrapperPlayServerFacePlayer.targetEntity;
    }

    public WrapperPlayServerFacePlayer.EntitySection getAimUnit() {
        return this.aimUnit;
    }

    public void setAimUnit(WrapperPlayServerFacePlayer.EntitySection entitySection) {
        this.aimUnit = entitySection;
    }

    public Vector3d getTargetPosition() {
        return this.targetPosition;
    }

    public void setTargetPosition(Vector3d vector3d) {
        this.targetPosition = vector3d;
    }

    @Nullable
    public WrapperPlayServerFacePlayer.TargetEntity getTargetEntity() {
        return this.targetEntity;
    }

    public void setTargetEntity(@Nullable WrapperPlayServerFacePlayer.TargetEntity targetEntity2) {
        this.targetEntity = targetEntity2;
    }

    public enum EntitySection {
        EYES,
        FEET;

        public int getId() {
            return this.ordinal();
        }

        public static WrapperPlayServerFacePlayer.EntitySection getById(int value) {
            return values()[value];
        }
    }

    public static class TargetEntity {

        private int entityId;

        private WrapperPlayServerFacePlayer.EntitySection entitySection;

        public TargetEntity(
                int entityId, WrapperPlayServerFacePlayer.EntitySection entitySection2) {
            this.entityId = entityId;
            this.entitySection = entitySection2;
        }

        public int getEntityId() {
            return this.entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public WrapperPlayServerFacePlayer.EntitySection getEntitySection() {
            return this.entitySection;
        }

        public void setEntitySection(WrapperPlayServerFacePlayer.EntitySection entitySection2) {
            this.entitySection = entitySection2;
        }
    }
}
