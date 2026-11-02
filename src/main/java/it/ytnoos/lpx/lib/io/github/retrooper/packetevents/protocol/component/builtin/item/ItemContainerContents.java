package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ItemContainerContents {

    private List<ItemStack> items;

    public ItemContainerContents(List<ItemStack> items) {
        this.items = items;
    }

    public static ItemContainerContents read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemStackSerialization::readOptionalTemplate);
        return new ItemContainerContents(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemContainerContents itemContainerContents) {
        packetWrapper.writeList(
                itemContainerContents.items, ItemStackSerialization::writeOptionalTemplate);
    }

    public void addItem(ItemStack itemStack) {
        this.items.add(itemStack);
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemContainerContents)) {
            return false;
        }
        ItemContainerContents itemContainerContents = (ItemContainerContents) result;
        return this.items.equals(itemContainerContents.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.items);
    }
}
