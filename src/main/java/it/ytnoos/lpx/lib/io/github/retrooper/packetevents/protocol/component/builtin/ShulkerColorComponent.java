package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ShulkerColorComponent {

    private DyeColor color;

    public ShulkerColorComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static ShulkerColorComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new ShulkerColorComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ShulkerColorComponent shulkerColorComponent) {
        DyeColor.write(packetWrapper, shulkerColorComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ShulkerColorComponent)) {
            return false;
        }
        ShulkerColorComponent shulkerColorComponent = (ShulkerColorComponent) result;
        return this.color.equals(shulkerColorComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
