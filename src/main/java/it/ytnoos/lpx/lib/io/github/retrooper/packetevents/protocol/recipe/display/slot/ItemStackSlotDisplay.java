package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemStackSlotDisplay extends SlotDisplay<ItemStackSlotDisplay> {

    private ItemStack stack;

    public ItemStackSlotDisplay(ItemStack itemStack) {
        super(SlotDisplayTypes.ITEM_STACK);
        this.stack = itemStack;
    }

    public static ItemStackSlotDisplay read(PacketWrapper<?> packetWrapper) {
        ItemStack itemStack = ItemStackSerialization.readTemplate(packetWrapper);
        return new ItemStackSlotDisplay(itemStack);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemStackSlotDisplay itemStackSlotDisplay) {
        ItemStackSerialization.writeTemplate(packetWrapper, itemStackSlotDisplay.stack);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack itemStack) {
        this.stack = itemStack;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemStackSlotDisplay)) {
            return false;
        }
        ItemStackSlotDisplay itemStackSlotDisplay = (ItemStackSlotDisplay) result;
        return this.stack.equals(itemStackSlotDisplay.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.stack);
    }

    @Override
    public String toString() {
        return "ItemStackSlotDisplay{stack=" + this.stack + 125;
    }
}
