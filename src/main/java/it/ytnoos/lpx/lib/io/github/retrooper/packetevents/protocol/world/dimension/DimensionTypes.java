package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.AmbientSounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.BackgroundMusic;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.BiomeEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClocks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.defaulttags.BlockTags;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.VersionRange;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureNbtUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.CompoundBinaryTag;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.function.Consumer;

@NullMarked
public final class DimensionTypes {

    private static final VersionedRegistry<DimensionType> REGISTRY =
            new VersionedRegistry<>("dimension_type", ClientVersion.V_1_18);

    @Obsolete
    public static final DimensionType OVERWORLD_PRE_1_18 =
            define(
                    "overworld",
                    new VersionRange(null, ClientVersion.V_1_17_1),
                    result ->
                            result.setCoordinateScale(1.0)
                                    .setMinY(0)
                                    .setHeight(256)
                                    .setLogicalHeight(256)
                                    .setInfiniburn(BlockTags.INFINIBURN_OVERWORLD.getKey())
                                    .setHasCeiling(false)
                                    .setHasSkylight(true)
                                    .setAmbientLight(0.0F)
                                    .setNatural(true)
                                    .setRespawnAnchorWorks(false)
                                    .setBedWorks(true)
                                    .setEffects(new ResourceLocation("overworld"))
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_CAN_START_RAID, true));

    public static final DimensionType OVERWORLD =
            define(
                    "overworld",
                    new VersionRange(ClientVersion.V_1_18, null),
                    result ->
                            result.setMonsterSpawnLightLevel(
                                            AdventureNbtUtil.fromAdventure(
                                                    CompoundBinaryTag.builder()
                                                            .putString(
                                                                    "type",
                                                                    new ResourceLocation("uniform")
                                                                            .toString())
                                                            .putInt("min_inclusive", 0)
                                                            .putInt("max_inclusive", 7)
                                                            .put(
                                                                    "value",
                                                                    CompoundBinaryTag.builder()
                                                                            .putInt(
                                                                                    "min_inclusive",
                                                                                    0)
                                                                            .putInt(
                                                                                    "max_inclusive",
                                                                                    7)
                                                                            .build())
                                                            .build()))
                                    .setMonsterSpawnBlockLightLimit(0)
                                    .setCoordinateScale(1.0)
                                    .setMinY(-64)
                                    .setHeight(384)
                                    .setLogicalHeight(384)
                                    .setInfiniburn(BlockTags.INFINIBURN_OVERWORLD.getKey())
                                    .setHasCeiling(false)
                                    .setHasSkylight(true)
                                    .setAmbientLight(0.0F)
                                    .setAttribute(
                                            EnvironmentAttributes.AUDIO_BACKGROUND_MUSIC,
                                            new BackgroundMusic(
                                                    new BiomeEffects.MusicSettings(
                                                            Sounds.MUSIC_GAME, 12000, 24000, false),
                                                    new BiomeEffects.MusicSettings(
                                                            Sounds.MUSIC_CREATIVE,
                                                            12000,
                                                            24000,
                                                            false),
                                                    null))
                                    .setAttribute(
                                            EnvironmentAttributes.AUDIO_AMBIENT_SOUNDS,
                                            new AmbientSounds(
                                                    null,
                                                    new BiomeEffects.MoodSettings(
                                                            Sounds.AMBIENT_CAVE, 6000, 8, 2.0),
                                                    Collections.emptyList()))
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_CLOUD_HEIGHT, 192.33F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_CLOUD_COLOR,
                                            new AlphaColor(-855638017))
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_FOG_COLOR,
                                            new Color(12638463))
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_SKY_COLOR,
                                            new Color(7907327))
                                    .setNatural(true)
                                    .setRespawnAnchorWorks(false)
                                    .setBedWorks(true)
                                    .setEffects(new ResourceLocation("overworld"))
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_CAN_START_RAID, true)
                                    .setAttribute(
                                            EnvironmentAttributes.AMBIENT_LIGHT_COLOR,
                                            new Color(657930))
                                    .setDefaultClock(WorldClocks.OVERWORLD));

    @Obsolete
    public static final DimensionType OVERWORLD_CAVES_PRE_1_18 =
            define(
                    "overworld_caves",
                    new VersionRange(null, ClientVersion.V_1_17_1),
                    result ->
                            result.setCoordinateScale(1.0)
                                    .setMinY(0)
                                    .setHeight(256)
                                    .setLogicalHeight(256)
                                    .setInfiniburn(BlockTags.INFINIBURN_OVERWORLD.getKey())
                                    .setHasCeiling(true)
                                    .setHasSkylight(true)
                                    .setAmbientLight(0.0F)
                                    .setNatural(true)
                                    .setRespawnAnchorWorks(false)
                                    .setBedWorks(true)
                                    .setEffects(new ResourceLocation("overworld"))
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_CAN_START_RAID, true));

    public static final DimensionType OVERWORLD_CAVES =
            define(
                    "overworld_caves",
                    new VersionRange(ClientVersion.V_1_18, null),
                    result ->
                            result.setMonsterSpawnLightLevel(
                                            AdventureNbtUtil.fromAdventure(
                                                    CompoundBinaryTag.builder()
                                                            .putString(
                                                                    "type",
                                                                    new ResourceLocation("uniform")
                                                                            .toString())
                                                            .putInt("min_inclusive", 0)
                                                            .putInt("max_inclusive", 7)
                                                            .put(
                                                                    "value",
                                                                    CompoundBinaryTag.builder()
                                                                            .putInt(
                                                                                    "min_inclusive",
                                                                                    0)
                                                                            .putInt(
                                                                                    "max_inclusive",
                                                                                    7)
                                                                            .build())
                                                            .build()))
                                    .setMonsterSpawnBlockLightLimit(0)
                                    .setCoordinateScale(1.0)
                                    .setMinY(-64)
                                    .setHeight(384)
                                    .setLogicalHeight(384)
                                    .setInfiniburn(BlockTags.INFINIBURN_OVERWORLD.getKey())
                                    .setHasCeiling(true)
                                    .setHasSkylight(true)
                                    .setAmbientLight(0.0F)
                                    .setAttribute(
                                            EnvironmentAttributes.AUDIO_BACKGROUND_MUSIC,
                                            new BackgroundMusic(
                                                    new BiomeEffects.MusicSettings(
                                                            Sounds.MUSIC_GAME, 12000, 24000, false),
                                                    new BiomeEffects.MusicSettings(
                                                            Sounds.MUSIC_CREATIVE,
                                                            12000,
                                                            24000,
                                                            false),
                                                    null))
                                    .setAttribute(
                                            EnvironmentAttributes.AUDIO_AMBIENT_SOUNDS,
                                            new AmbientSounds(
                                                    null,
                                                    new BiomeEffects.MoodSettings(
                                                            Sounds.AMBIENT_CAVE, 6000, 8, 2.0),
                                                    Collections.emptyList()))
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_CLOUD_HEIGHT, 192.33F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_CLOUD_COLOR,
                                            new AlphaColor(-855638017))
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_FOG_COLOR,
                                            new Color(12638463))
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_SKY_COLOR,
                                            new Color(7907327))
                                    .setNatural(true)
                                    .setRespawnAnchorWorks(false)
                                    .setBedWorks(true)
                                    .setEffects(new ResourceLocation("overworld"))
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_CAN_START_RAID, true)
                                    .setAttribute(
                                            EnvironmentAttributes.AMBIENT_LIGHT_COLOR,
                                            new Color(657930))
                                    .setDefaultClock(WorldClocks.OVERWORLD));

    private static final Consumer<DimensionTypeBuilder> THE_END_COMMON =
            result ->
                    result.setHasFixedTime(true)
                            .setMonsterSpawnBlockLightLimit(0)
                            .setMonsterSpawnLightLevel(new NBTInt(15))
                            .setCoordinateScale(1.0)
                            .setSkybox(DimensionType.Skybox.END)
                            .setMinY(0)
                            .setHeight(256)
                            .setLogicalHeight(256)
                            .setInfiniburn(BlockTags.INFINIBURN_END.getKey())
                            .setHasCeiling(false)
                            .setHasSkylight(true)
                            .setAmbientLight(0.25F)
                            .setAttribute(
                                    EnvironmentAttributes.AUDIO_BACKGROUND_MUSIC,
                                    new BackgroundMusic(
                                            new BiomeEffects.MusicSettings(
                                                    Sounds.MUSIC_END, 6000, 24000, true),
                                            null,
                                            null))
                            .setAttribute(
                                    EnvironmentAttributes.AUDIO_AMBIENT_SOUNDS,
                                    new AmbientSounds(
                                            null,
                                            new BiomeEffects.MoodSettings(
                                                    Sounds.AMBIENT_CAVE, 6000, 8, 2.0),
                                            Collections.emptyList()))
                            .setAttribute(
                                    EnvironmentAttributes.VISUAL_FOG_COLOR, new Color(1577752))
                            .setAttribute(EnvironmentAttributes.VISUAL_SKY_COLOR, new Color(0))
                            .setAttribute(
                                    EnvironmentAttributes.VISUAL_SKY_LIGHT_COLOR,
                                    new Color(11296973))
                            .setAttribute(EnvironmentAttributes.VISUAL_SKY_LIGHT_FACTOR, 0.0F)
                            .setNatural(false)
                            .setRespawnAnchorWorks(false)
                            .setBedWorks(false)
                            .setEffects(new ResourceLocation("the_end"))
                            .setFixedTime(6000L)
                            .setAttribute(EnvironmentAttributes.GAMEPLAY_CAN_START_RAID, true)
                            .setAttribute(
                                    EnvironmentAttributes.AMBIENT_LIGHT_COLOR, new Color(4147007))
                            .setDefaultClock(WorldClocks.THE_END)
                            .setHasEnderDragonFight(true);

    @Obsolete
    public static final DimensionType THE_END_PRE_1_21_9 =
            define(
                    "the_end",
                    new VersionRange(null, ClientVersion.V_1_21_7),
                    THE_END_COMMON.andThen(result -> result.setHasSkylight(false)));

    public static final DimensionType THE_END =
            define("the_end", new VersionRange(ClientVersion.V_1_21_9, null), THE_END_COMMON);

    public static final DimensionType THE_NETHER =
            define(
                    "the_nether",
                    result ->
                            result.setHasFixedTime(true)
                                    .setMonsterSpawnLightLevel(new NBTInt(7))
                                    .setMonsterSpawnBlockLightLimit(15)
                                    .setCoordinateScale(8.0)
                                    .setSkybox(DimensionType.Skybox.NONE)
                                    .setCardinalLight(DimensionType.CardinalLight.NETHER)
                                    .setMinY(0)
                                    .setHeight(256)
                                    .setLogicalHeight(128)
                                    .setInfiniburn(BlockTags.INFINIBURN_NETHER.getKey())
                                    .setHasCeiling(true)
                                    .setHasSkylight(false)
                                    .setAmbientLight(0.1F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_SKY_LIGHT_FACTOR, 0.0F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_SKY_LIGHT_COLOR,
                                            new Color(8026879))
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_SKY_LIGHT_LEVEL, 4.0F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_FOG_START_DISTANCE, 10.0F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_FOG_END_DISTANCE, 96.0F)
                                    .setAttribute(
                                            EnvironmentAttributes.VISUAL_DEFAULT_DRIPSTONE_PARTICLE,
                                            new Particle<>(ParticleTypes.DRIPPING_DRIPSTONE_LAVA))
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_PIGLINS_ZOMBIFY, false)
                                    .setAttribute(EnvironmentAttributes.GAMEPLAY_FAST_LAVA, true)
                                    .setAttribute(
                                            EnvironmentAttributes.GAMEPLAY_WATER_EVAPORATES, true)
                                    .setNatural(false)
                                    .setRespawnAnchorWorks(true)
                                    .setBedWorks(false)
                                    .setEffects(new ResourceLocation("the_nether"))
                                    .setFixedTime(18000L)
                                    .setAttribute(
                                            EnvironmentAttributes.AMBIENT_LIGHT_COLOR,
                                            new Color(3156001)));

    private DimensionTypes() {}

    @Internal
    public static DimensionType define(String text, Consumer<DimensionTypeBuilder> consumer) {
        return define(text, VersionRange.ALL_VERSIONS, consumer);
    }

    @Internal
    public static DimensionType define(
            String text, VersionRange versionRange, Consumer<DimensionTypeBuilder> consumer) {
        return REGISTRY.define(
                text,
                versionRange,
                result -> {
                    DimensionTypeBuilder dimensionTypeBuilder2 =
                            DimensionTypeBuilder.dimensionTypeBuilder();
                    ((Consumer) consumer).accept(dimensionTypeBuilder2);
                    return dimensionTypeBuilder2.build(result);
                });
    }

    public static VersionedRegistry<DimensionType> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
