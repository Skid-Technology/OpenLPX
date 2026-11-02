package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ChargedProjectiles {

    private List<ItemStack> items;

    public ChargedProjectiles(List<ItemStack> items) {
        this.items = items;
    }

    public static ChargedProjectiles read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemStackSerialization::readTemplate, 128);
        return new ChargedProjectiles(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ChargedProjectiles chargedProjectiles) {
        packetWrapper.writeList(chargedProjectiles.items, ItemStackSerialization::writeTemplate);
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
        if (!(result instanceof ChargedProjectiles)) {
            return false;
        }
        ChargedProjectiles chargedProjectiles = (ChargedProjectiles) result;
        return this.items.equals(chargedProjectiles.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.items);
    }
}
