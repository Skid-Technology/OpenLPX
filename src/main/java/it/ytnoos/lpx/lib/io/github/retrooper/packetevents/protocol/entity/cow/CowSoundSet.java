package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

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
public final class CowSoundSet {

    public static final NbtCodec<CowSoundSet> CODEC =
            (new NbtMapCodec<CowSoundSet>() {

                        public CowSoundSet decode(
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
                            return new CowSoundSet(orThrow, orThrow2, orThrow3, orThrow4);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                CowSoundSet cowSoundSet)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "ambient_sound",
                                    cowSoundSet.ambientSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "hurt_sound",
                                    cowSoundSet.hurtSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "death_sound",
                                    cowSoundSet.deathSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "step_sound",
                                    cowSoundSet.stepSound,
                                    Sound.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    private final Sound ambientSound;

    private final Sound hurtSound;

    private final Sound deathSound;

    private final Sound stepSound;

    public CowSoundSet(Sound sound, Sound sound2, Sound sound3, Sound sound4) {
        this.ambientSound = sound;
        this.hurtSound = sound2;
        this.deathSound = sound3;
        this.stepSound = sound4;
    }

    @Internal
    public static CowSoundSet getOrThrow(String text) {
        VersionedRegistry versionedRegistry;
        Sound sound =
                (Sound)
                        (versionedRegistry = Sounds.getRegistry())
                                .getByNameOrThrow(text + "ambient");
        Sound sound2 = (Sound) versionedRegistry.getByNameOrThrow(text + "hurt");
        Sound sound3 = (Sound) versionedRegistry.getByNameOrThrow(text + "death");
        Sound sound4 = (Sound) versionedRegistry.getByNameOrThrow(text + "step");
        return new CowSoundSet(sound, sound2, sound3, sound4);
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
        if (!(result instanceof CowSoundSet)) {
            return false;
        } else {
            CowSoundSet cowSoundSet = (CowSoundSet) result;
            if (!this.ambientSound.equals(cowSoundSet.ambientSound)) {
                return false;
            } else if (!this.hurtSound.equals(cowSoundSet.hurtSound)) {
                return false;
            } else {
                return !this.deathSound.equals(cowSoundSet.deathSound)
                        ? false
                        : this.stepSound.equals(cowSoundSet.stepSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ambientSound, this.hurtSound, this.deathSound, this.stepSound);
    }
}
