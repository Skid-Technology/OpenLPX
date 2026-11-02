package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow.CowVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow.CowVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class CowVariantComponent {

    private CowVariant variant;

    public CowVariantComponent(CowVariant cowVariant) {
        this.variant = cowVariant;
    }

    public static CowVariantComponent read(PacketWrapper<?> packetWrapper) {
        CowVariant cowVariant = packetWrapper.readMappedEntity(CowVariants.getRegistry());
        return new CowVariantComponent(cowVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CowVariantComponent cowVariantComponent) {
        packetWrapper.writeMappedEntity(cowVariantComponent.variant);
    }

    public CowVariant getVariant() {
        return this.variant;
    }

    public void setVariant(CowVariant cowVariant) {
        this.variant = cowVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof CowVariantComponent)) {
            return false;
        }
        CowVariantComponent cowVariantComponent = (CowVariantComponent) result;
        return this.variant.equals(cowVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
