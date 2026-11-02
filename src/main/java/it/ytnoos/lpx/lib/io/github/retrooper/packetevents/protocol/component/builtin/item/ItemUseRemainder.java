package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemUseRemainder {

    private ItemStack target;

    public ItemUseRemainder(ItemStack itemStack) {
        this.target = itemStack;
    }

    public static ItemUseRemainder read(PacketWrapper<?> packetWrapper) {
        ItemStack itemStack = ItemStackSerialization.readTemplate(packetWrapper);
        return new ItemUseRemainder(itemStack);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemUseRemainder itemUseRemainder) {
        ItemStackSerialization.writeTemplate(packetWrapper, itemUseRemainder.target);
    }

    public ItemStack getTarget() {
        return this.target;
    }

    public void setTarget(ItemStack itemStack) {
        this.target = itemStack;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemUseRemainder)) {
            return false;
        }
        ItemUseRemainder itemUseRemainder = (ItemUseRemainder) result;
        return this.target.equals(itemUseRemainder.target);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.target);
    }

    @Override
    public String toString() {
        return "ItemUseRemainder{target=" + this.target + 125;
    }
}
