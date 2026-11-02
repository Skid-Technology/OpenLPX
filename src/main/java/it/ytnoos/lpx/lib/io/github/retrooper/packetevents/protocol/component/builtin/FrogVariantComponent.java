package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.frog.FrogVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.frog.FrogVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class FrogVariantComponent {

    private FrogVariant variant;

    public FrogVariantComponent(FrogVariant frogVariant) {
        this.variant = frogVariant;
    }

    public static FrogVariantComponent read(PacketWrapper<?> packetWrapper) {
        FrogVariant frogVariant = packetWrapper.readMappedEntity(FrogVariants.getRegistry());
        return new FrogVariantComponent(frogVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, FrogVariantComponent frogVariantComponent) {
        packetWrapper.writeMappedEntity(frogVariantComponent.variant);
    }

    public FrogVariant getVariant() {
        return this.variant;
    }

    public void setVariant(FrogVariant frogVariant) {
        this.variant = frogVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof FrogVariantComponent)) {
            return false;
        }
        FrogVariantComponent frogVariantComponent = (FrogVariantComponent) result;
        return this.variant.equals(frogVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
