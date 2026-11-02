package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.EntityPositionData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.teleport.RelativeFlag;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEntityTeleport
        extends PacketWrapper<WrapperPlayServerEntityTeleport> {

    private int entityID;

    private EntityPositionData values;

    private RelativeFlag relativeFlags;

    private boolean onGround;

    public WrapperPlayServerEntityTeleport(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityTeleport(int value, Location location, boolean matches) {
        this(value, location.getPosition(), location.getYaw(), location.getPitch(), matches);
    }

    public WrapperPlayServerEntityTeleport(
            int value, Vector3d vector3d, float value2, float value3, boolean matches) {
        this(value, vector3d, Vector3d.zero(), value2, value3, RelativeFlag.NONE, matches);
    }

    public WrapperPlayServerEntityTeleport(
            int value,
            Vector3d vector3d,
            Vector3d vector3d2,
            float value2,
            float value3,
            RelativeFlag relativeFlag,
            boolean matches) {
        this(
                value,
                new EntityPositionData(vector3d, vector3d2, value2, value3),
                relativeFlag,
                matches);
    }

    public WrapperPlayServerEntityTeleport(
            int entityID,
            EntityPositionData entityPositionData,
            RelativeFlag relativeFlag,
            boolean onGround) {
        super(PacketType.Play.Server.ENTITY_TELEPORT);
        this.entityID = entityID;
        this.values = entityPositionData;
        this.relativeFlags = relativeFlag;
        this.onGround = onGround;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.entityID = this.readVarInt();
            this.values = EntityPositionData.read(this);
            this.relativeFlags = new RelativeFlag(this.readInt());
        } else {
            this.entityID =
                    this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)
                            ? this.readVarInt()
                            : this.readInt();
            Vector3d vector3d =
                    this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)
                            ? Vector3d.read(this)
                            : new Vector3d(
                                    this.readInt() / 32.0,
                                    this.readInt() / 32.0,
                                    this.readInt() / 32.0);
            float value = this.readByte() / 0.7111111F;
            float value2 = this.readByte() / 0.7111111F;
            this.values = new EntityPositionData(vector3d, Vector3d.zero(), value, value2);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.onGround = this.readBoolean();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeVarInt(this.entityID);
            EntityPositionData.write(this, this.values);
            this.writeInt(this.relativeFlags.getFullMask());
        } else {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
                this.writeVarInt(this.entityID);
            } else {
                this.writeInt(this.entityID);
            }
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                Vector3d.write(this, this.values.getPosition());
            } else {
                Vector3d position = this.values.getPosition();
                this.writeInt(MathUtil.floor(position.x * 32.0));
                this.writeInt(MathUtil.floor(position.y * 32.0));
                this.writeInt(MathUtil.floor(position.z * 32.0));
            }
            this.writeByte((int) (this.values.getYaw() * 0.7111111F));
            this.writeByte((int) (this.values.getPitch() * 0.7111111F));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeBoolean(this.onGround);
        }
    }

    public void copy(WrapperPlayServerEntityTeleport wrapperPlayServerEntityTeleport) {
        this.entityID = wrapperPlayServerEntityTeleport.entityID;
        this.values = wrapperPlayServerEntityTeleport.values;
        this.relativeFlags = wrapperPlayServerEntityTeleport.relativeFlags;
        this.onGround = wrapperPlayServerEntityTeleport.onGround;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
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

    public RelativeFlag getRelativeFlags() {
        return this.relativeFlags;
    }

    public void setRelativeFlags(RelativeFlag relativeFlag) {
        this.relativeFlags = relativeFlag;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
