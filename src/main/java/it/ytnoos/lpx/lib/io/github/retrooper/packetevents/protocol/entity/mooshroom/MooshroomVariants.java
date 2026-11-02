package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.mooshroom;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class MooshroomVariants {

    private static final VersionedRegistry<MooshroomVariant> REGISTRY =
            new VersionedRegistry<>("mooshroom_variant");

    public static final MooshroomVariant RED = define("red");

    public static final MooshroomVariant BROWN = define("brown");

    private MooshroomVariants() {}

    @Internal
    public static MooshroomVariant define(String text) {
        return REGISTRY.define(text, StaticMooshroomVariant::new);
    }

    public static VersionedRegistry<MooshroomVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
