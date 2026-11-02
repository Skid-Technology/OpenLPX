package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
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
public final class WolfSoundSet {

    public static final NbtCodec<WolfSoundSet> CODEC =
            (new NbtMapCodec<WolfSoundSet>() {

                        public WolfSoundSet decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            Sound orThrow =
                                    nBTCompound.getOrThrow(
                                            "ambient_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "death_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow3 =
                                    nBTCompound.getOrThrow(
                                            "growl_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow4 =
                                    nBTCompound.getOrThrow(
                                            "hurt_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow5 =
                                    nBTCompound.getOrThrow(
                                            "pant_sound", Sound.CODEC, packetWrapper);
                            Sound orThrow6 =
                                    nBTCompound.getOrThrow(
                                            "whine_sound", Sound.CODEC, packetWrapper);
                            Sound sound =
                                    packetWrapper
                                                    .getServerVersion()
                                                    .isOlderThan(ServerVersion.V_26_1)
                                            ? Sounds.ENTITY_WOLF_STEP
                                            : nBTCompound.getOrThrow(
                                                    "step_sound", Sound.CODEC, packetWrapper);
                            return new WolfSoundSet(
                                    orThrow, orThrow2, orThrow3, orThrow4, orThrow5, orThrow6,
                                    sound);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                WolfSoundSet wolfSoundSet)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "ambient_sound",
                                    wolfSoundSet.ambientSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "death_sound",
                                    wolfSoundSet.deathSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "growl_sound",
                                    wolfSoundSet.growlSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "hurt_sound",
                                    wolfSoundSet.hurtSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "pant_sound",
                                    wolfSoundSet.pantSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "whine_sound",
                                    wolfSoundSet.whineSound,
                                    Sound.CODEC,
                                    packetWrapper);
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_26_1)) {
                                nBTCompound.set(
                                        "step_sound",
                                        wolfSoundSet.stepSound,
                                        Sound.CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    private final Sound ambientSound;

    private final Sound deathSound;

    private final Sound growlSound;

    private final Sound hurtSound;

    private final Sound pantSound;

    private final Sound whineSound;

    private final Sound stepSound;

    public WolfSoundSet(
            Sound sound,
            Sound sound2,
            Sound sound3,
            Sound sound4,
            Sound sound5,
            Sound sound6,
            Sound sound7) {
        this.ambientSound = sound;
        this.deathSound = sound2;
        this.growlSound = sound3;
        this.hurtSound = sound4;
        this.pantSound = sound5;
        this.whineSound = sound6;
        this.stepSound = sound7;
    }

    @Internal
    public static WolfSoundSet getOrThrow(String text, Sound sound) {
        VersionedRegistry versionedRegistry;
        Sound sound2 =
                (Sound)
                        (versionedRegistry = Sounds.getRegistry())
                                .getByNameOrThrow(text + "ambient");
        Sound sound3 = (Sound) versionedRegistry.getByNameOrThrow(text + "death");
        Sound sound4 = (Sound) versionedRegistry.getByNameOrThrow(text + "growl");
        Sound sound5 = (Sound) versionedRegistry.getByNameOrThrow(text + "hurt");
        Sound sound6 = (Sound) versionedRegistry.getByNameOrThrow(text + "pant");
        Sound sound7 = (Sound) versionedRegistry.getByNameOrThrow(text + "whine");
        return new WolfSoundSet(sound2, sound3, sound4, sound5, sound6, sound7, sound);
    }

    public Sound getAmbientSound() {
        return this.ambientSound;
    }

    public Sound getDeathSound() {
        return this.deathSound;
    }

    public Sound getGrowlSound() {
        return this.growlSound;
    }

    public Sound getHurtSound() {
        return this.hurtSound;
    }

    public Sound getPantSound() {
        return this.pantSound;
    }

    public Sound getWhineSound() {
        return this.whineSound;
    }

    public Sound getStepSound() {
        return this.stepSound;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof WolfSoundSet)) {
            return false;
        } else {
            WolfSoundSet wolfSoundSet = (WolfSoundSet) result;
            if (!this.ambientSound.equals(wolfSoundSet.ambientSound)) {
                return false;
            } else if (!this.deathSound.equals(wolfSoundSet.deathSound)) {
                return false;
            } else if (!this.growlSound.equals(wolfSoundSet.growlSound)) {
                return false;
            } else if (!this.hurtSound.equals(wolfSoundSet.hurtSound)) {
                return false;
            } else if (!this.pantSound.equals(wolfSoundSet.pantSound)) {
                return false;
            } else {
                return !this.whineSound.equals(wolfSoundSet.whineSound)
                        ? false
                        : this.stepSound.equals(wolfSoundSet.stepSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.ambientSound,
                this.deathSound,
                this.growlSound,
                this.hurtSound,
                this.pantSound,
                this.whineSound,
                this.stepSound);
    }
}
