package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.parrot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class ParrotVariants {

    private static final VersionedRegistry<ParrotVariant> REGISTRY =
            new VersionedRegistry<>("parrot_variant");

    public static final ParrotVariant RED_BLUE = define("red_blue");

    public static final ParrotVariant BLUE = define("blue");

    public static final ParrotVariant GREEN = define("green");

    public static final ParrotVariant YELLOW_BLUE = define("yellow_blue");

    public static final ParrotVariant GRAY = define("gray");

    private ParrotVariants() {}

    @Internal
    public static ParrotVariant define(String text) {
        return REGISTRY.define(text, StaticParrotVariant::new);
    }

    public static VersionedRegistry<ParrotVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
