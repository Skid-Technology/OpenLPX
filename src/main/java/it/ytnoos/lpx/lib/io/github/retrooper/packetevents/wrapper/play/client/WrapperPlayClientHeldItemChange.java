package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientHeldItemChange
        extends PacketWrapper<WrapperPlayClientHeldItemChange> {

    private int slot;

    public WrapperPlayClientHeldItemChange(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientHeldItemChange(int slot) {
        super(PacketType.Play.Client.HELD_ITEM_CHANGE);
        this.slot = slot;
    }

    @Override
    public void read() {
        this.slot = this.readShort();
    }

    @Override
    public void write() {
        this.writeShort(this.slot);
    }

    public void copy(WrapperPlayClientHeldItemChange wrapperPlayClientHeldItemChange) {
        this.slot = wrapperPlayClientHeldItemChange.slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
