package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.fox;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class FoxVariants {

    private static final VersionedRegistry<FoxVariant> REGISTRY =
            new VersionedRegistry<>("fox_variant");

    public static final FoxVariant RED = define("red");

    public static final FoxVariant SNOW = define("snow");

    private FoxVariants() {}

    @Internal
    public static FoxVariant define(String text) {
        return REGISTRY.define(text, StaticFoxVariant::new);
    }

    public static VersionedRegistry<FoxVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
