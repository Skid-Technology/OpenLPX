package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biome;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biomes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Collections;

public final class WolfVariants {

    private static final VersionedRegistry<WolfVariant> REGISTRY =
            new VersionedRegistry<>("wolf_variant");

    public static final WolfVariant PALE =
            define("pale", "wolf", new MappedEntitySet<>(Collections.singletonList(Biomes.TAIGA)));

    public static final WolfVariant SPOTTED =
            define("spotted", new MappedEntitySet<>(ResourceLocation.minecraft("is_savanna")));

    public static final WolfVariant SNOWY =
            define("snowy", new MappedEntitySet<>(Collections.singletonList(Biomes.GROVE)));

    public static final WolfVariant BLACK =
            define(
                    "black",
                    new MappedEntitySet<>(Collections.singletonList(Biomes.OLD_GROWTH_PINE_TAIGA)));

    public static final WolfVariant ASHEN =
            define("ashen", new MappedEntitySet<>(Collections.singletonList(Biomes.SNOWY_TAIGA)));

    public static final WolfVariant RUSTY =
            define("rusty", new MappedEntitySet<>(ResourceLocation.minecraft("is_jungle")));

    public static final WolfVariant WOODS =
            define("woods", new MappedEntitySet<>(Collections.singletonList(Biomes.FOREST)));

    public static final WolfVariant CHESTNUT =
            define(
                    "chestnut",
                    new MappedEntitySet<>(
                            Collections.singletonList(Biomes.OLD_GROWTH_SPRUCE_TAIGA)));

    public static final WolfVariant STRIPED =
            define("striped", new MappedEntitySet<>(ResourceLocation.minecraft("is_badlands")));

    private WolfVariants() {}

    @Internal
    public static WolfVariant define(String text, MappedEntitySet<Biome> mappedEntitySet) {
        return define(text, "wolf_" + text, mappedEntitySet);
    }

    @Internal
    public static WolfVariant define(
            String text, String text2, MappedEntitySet<Biome> mappedEntitySet) {
        WolfAssetSet orThrow = WolfAssetSet.getOrThrow("minecraft", "entity/wolf/" + text2, "");
        WolfAssetSet orThrow2 =
                WolfAssetSet.getOrThrow("minecraft", "entity/wolf/" + text2, "_baby");
        return define(text, orThrow, orThrow2, mappedEntitySet);
    }

    @Internal
    public static WolfVariant define(
            String text,
            WolfAssetSet wolfAssetSet,
            WolfAssetSet wolfAssetSet2,
            MappedEntitySet<Biome> mappedEntitySet) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticWolfVariant(
                                result,
                                wolfAssetSet,
                                wolfAssetSet2,
                                ((MappedEntitySet) mappedEntitySet)));
    }

    public static VersionedRegistry<WolfVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
