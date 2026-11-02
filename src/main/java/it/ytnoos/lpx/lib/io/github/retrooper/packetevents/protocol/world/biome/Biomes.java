package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.SequentialNBTReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.MappingHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalInt;

@NullMarked
public final class Biomes {

    private static final VersionedRegistry<Biome> REGISTRY =
            new VersionedRegistry<>("worldgen/biome");

    private static final Map<ResourceLocation, NBTCompound> BIOME_DATA = new HashMap<>();

    @Obsolete public static final Biome SNOWY_MOUNTAINS;

    @Obsolete public static final Biome GIANT_SPRUCE_TAIGA;

    @Obsolete public static final Biome BADLANDS_PLATEAU;

    @Obsolete public static final Biome DESERT_HILLS;

    @Obsolete public static final Biome SNOWY_TAIGA_HILLS;

    @Obsolete public static final Biome DARK_FOREST_HILLS;

    @Obsolete public static final Biome MUSHROOM_FIELD_SHORE;

    @Obsolete public static final Biome TALL_BIRCH_FOREST;

    @Obsolete public static final Biome SNOWY_TAIGA_MOUNTAINS;

    @Obsolete public static final Biome TAIGA_MOUNTAINS;

    @Obsolete public static final Biome BAMBOO_JUNGLE_HILLS;

    @Obsolete public static final Biome WOODED_MOUNTAINS;

    @Obsolete public static final Biome TAIGA_HILLS;

    @Obsolete public static final Biome MODIFIED_GRAVELLY_MOUNTAINS;

    @Obsolete public static final Biome MODIFIED_WOODED_BADLANDS_PLATEAU;

    @Obsolete public static final Biome DEEP_WARM_OCEAN;

    @Obsolete public static final Biome GIANT_TREE_TAIGA;

    @Obsolete public static final Biome MODIFIED_JUNGLE;

    @Obsolete public static final Biome TALL_BIRCH_HILLS;

    @Obsolete public static final Biome WOODED_BADLANDS_PLATEAU;

    @Obsolete public static final Biome SNOWY_TUNDRA;

    @Obsolete public static final Biome MOUNTAINS;

    @Obsolete public static final Biome WOODED_HILLS;

    @Obsolete public static final Biome GRAVELLY_MOUNTAINS;

    @Obsolete public static final Biome GIANT_SPRUCE_TAIGA_HILLS;

    @Obsolete public static final Biome MODIFIED_BADLANDS_PLATEAU;

    @Obsolete public static final Biome JUNGLE_HILLS;

    @Obsolete public static final Biome JUNGLE_EDGE;

    @Obsolete public static final Biome MODIFIED_JUNGLE_EDGE;

    @Obsolete public static final Biome SWAMP_HILLS;

    @Obsolete public static final Biome GIANT_TREE_TAIGA_HILLS;

    @Obsolete public static final Biome SHATTERED_SAVANNA;

    @Obsolete public static final Biome MOUNTAIN_EDGE;

    @Obsolete public static final Biome DESERT_LAKES;

    @Obsolete public static final Biome BIRCH_FOREST_HILLS;

    @Obsolete public static final Biome SHATTERED_SAVANNA_PLATEAU;

    @Obsolete public static final Biome STONE_SHORE;

    @Obsolete public static final Biome NETHER;

    public static final Biome BADLANDS;

    public static final Biome BAMBOO_JUNGLE;

    public static final Biome BASALT_DELTAS;

    public static final Biome BEACH;

    public static final Biome BIRCH_FOREST;

    public static final Biome CHERRY_GROVE;

    public static final Biome COLD_OCEAN;

    public static final Biome CRIMSON_FOREST;

    public static final Biome DARK_FOREST;

    public static final Biome DEEP_COLD_OCEAN;

    public static final Biome DEEP_DARK;

    public static final Biome DEEP_FROZEN_OCEAN;

    public static final Biome DEEP_LUKEWARM_OCEAN;

    public static final Biome DEEP_OCEAN;

    public static final Biome DESERT;

    public static final Biome DRIPSTONE_CAVES;

    public static final Biome END_BARRENS;

    public static final Biome END_HIGHLANDS;

    public static final Biome END_MIDLANDS;

    public static final Biome ERODED_BADLANDS;

    public static final Biome FLOWER_FOREST;

    public static final Biome FOREST;

    public static final Biome FROZEN_OCEAN;

    public static final Biome FROZEN_PEAKS;

    public static final Biome FROZEN_RIVER;

    public static final Biome GROVE;

    public static final Biome ICE_SPIKES;

    public static final Biome JAGGED_PEAKS;

    public static final Biome JUNGLE;

    public static final Biome LUKEWARM_OCEAN;

    public static final Biome LUSH_CAVES;

    public static final Biome MANGROVE_SWAMP;

    public static final Biome MEADOW;

    public static final Biome MUSHROOM_FIELDS;

    public static final Biome NETHER_WASTES;

    public static final Biome OCEAN;

    public static final Biome OLD_GROWTH_BIRCH_FOREST;

    public static final Biome OLD_GROWTH_PINE_TAIGA;

