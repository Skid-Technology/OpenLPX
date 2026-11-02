package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig.PigSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig.PigSoundVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class PigSoundVariantComponent {

    private final PigSoundVariant soundVariant;

    public PigSoundVariantComponent(PigSoundVariant pigSoundVariant) {
        this.soundVariant = pigSoundVariant;
    }

    public static PigSoundVariantComponent read(PacketWrapper<?> packetWrapper) {
        PigSoundVariant pigSoundVariant =
                packetWrapper.readMappedEntity(PigSoundVariants.getRegistry());
        return new PigSoundVariantComponent(pigSoundVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, PigSoundVariantComponent pigSoundVariantComponent) {
        packetWrapper.writeMappedEntity(pigSoundVariantComponent.soundVariant);
    }

    public PigSoundVariant getSoundVariant() {
        return this.soundVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof PigSoundVariantComponent)) {
            return false;
        }
        PigSoundVariantComponent pigSoundVariantComponent = (PigSoundVariantComponent) result;
        return this.soundVariant.equals(pigSoundVariantComponent.soundVariant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.soundVariant);
    }
}
