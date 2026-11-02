package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEntityRotation
        extends PacketWrapper<WrapperPlayServerEntityRotation> {

    private int entityID;

    private float yaw;

    private float pitch;

    private boolean onGround;

    public WrapperPlayServerEntityRotation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityRotation(int entityID, float yaw, float pitch, boolean onGround) {
        super(PacketType.Play.Server.ENTITY_ROTATION);
        this.entityID = entityID;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.onGround = this.readBoolean();
        }
        this.yaw = this.readByte() / 0.7111111F;
        this.pitch = this.readByte() / 0.7111111F;
        if (this.serverVersion.isOlderThan(ServerVersion.V_26_3)) {
            this.onGround = this.readBoolean();
        }
    }

    public void copy(WrapperPlayServerEntityRotation wrapperPlayServerEntityRotation) {
        this.entityID = wrapperPlayServerEntityRotation.entityID;
        this.yaw = wrapperPlayServerEntityRotation.yaw;
        this.pitch = wrapperPlayServerEntityRotation.pitch;
        this.onGround = wrapperPlayServerEntityRotation.onGround;
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.writeBoolean(this.onGround);
        }
        this.writeByte((int) (this.yaw * 0.7111111F));
        this.writeByte((int) (this.pitch * 0.7111111F));
        if (this.serverVersion.isOlderThan(ServerVersion.V_26_3)) {
            this.writeBoolean(this.onGround);
        }
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
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
