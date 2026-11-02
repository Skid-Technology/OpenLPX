package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public final class EntityPositionData {

    private Vector3d position;

    private Vector3d deltaMovement;

    private float yaw;

    private float pitch;

    public EntityPositionData(Vector3d vector3d, Vector3d vector3d2, float yaw, float pitch) {
        this.position = vector3d;
        this.deltaMovement = vector3d2;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static EntityPositionData read(PacketWrapper<?> packetWrapper) {
        Vector3d vector3d = Vector3d.read(packetWrapper);
        Vector3d vector3d2 = Vector3d.read(packetWrapper);
        float value = packetWrapper.readFloat();
        float value2 = packetWrapper.readFloat();
        return new EntityPositionData(vector3d, vector3d2, value, value2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, EntityPositionData entityPositionData) {
        Vector3d.write(packetWrapper, entityPositionData.position);
        Vector3d.write(packetWrapper, entityPositionData.deltaMovement);
        packetWrapper.writeFloat(entityPositionData.yaw);
        packetWrapper.writeFloat(entityPositionData.pitch);
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
    }

    public Vector3d getDeltaMovement() {
        return this.deltaMovement;
    }

    public void setDeltaMovement(Vector3d vector3d) {
        this.deltaMovement = vector3d;
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

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof EntityPositionData)) {
            return false;
        } else {
            EntityPositionData entityPositionData;
            if (Float.compare((entityPositionData = (EntityPositionData) result).yaw, this.yaw)
                    != 0) {
                return false;
            } else if (Float.compare(entityPositionData.pitch, this.pitch) != 0) {
                return false;
            } else {
                return !this.position.equals(entityPositionData.position)
                        ? false
                        : this.deltaMovement.equals(entityPositionData.deltaMovement);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.deltaMovement, this.yaw, this.pitch);
    }

    @Override
    public String toString() {
        return "EntityPositionData{position="
                + this.position
                + ", deltaMovement="
                + this.deltaMovement
                + ", yaw="
                + this.yaw
                + ", pitch="
                + this.pitch
                + 125;
    }
}
