package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffectTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class PlaySoundConsumeEffect extends ConsumeEffect<PlaySoundConsumeEffect> {

    private final Sound sound;

    public PlaySoundConsumeEffect(Sound sound2) {
        super(ConsumeEffectTypes.PLAY_SOUND);
        this.sound = sound2;
    }

    public static PlaySoundConsumeEffect read(PacketWrapper<?> packetWrapper) {
        Sound sound = Sound.read(packetWrapper);
        return new PlaySoundConsumeEffect(sound);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, PlaySoundConsumeEffect playSoundConsumeEffect) {
        Sound.write(packetWrapper, playSoundConsumeEffect.sound);
    }

    public Sound getSound() {
        return this.sound;
    }
}
