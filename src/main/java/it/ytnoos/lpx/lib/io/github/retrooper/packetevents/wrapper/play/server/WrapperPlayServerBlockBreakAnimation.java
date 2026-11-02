package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerBlockBreakAnimation
        extends PacketWrapper<WrapperPlayServerBlockBreakAnimation> {

    private int entityID;

    private Vector3i blockPosition;

    private byte destroyStage;

    public WrapperPlayServerBlockBreakAnimation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerBlockBreakAnimation(
            int entityID, Vector3i vector3i, byte destroyStage) {
        super(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        this.entityID = entityID;
        this.blockPosition = vector3i;
        this.destroyStage = destroyStage;
    }

    @Override
    public void read() {
        this.entityID = this.readVarInt();
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            int value = this.readInt();
            int value2 = this.readInt();
            int value3 = this.readInt();
            this.blockPosition = new Vector3i(value, value2, value3);
        } else {
            this.blockPosition = this.readBlockPosition();
        }
        this.destroyStage = (byte) this.readUnsignedByte();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityID);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeInt(this.blockPosition.x);
            this.writeInt(this.blockPosition.y);
            this.writeInt(this.blockPosition.z);
        } else {
            this.writeBlockPosition(this.blockPosition);
        }
        this.writeByte(this.destroyStage);
    }

    public void copy(WrapperPlayServerBlockBreakAnimation wrapperPlayServerBlockBreakAnimation) {
        this.entityID = wrapperPlayServerBlockBreakAnimation.entityID;
        this.blockPosition = wrapperPlayServerBlockBreakAnimation.blockPosition;
        this.destroyStage = wrapperPlayServerBlockBreakAnimation.destroyStage;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public byte getDestroyStage() {
        return this.destroyStage;
    }

    public void setDestroyStage(byte destroyStage) {
        this.destroyStage = destroyStage;
    }
}
