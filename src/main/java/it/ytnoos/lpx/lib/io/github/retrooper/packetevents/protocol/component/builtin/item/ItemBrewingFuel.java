package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers.ResolvableFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers.ResolvableInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemBrewingFuel {

    private final ResolvableInt uses;

    private final ResolvableFloat speedMultiplier;

    public ItemBrewingFuel(ResolvableInt resolvableInt, ResolvableFloat resolvableFloat) {
        this.uses = resolvableInt;
        this.speedMultiplier = resolvableFloat;
    }

    public static ItemBrewingFuel read(PacketWrapper<?> packetWrapper) {
        ResolvableInt resolvableInt = ResolvableInt.read(packetWrapper);
        ResolvableFloat resolvableFloat = ResolvableFloat.read(packetWrapper);
        return new ItemBrewingFuel(resolvableInt, resolvableFloat);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemBrewingFuel itemBrewingFuel) {
        ResolvableInt.write(packetWrapper, itemBrewingFuel.uses);
        ResolvableFloat.write(packetWrapper, itemBrewingFuel.speedMultiplier);
    }

    public ResolvableInt getUses() {
        return this.uses;
    }

    public ResolvableFloat getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemBrewingFuel itemBrewingFuel = (ItemBrewingFuel) result;
            return !this.uses.equals(itemBrewingFuel.uses)
                    ? false
                    : this.speedMultiplier.equals(itemBrewingFuel.speedMultiplier);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uses, this.speedMultiplier);
    }
}
