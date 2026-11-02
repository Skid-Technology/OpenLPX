package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class ChickenSoundSet {

    public static final NbtCodec<ChickenSoundSet> CODEC =
            (new NbtMapCodec<ChickenSoundSet>() {

                        public ChickenSoundSet decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            Sound orThrow =
                                    nBTCompound.getOrThrow(
                                            "ambient_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "hurt_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow3 =
                                    nBTCompound.getOrThrow(
                                            "death_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow4 =
                                    nBTCompound.getOrThrow(
                                            "step_sound", Sound.CODEC, packetWrapper);
                            return new ChickenSoundSet(orThrow, orThrow2, orThrow3, orThrow4);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                ChickenSoundSet chickenSoundSet)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "ambient_sound",
                                    chickenSoundSet.ambientSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "hurt_sound",
                                    chickenSoundSet.hurtSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "death_sound",
                                    chickenSoundSet.deathSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "step_sound",
                                    chickenSoundSet.stepSound,
                                    Sound.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    private final Sound ambientSound;

    private final Sound hurtSound;

    private final Sound deathSound;

    private final Sound stepSound;

    public ChickenSoundSet(Sound sound, Sound sound2, Sound sound3, Sound sound4) {
        this.ambientSound = sound;
        this.hurtSound = sound2;
        this.deathSound = sound3;
        this.stepSound = sound4;
    }

    @Internal
    public static ChickenSoundSet getOrThrow(String text, Sound sound) {
        VersionedRegistry versionedRegistry;
        Sound sound2 =
                (Sound)
                        (versionedRegistry = Sounds.getRegistry())
                                .getByNameOrThrow(text + "ambient");
        Sound sound3 = (Sound) versionedRegistry.getByNameOrThrow(text + "hurt");
        Sound sound4 = (Sound) versionedRegistry.getByNameOrThrow(text + "death");
        return new ChickenSoundSet(sound2, sound3, sound4, sound);
    }

    public Sound getAmbientSound() {
        return this.ambientSound;
    }

    public Sound getHurtSound() {
        return this.hurtSound;
    }

    public Sound getDeathSound() {
        return this.deathSound;
    }

    public Sound getStepSound() {
        return this.stepSound;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ChickenSoundSet)) {
            return false;
        } else {
            ChickenSoundSet chickenSoundSet = (ChickenSoundSet) result;
            if (!this.ambientSound.equals(chickenSoundSet.ambientSound)) {
                return false;
            } else if (!this.hurtSound.equals(chickenSoundSet.hurtSound)) {
                return false;
            } else {
                return !this.deathSound.equals(chickenSoundSet.deathSound)
                        ? false
                        : this.stepSound.equals(chickenSoundSet.stepSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ambientSound, this.hurtSound, this.deathSound, this.stepSound);
    }
}
