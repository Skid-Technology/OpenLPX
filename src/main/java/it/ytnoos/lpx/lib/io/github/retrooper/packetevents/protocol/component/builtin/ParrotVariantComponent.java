package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.parrot.ParrotVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.parrot.ParrotVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ParrotVariantComponent {

    private ParrotVariant variant;

    public ParrotVariantComponent(ParrotVariant parrotVariant) {
        this.variant = parrotVariant;
    }

    public static ParrotVariantComponent read(PacketWrapper<?> packetWrapper) {
        ParrotVariant parrotVariant = packetWrapper.readMappedEntity(ParrotVariants.getRegistry());
        return new ParrotVariantComponent(parrotVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParrotVariantComponent parrotVariantComponent) {
        packetWrapper.writeMappedEntity(parrotVariantComponent.variant);
    }

    public ParrotVariant getVariant() {
        return this.variant;
    }

    public void setVariant(ParrotVariant parrotVariant) {
        this.variant = parrotVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ParrotVariantComponent)) {
            return false;
        }
        ParrotVariantComponent parrotVariantComponent = (ParrotVariantComponent) result;
        return this.variant.equals(parrotVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
