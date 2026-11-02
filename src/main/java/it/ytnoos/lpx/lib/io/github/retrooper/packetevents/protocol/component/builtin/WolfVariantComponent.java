package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant.WolfVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant.WolfVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class WolfVariantComponent {

    private WolfVariant variant;

    public WolfVariantComponent(WolfVariant wolfVariant) {
        this.variant = wolfVariant;
    }

    public static WolfVariantComponent read(PacketWrapper<?> packetWrapper) {
        WolfVariant wolfVariant = packetWrapper.readMappedEntity(WolfVariants.getRegistry());
        return new WolfVariantComponent(wolfVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WolfVariantComponent wolfVariantComponent) {
        packetWrapper.writeMappedEntity(wolfVariantComponent.variant);
    }

    public WolfVariant getVariant() {
        return this.variant;
    }

    public void setVariant(WolfVariant wolfVariant) {
        this.variant = wolfVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof WolfVariantComponent)) {
            return false;
        }
        WolfVariantComponent wolfVariantComponent = (WolfVariantComponent) result;
        return this.variant.equals(wolfVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
