package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemSlotDisplay extends SlotDisplay<ItemSlotDisplay> {

    private ItemType item;

    public ItemSlotDisplay(ItemType itemType) {
        super(SlotDisplayTypes.ITEM);
        this.item = itemType;
    }

    public static ItemSlotDisplay read(PacketWrapper<?> packetWrapper) {
        ItemType itemType = packetWrapper.readMappedEntity(ItemTypes.getRegistry());
        return new ItemSlotDisplay(itemType);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemSlotDisplay itemSlotDisplay) {
        packetWrapper.writeMappedEntity(itemSlotDisplay.getItem());
    }

    public ItemType getItem() {
        return this.item;
    }

    public void setItem(ItemType itemType) {
        this.item = itemType;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemSlotDisplay)) {
            return false;
        }
        ItemSlotDisplay itemSlotDisplay = (ItemSlotDisplay) result;
        return this.item.equals(itemSlotDisplay.item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.item);
    }

    @Override
    public String toString() {
        return "ItemSlotDisplay{item=" + this.item + 125;
    }
}
