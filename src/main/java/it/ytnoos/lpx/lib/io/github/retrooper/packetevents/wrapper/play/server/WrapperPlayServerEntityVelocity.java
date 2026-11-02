package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.LpVector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEntityVelocity
        extends PacketWrapper<WrapperPlayServerEntityVelocity> {

    private int entityID;

    private Vector3d velocity;

    public WrapperPlayServerEntityVelocity(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEntityVelocity(int entityID, Vector3d vector3d) {
        super(PacketType.Play.Server.ENTITY_VELOCITY);
        this.entityID = entityID;
        this.velocity = vector3d;
    }

    @Override
    public void read() {
        this.entityID =
                this.serverVersion.isOlderThan(ServerVersion.V_1_8)
                        ? this.readInt()
                        : this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.velocity = LpVector3d.read(this);
        } else {
            double value = this.readShort() / 8000.0;
            double value2 = this.readShort() / 8000.0;
            double value3 = this.readShort() / 8000.0;
            this.velocity = new Vector3d(value, value2, value3);
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_8)) {
            this.writeInt(this.entityID);
        } else {
            this.writeVarInt(this.entityID);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            LpVector3d.write(this, this.velocity);
        } else {
            this.writeShort(
                    (int) (this.velocity.x * 8000.0 + Math.copySign(1.0E-11, this.velocity.x)));
            this.writeShort(
                    (int) (this.velocity.y * 8000.0 + Math.copySign(1.0E-11, this.velocity.y)));
            this.writeShort(
                    (int) (this.velocity.z * 8000.0 + Math.copySign(1.0E-11, this.velocity.z)));
        }
    }

    public void copy(WrapperPlayServerEntityVelocity wrapperPlayServerEntityVelocity) {
        this.entityID = wrapperPlayServerEntityVelocity.entityID;
        this.velocity = wrapperPlayServerEntityVelocity.velocity;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public Vector3d getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector3d vector3d) {
        this.velocity = vector3d;
    }
}
