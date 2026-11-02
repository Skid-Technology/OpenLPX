package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.AmbientSounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.RandomWeightedList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

@NullMarked
public class BiomeEffects {

    private static final Color FALLBACK_FOG_COLOR = new Color(12638463);

    private static final Color FALLBACK_WATER_FOG_COLOR = new Color(329011);

    private static final Color FALLBACK_SKY_COLOR = new Color(7842047);

    public static final NbtCodec<BiomeEffects> CODEC = codecWithAttributes(null);

    @Obsolete private final Color fogColor;

    private final Color waterColor;

    @Obsolete private final Color waterFogColor;

    @Obsolete private final Color skyColor;

    @Nullable private final Color foliageColor;

    @Nullable private final Color dryFoliageColor;

    @Nullable private final Color grassColor;

    private final BiomeEffects.GrassColorModifier grassColorModifier;

    @Obsolete private final BiomeEffects.@Nullable ParticleSettings particle;

    @Obsolete @Nullable private final Sound ambientSound;

    @Obsolete private final BiomeEffects.@Nullable MoodSettings moodSound;

    @Obsolete private final BiomeEffects.@Nullable AdditionsSettings additionsSound;

    @Obsolete private final RandomWeightedList<BiomeEffects.MusicSettings> music;

    @Obsolete private final float musicVolume;

