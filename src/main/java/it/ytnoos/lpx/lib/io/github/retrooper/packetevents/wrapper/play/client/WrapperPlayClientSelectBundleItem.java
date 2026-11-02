package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSelectBundleItem
        extends PacketWrapper<WrapperPlayClientSelectBundleItem> {

    private int slotId;

    private int selectedItemIndex;

    public WrapperPlayClientSelectBundleItem(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSelectBundleItem(int slotId, int selectedItemIndex) {
        super(PacketType.Play.Client.SELECT_BUNDLE_ITEM);
        this.slotId = slotId;
        this.selectedItemIndex = selectedItemIndex;
    }

    @Override
    public void read() {
        this.slotId = this.readVarInt();
        this.selectedItemIndex = this.readVarInt();
        if (this.selectedItemIndex < 0 && this.selectedItemIndex != -1) {
            throw new IllegalArgumentException(
                    "Invalid selectedItemIndex: " + this.selectedItemIndex);
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.slotId);
        this.writeVarInt(this.selectedItemIndex);
    }

    public void copy(WrapperPlayClientSelectBundleItem wrapperPlayClientSelectBundleItem) {
        this.slotId = wrapperPlayClientSelectBundleItem.slotId;
        this.selectedItemIndex = wrapperPlayClientSelectBundleItem.selectedItemIndex;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getSelectedItemIndex() {
        return this.selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }
}
