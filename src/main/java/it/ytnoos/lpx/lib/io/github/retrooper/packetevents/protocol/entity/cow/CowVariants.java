package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CowVariants {

    private static final VersionedRegistry<CowVariant> REGISTRY =
            new VersionedRegistry<>("cow_variant");

    public static final CowVariant COLD = define("cold", CowVariant.ModelType.COLD, "cold_cow");

    public static final CowVariant TEMPERATE =
            define("temperate", CowVariant.ModelType.NORMAL, "temperate_cow");

    public static final CowVariant WARM = define("warm", CowVariant.ModelType.WARM, "warm_cow");

    private CowVariants() {}

    @Internal
    public static CowVariant define(String text, CowVariant.ModelType modelType, String text2) {
        ResourceLocation resourceLocation = new ResourceLocation("entity/cow/cow_" + text2);
        ResourceLocation resourceLocation2 =
                new ResourceLocation("entity/cow/cow_" + text2 + "_baby");
        return REGISTRY.define(
                text,
                result ->
                        new StaticCowVariant(
                                result, modelType, resourceLocation, resourceLocation2));
    }

    public static VersionedRegistry<CowVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
