package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.DiggingAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientPlayerDigging extends PacketWrapper<WrapperPlayClientPlayerDigging> {

    private DiggingAction action;

    private Vector3i blockPosition;

    private BlockFace blockFace;

    private int blockFaceId;

    private int sequence;

    public WrapperPlayClientPlayerDigging(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerDigging(
            DiggingAction diggingAction, Vector3i vector3i, BlockFace blockFace2, int sequence) {
        super(PacketType.Play.Client.PLAYER_DIGGING);
        this.action = diggingAction;
        this.blockPosition = vector3i;
        this.blockFace = blockFace2;
        this.blockFaceId = blockFace2.getFaceValue();
        this.sequence = sequence;
    }

    public WrapperPlayClientPlayerDigging(
            DiggingAction diggingAction, Vector3i vector3i, int blockFaceId, int sequence) {
        super(PacketType.Play.Client.PLAYER_DIGGING);
        this.action = diggingAction;
        this.blockPosition = vector3i;
        this.blockFace = BlockFace.getBlockFaceByValue(blockFaceId);
        this.blockFaceId = blockFaceId;
        this.sequence = sequence;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.action = DiggingAction.getById(this.serverVersion, this.readVarInt());
        } else {
            this.action = DiggingAction.getById(this.serverVersion, this.readByte());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.blockPosition = this.readBlockPosition();
        } else {
            int value = this.readInt();
            short value2 = this.readUnsignedByte();
            int value3 = this.readInt();
            this.blockPosition = new Vector3i(value, value2, value3);
        }
        this.blockFaceId = this.readUnsignedByte();
        this.blockFace = BlockFace.getBlockFaceByValue(this.blockFaceId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.sequence = this.readVarInt();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt(this.action.getId(this.serverVersion));
            this.writeBlockPosition(this.blockPosition);
        } else {
            this.writeByte(this.action.getId(this.serverVersion));
            this.writeInt(this.blockPosition.x);
            this.writeByte(this.blockPosition.y);
            this.writeInt(this.blockPosition.z);
        }
        this.writeByte(this.blockFaceId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeVarInt(this.sequence);
        }
    }

    public void copy(WrapperPlayClientPlayerDigging wrapperPlayClientPlayerDigging) {
        this.action = wrapperPlayClientPlayerDigging.action;
        this.blockPosition = wrapperPlayClientPlayerDigging.blockPosition;
        this.blockFace = wrapperPlayClientPlayerDigging.blockFace;
        this.blockFaceId = wrapperPlayClientPlayerDigging.blockFaceId;
        this.sequence = wrapperPlayClientPlayerDigging.sequence;
    }

    public DiggingAction getAction() {
        return this.action;
    }

    public void setAction(DiggingAction diggingAction) {
        this.action = diggingAction;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public BlockFace getBlockFace() {
        return this.blockFace;
    }

    public void setBlockFace(BlockFace blockFace2) {
        this.blockFace = blockFace2;
        this.blockFaceId = blockFace2.getFaceValue();
    }

    public int getBlockFaceId() {
        return this.blockFaceId;
    }

    public void setBlockFaceId(int blockFaceId) {
        this.blockFace = BlockFace.getBlockFaceByValue(blockFaceId);
        this.blockFaceId = blockFaceId;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
