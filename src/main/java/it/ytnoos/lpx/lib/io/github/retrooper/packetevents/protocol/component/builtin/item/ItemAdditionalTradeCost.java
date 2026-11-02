package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemAdditionalTradeCost {

    private final int cost;

    public ItemAdditionalTradeCost(int cost) {
        this.cost = cost;
    }

    public static ItemAdditionalTradeCost read(PacketWrapper<?> packetWrapper) {
        return new ItemAdditionalTradeCost(packetWrapper.readVarInt());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemAdditionalTradeCost itemAdditionalTradeCost) {
        packetWrapper.writeVarInt(itemAdditionalTradeCost.cost);
    }

    public ItemAdditionalTradeCost withCost(int value) {
        return new ItemAdditionalTradeCost(value);
    }

    public int getCost() {
        return this.cost;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemAdditionalTradeCost)) {
            return false;
        }
        ItemAdditionalTradeCost itemAdditionalTradeCost = (ItemAdditionalTradeCost) result;
        return this.cost == itemAdditionalTradeCost.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.cost);
    }
}
