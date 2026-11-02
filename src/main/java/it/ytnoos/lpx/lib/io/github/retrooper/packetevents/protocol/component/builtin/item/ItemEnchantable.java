package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemEnchantable {

    private int value;

    public ItemEnchantable(int value) {
        this.value = value;
    }

    public static ItemEnchantable read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        return new ItemEnchantable(value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemEnchantable itemEnchantable) {
        packetWrapper.writeVarInt(itemEnchantable.value);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemEnchantable)) {
            return false;
        }
        ItemEnchantable itemEnchantable = (ItemEnchantable) result;
        return this.value == itemEnchantable.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public String toString() {
        return "ItemEnchantable{value=" + this.value + 125;
    }
}
