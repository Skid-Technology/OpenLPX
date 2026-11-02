package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class WrapperPlayClientPickItem extends PacketWrapper<WrapperPlayClientPickItem> {

    private int slot;

    public WrapperPlayClientPickItem(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPickItem(int slot) {
        super(PacketType.Play.Client.PICK_ITEM);
        this.slot = slot;
    }

    @Override
    public void read() {
        this.slot = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.slot);
    }

    public void copy(WrapperPlayClientPickItem wrapperPlayClientPickItem) {
        this.slot = wrapperPlayClientPickItem.slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
