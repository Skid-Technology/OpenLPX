package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.LpVector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class WrapperPlayServerSpawnEntity extends PacketWrapper<WrapperPlayServerSpawnEntity> {

    private int entityID;

    private Optional<UUID> uuid;

    private EntityType entityType;

    private Vector3d position;

    private float pitch;

    private float yaw;

    private float headYaw;

    private int data;

    private Optional<Vector3d> velocity;

    public WrapperPlayServerSpawnEntity(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnEntity(
            int entityID,
            Optional<UUID> optional,
            EntityType entityType2,
            Vector3d vector3d,
            float pitch,
            float yaw,
            float headYaw,
            int data,
            Optional<Vector3d> optional2) {
        super(PacketType.Play.Server.SPAWN_ENTITY);
        this.entityID = entityID;
        this.uuid = optional;
        this.entityType = entityType2;
        this.position = vector3d;
        this.pitch = pitch;
        this.yaw = yaw;
        this.headYaw = headYaw;
        this.data = data;
        this.velocity = optional2;
    }

    public WrapperPlayServerSpawnEntity(
            int entityID,
            @Nullable UUID uUID,
            EntityType entityType2,
            Location location,
            float headYaw,
            int data,
            @Nullable Vector3d vector3d) {
        super(PacketType.Play.Server.SPAWN_ENTITY);
        this.entityID = entityID;
        this.uuid = Optional.ofNullable(uUID);
        this.entityType = entityType2;
        this.position = location.getPosition();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
        this.headYaw = headYaw;
        this.data = data;
        this.velocity = Optional.ofNullable(vector3d);
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9);
        this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19);
        if (newerThanOrEquals) {
            this.uuid = Optional.of(this.readUUID());
        } else {
            this.uuid = Optional.empty();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.entityType =
                    EntityTypes.getById(this.serverVersion.toClientVersion(), this.readVarInt());
        } else {
            byte value = this.readByte();
            this.entityType =
                    EntityTypes.getByLegacyId(this.serverVersion.toClientVersion(), value);
            if (this.entityType == null) {
                this.entityType = EntityTypes.getById(this.serverVersion.toClientVersion(), value);
            }
        }
        double value2;
        double value3;
        double value4;
        if (newerThanOrEquals) {
            value4 = this.readDouble();
            value2 = this.readDouble();
            value3 = this.readDouble();
        } else {
            value4 = this.readInt() / 32.0;
            value2 = this.readInt() / 32.0;
            value3 = this.readInt() / 32.0;
        }
        this.position = new Vector3d(value4, value2, value3);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.velocity = Optional.of(LpVector3d.read(this));
        }
        this.pitch = this.readByte() / 0.7111111F;
        this.yaw = this.readByte() / 0.7111111F;
        if (newerThanOrEquals2) {
            this.headYaw = this.readByte() / 0.7111111F;
            this.data = this.readVarInt();
        } else {
            this.data = this.readInt();
        }
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_21_9)) {
            if (!newerThanOrEquals && this.data <= 0) {
                this.velocity = Optional.empty();
            } else {
                double value5 = this.readShort() / 8000.0;
                double value6 = this.readShort() / 8000.0;
                double value7 = this.readShort() / 8000.0;
                this.velocity = Optional.of(new Vector3d(value5, value6, value7));
            }
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9);
        this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_15);
        boolean newerThanOrEquals2 = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19);
        if (newerThanOrEquals) {
            this.writeUUID(this.uuid.orElse(new UUID(0L, 0L)));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.writeVarInt(this.entityType.getId(this.serverVersion.toClientVersion()));
        } else if (this.entityType.getLegacyId(this.serverVersion.toClientVersion()) != -1) {
            this.writeByte(this.entityType.getLegacyId(this.serverVersion.toClientVersion()));
        } else {
            this.writeByte(this.entityType.getId(this.serverVersion.toClientVersion()));
        }
        if (newerThanOrEquals) {
            this.writeDouble(this.position.x);
            this.writeDouble(this.position.y);
            this.writeDouble(this.position.z);
        } else {
            this.writeInt(MathUtil.floor(this.position.x * 32.0));
            this.writeInt(MathUtil.floor(this.position.y * 32.0));
            this.writeInt(MathUtil.floor(this.position.z * 32.0));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            LpVector3d.write(this, this.velocity.orElse(Vector3d.zero()));
        }
        this.writeByte(MathUtil.floor(this.pitch * 0.7111111F));
        this.writeByte(MathUtil.floor(this.yaw * 0.7111111F));
        if (newerThanOrEquals2) {
            this.writeByte(MathUtil.floor(this.headYaw * 0.7111111F));
            this.writeVarInt(this.data);
        } else {
            this.writeInt(this.data);
        }
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_21_9)
                && (newerThanOrEquals || this.data > 0)) {
            Vector3d vector3d;
            int value =
                    (int)
                            ((vector3d = this.velocity.orElse(new Vector3d(-1.0, -1.0, -1.0))).x
                                    * 8000.0);
            int value2 = (int) (vector3d.y * 8000.0);
            int value3 = (int) (vector3d.z * 8000.0);
            this.writeShort(value);
            this.writeShort(value2);
            this.writeShort(value3);
        }
    }

    public void copy(WrapperPlayServerSpawnEntity wrapperPlayServerSpawnEntity) {
        this.entityID = wrapperPlayServerSpawnEntity.entityID;
        this.uuid = wrapperPlayServerSpawnEntity.uuid;
        this.entityType = wrapperPlayServerSpawnEntity.entityType;
        this.position = wrapperPlayServerSpawnEntity.position;
        this.pitch = wrapperPlayServerSpawnEntity.pitch;
        this.yaw = wrapperPlayServerSpawnEntity.yaw;
        this.headYaw = wrapperPlayServerSpawnEntity.headYaw;
        this.data = wrapperPlayServerSpawnEntity.data;
        this.velocity = wrapperPlayServerSpawnEntity.velocity;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public Optional<UUID> getUUID() {
        return this.uuid;
    }

    public void setUUID(Optional<UUID> optional) {
        this.uuid = optional;
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

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Optional<Vector3d> getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Optional<Vector3d> optional) {
        this.velocity = optional;
    }
}