    public static final Biome OLD_GROWTH_SPRUCE_TAIGA;

    public static final Biome PLAINS;

    public static final Biome RIVER;

    public static final Biome SAVANNA;

    public static final Biome SAVANNA_PLATEAU;

    public static final Biome SMALL_END_ISLANDS;

    public static final Biome SNOWY_BEACH;

    public static final Biome SNOWY_PLAINS;

    public static final Biome SNOWY_SLOPES;

    public static final Biome SNOWY_TAIGA;

    public static final Biome SOUL_SAND_VALLEY;

    public static final Biome SPARSE_JUNGLE;

    public static final Biome STONY_PEAKS;

    public static final Biome STONY_SHORE;

    public static final Biome SUNFLOWER_PLAINS;

    public static final Biome SWAMP;

    public static final Biome TAIGA;

    public static final Biome THE_END;

    public static final Biome THE_VOID;

    public static final Biome WARM_OCEAN;

    public static final Biome WARPED_FOREST;

    public static final Biome WINDSWEPT_FOREST;

    public static final Biome WINDSWEPT_GRAVELLY_HILLS;

    public static final Biome WINDSWEPT_HILLS;

    public static final Biome WINDSWEPT_SAVANNA;

    public static final Biome WOODED_BADLANDS;

    public static final Biome PALE_GARDEN;

    public static final Biome SULFUR_CAVES;

    public static final Biome DAPPLED_FOREST;

    private Biomes() {}

    @Internal
    public static Biome define(String text) {
        return define(text, false);
    }

    @Internal
    public static Biome define(String text, boolean matches) {
        return REGISTRY.define(
                text,
                result -> {
                    NBTCompound nBTCompound;
                    if ((nBTCompound = BIOME_DATA.get(result.getName())) != null) {
                        PacketWrapper packetWrapper =
                                PacketWrapper.createDummyWrapper(ClientVersion.getLatest());
                        return Biome.CODEC.decode(nBTCompound, packetWrapper).copy(result);
                    } else if (matches) {
                        BiomeEffects biomeEffects =
                                new BiomeEffects(
                                        12638463,
                                        4159204,
                                        329011,
                                        7907327,
                                        OptionalInt.empty(),
                                        OptionalInt.empty(),
                                        BiomeEffects.GrassColorModifier.NONE,
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty(),
                                        Optional.empty());
                        return new StaticBiome(
                                result,
                                true,
                                0.8F,
                                Biome.TemperatureModifier.NONE,
                                0.4F,
                                null,
                                null,
                                null,
                                biomeEffects,
                                EnvironmentAttributeMap.EMPTY);
                    } else {
                        throw new IllegalArgumentException(
                                "Can't define biome " + result.getName() + ", no data found");
                    }
                });
    }

    public static VersionedRegistry<Biome> getRegistry() {
        return REGISTRY;
    }

