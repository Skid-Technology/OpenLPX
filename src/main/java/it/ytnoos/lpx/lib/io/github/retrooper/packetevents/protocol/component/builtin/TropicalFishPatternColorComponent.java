package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class TropicalFishPatternColorComponent {

    private DyeColor color;

    public TropicalFishPatternColorComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static TropicalFishPatternColorComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new TropicalFishPatternColorComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            TropicalFishPatternColorComponent tropicalFishPatternColorComponent) {
        DyeColor.write(packetWrapper, tropicalFishPatternColorComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof TropicalFishPatternColorComponent)) {
            return false;
        }
        TropicalFishPatternColorComponent tropicalFishPatternColorComponent =
                (TropicalFishPatternColorComponent) result;
        return this.color.equals(tropicalFishPatternColorComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
