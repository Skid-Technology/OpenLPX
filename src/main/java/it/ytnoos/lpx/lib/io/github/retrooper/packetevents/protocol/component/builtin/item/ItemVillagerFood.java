package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemVillagerFood {

    private final int nutrition;

    public ItemVillagerFood(int nutrition) {
        this.nutrition = nutrition;
    }

    public static ItemVillagerFood read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        return new ItemVillagerFood(value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemVillagerFood itemVillagerFood) {
        packetWrapper.writeVarInt(itemVillagerFood.nutrition);
    }

    public int getNutrition() {
        return this.nutrition;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemVillagerFood itemVillagerFood = (ItemVillagerFood) result;
            return this.nutrition == itemVillagerFood.nutrition;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.nutrition);
    }
}
