package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class TropicalFishBaseColorComponent {

    private DyeColor color;

    public TropicalFishBaseColorComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static TropicalFishBaseColorComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new TropicalFishBaseColorComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            TropicalFishBaseColorComponent tropicalFishBaseColorComponent) {
        DyeColor.write(packetWrapper, tropicalFishBaseColorComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof TropicalFishBaseColorComponent)) {
            return false;
        }
        TropicalFishBaseColorComponent tropicalFishBaseColorComponent =
                (TropicalFishBaseColorComponent) result;
        return this.color.equals(tropicalFishBaseColorComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
