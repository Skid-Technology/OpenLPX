package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.nautilus;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ZombieNautilusVariants {

    private static final VersionedRegistry<ZombieNautilusVariant> REGISTRY =
            new VersionedRegistry<>("zombie_nautilus_variant");

    public static final ZombieNautilusVariant TEMPERATE =
            define("temperate", ZombieNautilusVariant.ModelType.NORMAL, "zombie_nautilus");

    public static final ZombieNautilusVariant WARM =
            define("warm", ZombieNautilusVariant.ModelType.WARM, "zombie_nautilus_coral");

    private ZombieNautilusVariants() {}

    @Internal
    public static ZombieNautilusVariant define(
            String text, ZombieNautilusVariant.ModelType modelType, String text2) {
        ResourceLocation resourceLocation = new ResourceLocation("entity/nautilus/" + text2);
        return REGISTRY.define(
                text,
                result -> new StaticZombieNautilusVariant(result, modelType, resourceLocation));
    }

    public static VersionedRegistry<ZombieNautilusVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
