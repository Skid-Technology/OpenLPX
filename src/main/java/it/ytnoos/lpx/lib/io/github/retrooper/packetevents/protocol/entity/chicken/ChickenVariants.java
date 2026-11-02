package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class ChickenVariants {

    private static final VersionedRegistry<ChickenVariant> REGISTRY =
            new VersionedRegistry<>("chicken_variant");

    public static final ChickenVariant COLD = define("cold", ChickenVariant.ModelType.COLD, "cold");

    public static final ChickenVariant TEMPERATE =
            define("temperate", ChickenVariant.ModelType.NORMAL, "temperate");

    public static final ChickenVariant WARM =
            define("warm", ChickenVariant.ModelType.NORMAL, "warm");

    private ChickenVariants() {}

    @Internal
    public static ChickenVariant define(
            String text, ChickenVariant.ModelType modelType, String text2) {
        ResourceLocation resourceLocation = new ResourceLocation("entity/chicken/chicken_" + text2);
        ResourceLocation resourceLocation2 =
                new ResourceLocation("entity/chicken/chicken_" + text2 + "_baby");
        return REGISTRY.define(
                text,
                result ->
                        new StaticChickenVariant(
                                result, modelType, resourceLocation, resourceLocation2));
    }

    public static VersionedRegistry<ChickenVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
