package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.EntityPositionData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.teleport.RelativeFlag;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayServerPlayerPositionAndLook
        extends PacketWrapper<WrapperPlayServerPlayerPositionAndLook> {

    private int teleportId;

    private EntityPositionData values;

    private RelativeFlag relativeFlags;

    @Obsolete private boolean dismountVehicle = false;

    public WrapperPlayServerPlayerPositionAndLook(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerPositionAndLook(
            double value,
            double value2,
            double value3,
            float value4,
            float value5,
            byte value6,
            int value7,
            boolean matches) {
        this(new Vector3d(value, value2, value3), value4, value5, value6, value7, matches);
    }

    public WrapperPlayServerPlayerPositionAndLook(
            Vector3d vector3d,
            float value,
            float value2,
            byte value3,
            int value4,
            boolean dismountVehicle) {
        this(vector3d, value, value2, value3, value4);
        this.dismountVehicle = dismountVehicle;
    }

    public WrapperPlayServerPlayerPositionAndLook(
            Vector3d vector3d, float value, float value2, byte value3, int value4) {
        this(value4, vector3d, Vector3d.zero(), value, value2, value3);
    }

    public WrapperPlayServerPlayerPositionAndLook(
            int value,
            Vector3d vector3d,
            Vector3d vector3d2,
            float value2,
            float value3,
            byte value4) {
        this(value, vector3d, vector3d2, value2, value3, null);
        this.relativeFlags = new RelativeFlag(value4);
    }

    public WrapperPlayServerPlayerPositionAndLook(
            int value,
            Vector3d vector3d,
            Vector3d vector3d2,
            float value2,
            float value3,
            RelativeFlag relativeFlag) {
        this(value, new EntityPositionData(vector3d, vector3d2, value2, value3), relativeFlag);
    }

    public WrapperPlayServerPlayerPositionAndLook(
            int teleportId, EntityPositionData entityPositionData, RelativeFlag relativeFlag) {
        super(PacketType.Play.Server.PLAYER_POSITION_AND_LOOK);
        this.teleportId = teleportId;
        this.values = entityPositionData;
        this.relativeFlags = relativeFlag;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.teleportId = this.readVarInt();
            this.values = EntityPositionData.read(this);
            this.relativeFlags = new RelativeFlag(this.readInt());
        } else {
            Vector3d vector3d = Vector3d.read(this);
            float value = this.readFloat();
            float value2 = this.readFloat();
            this.values = new EntityPositionData(vector3d, Vector3d.zero(), value, value2);
            this.relativeFlags = new RelativeFlag(this.readUnsignedByte());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.teleportId = this.readVarInt();
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)
                        && this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_3)) {
                    this.dismountVehicle = this.readBoolean();
                }
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeVarInt(this.teleportId);
            EntityPositionData.write(this, this.values);
            this.writeInt(this.relativeFlags.getFullMask());
        } else {
            Vector3d.write(this, this.values.getPosition());
            this.writeFloat(this.values.getYaw());
            this.writeFloat(this.values.getPitch());
            this.writeByte(this.relativeFlags.getFullMask());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                this.writeVarInt(this.teleportId);
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)
                        && this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_3)) {
                    this.writeBoolean(this.dismountVehicle);
                }
            }
        }
    }

    public void copy(
            WrapperPlayServerPlayerPositionAndLook wrapperPlayServerPlayerPositionAndLook) {
        this.teleportId = wrapperPlayServerPlayerPositionAndLook.teleportId;
        this.values = wrapperPlayServerPlayerPositionAndLook.values;
        this.relativeFlags = wrapperPlayServerPlayerPositionAndLook.relativeFlags;
        this.dismountVehicle = wrapperPlayServerPlayerPositionAndLook.dismountVehicle;
    }

    public int getTeleportId() {
        return this.teleportId;
    }

    public void setTeleportId(int teleportId) {
        this.teleportId = teleportId;
    }

    public EntityPositionData getValues() {
        return this.values;
    }

    public void setValues(EntityPositionData entityPositionData) {
        this.values = entityPositionData;
    }

    public Vector3d getPosition() {
        return this.values.getPosition();
    }

    public void setPosition(Vector3d vector3d) {
        this.values.setPosition(vector3d);
    }

    public double getX() {
        return this.getPosition().getX();
    }

    public void setX(double value) {
        this.setPosition(new Vector3d(value, this.getY(), this.getZ()));
    }

    public double getY() {
        return this.getPosition().getY();
    }

    public void setY(double value) {
        this.setPosition(new Vector3d(this.getX(), value, this.getZ()));
    }

    public double getZ() {
        return this.getPosition().getZ();
    }

    public void setZ(double value) {
        this.setPosition(new Vector3d(this.getX(), this.getY(), value));
    }

    public Vector3d getDeltaMovement() {
        return this.values.getDeltaMovement();
    }

    public void setDeltaMovement(Vector3d vector3d) {
        this.values.setDeltaMovement(vector3d);
    }

    public float getYaw() {
        return this.values.getYaw();
    }

    public void setYaw(float value) {
        this.values.setYaw(value);
    }

    public float getPitch() {
        return this.values.getPitch();
    }

    public void setPitch(float value) {
        this.values.setPitch(value);
    }

    @Deprecated
    public byte getRelativeMask() {
        return this.relativeFlags.getMask();
    }

    @Deprecated
    public void setRelativeMask(byte value) {
        this.relativeFlags = new RelativeFlag(value);
    }

    public boolean isRelativeFlag(RelativeFlag relativeFlag) {
        return this.relativeFlags.has(relativeFlag);
    }

    public void setRelative(RelativeFlag relativeFlag, boolean matches) {
        this.relativeFlags = this.relativeFlags.set(relativeFlag, matches);
    }

    public RelativeFlag getRelativeFlags() {
        return this.relativeFlags;
    }

    public void setRelativeFlags(RelativeFlag relativeFlag) {
        this.relativeFlags = relativeFlag;
    }

    @Obsolete
    public boolean isDismountVehicle() {
        return this.dismountVehicle;
    }

    @Obsolete
    public void setDismountVehicle(boolean dismountVehicle) {
        this.dismountVehicle = dismountVehicle;
    }
}
