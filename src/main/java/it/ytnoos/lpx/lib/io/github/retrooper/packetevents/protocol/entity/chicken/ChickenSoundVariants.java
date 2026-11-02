package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ChickenSoundVariants {

    private static final VersionedRegistry<ChickenSoundVariant> REGISTRY =
            new VersionedRegistry<>("chicken_sound_variant");

    public static final ChickenSoundVariant CLASSIC = define("classic", "chicken");

    public static final ChickenSoundVariant PICKY = define("picky", "chicken_picky");

    private ChickenSoundVariants() {}

    @Internal
    private static ChickenSoundVariant define(String text, String text2) {
        ChickenSoundSet orThrow =
                ChickenSoundSet.getOrThrow("entity." + text2 + ".", Sounds.ENTITY_CHICKEN_STEP);
        ChickenSoundSet orThrow2 =
                ChickenSoundSet.getOrThrow("entity.baby_chicken.", Sounds.ENTITY_BABY_CHICKEN_STEP);
        return REGISTRY.define(
                text, result -> new StaticChickenSoundVariant(result, orThrow, orThrow2));
    }

    public static VersionedRegistry<ChickenSoundVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
