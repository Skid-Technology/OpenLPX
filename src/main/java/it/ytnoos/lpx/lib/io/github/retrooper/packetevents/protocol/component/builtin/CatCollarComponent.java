package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class CatCollarComponent {

    private DyeColor color;

    public CatCollarComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static CatCollarComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new CatCollarComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CatCollarComponent catCollarComponent) {
        DyeColor.write(packetWrapper, catCollarComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof CatCollarComponent)) {
            return false;
        }
        CatCollarComponent catCollarComponent = (CatCollarComponent) result;
        return this.color.equals(catCollarComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
