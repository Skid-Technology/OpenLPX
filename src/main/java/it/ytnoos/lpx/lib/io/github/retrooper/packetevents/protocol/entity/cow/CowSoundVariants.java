package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CowSoundVariants {

    private static final VersionedRegistry<CowSoundVariant> REGISTRY =
            new VersionedRegistry<>("cow_sound_variant");

    public static final CowSoundVariant CLASSIC = define("classic", "cow");

    public static final CowSoundVariant MOODY = define("moody", "cow_moody");

    private CowSoundVariants() {}

    @Internal
    private static CowSoundVariant define(String text, String text2) {
        CowSoundSet orThrow = CowSoundSet.getOrThrow("entity." + text2 + ".");
        return REGISTRY.define(text, result -> new StaticCowSoundVariant(result, orThrow));
    }

    public static VersionedRegistry<CowSoundVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
