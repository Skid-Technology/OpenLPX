package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta.LinearVecDelta;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta.VecDelta;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayServerEntityRelativeMoveAndRotation
        extends PacketWrapper<WrapperPlayServerEntityRelativeMoveAndRotation> {

    private int entityID;

    private VecDelta delta;

    private double deltaX;

    private double deltaY;

    private double deltaZ;

    private float yaw;

    private float pitch;

    private boolean onGround;

    public WrapperPlayServerEntityRelativeMoveAndRotation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Obsolete
    public WrapperPlayServerEntityRelativeMoveAndRotation(
            int entityID,
            double deltaX,
            double deltaY,
            double deltaZ,
            float yaw,
            float pitch,
            boolean onGround) {
        super(PacketType.Play.Server.ENTITY_RELATIVE_MOVE_AND_ROTATION);
        this.entityID = entityID;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.delta = new LinearVecDelta(deltaX, deltaY, deltaZ);
    }

    public WrapperPlayServerEntityRelativeMoveAndRotation(
            int entityID, VecDelta vecDelta, float yaw, float pitch, boolean onGround) {
        super(PacketType.Play.Server.ENTITY_RELATIVE_MOVE_AND_ROTATION);
        this.entityID = entityID;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.setDelta(vecDelta);
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            int value = this.readVarInt();
            this.delta = VecDelta.read(this, value >>> 1);
            this.onGround = (value & 1) == 1;
            Vector3d vector3d = this.delta.apply(Vector3d.zero());
            this.deltaX = vector3d.x;
            this.deltaY = vector3d.y;
            this.deltaZ = vector3d.z;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.deltaX = this.readShort() / 4096.0;
            this.deltaY = this.readShort() / 4096.0;
            this.deltaZ = this.readShort() / 4096.0;
        } else {
            this.deltaX = this.readByte() / 32.0;
            this.deltaY = this.readByte() / 32.0;
            this.deltaZ = this.readByte() / 32.0;
        }
        this.yaw = this.readByte() / 0.7111111F;
        this.pitch = this.readByte() / 0.7111111F;
        if (this.serverVersion.isOlderThan(ServerVersion.V_26_3)) {
            this.onGround = this.readBoolean();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            VecDelta delta = this.getDelta();
            this.writeVarInt(delta.getStepCount() << 1 | (this.onGround ? 1 : 0));
            VecDelta.write(this, delta);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeShort((short) (this.deltaX * 4096.0));
            this.writeShort((short) (this.deltaY * 4096.0));
            this.writeShort((short) (this.deltaZ * 4096.0));
        } else {
            this.writeByte((byte) (this.deltaX * 32.0));
            this.writeByte((byte) (this.deltaY * 32.0));
            this.writeByte((byte) (this.deltaZ * 32.0));
        }
        this.writeByte((int) (this.yaw * 0.7111111F));
        this.writeByte((int) (this.pitch * 0.7111111F));
        if (this.serverVersion.isOlderThan(ServerVersion.V_26_3)) {
            this.writeBoolean(this.onGround);
        }
    }

    public void copy(
            WrapperPlayServerEntityRelativeMoveAndRotation
                    wrapperPlayServerEntityRelativeMoveAndRotation) {
        this.entityID = wrapperPlayServerEntityRelativeMoveAndRotation.entityID;
        this.delta = wrapperPlayServerEntityRelativeMoveAndRotation.delta;
        this.deltaX = wrapperPlayServerEntityRelativeMoveAndRotation.deltaX;
        this.deltaY = wrapperPlayServerEntityRelativeMoveAndRotation.deltaY;
        this.deltaZ = wrapperPlayServerEntityRelativeMoveAndRotation.deltaZ;
        this.yaw = wrapperPlayServerEntityRelativeMoveAndRotation.yaw;
        this.pitch = wrapperPlayServerEntityRelativeMoveAndRotation.pitch;
        this.onGround = wrapperPlayServerEntityRelativeMoveAndRotation.onGround;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public VecDelta getDelta() {
        if (this.delta == null) {
            this.delta = new LinearVecDelta(this.deltaX, this.deltaY, this.deltaZ);
        }
        return this.delta;
    }

    public void setDelta(VecDelta vecDelta) {
        this.delta = vecDelta;
        Vector3d vector3d = vecDelta.apply(Vector3d.zero());
        this.deltaX = vector3d.x;
        this.deltaY = vector3d.y;
        this.deltaZ = vector3d.z;
    }

    public double getDeltaX() {
        return this.deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.delta = null;
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return this.deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.delta = null;
        this.deltaY = deltaY;
    }

    public double getDeltaZ() {
        return this.deltaZ;
    }

    public void setDeltaZ(double deltaZ) {
        this.delta = null;
        this.deltaZ = deltaZ;
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

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
