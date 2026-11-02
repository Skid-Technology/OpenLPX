package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.axolotl.AxolotlVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.axolotl.AxolotlVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class AxolotlVariantComponent {

    private AxolotlVariant variant;

    public AxolotlVariantComponent(AxolotlVariant axolotlVariant) {
        this.variant = axolotlVariant;
    }

    public static AxolotlVariantComponent read(PacketWrapper<?> packetWrapper) {
        AxolotlVariant axolotlVariant =
                packetWrapper.readMappedEntity(AxolotlVariants.getRegistry());
        return new AxolotlVariantComponent(axolotlVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, AxolotlVariantComponent axolotlVariantComponent) {
        packetWrapper.writeMappedEntity(axolotlVariantComponent.variant);
    }

    public AxolotlVariant getVariant() {
        return this.variant;
    }

    public void setVariant(AxolotlVariant axolotlVariant) {
        this.variant = axolotlVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof AxolotlVariantComponent)) {
            return false;
        }
        AxolotlVariantComponent axolotlVariantComponent = (AxolotlVariantComponent) result;
        return this.variant.equals(axolotlVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
