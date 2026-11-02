package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig;

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
public final class PigSoundSet {

    public static final NbtCodec<PigSoundSet> CODEC =
            (new NbtMapCodec<PigSoundSet>() {

                        public PigSoundSet decode(
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
                            Sound orThrow5 =
                                    nBTCompound.getOrThrow("eat_sound", Sound.CODEC, packetWrapper);
                            return new PigSoundSet(orThrow, orThrow2, orThrow3, orThrow4, orThrow5);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                PigSoundSet pigSoundSet)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "ambient_sound",
                                    pigSoundSet.ambientSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "hurt_sound",
                                    pigSoundSet.hurtSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "death_sound",
                                    pigSoundSet.deathSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "step_sound",
                                    pigSoundSet.stepSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "eat_sound", pigSoundSet.eatSound, Sound.CODEC, packetWrapper);
                        }
                    })
                    .codec();

    private final Sound ambientSound;

    private final Sound hurtSound;

    private final Sound deathSound;

    private final Sound stepSound;

    private final Sound eatSound;

    public PigSoundSet(Sound sound, Sound sound2, Sound sound3, Sound sound4, Sound sound5) {
        this.ambientSound = sound;
        this.hurtSound = sound2;
        this.deathSound = sound3;
        this.stepSound = sound4;
        this.eatSound = sound5;
    }

    @Internal
    public static PigSoundSet getOrThrow(String text, Sound sound) {
        VersionedRegistry versionedRegistry;
        Sound sound2 =
                (Sound)
                        (versionedRegistry = Sounds.getRegistry())
                                .getByNameOrThrow(text + "ambient");
        Sound sound3 = (Sound) versionedRegistry.getByNameOrThrow(text + "hurt");
        Sound sound4 = (Sound) versionedRegistry.getByNameOrThrow(text + "death");
        Sound sound5 = (Sound) versionedRegistry.getByNameOrThrow(text + "eat");
        return new PigSoundSet(sound2, sound3, sound4, sound, sound5);
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

    public Sound getEatSound() {
        return this.eatSound;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            PigSoundSet pigSoundSet = (PigSoundSet) result;
            if (!this.ambientSound.equals(pigSoundSet.ambientSound)) {
                return false;
            } else if (!this.hurtSound.equals(pigSoundSet.hurtSound)) {
                return false;
            } else if (!this.deathSound.equals(pigSoundSet.deathSound)) {
                return false;
            } else {
                return !this.stepSound.equals(pigSoundSet.stepSound)
                        ? false
                        : this.eatSound.equals(pigSoundSet.eatSound);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.ambientSound, this.hurtSound, this.deathSound, this.stepSound, this.eatSound);
    }
}
