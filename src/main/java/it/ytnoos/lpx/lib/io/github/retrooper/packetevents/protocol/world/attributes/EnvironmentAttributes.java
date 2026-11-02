package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.BiomeEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

@NullMarked
public final class EnvironmentAttributes {

    private static final VersionedRegistry<EnvironmentAttribute<?>> REGISTRY =
            new VersionedRegistry<>("environment_attribute");

    public static final NbtCodec<EnvironmentAttribute<?>> CODEC = NbtCodecs.forRegistry(REGISTRY);

    public static final EnvironmentAttribute<Color> VISUAL_FOG_COLOR =
            define("visual/fog_color", AttributeTypes.RGB_COLOR, Color.BLACK);

    public static final EnvironmentAttribute<Float> VISUAL_FOG_START_DISTANCE =
            define("visual/fog_start_distance", AttributeTypes.FLOAT, 0.0F);

    public static final EnvironmentAttribute<Float> VISUAL_FOG_END_DISTANCE =
            define("visual/fog_end_distance", AttributeTypes.FLOAT, 1024.0F);

    public static final EnvironmentAttribute<Float> VISUAL_SKY_FOG_END_DISTANCE =
            define("visual/sky_fog_end_distance", AttributeTypes.FLOAT, 512.0F);

    public static final EnvironmentAttribute<Float> VISUAL_CLOUD_FOG_END_DISTANCE =
            define("visual/cloud_fog_end_distance", AttributeTypes.FLOAT, 2048.0F);

    public static final EnvironmentAttribute<Color> VISUAL_WATER_FOG_COLOR =
            define("visual/water_fog_color", AttributeTypes.RGB_COLOR, new Color(16448205));

    public static final EnvironmentAttribute<Float> VISUAL_WATER_FOG_START_DISTANCE =
            define("visual/water_fog_start_distance", AttributeTypes.FLOAT, -8.0F);

    public static final EnvironmentAttribute<Float> VISUAL_WATER_FOG_END_DISTANCE =
            define("visual/water_fog_end_distance", AttributeTypes.FLOAT, 96.0F);

    public static final EnvironmentAttribute<Color> VISUAL_SKY_COLOR =
            define("visual/sky_color", AttributeTypes.RGB_COLOR, Color.BLACK);

    public static final EnvironmentAttribute<AlphaColor> VISUAL_SUNRISE_SUNSET_COLOR =
            define(
                    "visual/sunrise_sunset_color",
                    AttributeTypes.ARGB_COLOR,
                    AlphaColor.TRANSPARENT);

    public static final EnvironmentAttribute<AlphaColor> VISUAL_CLOUD_COLOR =
            define("visual/cloud_color", AttributeTypes.ARGB_COLOR, AlphaColor.TRANSPARENT);

    public static final EnvironmentAttribute<Float> VISUAL_CLOUD_HEIGHT =
            define("visual/cloud_height", AttributeTypes.FLOAT, 192.33F);

    public static final EnvironmentAttribute<Float> VISUAL_SUN_ANGLE =
            define("visual/sun_angle", AttributeTypes.ANGLE_DEGREES, 0.0F);

    public static final EnvironmentAttribute<Float> VISUAL_MOON_ANGLE =
            define("visual/moon_angle", AttributeTypes.ANGLE_DEGREES, 0.0F);

    public static final EnvironmentAttribute<Float> VISUAL_STAR_ANGLE =
            define("visual/star_angle", AttributeTypes.ANGLE_DEGREES, 0.0F);

    public static final EnvironmentAttribute<MoonPhase> VISUAL_MOON_PHASE =
            define("visual/moon_phase", AttributeTypes.MOON_PHASE, MoonPhase.FULL_MOON);

    public static final EnvironmentAttribute<Float> VISUAL_STAR_BRIGHTNESS =
            define("visual/star_brightness", AttributeTypes.FLOAT, 0.0F);

    public static final EnvironmentAttribute<Color> VISUAL_SKY_LIGHT_COLOR =
            define("visual/sky_light_color", AttributeTypes.RGB_COLOR, Color.WHITE);

    public static final EnvironmentAttribute<Float> VISUAL_SKY_LIGHT_FACTOR =
            define("visual/sky_light_factor", AttributeTypes.FLOAT, 1.0F);

    public static final EnvironmentAttribute<Particle<?>> VISUAL_DEFAULT_DRIPSTONE_PARTICLE =
            define(
                    "visual/default_dripstone_particle",
                    AttributeTypes.PARTICLE,
                    new Particle<>(ParticleTypes.DRIPPING_DRIPSTONE_WATER));

    public static final EnvironmentAttribute<List<BiomeEffects.ParticleSettings>>
            VISUAL_AMBIENT_PARTICLES =
                    define(
                            "visual/ambient_particles",
                            AttributeTypes.AMBIENT_PARTICLES,
                            Collections.emptyList());

    public static final EnvironmentAttribute<BackgroundMusic> AUDIO_BACKGROUND_MUSIC =
            define(
                    "audio/background_music",
                    AttributeTypes.BACKGROUND_MUSIC,
                    BackgroundMusic.EMPTY);

    public static final EnvironmentAttribute<Float> AUDIO_MUSIC_VOLUME =
            define("audio/music_volume", AttributeTypes.FLOAT, 1.0F);

    public static final EnvironmentAttribute<AmbientSounds> AUDIO_AMBIENT_SOUNDS =
            define("audio/ambient_sounds", AttributeTypes.AMBIENT_SOUNDS, AmbientSounds.EMPTY);

    public static final EnvironmentAttribute<Boolean> AUDIO_FIREFLY_BUSH_SOUNDS =
            define("audio/firefly_bush_sounds", AttributeTypes.BOOLEAN, false);

