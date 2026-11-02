package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityMetadataProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class WrapperPlayServerSpawnPlayer extends PacketWrapper<WrapperPlayServerSpawnPlayer> {

    private int entityID;

    private UUID uuid;

    private Vector3d position;

    private float yaw;

    private float pitch;

    @Deprecated private ItemType item;

    private List<EntityData<?>> entityMetadata;

    public WrapperPlayServerSpawnPlayer(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnPlayer(
            int value,
            UUID uUID,
            Location location,
            EntityMetadataProvider entityMetadataProvider) {
        this(
                value,
                uUID,
                location.getPosition(),
                location.getYaw(),
                location.getPitch(),
                entityMetadataProvider.entityData(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion()));
    }

    public WrapperPlayServerSpawnPlayer(
            int value, UUID uUID, Location location, List<EntityData<?>> values) {
        this(value, uUID, location.getPosition(), location.getYaw(), location.getPitch(), values);
    }

    public WrapperPlayServerSpawnPlayer(
            int value, UUID uUID, Location location, EntityData<?>... entityData) {
        this(
                value,
                uUID,
                location.getPosition(),
                location.getYaw(),
                location.getPitch(),
                Arrays.asList(entityData));
    }

    public WrapperPlayServerSpawnPlayer(
            int entityID,
            UUID uuid,
            Vector3d vector3d,
            float yaw,
            float pitch,
            List<EntityData<?>> entityMetadata) {
        super(PacketType.Play.Server.SPAWN_PLAYER);
        this.entityID = entityID;
        this.uuid = uuid;
        this.position = vector3d;
        this.yaw = yaw;
        this.pitch = pitch;
        this.entityMetadata = entityMetadata;
        this.item = ItemTypes.AIR;
    }

    public WrapperPlayServerSpawnPlayer(
            int value,
            UUID uUID,
            Vector3d vector3d,
            float value2,
            float value3,
            EntityMetadataProvider entityMetadataProvider) {
        this(
                value,
                uUID,
                vector3d,
                value2,
                value3,
                entityMetadataProvider.entityData(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion()));
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        this.uuid = this.readUUID();
        boolean matches;
        if (matches = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.position = new Vector3d(this.readDouble(), this.readDouble(), this.readDouble());
        } else {
            this.position =
                    new Vector3d(
                            this.readInt() / 32.0, this.readInt() / 32.0, this.readInt() / 32.0);
        }
        this.yaw = this.readByte() / 0.7111111F;
        this.pitch = this.readByte() / 0.7111111F;
        if (!matches) {
            this.item = ItemTypes.getById(this.serverVersion.toClientVersion(), this.readShort());
        } else {
            this.item = ItemTypes.AIR;
        }
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_15)) {
            this.entityMetadata = this.readEntityMetadata();
        } else {
            this.entityMetadata = new ArrayList<>();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        this.writeUUID(this.uuid);
        boolean matches;
        if (matches = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeDouble(this.position.getX());
            this.writeDouble(this.position.getY());
            this.writeDouble(this.position.getZ());
        } else {
            this.writeInt(MathUtil.floor(this.position.getX() * 32.0));
            this.writeInt(MathUtil.floor(this.position.getY() * 32.0));
            this.writeInt(MathUtil.floor(this.position.getZ() * 32.0));
        }
        this.writeByte((byte) (this.yaw * 0.7111111F));
        this.writeByte((byte) (this.pitch * 0.7111111F));
        if (!matches) {
            this.writeShort(this.item.getId(this.serverVersion.toClientVersion()));
        }
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_15)) {
            this.writeEntityMetadata(this.entityMetadata);
        }
    }

    public void copy(WrapperPlayServerSpawnPlayer wrapperPlayServerSpawnPlayer) {
        this.entityID = wrapperPlayServerSpawnPlayer.entityID;
        this.uuid = wrapperPlayServerSpawnPlayer.uuid;
        this.position = wrapperPlayServerSpawnPlayer.position;
        this.yaw = wrapperPlayServerSpawnPlayer.yaw;
        this.pitch = wrapperPlayServerSpawnPlayer.pitch;
        this.item = wrapperPlayServerSpawnPlayer.item;
        this.entityMetadata = wrapperPlayServerSpawnPlayer.entityMetadata;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
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

    @Deprecated
    public List<EntityData<?>> getEntityMetadata() {
        return this.entityMetadata;
    }

    @Deprecated
    public void setEntityMetadata(List<EntityData<?>> entityMetadata) {
        this.entityMetadata = entityMetadata;
    }

    @Deprecated
    public void setEntityMetadata(EntityMetadataProvider entityMetadataProvider) {
        this.entityMetadata =
                entityMetadataProvider.entityData(this.serverVersion.toClientVersion());
    }

    @Deprecated
    public ItemType getItem() {
        return this.item;
    }

    @Deprecated
    public void setItem(ItemType itemType) {
        this.item = itemType;
    }
}