    public static NbtCodec<BiomeEffects> codecWithAttributes(
            @Nullable final EnvironmentAttributeMap environmentAttributeMap) {
        return (new NbtMapCodec<BiomeEffects>() {

                    public BiomeEffects decode(
                            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                            throws NbtCodecException {
                        Color orThrow =
                                nBTCompound.getOrThrow(
                                        "water_color", NbtCodecs.RGB_COLOR, packetWrapper);
                        Color result =
                                nBTCompound.getOrNull(
                                        "foliage_color", NbtCodecs.RGB_COLOR, packetWrapper);
                        Color orNull =
                                nBTCompound.getOrNull(
                                        "grass_color", NbtCodecs.RGB_COLOR, packetWrapper);
                        BiomeEffects.GrassColorModifier or =
                                nBTCompound.getOr(
                                        "grass_color_modifier",
                                        BiomeEffects.GrassColorModifier.CODEC,
                                        BiomeEffects.GrassColorModifier.NONE,
                                        packetWrapper);
                        Color color = null;
                        Color color2 = null;
                        Color color3 = null;
                        Color color4 = null;
                        BiomeEffects.ParticleSettings particleSettings = null;
                        Sound sound = null;
                        BiomeEffects.MoodSettings moodSettings = null;
                        BiomeEffects.AdditionsSettings additionsSettings = null;
                        float value;
                        RandomWeightedList randomWeightedList;
                        if (packetWrapper
                                .getServerVersion()
                                .isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                            color =
                                    nBTCompound.getOrNull(
                                            "dry_foliage_color",
                                            NbtCodecs.RGB_COLOR,
                                            packetWrapper);
                            if (environmentAttributeMap != null) {
                                color2 =
                                        environmentAttributeMap.getOrDefault(
                                                EnvironmentAttributes.VISUAL_FOG_COLOR);
                                color3 =
                                        environmentAttributeMap.getOrDefault(
                                                EnvironmentAttributes.VISUAL_WATER_FOG_COLOR);
                                color4 =
                                        environmentAttributeMap.getOrDefault(
                                                EnvironmentAttributes.VISUAL_SKY_COLOR);
                                List<
                                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents
                                                        .protocol.world.biome.BiomeEffects
                                                        .ParticleSettings>
                                        values;
                                particleSettings =
                                        (values =
                                                                environmentAttributeMap
                                                                        .getOrDefault(
                                                                                EnvironmentAttributes
                                                                                        .VISUAL_AMBIENT_PARTICLES))
                                                        .isEmpty()
                                                ? null
                                                : (BiomeEffects.ParticleSettings) values.get(0);
                                AmbientSounds ambientSounds;
                                sound =
                                        (ambientSounds =
                                                        environmentAttributeMap.getOrDefault(
                                                                EnvironmentAttributes
                                                                        .AUDIO_AMBIENT_SOUNDS))
                                                .getLoop();
                                moodSettings = ambientSounds.getMood();
                                additionsSettings =
                                        ambientSounds.getAdditions().isEmpty()
                                                ? null
                                                : ambientSounds.getAdditions().get(0);
                                value =
                                        environmentAttributeMap.getOrDefault(
                                                EnvironmentAttributes.AUDIO_MUSIC_VOLUME);
                                randomWeightedList =
                                        environmentAttributeMap
                                                .getOrDefault(
                                                        EnvironmentAttributes
                                                                .AUDIO_BACKGROUND_MUSIC)
                                                .asList();
                            } else {
                                value = 1.0F;
                                randomWeightedList = new RandomWeightedList();
                            }
                        } else {
                            color2 =
                                    nBTCompound.getOrThrow(
                                            "fog_color", NbtCodecs.RGB_COLOR, packetWrapper);
                            color3 =
                                    nBTCompound.getOrThrow(
                                            "water_fog_color", NbtCodecs.RGB_COLOR, packetWrapper);
                            color4 =
                                    nBTCompound.getOrThrow(
                                            "sky_color", NbtCodecs.RGB_COLOR, packetWrapper);
                            particleSettings =
                                    nBTCompound.getOrNull(
                                            "particle",
                                            BiomeEffects.ParticleSettings.CODEC,
                                            packetWrapper);
                            sound =
                                    nBTCompound.getOrNull(
                                            "ambient_sound", Sound.CODEC, packetWrapper);
                            moodSettings =
                                    nBTCompound.getOrNull(
                                            "mood_sound",
                                            BiomeEffects.MoodSettings.CODEC,
                                            packetWrapper);
                            additionsSettings =
                                    nBTCompound.getOrNull(
                                            "additions_sound",
                                            BiomeEffects.AdditionsSettings.CODEC,
                                            packetWrapper);
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
                                value =
                                        nBTCompound.getOr(
                                                "music_volume",
                                                NbtCodecs.FLOAT,
                                                1.0F,
                                                packetWrapper);
                                randomWeightedList =
                                        nBTCompound.getOrSupply(
                                                "music",
                                                BiomeEffects.MusicSettings.LIST_CODEC,
                                                RandomWeightedList::new,
                                                packetWrapper);
                            } else {
                                BiomeEffects.MusicSettings musicSettings;
                                randomWeightedList =
                                        (musicSettings =
                                                                nBTCompound.getOrNull(
                                                                        "music",
                                                                        BiomeEffects.MusicSettings
                                                                                .CODEC,
                                                                        packetWrapper))
                                                        != null
                                                ? new RandomWeightedList<>(musicSettings, 1)
                                                : new RandomWeightedList();
                                value = 1.0F;
                            }
                        }
                        if (color2 == null) {
                            color2 = BiomeEffects.FALLBACK_FOG_COLOR;
                        }
                        if (color3 == null) {
                            color3 = BiomeEffects.FALLBACK_WATER_FOG_COLOR;
                        }
                        if (color4 == null) {
                            color4 = BiomeEffects.FALLBACK_SKY_COLOR;
                        }
                        return new BiomeEffects(
                                color2,
                                orThrow,
                                color3,
                                color4,
                                result,
                                color,
                                orNull,
                                or,
                                particleSettings,
                                sound,
                                moodSettings,
                                additionsSettings,
                                randomWeightedList,
                                value);
                    }

                    public void encode(
                            NBTCompound nBTCompound,
                            PacketWrapper<?> packetWrapper,
                            BiomeEffects biomeEffects)
                            throws NbtCodecException {
                        nBTCompound.set(
                                "water_color",
                                biomeEffects.waterColor,
                                NbtCodecs.RGB_COLOR,
                                packetWrapper);
                        if (biomeEffects.foliageColor != null) {
                            nBTCompound.set(
                                    "foliage_color",
                                    biomeEffects.foliageColor,
                                    NbtCodecs.RGB_COLOR,
                                    packetWrapper);
                        }
                        if (biomeEffects.grassColor != null) {
                            nBTCompound.set(
                                    "grass_color",
                                    biomeEffects.grassColor,
                                    NbtCodecs.RGB_COLOR,
                                    packetWrapper);
                        }
                        if (biomeEffects.grassColorModifier
                                != BiomeEffects.GrassColorModifier.NONE) {
                            nBTCompound.set(
                                    "grass_color_modifier",
                                    biomeEffects.grassColorModifier,
                                    BiomeEffects.GrassColorModifier.CODEC,
                                    packetWrapper);
                        }
                        if (packetWrapper
                                .getServerVersion()
                                .isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                            if (biomeEffects.dryFoliageColor != null) {
                                nBTCompound.set(
                                        "dry_foliage_color",
                                        biomeEffects.dryFoliageColor,
                                        NbtCodecs.RGB_COLOR,
                                        packetWrapper);
                            }
                        } else {
                            nBTCompound.set(
                                    "fog_color",
                                    biomeEffects.fogColor,
                                    NbtCodecs.RGB_COLOR,
                                    packetWrapper);
                            nBTCompound.set(
                                    "water_fog_color",
                                    biomeEffects.waterFogColor,
                                    NbtCodecs.RGB_COLOR,
                                    packetWrapper);
                            nBTCompound.set(
                                    "sky_color",
                                    biomeEffects.skyColor,
                                    NbtCodecs.RGB_COLOR,
                                    packetWrapper);
                            if (biomeEffects.particle != null) {
                                nBTCompound.set(
                                        "particle",
                                        biomeEffects.particle,
                                        BiomeEffects.ParticleSettings.CODEC,
                                        packetWrapper);
                            }
                            if (biomeEffects.ambientSound != null) {
                                nBTCompound.set(
                                        "ambient_sound",
                                        biomeEffects.ambientSound,
                                        Sound.CODEC,
                                        packetWrapper);
                            }
                            if (biomeEffects.moodSound != null) {
                                nBTCompound.set(
                                        "mood_sound",
                                        biomeEffects.moodSound,
                                        BiomeEffects.MoodSettings.CODEC,
                                        packetWrapper);
                            }
                            if (biomeEffects.additionsSound != null) {
                                nBTCompound.set(
                                        "additions_sound",
                                        biomeEffects.additionsSound,
                                        BiomeEffects.AdditionsSettings.CODEC,
                                        packetWrapper);
                            }
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
                                nBTCompound.set(
                                        "music_volume",
                                        biomeEffects.musicVolume,
                                        NbtCodecs.FLOAT,
                                        packetWrapper);
                                nBTCompound.set(
                                        "music",
                                        biomeEffects.music,
                                        BiomeEffects.MusicSettings.LIST_CODEC,
                                        packetWrapper);
                            } else if (!biomeEffects.music.isEmpty()) {
                                RandomWeightedList.Entry<
                                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents
                                                        .protocol.world.biome.BiomeEffects
                                                        .MusicSettings>
                                        result = biomeEffects.music.getEntries().get(0);
                                nBTCompound.set(
                                        "music",
                                        (BiomeEffects.MusicSettings) result.getData(),
                                        BiomeEffects.MusicSettings.CODEC,
                                        packetWrapper);
                            }
                        }
                    }
                })
                .codec();
    }

    @Obsolete
    public BiomeEffects(
            int value,
            int value2,
            int value3,
            int value4,
            OptionalInt optionalInt,
            OptionalInt optionalInt2,
            BiomeEffects.GrassColorModifier grassColorModifier,
            Optional<BiomeEffects.ParticleSettings> optional,
            Optional<Sound> optional2,
            Optional<BiomeEffects.MoodSettings> optional3,
            Optional<BiomeEffects.AdditionsSettings> optional4,
            Optional<BiomeEffects.MusicSettings> optional5) {
        this(
                value,
                value2,
                value3,
                value4,
                optionalInt,
                optionalInt2,
                grassColorModifier,
                optional,
                optional2,
                optional3,
                optional4,
                optional5
                        .<RandomWeightedList<BiomeEffects.MusicSettings>>map(
                                result -> new RandomWeightedList<>(result, 1))
                        .orElseGet(RandomWeightedList::new),
                1.0F);
    }

    @Obsolete
    public BiomeEffects(
            int value,
            int value2,
            int value3,
            int value4,
            OptionalInt optionalInt,
            OptionalInt optionalInt2,
            BiomeEffects.GrassColorModifier grassColorModifier,
            Optional<BiomeEffects.ParticleSettings> optional,
            Optional<Sound> optional2,
            Optional<BiomeEffects.MoodSettings> optional3,
            Optional<BiomeEffects.AdditionsSettings> optional4,
            RandomWeightedList<BiomeEffects.MusicSettings> randomWeightedList,
            float value5) {
        this(
                new Color(value),
                new Color(value2),
                new Color(value3),
                new Color(value4),
                optionalInt.isPresent() ? new Color(optionalInt.getAsInt()) : null,
                null,
                optionalInt2.isPresent() ? new Color(optionalInt2.getAsInt()) : null,
                grassColorModifier,
                (BiomeEffects.ParticleSettings) optional.orElse(null),
                (Sound) optional2.orElse(null),
                (BiomeEffects.MoodSettings) optional3.orElse(null),
                (BiomeEffects.AdditionsSettings) optional4.orElse(null),
                randomWeightedList,
                value5);
    }

    public BiomeEffects(
            Color color,
            @Nullable Color color2,
            @Nullable Color color3,
            @Nullable Color color4,
            BiomeEffects.GrassColorModifier grassColorModifier) {
        this(
                Color.BLACK,
                color,
                Color.BLACK,
                Color.BLACK,
                color2,
                color3,
                color4,
                grassColorModifier,
                null,
                null,
                null,
                null,
                new RandomWeightedList<>(),
                1.0F);
    }

    public BiomeEffects(
            Color color,
            Color color2,
            Color color3,
            Color color4,
            @Nullable Color color5,
            @Nullable Color color6,
            @Nullable Color color7,
            BiomeEffects.GrassColorModifier grassColorModifier2,
            BiomeEffects.@Nullable ParticleSettings particleSettings,
            @Nullable Sound sound,
            BiomeEffects.@Nullable MoodSettings moodSettings,
            BiomeEffects.@Nullable AdditionsSettings additionsSettings,
            RandomWeightedList<BiomeEffects.MusicSettings> randomWeightedList,
            float musicVolume) {
        this.fogColor = color;
        this.waterColor = color2;
        this.waterFogColor = color3;
        this.skyColor = color4;
        this.foliageColor = color5;
        this.dryFoliageColor = color6;
        this.grassColor = color7;
        this.grassColorModifier = grassColorModifier2;
        this.particle = particleSettings;
        this.ambientSound = sound;
        this.moodSound = moodSettings;
        this.additionsSound = additionsSettings;
        this.music = randomWeightedList;
        this.musicVolume = musicVolume;
    }

    @Obsolete
    public int getFogColor() {
        return this.fogColor.asRGB();
    }

    public int getWaterColor() {
        return this.waterColor.asRGB();
    }

    @Obsolete
    public int getWaterFogColor() {
        return this.waterFogColor.asRGB();
    }

    @Obsolete
    public int getSkyColor() {
        return this.skyColor.asRGB();
    }

    public OptionalInt getFoliageColor() {
        return this.foliageColor != null
                ? OptionalInt.of(this.foliageColor.asRGB())
                : OptionalInt.empty();
    }

    @Nullable
    public Color getDryFoliageColor() {
        return this.dryFoliageColor;
    }

    public OptionalInt getGrassColor() {
        return this.grassColor != null
                ? OptionalInt.of(this.grassColor.asRGB())
                : OptionalInt.empty();
    }

    public BiomeEffects.GrassColorModifier getGrassColorModifier() {
        return this.grassColorModifier;
    }

    @Obsolete
    public Optional<BiomeEffects.ParticleSettings> getParticle() {
        return Optional.ofNullable(this.particle);
    }

    @Obsolete
    public Optional<Sound> getAmbientSound() {
        return Optional.ofNullable(this.ambientSound);
    }

    @Obsolete
    public Optional<BiomeEffects.MoodSettings> getMoodSound() {
        return Optional.ofNullable(this.moodSound);
    }

    @Obsolete
    public RandomWeightedList<BiomeEffects.MusicSettings> getMusics() {
        return this.music;
    }

    @Obsolete
    public Optional<BiomeEffects.MusicSettings> getMusic() {
        List<
                        RandomWeightedList.Entry<
                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world
                                        .biome.BiomeEffects.MusicSettings>>
                values;
        return (values = this.music.getEntries()).isEmpty()
                ? Optional.empty()
                : Optional.of(
                        (BiomeEffects.MusicSettings)
                                ((RandomWeightedList.Entry) values.get(0)).getData());
    }

    @Obsolete
    public Optional<BiomeEffects.AdditionsSettings> getAdditionsSound() {
        return Optional.ofNullable(this.additionsSound);
    }

    public static final class AdditionsSettings {

        public static final NbtCodec<BiomeEffects.AdditionsSettings> CODEC =
                (new NbtMapCodec<BiomeEffects.AdditionsSettings>() {

                            public BiomeEffects.AdditionsSettings decode(
                                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                    throws NbtCodecException {
                                Sound orThrow =
                                        nBTCompound.getOrThrow("sound", Sound.CODEC, packetWrapper);
                                double orThrow2 =
                                        nBTCompound.getOrThrow(
                                                "tick_chance", NbtCodecs.DOUBLE, packetWrapper);
                                return new BiomeEffects.AdditionsSettings(orThrow, orThrow2);
                            }

                            public void encode(
                                    NBTCompound nBTCompound,
                                    PacketWrapper<?> packetWrapper,
                                    BiomeEffects.AdditionsSettings additionsSettings)
                                    throws NbtCodecException {
                                nBTCompound.set(
                                        "sound",
                                        additionsSettings.sound,
                                        Sound.CODEC,
                                        packetWrapper);
                                nBTCompound.set(
                                        "tick_chance",
                                        additionsSettings.tickChance,
                                        NbtCodecs.DOUBLE,
                                        packetWrapper);
                            }
                        })
                        .codec();

        public static final NbtCodec<List<BiomeEffects.AdditionsSettings>> LIST_CODEC =
                CODEC.applyList();

        private final Sound sound;

        private final double tickChance;

        public AdditionsSettings(Sound sound2, double tickChance) {
            this.sound = sound2;
            this.tickChance = tickChance;
        }

        @Deprecated
        public static BiomeEffects.AdditionsSettings decode(NBT nBT, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            Sound sound =
                    Sound.decode(
                            (nBTCompound = (NBTCompound) nBT).getTagOrThrow("sound"),
                            clientVersion);
            double asDouble = nBTCompound.getNumberTagOrThrow("tick_chance").getAsDouble();
            return new BiomeEffects.AdditionsSettings(sound, asDouble);
        }

        @Deprecated
        public static NBT encode(
                BiomeEffects.AdditionsSettings additionsSettings, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound())
                    .setTag("sound", Sound.encode(additionsSettings.sound, clientVersion));
            nBTCompound.setTag("tick_chance", new NBTDouble(additionsSettings.tickChance));
            return nBTCompound;
        }

        public Sound getSound() {
            return this.sound;
        }

        public double getTickChance() {
            return this.tickChance;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof BiomeEffects.AdditionsSettings)) {
                return false;
            }
            BiomeEffects.AdditionsSettings additionsSettings;
            return Double.compare(
                                    (additionsSettings = (BiomeEffects.AdditionsSettings) result)
                                            .tickChance,
                                    this.tickChance)
                            != 0
                    ? false
                    : this.sound.equals(additionsSettings.sound);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.sound, this.tickChance);
        }
    }

    public enum GrassColorModifier implements CodecNameable {
        NONE("none"),
        DARK_FOREST("dark_forest"),
        SWAMP("swamp");

        public static final NbtCodec<BiomeEffects.GrassColorModifier> CODEC =
                NbtCodecs.forEnum(values());

        public static final Index<String, BiomeEffects.GrassColorModifier> ID_INDEX =
                Index.create(
                        BiomeEffects.GrassColorModifier.class,
                        BiomeEffects.GrassColorModifier::getId);

        private final String id;

        GrassColorModifier(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }

    public static final class MoodSettings {

        public static final NbtCodec<BiomeEffects.MoodSettings> CODEC =
                (new NbtMapCodec<BiomeEffects.MoodSettings>() {

                            public BiomeEffects.MoodSettings decode(
                                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                    throws NbtCodecException {
                                Sound orThrow =
                                        nBTCompound.getOrThrow("sound", Sound.CODEC, packetWrapper);
                                int orThrow2 =
                                        nBTCompound.getOrThrow(
                                                "tick_delay", NbtCodecs.INT, packetWrapper);
                                int orThrow3 =
                                        nBTCompound.getOrThrow(
                                                "block_search_extent",
                                                NbtCodecs.INT,
                                                packetWrapper);
                                double orThrow4 =
                                        nBTCompound.getOrThrow(
                                                "offset", NbtCodecs.DOUBLE, packetWrapper);
                                return new BiomeEffects.MoodSettings(
                                        orThrow, orThrow2, orThrow3, orThrow4);
                            }

                            public void encode(
                                    NBTCompound nBTCompound,
                                    PacketWrapper<?> packetWrapper,
                                    BiomeEffects.MoodSettings moodSettings)
                                    throws NbtCodecException {
                                nBTCompound.set(
                                        "sound", moodSettings.sound, Sound.CODEC, packetWrapper);
                                nBTCompound.set(
                                        "tick_delay",
                                        moodSettings.tickDelay,
                                        NbtCodecs.INT,
                                        packetWrapper);
                                nBTCompound.set(
                                        "block_search_extent",
                                        moodSettings.blockSearchExtent,
                                        NbtCodecs.INT,
                                        packetWrapper);
                                nBTCompound.set(
                                        "offset",
                                        moodSettings.soundOffset,
                                        NbtCodecs.DOUBLE,
                                        packetWrapper);
                            }
                        })
                        .codec();

        private final Sound sound;

        private final int tickDelay;

        private final int blockSearchExtent;

        private final double soundOffset;

        public MoodSettings(
                Sound sound2, int tickDelay, int blockSearchExtent, double soundOffset) {
            this.sound = sound2;
            this.tickDelay = tickDelay;
            this.blockSearchExtent = blockSearchExtent;
            this.soundOffset = soundOffset;
        }

        @Deprecated
        public static BiomeEffects.MoodSettings decode(NBT nBT, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            Sound sound =
                    Sound.decode(
                            (nBTCompound = (NBTCompound) nBT).getTagOrThrow("sound"),
                            clientVersion);
            int asInt = nBTCompound.getNumberTagOrThrow("tick_delay").getAsInt();
            int asInt2 = nBTCompound.getNumberTagOrThrow("block_search_extent").getAsInt();
            double asDouble = nBTCompound.getNumberTagOrThrow("offset").getAsDouble();
            return new BiomeEffects.MoodSettings(sound, asInt, asInt2, asDouble);
        }

        @Deprecated
        public static NBT encode(
                BiomeEffects.MoodSettings moodSettings, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound())
                    .setTag("sound", Sound.encode(moodSettings.sound, clientVersion));
            nBTCompound.setTag("tick_delay", new NBTInt(moodSettings.tickDelay));
            nBTCompound.setTag("block_search_extent", new NBTInt(moodSettings.blockSearchExtent));
            nBTCompound.setTag("offset", new NBTDouble(moodSettings.soundOffset));
            return nBTCompound;
        }

        public Sound getSound() {
            return this.sound;
        }

        public int getTickDelay() {
            return this.tickDelay;
        }

        public int getBlockSearchExtent() {
            return this.blockSearchExtent;
        }

        public double getSoundOffset() {
            return this.soundOffset;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof BiomeEffects.MoodSettings)) {
                return false;
            } else {
                BiomeEffects.MoodSettings moodSettings = (BiomeEffects.MoodSettings) result;
                if (this.tickDelay != moodSettings.tickDelay) {
                    return false;
                } else if (this.blockSearchExtent != moodSettings.blockSearchExtent) {
                    return false;
                } else {
                    return Double.compare(moodSettings.soundOffset, this.soundOffset) != 0
                            ? false
                            : this.sound.equals(moodSettings.sound);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                    this.sound, this.tickDelay, this.blockSearchExtent, this.soundOffset);
        }
    }

    public static final class MusicSettings {

        public static final NbtCodec<BiomeEffects.MusicSettings> CODEC =
                (new NbtMapCodec<BiomeEffects.MusicSettings>() {

                            public BiomeEffects.MusicSettings decode(
                                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                    throws NbtCodecException {
                                Sound orThrow =
                                        nBTCompound.getOrThrow("sound", Sound.CODEC, packetWrapper);
                                int asInt = nBTCompound.getNumberTagOrThrow("min_delay").getAsInt();
                                int asInt2 =
                                        nBTCompound.getNumberTagOrThrow("max_delay").getAsInt();
                                boolean booleanValue =
                                        nBTCompound.getBoolean("replace_current_music");
                                return new BiomeEffects.MusicSettings(
                                        orThrow, asInt, asInt2, booleanValue);
                            }

                            public void encode(
                                    NBTCompound nBTCompound,
                                    PacketWrapper<?> packetWrapper,
                                    BiomeEffects.MusicSettings musicSettings)
                                    throws NbtCodecException {
                                nBTCompound.set(
                                        "sound", musicSettings.sound, Sound.CODEC, packetWrapper);
                                nBTCompound.setTag("min_delay", new NBTInt(musicSettings.minDelay));
                                nBTCompound.setTag("max_delay", new NBTInt(musicSettings.maxDelay));
                                nBTCompound.setTag(
                                        "replace_current_music",
                                        new NBTByte(musicSettings.replaceMusic));
                            }
                        })
                        .codec();

        public static final NbtCodec<RandomWeightedList<BiomeEffects.MusicSettings>> LIST_CODEC =
                RandomWeightedList.codec(CODEC);

        private final Sound sound;

        private final int minDelay;

        private final int maxDelay;

        private final boolean replaceMusic;

        public MusicSettings(Sound sound2, int minDelay, int maxDelay, boolean replaceMusic) {
            this.sound = sound2;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
            this.replaceMusic = replaceMusic;
        }

        @Deprecated
        public static BiomeEffects.MusicSettings decode(NBT nBT, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            Sound sound =
                    Sound.decode(
                            (nBTCompound = (NBTCompound) nBT).getTagOrThrow("sound"),
                            clientVersion);
            int asInt = nBTCompound.getNumberTagOrThrow("min_delay").getAsInt();
            int asInt2 = nBTCompound.getNumberTagOrThrow("max_delay").getAsInt();
            boolean booleanValue = nBTCompound.getBoolean("replace_current_music");
            return new BiomeEffects.MusicSettings(sound, asInt, asInt2, booleanValue);
        }

        @Deprecated
        public static NBT encode(
                BiomeEffects.MusicSettings musicSettings, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound())
                    .setTag("sound", Sound.encode(musicSettings.sound, clientVersion));
            nBTCompound.setTag("min_delay", new NBTInt(musicSettings.minDelay));
            nBTCompound.setTag("max_delay", new NBTInt(musicSettings.maxDelay));
            nBTCompound.setTag("replace_current_music", new NBTByte(musicSettings.replaceMusic));
            return nBTCompound;
        }

        public Sound getSound() {
            return this.sound;
        }

        public int getMinDelay() {
            return this.minDelay;
        }

        public int getMaxDelay() {
            return this.maxDelay;
        }

        public boolean isReplaceMusic() {
            return this.replaceMusic;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof BiomeEffects.MusicSettings)) {
                return false;
            } else {
                BiomeEffects.MusicSettings musicSettings = (BiomeEffects.MusicSettings) result;
                if (this.minDelay != musicSettings.minDelay) {
                    return false;
                } else if (this.maxDelay != musicSettings.maxDelay) {
                    return false;
                } else {
                    return this.replaceMusic != musicSettings.replaceMusic
                            ? false
                            : this.sound.equals(musicSettings.sound);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.sound, this.minDelay, this.maxDelay, this.replaceMusic);
        }
    }

    public static final class ParticleSettings {

        public static final NbtCodec<BiomeEffects.ParticleSettings> CODEC =
                (new NbtMapCodec<BiomeEffects.ParticleSettings>() {

                            public BiomeEffects.ParticleSettings decode(
                                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                    throws NbtCodecException {
                                String text =
                                        packetWrapper
                                                        .getServerVersion()
                                                        .isNewerThanOrEquals(
                                                                ServerVersion.V_1_21_11)
                                                ? "particle"
                                                : "options";
                                Particle particle =
                                        Particle.CODEC.decode(
                                                nBTCompound.getTagOrThrow(text), packetWrapper);
                                float asFloat =
                                        nBTCompound.getNumberTagOrThrow("probability").getAsFloat();
                                return new BiomeEffects.ParticleSettings(particle, asFloat);
                            }

                            public void encode(
                                    NBTCompound nBTCompound,
                                    PacketWrapper<?> packetWrapper,
                                    BiomeEffects.ParticleSettings particleSettings)
                                    throws NbtCodecException {
                                String text =
                                        packetWrapper
                                                        .getServerVersion()
                                                        .isNewerThanOrEquals(
                                                                ServerVersion.V_1_21_11)
                                                ? "particle"
                                                : "options";
                                nBTCompound.set(
                                        text,
                                        particleSettings.particle,
                                        Particle.CODEC,
                                        packetWrapper);
                                nBTCompound.setTag(
                                        "probability", new NBTFloat(particleSettings.probability));
                            }
                        })
                        .codec();

        private final Particle<?> particle;

        private final float probability;

        public ParticleSettings(Particle<?> particle2, float probability) {
            this.particle = particle2;
            this.probability = probability;
        }

        @Deprecated
        public static BiomeEffects.ParticleSettings decode(NBT nBT, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            Particle particle =
                    Particle.decode(
                            (nBTCompound = (NBTCompound) nBT).getTagOrNull("options"),
                            clientVersion);
            float asFloat = nBTCompound.getNumberTagOrThrow("probability").getAsFloat();
            return new BiomeEffects.ParticleSettings(particle, asFloat);
        }

        @Deprecated
        public static NBT encode(
                BiomeEffects.ParticleSettings particleSettings, ClientVersion clientVersion) {
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound())
                    .setTag("options", Particle.encode(particleSettings.particle, clientVersion));
            nBTCompound.setTag("probability", new NBTFloat(particleSettings.probability));
            return nBTCompound;
        }

        public Particle<?> getParticle() {
            return this.particle;
        }

        public float getProbability() {
            return this.probability;
        }

        @Override
        public boolean equals(Object result) {
            if (!(result instanceof BiomeEffects.ParticleSettings)) {
                return false;
            }
            BiomeEffects.ParticleSettings particleSettings;
            return Float.compare(
                                    (particleSettings = (BiomeEffects.ParticleSettings) result)
                                            .probability,
                                    this.probability)
                            != 0
                    ? false
                    : this.particle.equals(particleSettings.particle);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.particle, this.probability);
        }
    }
}
