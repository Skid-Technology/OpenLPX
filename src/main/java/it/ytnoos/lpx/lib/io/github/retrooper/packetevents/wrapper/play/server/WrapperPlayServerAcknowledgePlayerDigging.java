package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.DiggingAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerAcknowledgePlayerDigging
        extends PacketWrapper<WrapperPlayServerAcknowledgePlayerDigging> {

    private DiggingAction action;

    private boolean successful;

    private Vector3i blockPosition;

    private int blockID;

    public WrapperPlayServerAcknowledgePlayerDigging(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerAcknowledgePlayerDigging(
            DiggingAction diggingAction, boolean successful, Vector3i vector3i, int blockID) {
        super(PacketType.Play.Server.ACKNOWLEDGE_PLAYER_DIGGING);
        this.action = diggingAction;
        this.successful = successful;
        this.blockPosition = vector3i;
        this.blockID = blockID;
    }

    @Override
    public void read() {
        this.blockPosition = this.readBlockPosition();
        this.blockID = this.readVarInt();
        this.action = DiggingAction.getById(this.serverVersion, this.readVarInt());
        this.successful = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.blockPosition);
        this.writeVarInt(this.blockID);
        this.writeVarInt(this.action.getId(this.serverVersion));
        this.writeBoolean(this.successful);
    }

    public void copy(
            WrapperPlayServerAcknowledgePlayerDigging wrapperPlayServerAcknowledgePlayerDigging) {
        this.action = wrapperPlayServerAcknowledgePlayerDigging.action;
        this.successful = wrapperPlayServerAcknowledgePlayerDigging.successful;
        this.blockPosition = wrapperPlayServerAcknowledgePlayerDigging.blockPosition;
        this.blockID = wrapperPlayServerAcknowledgePlayerDigging.blockID;
    }

    public DiggingAction getAction() {
        return this.action;
    }

    public void setAction(DiggingAction diggingAction) {
        this.action = diggingAction;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
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

    public void setBlockId(int blockID) {
        this.blockID = blockID;
    }
}
