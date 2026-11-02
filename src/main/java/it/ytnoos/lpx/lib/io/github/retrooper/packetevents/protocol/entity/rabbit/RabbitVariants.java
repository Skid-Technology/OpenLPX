package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.rabbit;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class RabbitVariants {

    private static final VersionedRegistry<RabbitVariant> REGISTRY =
            new VersionedRegistry<>("rabbit_variant");

    public static final RabbitVariant BROWN = define("brown");

    public static final RabbitVariant WHITE = define("white");

    public static final RabbitVariant BLACK = define("black");

    public static final RabbitVariant WHITE_SPLOTCHED = define("white_splotched");

    public static final RabbitVariant GOLD = define("gold");

    public static final RabbitVariant SALT = define("salt");

    public static final RabbitVariant EVIL = define("evil");

    private RabbitVariants() {}

    @Internal
    public static RabbitVariant define(String text) {
        return REGISTRY.define(text, StaticRabbitVariant::new);
    }

    public static VersionedRegistry<RabbitVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
