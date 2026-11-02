package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.salmon.SalmonVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.salmon.SalmonVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class SalmonSizeComponent {

    private SalmonVariant variant;

    public SalmonSizeComponent(SalmonVariant salmonVariant) {
        this.variant = salmonVariant;
    }

    public static SalmonSizeComponent read(PacketWrapper<?> packetWrapper) {
        SalmonVariant salmonVariant = packetWrapper.readMappedEntity(SalmonVariants.getRegistry());
        return new SalmonSizeComponent(salmonVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SalmonSizeComponent salmonSizeComponent) {
        packetWrapper.writeMappedEntity(salmonSizeComponent.variant);
    }

    public SalmonVariant getVariant() {
        return this.variant;
    }

    public void setVariant(SalmonVariant salmonVariant) {
        this.variant = salmonVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof SalmonSizeComponent)) {
            return false;
        }
        SalmonSizeComponent salmonSizeComponent = (SalmonSizeComponent) result;
        return this.variant.equals(salmonSizeComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
