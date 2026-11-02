package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow.CowSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow.CowSoundVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class CowSoundVariantComponent {

    private final CowSoundVariant soundVariant;

    public CowSoundVariantComponent(CowSoundVariant cowSoundVariant) {
        this.soundVariant = cowSoundVariant;
    }

    public static CowSoundVariantComponent read(PacketWrapper<?> packetWrapper) {
        CowSoundVariant cowSoundVariant =
                packetWrapper.readMappedEntity(CowSoundVariants.getRegistry());
        return new CowSoundVariantComponent(cowSoundVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CowSoundVariantComponent cowSoundVariantComponent) {
        packetWrapper.writeMappedEntity(cowSoundVariantComponent.soundVariant);
    }

    public CowSoundVariant getSoundVariant() {
        return this.soundVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof CowSoundVariantComponent)) {
            return false;
        }
        CowSoundVariantComponent cowSoundVariantComponent = (CowSoundVariantComponent) result;
        return this.soundVariant.equals(cowSoundVariantComponent.soundVariant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.soundVariant);
    }
}
