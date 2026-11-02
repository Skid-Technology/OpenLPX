package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.numbers.ResolvableInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemCompostable {

    private final ResolvableInt layers;

    public ItemCompostable(ResolvableInt resolvableInt) {
        this.layers = resolvableInt;
    }

    public static ItemCompostable read(PacketWrapper<?> packetWrapper) {
        ResolvableInt resolvableInt = ResolvableInt.read(packetWrapper);
        return new ItemCompostable(resolvableInt);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemCompostable itemCompostable) {
        ResolvableInt.write(packetWrapper, itemCompostable.layers);
    }

    public ResolvableInt getLayers() {
        return this.layers;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemCompostable itemCompostable = (ItemCompostable) result;
            return this.layers.equals(itemCompostable.layers);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.layers);
    }
}
