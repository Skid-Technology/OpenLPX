package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.rabbit.RabbitVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.rabbit.RabbitVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class RabbitVariantComponent {

    private RabbitVariant variant;

    public RabbitVariantComponent(RabbitVariant rabbitVariant) {
        this.variant = rabbitVariant;
    }

    public static RabbitVariantComponent read(PacketWrapper<?> packetWrapper) {
        RabbitVariant rabbitVariant = packetWrapper.readMappedEntity(RabbitVariants.getRegistry());
        return new RabbitVariantComponent(rabbitVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, RabbitVariantComponent rabbitVariantComponent) {
        packetWrapper.writeMappedEntity(rabbitVariantComponent.variant);
    }

    public RabbitVariant getVariant() {
        return this.variant;
    }

    public void setVariant(RabbitVariant rabbitVariant) {
        this.variant = rabbitVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof RabbitVariantComponent)) {
            return false;
        }
        RabbitVariantComponent rabbitVariantComponent = (RabbitVariantComponent) result;
        return this.variant.equals(rabbitVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