    public static final EnvironmentAttribute<Float> GAMEPLAY_SKY_LIGHT_LEVEL =
            define("gameplay/sky_light_level", AttributeTypes.FLOAT, 15.0F);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_CAN_START_RAID =
            defineUnsynced("gameplay/can_start_raid", AttributeTypes.BOOLEAN, true);

    public static final EnvironmentAttribute<Boolean> GAMEPLAY_WATER_EVAPORATES =
            define("gameplay/water_evaporates", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<?> GAMEPLAY_BED_RULE =
            defineUnsynced("gameplay/bed_rule");

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_RESPAWN_ANCHOR_WORKS =
            defineUnsynced("gameplay/respawn_anchor_works", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_NETHER_PORTAL_SPAWNS_PIGLIN =
            defineUnsynced("gameplay/nether_portal_spawns_piglin", AttributeTypes.BOOLEAN, false);

    public static final EnvironmentAttribute<Boolean> GAMEPLAY_FAST_LAVA =
            define("gameplay/fast_lava", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_INCREASED_FIRE_BURNOUT =
            defineUnsynced("gameplay/increased_fire_burnout", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<TriState> GAMEPLAY_EYEBLOSSOM_OPEN =
            defineUnsynced("gameplay/eyeblossom_open", AttributeTypes.TRI_STATE, TriState.NOT_SET);

    @Obsolete
    public static final EnvironmentAttribute<Float> GAMEPLAY_TURTLE_EGG_HATCH_CHANCE =
            defineUnsynced("gameplay/turtle_egg_hatch_chance", AttributeTypes.FLOAT, 0.002F);

    public static final EnvironmentAttribute<Boolean> GAMEPLAY_PIGLINS_ZOMBIFY =
            define("gameplay/piglins_zombify", AttributeTypes.BOOLEAN, true);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_SNOW_GOLEM_MELTS =
            defineUnsynced("gameplay/snow_golem_melts", AttributeTypes.BOOLEAN, false);

    public static final EnvironmentAttribute<Boolean> GAMEPLAY_CREAKING_ACTIVE =
            define("gameplay/creaking_active", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<Float> GAMEPLAY_SURFACE_SLIME_SPAWN_CHANCE =
            defineUnsynced("gameplay/surface_slime_spawn_chance", AttributeTypes.FLOAT, 0.0F);

    @Obsolete
    public static final EnvironmentAttribute<Float> GAMEPLAY_CAT_WAKING_UP_GIFT_CHANCE =
            defineUnsynced("gameplay/cat_waking_up_gift_chance", AttributeTypes.FLOAT, 0.0F);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_BEES_STAY_IN_HIVE =
            defineUnsynced("gameplay/bees_stay_in_hive", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_MONSTERS_BURN =
            defineUnsynced("gameplay/monsters_burn", AttributeTypes.BOOLEAN, false);

    @Obsolete
    public static final EnvironmentAttribute<Boolean> GAMEPLAY_CAN_PILLAGER_PATROL_SPAWN =
            defineUnsynced("gameplay/can_pillager_patrol_spawn", AttributeTypes.BOOLEAN, true);

    @Obsolete
    public static final EnvironmentAttribute<?> GAMEPLAY_VILLAGER_ACTIVITY =
            defineUnsynced("gameplay/villager_activity");

    @Obsolete
    public static final EnvironmentAttribute<?> GAMEPLAY_BABY_VILLAGER_ACTIVITY =
            defineUnsynced("gameplay/baby_villager_activity");

    public static final EnvironmentAttribute<Color> BLOCK_LIGHT_TINT =
            define("visual/block_light_tint", AttributeTypes.RGB_COLOR, new Color(-10100));

    public static final EnvironmentAttribute<Color> NIGHT_VISION_COLOR =
            define("visual/night_vision_color", AttributeTypes.RGB_COLOR, new Color(-6710887));

    public static final EnvironmentAttribute<Color> AMBIENT_LIGHT_COLOR =
            define("visual/ambient_light_color", AttributeTypes.RGB_COLOR, new Color(-16777216));

    @Obsolete
    public static final EnvironmentAttribute<?> GAMEPLAY_STRAW_BED_RULE =
            defineUnsynced("gameplay/straw_bed_rule");

    @Obsolete
    public static final EnvironmentAttribute<?> GAMEPLAY_NATURAL_MOB_SPAWNS =
            defineUnsynced("gameplay/natural_mob_spawns");

    @Obsolete
    public static final EnvironmentAttribute<Float> GAMEPLAY_CREATURE_WORLD_GEN_SPAWN_PROBABILITY =
            defineUnsynced(
                    "gameplay/creature_world_gen_spawn_probability", AttributeTypes.FLOAT, 0.1F);

    private EnvironmentAttributes() {}

    public static VersionedRegistry<EnvironmentAttribute<?>> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static <T> EnvironmentAttribute<T> defineUnsynced(
            String text, AttributeType<T> attributeType, T t) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticEnvironmentAttribute<>(
                                result, false, ((AttributeType) attributeType), (T) ((Object) t)));
    }

    @Internal
    public static <T> EnvironmentAttribute<T> defineUnsynced(String text) {
        return REGISTRY.define(
                text, result -> new StaticEnvironmentAttribute<>(result, false, null, null));
    }

    @Internal
    public static <T> EnvironmentAttribute<T> define(
            String text, AttributeType<T> attributeType, T t) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticEnvironmentAttribute<>(
                                result, true, ((AttributeType) attributeType), (T) ((Object) t)));
    }

    static {
        REGISTRY.unloadMappings();
    }
}
