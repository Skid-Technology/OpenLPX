package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerBlockChange extends PacketWrapper<WrapperPlayServerBlockChange> {

    private Vector3i blockPosition;

    private int blockID;

    public WrapperPlayServerBlockChange(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerBlockChange(Vector3i vector3i, WrappedBlockState wrappedBlockState) {
        this(vector3i, wrappedBlockState.getGlobalId());
    }

    public WrapperPlayServerBlockChange(Vector3i vector3i, int blockID) {
        super(PacketType.Play.Server.BLOCK_CHANGE);
        this.blockPosition = vector3i;
        this.blockID = blockID;
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.blockPosition =
                    new Vector3i(this.readInt(), this.readUnsignedByte(), this.readInt());
            int value = this.readVarInt();
            short value2 = this.readUnsignedByte();
            this.blockID = value | value2 << 12;
        } else {
            this.blockPosition = this.readBlockPosition();
            this.blockID = this.readVarInt();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeInt(this.blockPosition.getX());
            this.writeByte(this.blockPosition.getY());
            this.writeInt(this.blockPosition.getZ());
            this.writeVarInt(this.blockID & 0xFF);
            this.writeByte(this.blockID >> 12);
        } else {
            this.writeBlockPosition(this.blockPosition);
            this.writeVarInt(this.blockID);
        }
    }

    public void copy(WrapperPlayServerBlockChange wrapperPlayServerBlockChange) {
        this.blockPosition = wrapperPlayServerBlockChange.blockPosition;
        this.blockID = wrapperPlayServerBlockChange.blockID;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public int getBlockId() {
        return this.blockID;
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public WrappedBlockState getBlockState() {
        return WrappedBlockState.getByGlobalId(this.serverVersion.toClientVersion(), this.blockID);
    }

    public void setBlockState(WrappedBlockState wrappedBlockState) {
        this.blockID = wrappedBlockState.getGlobalId();
    }
}
