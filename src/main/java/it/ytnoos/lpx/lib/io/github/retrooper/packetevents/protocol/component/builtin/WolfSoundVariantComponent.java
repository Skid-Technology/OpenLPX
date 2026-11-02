package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant.WolfSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant.WolfSoundVariants;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class WolfSoundVariantComponent {

    private WolfSoundVariant soundVariant;

    public WolfSoundVariantComponent(WolfSoundVariant wolfSoundVariant) {
        this.soundVariant = wolfSoundVariant;
    }

    public static WolfSoundVariantComponent read(PacketWrapper<?> packetWrapper) {
        WolfSoundVariant wolfSoundVariant =
                packetWrapper.readMappedEntity(WolfSoundVariants.getRegistry());
        return new WolfSoundVariantComponent(wolfSoundVariant);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WolfSoundVariantComponent wolfSoundVariantComponent) {
        packetWrapper.writeMappedEntity(wolfSoundVariantComponent.soundVariant);
    }

    public WolfSoundVariant getSoundVariant() {
        return this.soundVariant;
    }

    public void setSoundVariant(WolfSoundVariant wolfSoundVariant) {
        this.soundVariant = wolfSoundVariant;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof WolfSoundVariantComponent)) {
            return false;
        }
        WolfSoundVariantComponent wolfSoundVariantComponent = (WolfSoundVariantComponent) result;
        return this.soundVariant.equals(wolfSoundVariantComponent.soundVariant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.soundVariant);
    }
}
