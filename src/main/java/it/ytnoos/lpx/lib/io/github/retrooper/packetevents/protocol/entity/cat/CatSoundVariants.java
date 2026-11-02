package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CatSoundVariants {

    private static final VersionedRegistry<CatSoundVariant> REGISTRY =
            new VersionedRegistry<>("cat_sound_variant");

    public static final CatSoundVariant CLASSIC = define("classic", "cat");

    public static final CatSoundVariant ROYAL = define("royal", "cat_royal");

    private CatSoundVariants() {}

    @Internal
    private static CatSoundVariant define(String text, String text2) {
        CatSoundSet orThrow = CatSoundSet.getOrThrow("entity." + text2 + ".");
        CatSoundSet orThrow2 = CatSoundSet.getOrThrow("entity.baby_cat.");
        return REGISTRY.define(
                text, result -> new StaticCatSoundVariant(result, orThrow, orThrow2));
    }

    public static VersionedRegistry<CatSoundVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
