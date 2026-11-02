package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat;

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
public final class CatSoundSet {

    public static final NbtCodec<CatSoundSet> CODEC =
            (new NbtMapCodec<CatSoundSet>() {

                        public CatSoundSet decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            Sound orThrow =
                                    nBTCompound.getOrThrow(
                                            "ambient_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "stray_ambient_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow3 =
                                    nBTCompound.getOrThrow(
                                            "hiss_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow4 =
                                    nBTCompound.getOrThrow(
                                            "hurt_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow5 =
                                    nBTCompound.getOrThrow(
                                            "death_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow6 =
                                    nBTCompound.getOrThrow("eat_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow7 =
                                    nBTCompound.getOrThrow(
                                            "beg_for_food_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow8 =
                                    nBTCompound.getOrThrow(
                                            "purr_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow9 =
                                    nBTCompound.getOrThrow(
                                            "purreow_sound", Sound.CODEC, packetWrapper);
                            return new CatSoundSet(
                                    orThrow, orThrow2, orThrow3, orThrow4, orThrow5, orThrow6,
                                    orThrow7, orThrow8, orThrow9);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                CatSoundSet catSoundSet)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "ambient_sound",
                                    catSoundSet.ambientSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "stray_ambient_sound",
                                    catSoundSet.strayAmbientSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "hiss_sound",
                                    catSoundSet.hissSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "hurt_sound",
                                    catSoundSet.hurtSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "death_sound",
                                    catSoundSet.deathSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "eat_sound", catSoundSet.eatSound, Sound.CODEC, packetWrapper);
                            nBTCompound.set(
                                    "beg_for_food_sound",
                                    catSoundSet.begForFoodSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "purr_sound",
                                    catSoundSet.purrSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "purreow_sound",
                                    catSoundSet.purreowSound,
                                    Sound.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    private final Sound ambientSound;

    private final Sound strayAmbientSound;

    private final Sound hissSound;

    private final Sound hurtSound;

    private final Sound deathSound;

    private final Sound eatSound;

    private final Sound begForFoodSound;

    private final Sound purrSound;

    private final Sound purreowSound;

    public CatSoundSet(
            Sound sound,
            Sound sound2,
            Sound sound3,
            Sound sound4,
            Sound sound5,
            Sound sound6,
            Sound sound7,
            Sound sound8,
            Sound sound9) {
        this.ambientSound = sound;
        this.strayAmbientSound = sound2;
        this.hissSound = sound3;
        this.hurtSound = sound4;
        this.deathSound = sound5;
        this.eatSound = sound6;
        this.begForFoodSound = sound7;
        this.purrSound = sound8;
        this.purreowSound = sound9;
    }

    @Internal
    public static CatSoundSet getOrThrow(String text) {
        VersionedRegistry versionedRegistry;
        Sound sound =
                (Sound)
                        (versionedRegistry = Sounds.getRegistry())
                                .getByNameOrThrow(text + "ambient");
        Sound sound2 = (Sound) versionedRegistry.getByNameOrThrow(text + "stray_ambient");
        Sound sound3 = (Sound) versionedRegistry.getByNameOrThrow(text + "hiss");
        Sound sound4 = (Sound) versionedRegistry.getByNameOrThrow(text + "hurt");
        Sound sound5 = (Sound) versionedRegistry.getByNameOrThrow(text + "death");
        Sound sound6 = (Sound) versionedRegistry.getByNameOrThrow(text + "eat");
        Sound sound7 = (Sound) versionedRegistry.getByNameOrThrow(text + "beg_for_food");
        Sound sound8 = (Sound) versionedRegistry.getByNameOrThrow(text + "purr");
        Sound sound9 = (Sound) versionedRegistry.getByNameOrThrow(text + "purreow");
        return new CatSoundSet(
                sound, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9);
    }

    public Sound getAmbientSound() {
        return this.ambientSound;
    }

    public Sound getStrayAmbientSound() {
        return this.strayAmbientSound;
    }

    public Sound getHissSound() {
        return this.hissSound;
    }

    public Sound getHurtSound() {
        return this.hurtSound;
    }

    public Sound getDeathSound() {
        return this.deathSound;
    }

    public Sound getEatSound() {
        return this.eatSound;
    }

    public Sound getBegForFoodSound() {
        return this.begForFoodSound;
    }

    public Sound getPurrSound() {
        return this.purrSound;
    }

    public Sound getPurreowSound() {
        return this.purreowSound;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof CatSoundSet)) {
            return false;
        } else {
            CatSoundSet catSoundSet = (CatSoundSet) result;
            if (!this.ambientSound.equals(catSoundSet.ambientSound)) {
                return false;
            } else if (!this.strayAmbientSound.equals(catSoundSet.strayAmbientSound)) {
                return false;
            } else if (!this.hissSound.equals(catSoundSet.hissSound)) {
                return false;
            } else if (!this.hurtSound.equals(catSoundSet.hurtSound)) {
                return false;
            } else if (!this.deathSound.equals(catSoundSet.deathSound)) {
                return false;
            } else if (!this.eatSound.equals(catSoundSet.eatSound)) {
                return false;
            } else if (!this.begForFoodSound.equals(catSoundSet.begForFoodSound)) {
                return false;
            } else {
                return !this.purrSound.equals(catSoundSet.purrSound)
                        ? false
                        : this.purreowSound.equals(catSoundSet.purreowSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.ambientSound,
                this.strayAmbientSound,
                this.hissSound,
                this.hurtSound,
                this.deathSound,
                this.eatSound,
                this.begForFoodSound,
                this.purrSound,
                this.purreowSound);
    }
}
