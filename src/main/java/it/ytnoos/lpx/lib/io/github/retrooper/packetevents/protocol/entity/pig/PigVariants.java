package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class PigVariants {

    private static final VersionedRegistry<PigVariant> REGISTRY =
            new VersionedRegistry<>("pig_variant");

    public static final PigVariant COLD = define("cold", PigVariant.ModelType.COLD, "cold");

    public static final PigVariant TEMPERATE =
            define("temperate", PigVariant.ModelType.NORMAL, "temperate");

    public static final PigVariant WARM = define("warm", PigVariant.ModelType.NORMAL, "warm");

    private PigVariants() {}

    @Internal
    public static PigVariant define(String text, PigVariant.ModelType modelType, String text2) {
        ResourceLocation resourceLocation = new ResourceLocation("entity/pig/pig_" + text2);
        ResourceLocation resourceLocation2 =
                new ResourceLocation("entity/pig/pig_" + text2 + "_baby");
        return REGISTRY.define(
                text,
                result ->
                        new StaticPigVariant(
                                result, modelType, resourceLocation, resourceLocation2));
    }

    public static VersionedRegistry<PigVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
