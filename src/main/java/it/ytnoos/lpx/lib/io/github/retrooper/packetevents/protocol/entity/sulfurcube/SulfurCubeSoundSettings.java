package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class SulfurCubeSoundSettings {

    public static final NbtCodec<SulfurCubeSoundSettings> CODEC =
            (new NbtMapCodec<SulfurCubeSoundSettings>() {

                        public SulfurCubeSoundSettings decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            Sound orThrow =
                                    nBTCompound.getOrThrow("hit_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "push_sound", Sound.CODEC, packetWrapper);
                            float value =
                                    nBTCompound
                                            .getNumberTagValueOrThrow(
                                                    "push_sound_impulse_threshold")
                                            .floatValue();
                            float value2 =
                                    nBTCompound
                                            .getNumberTagValueOrThrow("push_sound_cooldown")
                                            .floatValue();
                            return new SulfurCubeSoundSettings(orThrow, orThrow2, value, value2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                SulfurCubeSoundSettings sulfurCubeSoundSettings)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "hit_sound",
                                    sulfurCubeSoundSettings.hitSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "push_sound",
                                    sulfurCubeSoundSettings.pushSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.setTag(
                                    "push_sound_impulse_threshold",
                                    new NBTFloat(
                                            sulfurCubeSoundSettings.pushSoundImpulseThreshold));
                            nBTCompound.setTag(
                                    "push_sound_cooldown",
                                    new NBTFloat(sulfurCubeSoundSettings.pushSoundCooldown));
                        }
                    })
                    .codec();

    private final Sound hitSound;

    private final Sound pushSound;

    private final float pushSoundImpulseThreshold;

    private final float pushSoundCooldown;

    public SulfurCubeSoundSettings(
            Sound sound, Sound sound2, float pushSoundImpulseThreshold, float pushSoundCooldown) {
        this.hitSound = sound;
        this.pushSound = sound2;
        this.pushSoundImpulseThreshold = pushSoundImpulseThreshold;
        this.pushSoundCooldown = pushSoundCooldown;
    }

    public Sound getHitSound() {
        return this.hitSound;
    }

    public Sound getPushSound() {
        return this.pushSound;
    }

    public float getPushSoundImpulseThreshold() {
        return this.pushSoundImpulseThreshold;
    }

    public float getPushSoundCooldown() {
        return this.pushSoundCooldown;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            SulfurCubeSoundSettings sulfurCubeSoundSettings;
            if (Float.compare(
                            (sulfurCubeSoundSettings = (SulfurCubeSoundSettings) result)
                                    .pushSoundImpulseThreshold,
                            this.pushSoundImpulseThreshold)
                    != 0) {
                return false;
            } else if (Float.compare(
                            sulfurCubeSoundSettings.pushSoundCooldown, this.pushSoundCooldown)
                    != 0) {
                return false;
            } else {
                return !this.hitSound.equals(sulfurCubeSoundSettings.hitSound)
                        ? false
                        : this.pushSound.equals(sulfurCubeSoundSettings.pushSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.hitSound,
                this.pushSound,
                this.pushSoundImpulseThreshold,
                this.pushSoundCooldown);
    }
}
