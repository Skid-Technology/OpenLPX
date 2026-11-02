package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.horse.HorseVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.horse.HorseVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class HorseVariantComponent {

    private HorseVariant variant;

    public HorseVariantComponent(HorseVariant horseVariant) {
        this.variant = horseVariant;
    }

    public static HorseVariantComponent read(PacketWrapper<?> packetWrapper) {
        HorseVariant horseVariant = packetWrapper.readMappedEntity(HorseVariants.getRegistry());
        return new HorseVariantComponent(horseVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, HorseVariantComponent horseVariantComponent) {
        packetWrapper.writeMappedEntity(horseVariantComponent.variant);
    }

    public HorseVariant getVariant() {
        return this.variant;
    }

    public void setVariant(HorseVariant horseVariant) {
        this.variant = horseVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof HorseVariantComponent)) {
            return false;
        }
        HorseVariantComponent horseVariantComponent = (HorseVariantComponent) result;
        return this.variant.equals(horseVariantComponent.variant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.variant);
    }
}
