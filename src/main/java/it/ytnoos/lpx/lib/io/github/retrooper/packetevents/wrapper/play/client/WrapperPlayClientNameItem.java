package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientNameItem extends PacketWrapper<WrapperPlayClientNameItem> {

    private String itemName;

    public WrapperPlayClientNameItem(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientNameItem(String itemName) {
        super(PacketType.Play.Client.NAME_ITEM);
        this.itemName = itemName;
    }

    @Override
    public void read() {
        this.itemName = this.readString();
    }

    @Override
    public void write() {
        this.writeString(this.itemName);
    }

    public void copy(WrapperPlayClientNameItem wrapperPlayClientNameItem) {
        this.itemName = wrapperPlayClientNameItem.itemName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
