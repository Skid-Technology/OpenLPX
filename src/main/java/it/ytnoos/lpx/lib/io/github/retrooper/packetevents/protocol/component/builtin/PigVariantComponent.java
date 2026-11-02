package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig.PigVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig.PigVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class PigVariantComponent {

    private PigVariant variant;

    public PigVariantComponent(PigVariant pigVariant) {
        this.variant = pigVariant;
    }

    public static PigVariantComponent read(PacketWrapper<?> packetWrapper) {
        PigVariant pigVariant = packetWrapper.readMappedEntity(PigVariants.getRegistry());
        return new PigVariantComponent(pigVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, PigVariantComponent pigVariantComponent) {
        packetWrapper.writeMappedEntity(pigVariantComponent.variant);
    }

    public PigVariant getVariant() {
        return this.variant;
    }

    public void setVariant(PigVariant pigVariant) {
        this.variant = pigVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof PigVariantComponent)) {
            return false;
        }
        PigVariantComponent pigVariantComponent = (PigVariantComponent) result;
        return this.variant.equals(pigVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
