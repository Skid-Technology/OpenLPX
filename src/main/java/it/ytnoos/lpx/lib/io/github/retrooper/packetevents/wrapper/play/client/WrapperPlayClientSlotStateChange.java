package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSlotStateChange
        extends PacketWrapper<WrapperPlayClientSlotStateChange> {

    private int slot;

    private int windowId;

    private boolean state;

    public WrapperPlayClientSlotStateChange(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSlotStateChange(int slot, int windowId, boolean state) {
        super(PacketType.Play.Client.SLOT_STATE_CHANGE);
        this.slot = slot;
        this.windowId = windowId;
        this.state = state;
    }

    @Override
    public void read() {
        this.slot = this.readVarInt();
        this.windowId =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)
                        ? this.readContainerId()
                        : this.readVarInt();
        this.state = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeVarInt(this.slot);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeContainerId(this.windowId);
        } else {
            this.writeVarInt(this.windowId);
        }
        this.writeBoolean(this.state);
    }

    public void copy(WrapperPlayClientSlotStateChange wrapperPlayClientSlotStateChange) {
        this.slot = wrapperPlayClientSlotStateChange.slot;
        this.windowId = wrapperPlayClientSlotStateChange.windowId;
        this.state = wrapperPlayClientSlotStateChange.state;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public boolean isState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
