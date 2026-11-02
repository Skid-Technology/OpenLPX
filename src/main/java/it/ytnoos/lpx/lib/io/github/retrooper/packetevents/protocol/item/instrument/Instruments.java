package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.instrument;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class Instruments {

    private static final VersionedRegistry<Instrument> REGISTRY =
            new VersionedRegistry<>("instrument");

    public static final Instrument PONDER_GOAT_HORN =
            define("ponder_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_0);

    public static final Instrument SING_GOAT_HORN =
            define("sing_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_1);

    public static final Instrument SEEK_GOAT_HORN =
            define("seek_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_2);

    public static final Instrument FEEL_GOAT_HORN =
            define("feel_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_3);

    public static final Instrument ADMIRE_GOAT_HORN =
            define("admire_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_4);

    public static final Instrument CALL_GOAT_HORN =
            define("call_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_5);

    public static final Instrument YEARN_GOAT_HORN =
            define("yearn_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_6);

    public static final Instrument DREAM_GOAT_HORN =
            define("dream_goat_horn", Sounds.ITEM_GOAT_HORN_SOUND_7);

    private Instruments() {}

    @Internal
    public static Instrument define(String text, Sound sound) {
        return define(text, sound, 140, 256.0F);
    }

    @Internal
    public static Instrument define(String text, Sound sound, int value, float value2) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticInstrument(
                                result,
                                sound,
                                value,
                                value2,
                                Component.translatable("instrument.minecraft." + text)));
    }

    public static VersionedRegistry<Instrument> getRegistry() {
        return REGISTRY;
    }

    public static Instrument getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static Instrument getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    static {
        REGISTRY.unloadMappings();
    }
}
