package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleBlockStateData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleColorData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleDustColorTransitionData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleDustData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleGeyserBaseData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleGeyserData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleItemStackData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticlePowerData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleSculkChargeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleShriekData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleSpellData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleTrailData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data.ParticleVibrationData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;

@NullMarked
public final class ParticleTypes {

    private static final VersionedRegistry<ParticleType<?>> REGISTRY =
            new VersionedRegistry<>("particle_type");

    public static final NbtCodec<ParticleType<?>> CODEC = NbtCodecs.forRegistry(REGISTRY);

    @Deprecated
    public static final ParticleType<ParticleData> AMBIENT_ENTITY_EFFECT =
            define("ambient_entity_effect");

    public static final ParticleType<ParticleData> ANGRY_VILLAGER = define("angry_villager");

    public static final ParticleType<ParticleBlockStateData> BLOCK =
            define(
                    "block",
                    ParticleBlockStateData::read,
                    ParticleBlockStateData::write,
                    ParticleBlockStateData::decode,
                    ParticleBlockStateData::encode);

    @Obsolete public static final ParticleType<ParticleData> BARRIER = define("barrier");

    @Obsolete public static final ParticleType<ParticleData> LIGHT = define("light");

    public static final ParticleType<ParticleBlockStateData> BLOCK_MARKER =
            define(
                    "block_marker",
                    ParticleBlockStateData::read,
                    ParticleBlockStateData::write,
                    ParticleBlockStateData::decode,
                    ParticleBlockStateData::encode);

    public static final ParticleType<ParticleData> BUBBLE = define("bubble");

    public static final ParticleType<ParticleData> CLOUD = define("cloud");

    public static final ParticleType<ParticleData> CRIT = define("crit");

    public static final ParticleType<ParticleData> DAMAGE_INDICATOR = define("damage_indicator");

    public static final ParticleType<ParticlePowerData> DRAGON_BREATH =
            define(
                    "dragon_breath",
                    ParticlePowerData::read,
                    ParticlePowerData::write,
                    ParticlePowerData::decode,
                    ParticlePowerData::encode);

    public static final ParticleType<ParticleData> DRIPPING_LAVA = define("dripping_lava");

    public static final ParticleType<ParticleData> FALLING_LAVA = define("falling_lava");

    public static final ParticleType<ParticleData> LANDING_LAVA = define("landing_lava");

    public static final ParticleType<ParticleData> DRIPPING_WATER = define("dripping_water");

    public static final ParticleType<ParticleData> FALLING_WATER = define("falling_water");

    public static final ParticleType<ParticleDustData> DUST =
            define(
                    "dust",
                    ParticleDustData::read,
                    ParticleDustData::write,
                    ParticleDustData::decode,
                    ParticleDustData::encode);

    public static final ParticleType<ParticleDustColorTransitionData> DUST_COLOR_TRANSITION =
            define(
                    "dust_color_transition",
                    ParticleDustColorTransitionData::read,
                    ParticleDustColorTransitionData::write,
                    ParticleDustColorTransitionData::decode,
                    ParticleDustColorTransitionData::encode);

    public static final ParticleType<ParticleSpellData> EFFECT =
            define(
                    "effect",
                    ParticleSpellData::read,
                    ParticleSpellData::write,
                    ParticleSpellData::decode,
                    ParticleSpellData::encode);

    public static final ParticleType<ParticleData> ELDER_GUARDIAN = define("elder_guardian");

    public static final ParticleType<ParticleData> ENCHANTED_HIT = define("enchanted_hit");

    public static final ParticleType<ParticleData> ENCHANT = define("enchant");

    public static final ParticleType<ParticleData> END_ROD = define("end_rod");

    public static final ParticleType<ParticleColorData> ENTITY_EFFECT =
            define(
                    "entity_effect",
                    ParticleColorData::read,
                    ParticleColorData::write,
                    ParticleColorData::decode,
                    ParticleColorData::encode);

    public static final ParticleType<ParticleData> EXPLOSION_EMITTER = define("explosion_emitter");

