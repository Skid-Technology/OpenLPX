package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSelectTrade extends PacketWrapper<WrapperPlayClientSelectTrade> {

    private int slot;

    public WrapperPlayClientSelectTrade(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSelectTrade(int slot) {
        super(PacketType.Play.Client.SELECT_TRADE);
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

    public void copy(WrapperPlayClientSelectTrade wrapperPlayClientSelectTrade) {
        this.slot = wrapperPlayClientSelectTrade.slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
