package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWindowConfirmation
        extends PacketWrapper<WrapperPlayServerWindowConfirmation> {

    private int windowId;

    private short actionId;

    private boolean accepted;

    public WrapperPlayServerWindowConfirmation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWindowConfirmation(int windowId, short actionId, boolean accepted) {
        super(PacketType.Play.Server.WINDOW_CONFIRMATION);
        this.windowId = windowId;
        this.actionId = actionId;
        this.accepted = accepted;
    }

    @Override
    public void read() {
        this.windowId = this.readUnsignedByte();
        this.actionId = this.readShort();
        this.accepted = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeByte(this.windowId);
        this.writeShort(this.actionId);
        this.writeBoolean(this.accepted);
    }

    public void copy(WrapperPlayServerWindowConfirmation wrapperPlayServerWindowConfirmation) {
        this.windowId = wrapperPlayServerWindowConfirmation.getWindowId();
        this.actionId = wrapperPlayServerWindowConfirmation.getActionId();
        this.accepted = wrapperPlayServerWindowConfirmation.isAccepted();
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public short getActionId() {
        return this.actionId;
    }

    public void setActionId(short actionId) {
        this.actionId = actionId;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