    static {
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/data/worldgen/biome");
            try {
                compound.skipOne();
                SequentialNBTReader.Compound compound2 =
                        (SequentialNBTReader.Compound) compound.next().getValue();
                while (compound2.hasNext()) {
                    Entry entry = (Entry) compound2.next();
                    ResourceLocation resourceLocation =
                            new ResourceLocation((String) entry.getKey());
                    BIOME_DATA.put(
                            resourceLocation,
                            ((SequentialNBTReader.Compound) entry.getValue()).readFully());
                }
            } catch (Throwable throwable) {
                if (compound != null) {
                    try {
                        compound.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            if (compound != null) {
                compound.close();
            }
        } catch (IOException oException) {
            throw new RuntimeException("Error while reading biome data", oException);
        }
        SNOWY_MOUNTAINS = define("snowy_mountains", true);
        GIANT_SPRUCE_TAIGA = define("giant_spruce_taiga", true);
        BADLANDS_PLATEAU = define("badlands_plateau", true);
        DESERT_HILLS = define("desert_hills", true);
        SNOWY_TAIGA_HILLS = define("snowy_taiga_hills", true);
        DARK_FOREST_HILLS = define("dark_forest_hills", true);
        MUSHROOM_FIELD_SHORE = define("mushroom_field_shore", true);
        TALL_BIRCH_FOREST = define("tall_birch_forest", true);
        SNOWY_TAIGA_MOUNTAINS = define("snowy_taiga_mountains", true);
        TAIGA_MOUNTAINS = define("taiga_mountains", true);
        BAMBOO_JUNGLE_HILLS = define("bamboo_jungle_hills", true);
        WOODED_MOUNTAINS = define("wooded_mountains", true);
        TAIGA_HILLS = define("taiga_hills", true);
        MODIFIED_GRAVELLY_MOUNTAINS = define("modified_gravelly_mountains", true);
        MODIFIED_WOODED_BADLANDS_PLATEAU = define("modified_wooded_badlands_plateau", true);
        DEEP_WARM_OCEAN = define("deep_warm_ocean", true);
        GIANT_TREE_TAIGA = define("giant_tree_taiga", true);
        MODIFIED_JUNGLE = define("modified_jungle", true);
        TALL_BIRCH_HILLS = define("tall_birch_hills", true);
        WOODED_BADLANDS_PLATEAU = define("wooded_badlands_plateau", true);
        SNOWY_TUNDRA = define("snowy_tundra", true);
        MOUNTAINS = define("mountains", true);
        WOODED_HILLS = define("wooded_hills", true);
        GRAVELLY_MOUNTAINS = define("gravelly_mountains", true);
        GIANT_SPRUCE_TAIGA_HILLS = define("giant_spruce_taiga_hills", true);
        MODIFIED_BADLANDS_PLATEAU = define("modified_badlands_plateau", true);
        JUNGLE_HILLS = define("jungle_hills", true);
        JUNGLE_EDGE = define("jungle_edge", true);
        MODIFIED_JUNGLE_EDGE = define("modified_jungle_edge", true);
        SWAMP_HILLS = define("swamp_hills", true);
        GIANT_TREE_TAIGA_HILLS = define("giant_tree_taiga_hills", true);
        SHATTERED_SAVANNA = define("shattered_savanna", true);
        MOUNTAIN_EDGE = define("mountain_edge", true);
        DESERT_LAKES = define("desert_lakes", true);
        BIRCH_FOREST_HILLS = define("birch_forest_hills", true);
        SHATTERED_SAVANNA_PLATEAU = define("shattered_savanna_plateau", true);
        STONE_SHORE = define("stone_shore", true);
        NETHER = define("nether", true);
        BADLANDS = define("badlands");
        BAMBOO_JUNGLE = define("bamboo_jungle");
        BASALT_DELTAS = define("basalt_deltas");
        BEACH = define("beach");
        BIRCH_FOREST = define("birch_forest");
        CHERRY_GROVE = define("cherry_grove");
        COLD_OCEAN = define("cold_ocean");
        CRIMSON_FOREST = define("crimson_forest");
        DARK_FOREST = define("dark_forest");
        DEEP_COLD_OCEAN = define("deep_cold_ocean");
        DEEP_DARK = define("deep_dark");
        DEEP_FROZEN_OCEAN = define("deep_frozen_ocean");
        DEEP_LUKEWARM_OCEAN = define("deep_lukewarm_ocean");
        DEEP_OCEAN = define("deep_ocean");
        DESERT = define("desert");
        DRIPSTONE_CAVES = define("dripstone_caves");
        END_BARRENS = define("end_barrens");
        END_HIGHLANDS = define("end_highlands");
        END_MIDLANDS = define("end_midlands");
        ERODED_BADLANDS = define("eroded_badlands");
        FLOWER_FOREST = define("flower_forest");
        FOREST = define("forest");
        FROZEN_OCEAN = define("frozen_ocean");
        FROZEN_PEAKS = define("frozen_peaks");
        FROZEN_RIVER = define("frozen_river");
        GROVE = define("grove");
        ICE_SPIKES = define("ice_spikes");
        JAGGED_PEAKS = define("jagged_peaks");
        JUNGLE = define("jungle");
        LUKEWARM_OCEAN = define("lukewarm_ocean");
        LUSH_CAVES = define("lush_caves");
        MANGROVE_SWAMP = define("mangrove_swamp");
        MEADOW = define("meadow");
        MUSHROOM_FIELDS = define("mushroom_fields");
        NETHER_WASTES = define("nether_wastes");
        OCEAN = define("ocean");
        OLD_GROWTH_BIRCH_FOREST = define("old_growth_birch_forest");
        OLD_GROWTH_PINE_TAIGA = define("old_growth_pine_taiga");
        OLD_GROWTH_SPRUCE_TAIGA = define("old_growth_spruce_taiga");
        PLAINS = define("plains");
        RIVER = define("river");
        SAVANNA = define("savanna");
        SAVANNA_PLATEAU = define("savanna_plateau");
        SMALL_END_ISLANDS = define("small_end_islands");
        SNOWY_BEACH = define("snowy_beach");
        SNOWY_PLAINS = define("snowy_plains");
        SNOWY_SLOPES = define("snowy_slopes");
        SNOWY_TAIGA = define("snowy_taiga");
        SOUL_SAND_VALLEY = define("soul_sand_valley");
        SPARSE_JUNGLE = define("sparse_jungle");
        STONY_PEAKS = define("stony_peaks");
        STONY_SHORE = define("stony_shore");
        SUNFLOWER_PLAINS = define("sunflower_plains");
        SWAMP = define("swamp");
        TAIGA = define("taiga");
        THE_END = define("the_end");
        THE_VOID = define("the_void");
        WARM_OCEAN = define("warm_ocean");
        WARPED_FOREST = define("warped_forest");
        WINDSWEPT_FOREST = define("windswept_forest");
        WINDSWEPT_GRAVELLY_HILLS = define("windswept_gravelly_hills");
        WINDSWEPT_HILLS = define("windswept_hills");
        WINDSWEPT_SAVANNA = define("windswept_savanna");
        WOODED_BADLANDS = define("wooded_badlands");
        PALE_GARDEN = define("pale_garden");
        SULFUR_CAVES = define("sulfur_caves");
        DAPPLED_FOREST = define("dappled_forest");
        BIOME_DATA.clear();
        REGISTRY.unloadMappings();
    }
}
