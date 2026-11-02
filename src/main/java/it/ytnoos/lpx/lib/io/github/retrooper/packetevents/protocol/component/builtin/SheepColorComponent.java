package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class SheepColorComponent {

    private DyeColor color;

    public SheepColorComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static SheepColorComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new SheepColorComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SheepColorComponent sheepColorComponent) {
        DyeColor.write(packetWrapper, sheepColorComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof SheepColorComponent)) {
            return false;
        }
        SheepColorComponent sheepColorComponent = (SheepColorComponent) result;
        return this.color.equals(sheepColorComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