    public static final ParticleType<ParticleData> EXPLOSION = define("explosion");

    public static final ParticleType<ParticleData> SONIC_BOOM = define("sonic_boom");

    public static final ParticleType<ParticleBlockStateData> FALLING_DUST =
            define(
                    "falling_dust",
                    ParticleBlockStateData::read,
                    ParticleBlockStateData::write,
                    ParticleBlockStateData::decode,
                    ParticleBlockStateData::encode);

    public static final ParticleType<ParticleData> FIREWORK = define("firework");

    public static final ParticleType<ParticleData> FISHING = define("fishing");

    public static final ParticleType<ParticleData> FLAME = define("flame");

    public static final ParticleType<ParticleData> SCULK_SOUL = define("sculk_soul");

    public static final ParticleType<ParticleSculkChargeData> SCULK_CHARGE =
            define(
                    "sculk_charge",
                    ParticleSculkChargeData::read,
                    ParticleSculkChargeData::write,
                    ParticleSculkChargeData::decode,
                    ParticleSculkChargeData::encode);

    public static final ParticleType<ParticleData> SCULK_CHARGE_POP = define("sculk_charge_pop");

    public static final ParticleType<ParticleData> SOUL_FIRE_FLAME = define("soul_fire_flame");

    public static final ParticleType<ParticleData> SOUL = define("soul");

