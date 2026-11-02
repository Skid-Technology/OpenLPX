package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
public class WrapperPlayServerSpawnPosition extends PacketWrapper<WrapperPlayServerSpawnPosition> {

    private ResourceLocation dimension;

    private Vector3i position;

    private float yaw;

    private float pitch;

    public WrapperPlayServerSpawnPosition(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnPosition(Vector3i vector3i) {
        this(vector3i, 0.0F);
    }

    public WrapperPlayServerSpawnPosition(Vector3i vector3i, float value) {
        this(WorldBlockPosition.OVERWORLD_DIMENSION, vector3i, value, 0.0F);
    }

    public WrapperPlayServerSpawnPosition(
            ResourceLocation resourceLocation, Vector3i vector3i, float yaw, float pitch) {
        super(PacketType.Play.Server.SPAWN_POSITION);
        this.dimension = resourceLocation;
        this.position = vector3i;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.dimension = ResourceLocation.read(this);
        }
        this.position =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)
                        ? this.readBlockPosition()
                        : new Vector3i(this.readInt(), this.readInt(), this.readInt());
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.yaw = this.readFloat();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.pitch = this.readFloat();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            ResourceLocation.write(this, this.dimension);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeBlockPosition(this.position);
        } else {
            this.writeInt(this.position.x);
            this.writeInt(this.position.y);
            this.writeInt(this.position.z);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.writeFloat(this.yaw);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.writeFloat(this.pitch);
        }
    }

    public void copy(WrapperPlayServerSpawnPosition wrapperPlayServerSpawnPosition) {
        this.dimension = wrapperPlayServerSpawnPosition.dimension;
        this.position = wrapperPlayServerSpawnPosition.position;
        this.yaw = wrapperPlayServerSpawnPosition.yaw;
        this.pitch = wrapperPlayServerSpawnPosition.pitch;
    }

    public ResourceLocation getDimension() {
        return this.dimension;
    }

    public void setDimension(ResourceLocation resourceLocation) {
        this.dimension = resourceLocation;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    @Deprecated
    public Optional<Float> getAngle() {
        return Optional.ofNullable(this.yaw);
    }

    @Deprecated
    public void setAngle(float yaw) {
        this.yaw = yaw;
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
}
