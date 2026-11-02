package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class WolfSoundVariants {

    private static final VersionedRegistry<WolfSoundVariant> REGISTRY =
            new VersionedRegistry<>("wolf_sound_variant");

    public static final WolfSoundVariant CLASSIC = define("classic", "wolf");

    public static final WolfSoundVariant PUGLIN = define("puglin", "wolf_puglin");

    public static final WolfSoundVariant SAD = define("sad", "wolf_sad");

    public static final WolfSoundVariant ANGRY = define("angry", "wolf_angry");

    public static final WolfSoundVariant GRUMPY = define("grumpy", "wolf_grumpy");

    public static final WolfSoundVariant BIG = define("big", "wolf_big");

    public static final WolfSoundVariant CUTE = define("cute", "wolf_cute");

    private WolfSoundVariants() {}

    public static VersionedRegistry<WolfSoundVariant> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static WolfSoundVariant define(String text, String text2) {
        WolfSoundSet orThrow =
                WolfSoundSet.getOrThrow("entity." + text2 + ".", Sounds.ENTITY_WOLF_STEP);
        WolfSoundSet orThrow2 =
                WolfSoundSet.getOrThrow("entity.baby_wolf.", Sounds.ENTITY_BABY_WOLF_STEP);
        return define(text, orThrow, orThrow2);
    }

    @Internal
    public static WolfSoundVariant define(
            String text, WolfSoundSet wolfSoundSet, WolfSoundSet wolfSoundSet2) {
        return REGISTRY.define(
                text, result -> new StaticWolfSoundVariant(result, wolfSoundSet, wolfSoundSet2));
    }

    static {
        REGISTRY.unloadMappings();
    }
}
