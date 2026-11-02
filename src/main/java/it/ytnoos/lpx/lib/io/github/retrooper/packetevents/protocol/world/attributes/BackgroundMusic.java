package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.BiomeEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.RandomWeightedList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

@NullMarked
public class BackgroundMusic {

    public static final NbtCodec<BackgroundMusic> CODEC =
            (new NbtMapCodec<BackgroundMusic>() {

                        public BackgroundMusic decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            BiomeEffects.MusicSettings orNull =
                                    nBTCompound.getOrNull(
                                            "default",
                                            BiomeEffects.MusicSettings.CODEC,
                                            packetWrapper);
                            BiomeEffects.MusicSettings orNull2 =
                                    nBTCompound.getOrNull(
                                            "creative",
                                            BiomeEffects.MusicSettings.CODEC,
                                            packetWrapper);
                            BiomeEffects.MusicSettings orNull3 =
                                    nBTCompound.getOrNull(
                                            "underwater",
                                            BiomeEffects.MusicSettings.CODEC,
                                            packetWrapper);
                            return new BackgroundMusic(orNull, orNull2, orNull3);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                BackgroundMusic backgroundMusic)
                                throws NbtCodecException {
                            if (backgroundMusic.defaultMusic != null) {
                                nBTCompound.set(
                                        "default",
                                        backgroundMusic.defaultMusic,
                                        BiomeEffects.MusicSettings.CODEC,
                                        packetWrapper);
                            }
                            if (backgroundMusic.creativeMusic != null) {
                                nBTCompound.set(
                                        "creative",
                                        backgroundMusic.creativeMusic,
                                        BiomeEffects.MusicSettings.CODEC,
                                        packetWrapper);
                            }
                            if (backgroundMusic.underwaterMusic != null) {
                                nBTCompound.set(
                                        "underwater",
                                        backgroundMusic.underwaterMusic,
                                        BiomeEffects.MusicSettings.CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    public static final BackgroundMusic EMPTY = new BackgroundMusic(null, null, null);

    private final BiomeEffects.@Nullable MusicSettings defaultMusic;

    private final BiomeEffects.@Nullable MusicSettings creativeMusic;

    private final BiomeEffects.@Nullable MusicSettings underwaterMusic;

    public BackgroundMusic(
            BiomeEffects.@Nullable MusicSettings musicSettings,
            BiomeEffects.@Nullable MusicSettings musicSettings2,
            BiomeEffects.@Nullable MusicSettings musicSettings3) {
        this.defaultMusic = musicSettings;
        this.creativeMusic = musicSettings2;
        this.underwaterMusic = musicSettings3;
    }

    @Internal
    public RandomWeightedList<BiomeEffects.MusicSettings> asList() {
        ArrayList<RandomWeightedList.Entry<BiomeEffects.MusicSettings>> values = new ArrayList<>(3);
        if (this.defaultMusic != null) {
            values.add(new RandomWeightedList.Entry<>(this.defaultMusic, 0));
        }
        if (this.creativeMusic != null) {
            values.add(new RandomWeightedList.Entry<>(this.creativeMusic, 0));
        }
        if (this.underwaterMusic != null) {
            values.add(new RandomWeightedList.Entry<>(this.underwaterMusic, 0));
        }
        return new RandomWeightedList<>(values);
    }

    public BiomeEffects.@Nullable MusicSettings getDefaultMusic() {
        return this.defaultMusic;
    }

    public BiomeEffects.@Nullable MusicSettings getCreativeMusic() {
        return this.creativeMusic;
    }

    public BiomeEffects.@Nullable MusicSettings getUnderwaterMusic() {
        return this.underwaterMusic;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof BackgroundMusic)) {
            return false;
        } else {
            BackgroundMusic backgroundMusic = (BackgroundMusic) result;
            if (!Objects.equals(this.defaultMusic, backgroundMusic.defaultMusic)) {
                return false;
            } else {
                return !Objects.equals(this.creativeMusic, backgroundMusic.creativeMusic)
                        ? false
                        : Objects.equals(this.underwaterMusic, backgroundMusic.underwaterMusic);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.defaultMusic, this.creativeMusic, this.underwaterMusic);
    }
}
