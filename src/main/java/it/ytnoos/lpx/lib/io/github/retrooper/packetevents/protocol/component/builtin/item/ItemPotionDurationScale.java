package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemPotionDurationScale {

    private float scale;

    public ItemPotionDurationScale(float scale) {
        this.scale = scale;
    }

    public static ItemPotionDurationScale read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        return new ItemPotionDurationScale(value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemPotionDurationScale itemPotionDurationScale) {
        packetWrapper.writeFloat(itemPotionDurationScale.scale);
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public boolean equals(Object result) {
        return !(result instanceof ItemPotionDurationScale)
                ? false
                : Float.compare(((ItemPotionDurationScale) result).scale, this.scale) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.scale);
    }
}
