package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.BiomeEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public class AmbientSounds {

    public static final NbtCodec<AmbientSounds> CODEC =
            (new NbtMapCodec<AmbientSounds>() {

                        public AmbientSounds decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            Sound orNull =
                                    nBTCompound.getOrNull("loop", Sound.CODEC, packetWrapper);
                            BiomeEffects.MoodSettings orNull2 =
                                    nBTCompound.getOrNull(
                                            "mood", BiomeEffects.MoodSettings.CODEC, packetWrapper);
                            List<BiomeEffects.AdditionsSettings> or =
                                    nBTCompound.getOr(
                                            "additions",
                                            BiomeEffects.AdditionsSettings.LIST_CODEC,
                                            Collections.emptyList(),
                                            packetWrapper);
                            return new AmbientSounds(orNull, orNull2, or);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                AmbientSounds ambientSounds)
                                throws NbtCodecException {
                            if (ambientSounds.loop != null) {
                                nBTCompound.set(
                                        "loop", ambientSounds.loop, Sound.CODEC, packetWrapper);
                            }
                            if (ambientSounds.mood != null) {
                                nBTCompound.set(
                                        "mood",
                                        ambientSounds.mood,
                                        BiomeEffects.MoodSettings.CODEC,
                                        packetWrapper);
                            }
                            if (!ambientSounds.additions.isEmpty()) {
                                nBTCompound.set(
                                        "additions",
                                        ambientSounds.additions,
                                        BiomeEffects.AdditionsSettings.LIST_CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    public static final AmbientSounds EMPTY =
            new AmbientSounds(null, null, Collections.emptyList());

    @Nullable private final Sound loop;

    private final BiomeEffects.@Nullable MoodSettings mood;

    private final List<BiomeEffects.AdditionsSettings> additions;

    public AmbientSounds(
            @Nullable Sound sound,
            BiomeEffects.@Nullable MoodSettings moodSettings,
            List<BiomeEffects.AdditionsSettings> additions) {
        this.loop = sound;
        this.mood = moodSettings;
        this.additions = additions;
    }

    @Nullable
    public Sound getLoop() {
        return this.loop;
    }

    public BiomeEffects.@Nullable MoodSettings getMood() {
        return this.mood;
    }

    public List<BiomeEffects.AdditionsSettings> getAdditions() {
        return this.additions;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof AmbientSounds)) {
            return false;
        } else {
            AmbientSounds ambientSounds = (AmbientSounds) result;
            if (!Objects.equals(this.loop, ambientSounds.loop)) {
                return false;
            } else {
                return !Objects.equals(this.mood, ambientSounds.mood)
                        ? false
                        : this.additions.equals(ambientSounds.additions);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.loop, this.mood, this.additions);
    }
}
