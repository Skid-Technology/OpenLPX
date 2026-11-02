package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.salmon;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class SalmonVariants {

    private static final VersionedRegistry<SalmonVariant> REGISTRY =
            new VersionedRegistry<>("salmon_variant");

    public static final SalmonVariant SMALL = define("small");

    public static final SalmonVariant MEDIUM = define("medium");

    public static final SalmonVariant LARGE = define("large");

    private SalmonVariants() {}

    @Internal
    public static SalmonVariant define(String text) {
        return REGISTRY.define(text, StaticSalmonVariant::new);
    }

    public static VersionedRegistry<SalmonVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
