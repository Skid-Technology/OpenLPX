package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat.CatSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat.CatSoundVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class CatSoundVariantComponent {

    private final CatSoundVariant soundVariant;

    public CatSoundVariantComponent(CatSoundVariant catSoundVariant) {
        this.soundVariant = catSoundVariant;
    }

    public static CatSoundVariantComponent read(PacketWrapper<?> packetWrapper) {
        CatSoundVariant catSoundVariant =
                packetWrapper.readMappedEntity(CatSoundVariants.getRegistry());
        return new CatSoundVariantComponent(catSoundVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CatSoundVariantComponent catSoundVariantComponent) {
        packetWrapper.writeMappedEntity(catSoundVariantComponent.soundVariant);
    }

    public CatSoundVariant getSoundVariant() {
        return this.soundVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof CatSoundVariantComponent)) {
            return false;
        }
        CatSoundVariantComponent catSoundVariantComponent = (CatSoundVariantComponent) result;
        return this.soundVariant.equals(catSoundVariantComponent.soundVariant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.soundVariant);
    }
}
