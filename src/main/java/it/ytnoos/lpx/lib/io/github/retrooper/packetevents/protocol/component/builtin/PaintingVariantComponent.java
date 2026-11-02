package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.painting.PaintingVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class PaintingVariantComponent {

    private PaintingVariant variant;

    public PaintingVariantComponent(PaintingVariant paintingVariant) {
        this.variant = paintingVariant;
    }

    public static PaintingVariantComponent read(PacketWrapper<?> packetWrapper) {
        PaintingVariant paintingVariant = PaintingVariant.read(packetWrapper);
        return new PaintingVariantComponent(paintingVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, PaintingVariantComponent paintingVariantComponent) {
        PaintingVariant.write(packetWrapper, paintingVariantComponent.variant);
    }

    public PaintingVariant getVariant() {
        return this.variant;
    }

    public void setVariant(PaintingVariant paintingVariant) {
        this.variant = paintingVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof PaintingVariantComponent)) {
            return false;
        }
        PaintingVariantComponent paintingVariantComponent = (PaintingVariantComponent) result;
        return this.variant.equals(paintingVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
