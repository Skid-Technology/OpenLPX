package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerBlockAction extends PacketWrapper<WrapperPlayServerBlockAction> {

    private Vector3i blockPosition;

    private int actionID;

    private int actionData;

    private int blockTypeID;

    public WrapperPlayServerBlockAction(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerBlockAction(
            Vector3i vector3i, int actionID, int actionData, int blockTypeID) {
        super(PacketType.Play.Server.BLOCK_ACTION);
        this.blockPosition = vector3i;
        this.actionID = actionID;
        this.actionData = actionData;
        this.blockTypeID = blockTypeID;
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            int value = this.readInt();
            short value2 = this.readShort();
            int value3 = this.readInt();
            this.blockPosition = new Vector3i(value, value2, value3);
        } else {
            this.blockPosition = this.readBlockPosition();
        }
        this.actionID = this.readUnsignedByte();
        this.actionData = this.readUnsignedByte();
        this.blockTypeID = this.readVarInt();
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeInt(this.blockPosition.x);
            this.writeShort(this.blockPosition.y);
            this.writeInt(this.blockPosition.z);
        } else {
            this.writeBlockPosition(this.blockPosition);
        }
        this.writeByte(this.actionID);
        this.writeByte(this.actionData);
        this.writeVarInt(this.blockTypeID);
    }

    public void copy(WrapperPlayServerBlockAction wrapperPlayServerBlockAction) {
        this.blockPosition = wrapperPlayServerBlockAction.blockPosition;
        this.actionID = wrapperPlayServerBlockAction.actionID;
        this.actionData = wrapperPlayServerBlockAction.actionData;
        this.blockTypeID = wrapperPlayServerBlockAction.blockTypeID;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public int getActionId() {
        return this.actionID;
    }

    public void setActionId(int actionID) {
        this.actionID = actionID;
    }

    public int getActionData() {
        return this.actionData;
    }

    public void setActionData(int actionData) {
        this.actionData = actionData;
    }

    public int getBlockTypeId() {
        return this.blockTypeID;
    }

    public void setBlockTypeId(int blockTypeID) {
        this.blockTypeID = blockTypeID;
    }

    public StateType getStateType() {
        return StateTypes.getById(this.serverVersion.toClientVersion(), this.blockTypeID);
    }

    public void setStateType(StateType stateType) {
        this.blockTypeID = stateType.getMapped().getId(this.serverVersion.toClientVersion());
    }

    @Deprecated
    public WrappedBlockState getBlockType() {
        return this.getStateType().createBlockState(this.serverVersion.toClientVersion());
    }

    @Deprecated
    public void setBlockType(WrappedBlockState wrappedBlockState) {
        this.setStateType(wrappedBlockState.getType());
    }
}
