package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class WrapperPlayServerMoveMinecart extends PacketWrapper<WrapperPlayServerMoveMinecart> {

    private int entityId;

    private List<WrapperPlayServerMoveMinecart.MinecartStep> lerpSteps;

    public WrapperPlayServerMoveMinecart(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerMoveMinecart(
            int entityId, List<WrapperPlayServerMoveMinecart.MinecartStep> lerpSteps) {
        super(PacketType.Play.Server.MOVE_MINECART);
        this.entityId = entityId;
        this.lerpSteps = lerpSteps;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.lerpSteps = this.readList(WrapperPlayServerMoveMinecart.MinecartStep::read);
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeList(this.lerpSteps, WrapperPlayServerMoveMinecart.MinecartStep::write);
    }

    public void copy(WrapperPlayServerMoveMinecart wrapperPlayServerMoveMinecart) {
        this.entityId = wrapperPlayServerMoveMinecart.entityId;
        this.lerpSteps = wrapperPlayServerMoveMinecart.lerpSteps;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public List<WrapperPlayServerMoveMinecart.MinecartStep> getLerpSteps() {
        return this.lerpSteps;
    }

    public void setLerpSteps(List<WrapperPlayServerMoveMinecart.MinecartStep> lerpSteps) {
        this.lerpSteps = lerpSteps;
    }

    public static final class MinecartStep {

        private Vector3d position;

        private Vector3d movement;

        private float yaw;

        private float pitch;

        private float weight;

        public MinecartStep(
                Vector3d vector3d, Vector3d vector3d2, float yaw, float pitch, float weight) {
            this.position = vector3d;
            this.movement = vector3d2;
            this.yaw = yaw;
            this.pitch = pitch;
            this.weight = weight;
        }

        public static WrapperPlayServerMoveMinecart.MinecartStep read(
                PacketWrapper<?> packetWrapper) {
            Vector3d vector3d = Vector3d.read(packetWrapper);
            Vector3d vector3d2 = Vector3d.read(packetWrapper);
            float value = packetWrapper.readRotation();
            float value2 = packetWrapper.readRotation();
            float value3 = packetWrapper.readFloat();
            return new WrapperPlayServerMoveMinecart.MinecartStep(
                    vector3d, vector3d2, value, value2, value3);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                WrapperPlayServerMoveMinecart.MinecartStep minecartStep) {
            Vector3d.write(packetWrapper, minecartStep.position);
            Vector3d.write(packetWrapper, minecartStep.movement);
            packetWrapper.writeRotation(minecartStep.yaw);
            packetWrapper.writeRotation(minecartStep.pitch);
            packetWrapper.writeFloat(minecartStep.weight);
        }

        public Vector3d getPosition() {
            return this.position;
        }

        public void setPosition(Vector3d vector3d) {
            this.position = vector3d;
        }

        public Vector3d getMovement() {
            return this.movement;
        }

        public void setMovement(Vector3d vector3d) {
            this.movement = vector3d;
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

        public float getWeight() {
            return this.weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof WrapperPlayServerMoveMinecart.MinecartStep)) {
                return false;
            } else {
                WrapperPlayServerMoveMinecart.MinecartStep minecartStep;
                if (Float.compare(
                                (minecartStep = (WrapperPlayServerMoveMinecart.MinecartStep) result)
                                        .yaw,
                                this.yaw)
                        != 0) {
                    return false;
                } else if (Float.compare(minecartStep.pitch, this.pitch) != 0) {
                    return false;
                } else if (Float.compare(minecartStep.weight, this.weight) != 0) {
                    return false;
                } else {
                    return !this.position.equals(minecartStep.position)
                            ? false
                            : this.movement.equals(minecartStep.movement);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.position, this.movement, this.yaw, this.pitch, this.weight);
        }
    }
}
