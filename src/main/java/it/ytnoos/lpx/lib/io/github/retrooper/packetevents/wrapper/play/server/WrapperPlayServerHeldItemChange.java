package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerHeldItemChange
        extends PacketWrapper<WrapperPlayServerHeldItemChange> {

    private int slot;

    public WrapperPlayServerHeldItemChange(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerHeldItemChange(int slot) {
        super(PacketType.Play.Server.HELD_ITEM_CHANGE);
        this.slot = slot;
    }

    @Override
    public void read() {
        this.slot =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_4)
                        ? this.readVarInt()
                        : this.readByte();
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            this.writeVarInt(this.slot);
        } else {
            this.writeByte(this.slot);
        }
    }

    public void copy(WrapperPlayServerHeldItemChange wrapperPlayServerHeldItemChange) {
        this.slot = wrapperPlayServerHeldItemChange.slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
