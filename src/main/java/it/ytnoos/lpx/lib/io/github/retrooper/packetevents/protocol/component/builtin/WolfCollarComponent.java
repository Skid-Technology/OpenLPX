package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class WolfCollarComponent {

    private DyeColor color;

    public WolfCollarComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static WolfCollarComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new WolfCollarComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WolfCollarComponent wolfCollarComponent) {
        DyeColor.write(packetWrapper, wolfCollarComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof WolfCollarComponent)) {
            return false;
        }
        WolfCollarComponent wolfCollarComponent = (WolfCollarComponent) result;
        return this.color.equals(wolfCollarComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
