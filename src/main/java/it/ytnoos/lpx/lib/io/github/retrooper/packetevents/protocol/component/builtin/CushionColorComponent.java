package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class CushionColorComponent {

    private DyeColor color;

    public CushionColorComponent(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    public static CushionColorComponent read(PacketWrapper<?> packetWrapper) {
        DyeColor dyeColor = DyeColor.read(packetWrapper);
        return new CushionColorComponent(dyeColor);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CushionColorComponent cushionColorComponent) {
        DyeColor.write(packetWrapper, cushionColorComponent.color);
    }

    public DyeColor getDyeColor() {
        return this.color;
    }

    public void setDyeColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (!(result instanceof CushionColorComponent)) {
            return false;
        }
        CushionColorComponent cushionColorComponent = (CushionColorComponent) result;
        return this.color.equals(cushionColorComponent.color);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
