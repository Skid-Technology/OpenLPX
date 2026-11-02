package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers.ResolvableFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers.ResolvableInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemCookingFuel {

    private final ResolvableInt burnTime;

    private final ResolvableFloat speedMultiplier;

    public ItemCookingFuel(ResolvableInt resolvableInt, ResolvableFloat resolvableFloat) {
        this.burnTime = resolvableInt;
        this.speedMultiplier = resolvableFloat;
    }

    public static ItemCookingFuel read(PacketWrapper<?> packetWrapper) {
        ResolvableInt resolvableInt = ResolvableInt.read(packetWrapper);
        ResolvableFloat resolvableFloat = ResolvableFloat.read(packetWrapper);
        return new ItemCookingFuel(resolvableInt, resolvableFloat);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemCookingFuel itemCookingFuel) {
        ResolvableInt.write(packetWrapper, itemCookingFuel.burnTime);
        ResolvableFloat.write(packetWrapper, itemCookingFuel.speedMultiplier);
    }

    public ResolvableInt getBurnTime() {
        return this.burnTime;
    }

    public ResolvableFloat getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemCookingFuel itemCookingFuel = (ItemCookingFuel) result;
            return !this.burnTime.equals(itemCookingFuel.burnTime)
                    ? false
                    : this.speedMultiplier.equals(itemCookingFuel.speedMultiplier);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.burnTime, this.speedMultiplier);
    }
}
