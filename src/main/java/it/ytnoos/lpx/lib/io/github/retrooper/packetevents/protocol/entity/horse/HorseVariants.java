package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.horse;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class HorseVariants {

    private static final VersionedRegistry<HorseVariant> REGISTRY =
            new VersionedRegistry<>("horse_variant");

    public static final HorseVariant WHITE = define("white");

    public static final HorseVariant CREAMY = define("creamy");

    public static final HorseVariant CHESTNUT = define("chestnut");

    public static final HorseVariant BROWN = define("brown");

    public static final HorseVariant BLACK = define("black");

    public static final HorseVariant GRAY = define("gray");

    public static final HorseVariant DARK_BROWN = define("dark_brown");

    private HorseVariants() {}

    @Internal
    public static HorseVariant define(String text) {
        return REGISTRY.define(text, StaticHorseVariant::new);
    }

    public static VersionedRegistry<HorseVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
