package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class PigSoundVariants {

    private static final VersionedRegistry<PigSoundVariant> REGISTRY =
            new VersionedRegistry<>("pig_sound_variant");

    public static final PigSoundVariant CLASSIC = define("classic", "pig");

    public static final PigSoundVariant MINI = define("mini", "pig_mini");

    public static final PigSoundVariant BIG = define("big", "pig_big");

    private PigSoundVariants() {}

    @Internal
    private static PigSoundVariant define(String text, String text2) {
        PigSoundSet orThrow =
                PigSoundSet.getOrThrow("entity." + text2 + ".", Sounds.ENTITY_PIG_STEP);
        PigSoundSet orThrow2 =
                PigSoundSet.getOrThrow("entity.baby_pig.", Sounds.ENTITY_BABY_PIG_STEP);
        return REGISTRY.define(
                text, result -> new StaticPigSoundVariant(result, orThrow, orThrow2));
    }

    public static VersionedRegistry<PigSoundVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
