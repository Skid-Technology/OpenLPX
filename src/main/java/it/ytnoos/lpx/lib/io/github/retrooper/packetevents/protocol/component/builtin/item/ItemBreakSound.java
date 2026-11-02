package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemBreakSound {

    private Sound sound;

    public ItemBreakSound(Sound sound2) {
        this.sound = sound2;
    }

    public static ItemBreakSound read(PacketWrapper<?> packetWrapper) {
        Sound sound = Sound.read(packetWrapper);
        return new ItemBreakSound(sound);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemBreakSound itemBreakSound) {
        Sound.write(packetWrapper, itemBreakSound.sound);
    }

    public Sound getSound() {
        return this.sound;
    }

    public void setSound(Sound sound2) {
        this.sound = sound2;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemBreakSound)) {
            return false;
        }
        ItemBreakSound itemBreakSound = (ItemBreakSound) result;
        return this.sound.equals(itemBreakSound.sound);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.sound);
    }
}