    public static final ParticleType<ParticleColorData> FLASH =
            define(
                    "flash",
                    result ->
                            result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)
                                    ? ParticleColorData.read(result)
                                    : new ParticleColorData(0),
                    (result, result2) -> {
                        if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
                            ParticleColorData.write(result, result2);
                        }
                    },
                    (result, result2) ->
                            result2.isNewerThanOrEquals(ClientVersion.V_1_21_9)
                                    ? ParticleColorData.decode(result, result2)
                                    : new ParticleColorData(0),
                    (result, result2, result3) -> {
                        if (result2.isNewerThanOrEquals(ClientVersion.V_1_21_9)) {
                            ParticleColorData.encode(result, result2, result3);
                        }
                    });

    public static final ParticleType<ParticleData> HAPPY_VILLAGER = define("happy_villager");

    public static final ParticleType<ParticleData> COMPOSTER = define("composter");

    public static final ParticleType<ParticleData> HEART = define("heart");

    public static final ParticleType<ParticleSpellData> INSTANT_EFFECT =
            define(
                    "instant_effect",
                    ParticleSpellData::read,
                    ParticleSpellData::write,
                    ParticleSpellData::decode,
                    ParticleSpellData::encode);

    public static final ParticleType<ParticleItemStackData> ITEM =
            define(
                    "item",
                    ParticleItemStackData::read,
                    ParticleItemStackData::write,
                    ParticleItemStackData::decode,
                    ParticleItemStackData::encode);

    public static final ParticleType<ParticleVibrationData> VIBRATION =
            define(
                    "vibration",
                    ParticleVibrationData::read,
                    ParticleVibrationData::write,
                    ParticleVibrationData::decode,
                    ParticleVibrationData::encode);

    public static final ParticleType<ParticleData> ITEM_SLIME = define("item_slime");

    public static final ParticleType<ParticleData> ITEM_SNOWBALL = define("item_snowball");

    public static final ParticleType<ParticleData> LARGE_SMOKE = define("large_smoke");

    public static final ParticleType<ParticleData> LAVA = define("lava");

    public static final ParticleType<ParticleData> MYCELIUM = define("mycelium");

    public static final ParticleType<ParticleData> NOTE = define("note");

    public static final ParticleType<ParticleData> POOF = define("poof");

    public static final ParticleType<ParticleData> PORTAL = define("portal");

    public static final ParticleType<ParticleData> RAIN = define("rain");

    public static final ParticleType<ParticleData> SMOKE = define("smoke");

    public static final ParticleType<ParticleData> SNEEZE = define("sneeze");

    public static final ParticleType<ParticleData> SPIT = define("spit");

    public static final ParticleType<ParticleData> SQUID_INK = define("squid_ink");

    public static final ParticleType<ParticleData> SWEEP_ATTACK = define("sweep_attack");

    public static final ParticleType<ParticleData> TOTEM_OF_UNDYING = define("totem_of_undying");

    public static final ParticleType<ParticleData> UNDERWATER = define("underwater");

    public static final ParticleType<ParticleData> SPLASH = define("splash");

    public static final ParticleType<ParticleData> WITCH = define("witch");

    public static final ParticleType<ParticleData> BUBBLE_POP = define("bubble_pop");

    public static final ParticleType<ParticleData> CURRENT_DOWN = define("current_down");

    public static final ParticleType<ParticleData> BUBBLE_COLUMN_UP = define("bubble_column_up");

    public static final ParticleType<ParticleData> NAUTILUS = define("nautilus");

    public static final ParticleType<ParticleData> DOLPHIN = define("dolphin");

    public static final ParticleType<ParticleData> CAMPFIRE_COSY_SMOKE =
            define("campfire_cosy_smoke");

    public static final ParticleType<ParticleData> CAMPFIRE_SIGNAL_SMOKE =
            define("campfire_signal_smoke");

    public static final ParticleType<ParticleData> DRIPPING_HONEY = define("dripping_honey");

    public static final ParticleType<ParticleData> FALLING_HONEY = define("falling_honey");

    public static final ParticleType<ParticleData> LANDING_HONEY = define("landing_honey");

    public static final ParticleType<ParticleData> FALLING_NECTAR = define("falling_nectar");

    public static final ParticleType<ParticleData> FALLING_SPORE_BLOSSOM =
            define("falling_spore_blossom");

    public static final ParticleType<ParticleData> ASH = define("ash");

    public static final ParticleType<ParticleData> CRIMSON_SPORE = define("crimson_spore");

    public static final ParticleType<ParticleData> WARPED_SPORE = define("warped_spore");

    public static final ParticleType<ParticleData> SPORE_BLOSSOM_AIR = define("spore_blossom_air");

    public static final ParticleType<ParticleData> DRIPPING_OBSIDIAN_TEAR =
            define("dripping_obsidian_tear");

    public static final ParticleType<ParticleData> FALLING_OBSIDIAN_TEAR =
            define("falling_obsidian_tear");

    public static final ParticleType<ParticleData> LANDING_OBSIDIAN_TEAR =
            define("landing_obsidian_tear");

    public static final ParticleType<ParticleData> REVERSE_PORTAL = define("reverse_portal");

    public static final ParticleType<ParticleData> WHITE_ASH = define("white_ash");

    public static final ParticleType<ParticleData> SMALL_FLAME = define("small_flame");

    public static final ParticleType<ParticleData> SNOWFLAKE = define("snowflake");

    public static final ParticleType<ParticleData> DRIPPING_DRIPSTONE_LAVA =
            define("dripping_dripstone_lava");

    public static final ParticleType<ParticleData> FALLING_DRIPSTONE_LAVA =
            define("falling_dripstone_lava");

    public static final ParticleType<ParticleData> DRIPPING_DRIPSTONE_WATER =
            define("dripping_dripstone_water");

    public static final ParticleType<ParticleData> FALLING_DRIPSTONE_WATER =
            define("falling_dripstone_water");

    public static final ParticleType<ParticleData> GLOW_SQUID_INK = define("glow_squid_ink");

    public static final ParticleType<ParticleData> GLOW = define("glow");

    public static final ParticleType<ParticleData> WAX_ON = define("wax_on");

    public static final ParticleType<ParticleData> WAX_OFF = define("wax_off");

    public static final ParticleType<ParticleData> ELECTRIC_SPARK = define("electric_spark");

    public static final ParticleType<ParticleData> SCRAPE = define("scrape");

    public static final ParticleType<ParticleShriekData> SHRIEK =
            define(
                    "shriek",
                    ParticleShriekData::read,
                    ParticleShriekData::write,
                    ParticleShriekData::decode,
                    ParticleShriekData::encode);

    public static final ParticleType<ParticleData> DRIPPING_CHERRY_LEAVES =
            define("dripping_cherry_leaves");

    public static final ParticleType<ParticleData> FALLING_CHERRY_LEAVES =
            define("falling_cherry_leaves");

    public static final ParticleType<ParticleData> LANDING_CHERRY_LEAVES =
            define("landing_cherry_leaves");

    public static final ParticleType<ParticleData> CHERRY_LEAVES = define("cherry_leaves");

    public static final ParticleType<ParticleData> EGG_CRACK = define("egg_crack");

    public static final ParticleType<ParticleData> GUST = define("gust");

    @Deprecated
    public static final ParticleType<ParticleData> GUST_EMITTER = define("gust_emitter");

    public static final ParticleType<ParticleData> WHITE_SMOKE = define("white_smoke");

    public static final ParticleType<ParticleData> DUST_PLUME = define("dust_plume");

    public static final ParticleType<ParticleData> GUST_DUST = define("gust_dust");

    public static final ParticleType<ParticleData> TRIAL_SPAWNER_DETECTION =
            define("trial_spawner_detection");

    public static final ParticleType<ParticleData> SMALL_GUST = define("small_gust");

    public static final ParticleType<ParticleData> GUST_EMITTER_LARGE =
            define("gust_emitter_large");

    public static final ParticleType<ParticleData> GUST_EMITTER_SMALL =
            define("gust_emitter_small");

    public static final ParticleType<ParticleData> INFESTED = define("infested");

    public static final ParticleType<ParticleData> ITEM_COBWEB = define("item_cobweb");

    public static final ParticleType<ParticleData> TRIAL_SPAWNER_DETECTION_OMINOUS =
            define("trial_spawner_detection_ominous");

    public static final ParticleType<ParticleData> VAULT_CONNECTION = define("vault_connection");

    public static final ParticleType<ParticleBlockStateData> DUST_PILLAR =
            define(
                    "dust_pillar",
                    ParticleBlockStateData::read,
                    ParticleBlockStateData::write,
                    ParticleBlockStateData::decode,
                    ParticleBlockStateData::encode);

    public static final ParticleType<ParticleData> OMINOUS_SPAWNING = define("ominous_spawning");

    public static final ParticleType<ParticleData> RAID_OMEN = define("raid_omen");

    public static final ParticleType<ParticleData> TRIAL_OMEN = define("trial_omen");

    public static final ParticleType<ParticleTrailData> TRAIL =
            define(
                    "trail",
                    ParticleTrailData::read,
                    ParticleTrailData::write,
                    ParticleTrailData::decode,
                    ParticleTrailData::encode);

    public static final ParticleType<ParticleBlockStateData> BLOCK_CRUMBLE =
            define(
                    "block_crumble",
                    ParticleBlockStateData::read,
                    ParticleBlockStateData::write,
                    ParticleBlockStateData::decode,
                    ParticleBlockStateData::encode);

    public static final ParticleType<ParticleData> PALE_OAK_LEAVES = define("pale_oak_leaves");

    public static final ParticleType<ParticleColorData> TINTED_LEAVES =
            define(
                    "tinted_leaves",
                    ParticleColorData::read,
                    ParticleColorData::write,
                    ParticleColorData::decode,
                    ParticleColorData::encode);

    public static final ParticleType<ParticleData> FIREFLY = define("firefly");

    public static final ParticleType<ParticleData> COPPER_FIRE_FLAME = define("copper_fire_flame");

    public static final ParticleType<ParticleData> PAUSE_MOB_GROWTH = define("pause_mob_growth");

    public static final ParticleType<ParticleData> RESET_MOB_GROWTH = define("reset_mob_growth");

    public static final ParticleType<ParticleData> SULFUR_BUBBLES = define("sulfur_bubbles");

    public static final ParticleType<ParticleData> NOXIOUS_GAS = define("noxious_gas");

    public static final ParticleType<ParticleData> NOXIOUS_GAS_CLOUD = define("noxious_gas_cloud");

    public static final ParticleType<ParticleGeyserData> GEYSER =
            define(
                    "geyser",
                    ParticleGeyserData::read,
                    ParticleGeyserData::write,
                    ParticleGeyserData.MAP_CODEC);

    public static final ParticleType<ParticleGeyserBaseData> GEYSER_BASE =
            define(
                    "geyser_base",
                    ParticleGeyserBaseData::read,
                    ParticleGeyserBaseData::write,
                    ParticleGeyserBaseData.MAP_CODEC);

    public static final ParticleType<ParticleGeyserBaseData> GEYSER_POOF =
            define(
                    "geyser_poof",
                    ParticleGeyserBaseData::read,
                    ParticleGeyserBaseData::write,
                    ParticleGeyserBaseData.MAP_CODEC);

    public static final ParticleType<ParticleGeyserData> GEYSER_PLUME =
            define(
                    "geyser_plume",
                    ParticleGeyserData::read,
                    ParticleGeyserData::write,
                    ParticleGeyserData.MAP_CODEC);

    public static final ParticleType<ParticleData> SULFUR_CUBE_GOO = define("sulfur_cube_goo");

    public static final ParticleType<ParticleData> RED_POPLAR_LEAVES = define("red_poplar_leaves");

    public static final ParticleType<ParticleData> ORANGE_POPLAR_LEAVES =
            define("orange_poplar_leaves");

    public static final ParticleType<ParticleData> YELLOW_POPLAR_LEAVES =
            define("yellow_poplar_leaves");

    private ParticleTypes() {}

    public static VersionedRegistry<ParticleType<?>> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static ParticleType<ParticleData> define(String text) {
        return define(
                text,
                result -> ParticleData.emptyData(),
                null,
                (result, result2) -> ParticleData.emptyData(),
                null);
    }

    @Internal
    public static <T extends ParticleData> ParticleType<T> define(
            String text,
            PacketWrapper.Reader<T> reader,
            @Nullable PacketWrapper.Writer<T> writer,
            NbtMapCodec<T> nbtMapCodec) {
        ParticleTypes.Decoder decoder = ParticleTypes.Decoder.fromCodec(nbtMapCodec);
        ParticleTypes.Encoder encoder = ParticleTypes.Encoder.fromCodec(nbtMapCodec);
        return define(text, reader, writer, decoder, encoder);
    }

    @Internal
    public static <T extends ParticleData> ParticleType<T> define(
            String text,
            PacketWrapper.Reader<T> reader,
            @Nullable PacketWrapper.Writer<T> writer,
            ParticleTypes.Decoder<T> decoder,
            @Nullable ParticleTypes.Encoder<T> encoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticParticleType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer),
                                ((ParticleTypes.Decoder) decoder),
                                ((ParticleTypes.Encoder) encoder)));
    }

    @Nullable
    public static ParticleType<?> getByName(String text) {
        return REGISTRY.getByName(text);
    }

    @Nullable
    public static ParticleType<?> getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<ParticleType<?>> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }

    @FunctionalInterface
    @Internal
    public interface Decoder<T> {

        static <T> ParticleTypes.Decoder<T> fromCodec(NbtMapCodec<T> nbtMapCodec) {
            return (result, result2) ->
                    (T)
                            ((NbtMapCodec) nbtMapCodec)
                                    .decode(result, PacketWrapper.createDummyWrapper(result2));
        }

        T decode(NBTCompound nBTCompound, ClientVersion clientVersion);
    }

    @FunctionalInterface
    @Internal
    public interface Encoder<T> {

        static <T> ParticleTypes.Encoder<T> fromCodec(NbtMapCodec<T> nbtMapCodec) {
            return (result, result2, result3) ->
                    ((NbtMapCodec) nbtMapCodec)
                            .encode(result3, PacketWrapper.createDummyWrapper(result2), result);
        }

        void encode(T t, ClientVersion clientVersion, NBTCompound nBTCompound);
    }
}
