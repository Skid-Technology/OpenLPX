package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat.CatVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat.CatVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class CatVariantComponent {

    private CatVariant variant;

    public CatVariantComponent(CatVariant catVariant) {
        this.variant = catVariant;
    }

    public static CatVariantComponent read(PacketWrapper<?> packetWrapper) {
        CatVariant catVariant = packetWrapper.readMappedEntity(CatVariants.getRegistry());
        return new CatVariantComponent(catVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CatVariantComponent catVariantComponent) {
        packetWrapper.writeMappedEntity(catVariantComponent.variant);
    }

    public CatVariant getVariant() {
        return this.variant;
    }

    public void setVariant(CatVariant catVariant) {
        this.variant = catVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof CatVariantComponent)) {
            return false;
        }
        CatVariantComponent catVariantComponent = (CatVariantComponent) result;
        return this.variant.equals(catVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
