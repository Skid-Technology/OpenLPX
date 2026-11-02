package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class BundleContents {

    private List<ItemStack> items;

    public BundleContents(List<ItemStack> items) {
        this.items = items;
    }

    public static BundleContents read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemStackSerialization::readTemplate);
        return new BundleContents(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, BundleContents bundleContents) {
        packetWrapper.writeList(bundleContents.items, ItemStackSerialization::writeTemplate);
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
        if (!(result instanceof BundleContents)) {
            return false;
        }
        BundleContents bundleContents = (BundleContents) result;
        return this.items.equals(bundleContents.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.items);
    }
}
