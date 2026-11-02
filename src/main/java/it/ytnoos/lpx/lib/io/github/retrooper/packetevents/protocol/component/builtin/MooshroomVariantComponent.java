package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.mooshroom.MooshroomVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.mooshroom.MooshroomVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class MooshroomVariantComponent {

    private MooshroomVariant variant;

    public MooshroomVariantComponent(MooshroomVariant mooshroomVariant) {
        this.variant = mooshroomVariant;
    }

    public static MooshroomVariantComponent read(PacketWrapper<?> packetWrapper) {
        MooshroomVariant mooshroomVariant =
                packetWrapper.readMappedEntity(MooshroomVariants.getRegistry());
        return new MooshroomVariantComponent(mooshroomVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, MooshroomVariantComponent mooshroomVariantComponent) {
        packetWrapper.writeMappedEntity(mooshroomVariantComponent.variant);
    }

    public MooshroomVariant getVariant() {
        return this.variant;
    }

    public void setVariant(MooshroomVariant mooshroomVariant) {
        this.variant = mooshroomVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof MooshroomVariantComponent)) {
            return false;
        }
        MooshroomVariantComponent mooshroomVariantComponent = (MooshroomVariantComponent) result;
        return this.variant.equals(mooshroomVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
