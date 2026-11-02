package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityMetadataProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WrapperPlayServerSpawnLivingEntity
        extends PacketWrapper<WrapperPlayServerSpawnLivingEntity> {

    private int entityID;

    private UUID entityUUID;

    private EntityType entityType;

    private Vector3d position;

    private float yaw;

    private float pitch;

    private float headPitch;

    private Vector3d velocity;

    private List<EntityData<?>> entityMetadata;

    public WrapperPlayServerSpawnLivingEntity(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnLivingEntity(
            int entityID,
            UUID entityUUID,
            EntityType entityType2,
            Vector3d vector3d,
            float yaw,
            float pitch,
            float headPitch,
            Vector3d vector3d2,
            List<EntityData<?>> entityMetadata) {
        super(PacketType.Play.Server.SPAWN_LIVING_ENTITY);
        this.entityID = entityID;
        this.entityUUID = entityUUID;
        this.entityType = entityType2;
        this.position = vector3d;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headPitch = headPitch;
        this.velocity = vector3d2;
        this.entityMetadata = entityMetadata;
    }

    public WrapperPlayServerSpawnLivingEntity(
            int value,
            UUID uUID,
            EntityType entityType,
            Vector3d vector3d,
            float value2,
            float value3,
            float value4,
            Vector3d vector3d2,
            EntityMetadataProvider entityMetadataProvider) {
        this(
                value,
                uUID,
                entityType,
                vector3d,
                value2,
                value3,
                value4,
                vector3d2,
                entityMetadataProvider.entityData(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion()));
    }

    public WrapperPlayServerSpawnLivingEntity(
            int value,
            UUID uUID,
            EntityType entityType,
            Location location,
            float value2,
            Vector3d vector3d,
            List<EntityData<?>> values) {
        this(
                value,
                uUID,
                entityType,
                location.getPosition(),
                location.getYaw(),
                location.getPitch(),
                value2,
                vector3d,
                values);
    }

    public WrapperPlayServerSpawnLivingEntity(
            int value,
            UUID uUID,
            EntityType entityType,
            Location location,
            float value2,
            Vector3d vector3d,
            EntityMetadataProvider entityMetadataProvider) {
        this(
                value,
                uUID,
                entityType,
                location.getPosition(),
                location.getYaw(),
                location.getPitch(),
                value2,
                vector3d,
                entityMetadataProvider.entityData(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion()));
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_9)) {
            this.entityUUID = new UUID(0L, 0L);
            int value = this.readByte() & 255;
            this.entityType = EntityTypes.getById(this.serverVersion.toClientVersion(), value);
            this.position =
                    new Vector3d(
                            this.readInt() / 32.0, this.readInt() / 32.0, this.readInt() / 32.0);
        } else {
            this.entityUUID = this.readUUID();
            int value2;
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)) {
                value2 = this.readVarInt();
            } else {
                value2 = this.readUnsignedByte();
            }
            this.entityType = EntityTypes.getById(this.serverVersion.toClientVersion(), value2);
            this.position = new Vector3d(this.readDouble(), this.readDouble(), this.readDouble());
        }
        this.yaw = this.readByte() / 0.7111111F;
        this.pitch = this.readByte() / 0.7111111F;
        this.headPitch = this.readByte() / 0.7111111F;
        double value3 = this.readShort() / 8000.0;
        double value4 = this.readShort() / 8000.0;
        double value5 = this.readShort() / 8000.0;
        this.velocity = new Vector3d(value3, value4, value5);
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_15)) {
            this.entityMetadata = this.readEntityMetadata();
        } else {
            this.entityMetadata = new ArrayList<>();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_9)) {
            this.writeByte(this.entityType.getId(this.serverVersion.toClientVersion()) & 0xFF);
            this.writeInt(MathUtil.floor(this.position.x * 32.0));
            this.writeInt(MathUtil.floor(this.position.y * 32.0));
            this.writeInt(MathUtil.floor(this.position.z * 32.0));
        } else {
            this.writeUUID(this.entityUUID);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)) {
                this.writeVarInt(this.entityType.getId(this.serverVersion.toClientVersion()));
            } else {
                this.writeByte(this.entityType.getId(this.serverVersion.toClientVersion()) & 0xFF);
            }
            this.writeDouble(this.position.x);
            this.writeDouble(this.position.y);
            this.writeDouble(this.position.z);
        }
        this.writeByte((int) (this.yaw * 0.7111111F));
        this.writeByte((int) (this.pitch * 0.7111111F));
        this.writeByte((int) (this.headPitch * 0.7111111F));
        this.writeShort((int) (this.velocity.x * 8000.0));
        this.writeShort((int) (this.velocity.y * 8000.0));
        this.writeShort((int) (this.velocity.z * 8000.0));
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_15)) {
            this.writeEntityMetadata(this.entityMetadata);
        }
    }

    public void copy(WrapperPlayServerSpawnLivingEntity wrapperPlayServerSpawnLivingEntity) {
        this.entityID = wrapperPlayServerSpawnLivingEntity.entityID;
        this.entityUUID = wrapperPlayServerSpawnLivingEntity.entityUUID;
        this.entityType = wrapperPlayServerSpawnLivingEntity.entityType;
        this.position = wrapperPlayServerSpawnLivingEntity.position;
        this.yaw = wrapperPlayServerSpawnLivingEntity.yaw;
        this.pitch = wrapperPlayServerSpawnLivingEntity.pitch;
        this.headPitch = wrapperPlayServerSpawnLivingEntity.headPitch;
        this.velocity = wrapperPlayServerSpawnLivingEntity.velocity;
        this.entityMetadata = wrapperPlayServerSpawnLivingEntity.entityMetadata;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public UUID getEntityUUID() {
        return this.entityUUID;
    }

    public void setEntityUUID(UUID entityUUID) {
        this.entityUUID = entityUUID;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public void setEntityType(EntityType entityType2) {
        this.entityType = entityType2;
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getHeadPitch() {
        return this.headPitch;
    }

    public void setHeadPitch(float headPitch) {
        this.headPitch = headPitch;
    }

    public Vector3d getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector3d vector3d) {
        this.velocity = vector3d;
    }

    public List<EntityData<?>> getEntityMetadata() {
        return this.entityMetadata;
    }

    public void setEntityMetadata(List<EntityData<?>> entityMetadata) {
        this.entityMetadata = entityMetadata;
    }

    public void setEntityMetadata(EntityMetadataProvider entityMetadataProvider) {
        this.entityMetadata =
                entityMetadataProvider.entityData(this.serverVersion.toClientVersion());
    }
}
