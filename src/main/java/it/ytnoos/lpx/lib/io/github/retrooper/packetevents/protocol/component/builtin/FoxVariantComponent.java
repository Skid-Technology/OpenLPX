package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.fox.FoxVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.fox.FoxVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class FoxVariantComponent {

    private FoxVariant variant;

    public FoxVariantComponent(FoxVariant foxVariant) {
        this.variant = foxVariant;
    }

    public static FoxVariantComponent read(PacketWrapper<?> packetWrapper) {
        FoxVariant foxVariant = packetWrapper.readMappedEntity(FoxVariants.getRegistry());
        return new FoxVariantComponent(foxVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, FoxVariantComponent foxVariantComponent) {
        packetWrapper.writeMappedEntity(foxVariantComponent.variant);
    }

    public FoxVariant getVariant() {
        return this.variant;
    }

    public void setVariant(FoxVariant foxVariant) {
        this.variant = foxVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof FoxVariantComponent)) {
            return false;
        }
        FoxVariantComponent foxVariantComponent = (FoxVariantComponent) result;
        return this.variant.equals(foxVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
