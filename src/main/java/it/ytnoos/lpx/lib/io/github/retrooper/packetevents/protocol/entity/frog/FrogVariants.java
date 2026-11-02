package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.frog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class FrogVariants {

    private static final VersionedRegistry<FrogVariant> REGISTRY =
            new VersionedRegistry<>("frog_variant");

    public static final FrogVariant COLD = define("cold", "cold");

    public static final FrogVariant TEMPERATE = define("temperate", "temperate");

    public static final FrogVariant WARM = define("warm", "warm");

    private FrogVariants() {}

    @Internal
    public static FrogVariant define(String text, String text2) {
        ResourceLocation resourceLocation = new ResourceLocation("entity/frog/frog_" + text2);
        return REGISTRY.define(text, result -> new StaticFrogVariant(result, resourceLocation));
    }

    public static VersionedRegistry<FrogVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
