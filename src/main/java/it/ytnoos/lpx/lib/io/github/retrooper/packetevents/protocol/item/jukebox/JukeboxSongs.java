package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.jukebox;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public final class JukeboxSongs {

    private static final VersionedRegistry<IJukeboxSong> REGISTRY =
            new VersionedRegistry<>("jukebox_song");

    public static final IJukeboxSong THIRTEEN = define("13", Sounds.MUSIC_DISC_13, 178.0F, 1);

    public static final IJukeboxSong CAT = define("cat", Sounds.MUSIC_DISC_CAT, 185.0F, 2);

    public static final IJukeboxSong BLOCKS = define("blocks", Sounds.MUSIC_DISC_BLOCKS, 345.0F, 3);

    public static final IJukeboxSong CHIRP = define("chirp", Sounds.MUSIC_DISC_CHIRP, 185.0F, 4);

    public static final IJukeboxSong FAR = define("far", Sounds.MUSIC_DISC_FAR, 174.0F, 5);

    public static final IJukeboxSong MALL = define("mall", Sounds.MUSIC_DISC_MALL, 197.0F, 6);

    public static final IJukeboxSong MELLOHI =
            define("mellohi", Sounds.MUSIC_DISC_MELLOHI, 96.0F, 7);

    public static final IJukeboxSong STAL = define("stal", Sounds.MUSIC_DISC_STAL, 150.0F, 8);

    public static final IJukeboxSong STRAD = define("strad", Sounds.MUSIC_DISC_STRAD, 188.0F, 9);

    public static final IJukeboxSong WARD = define("ward", Sounds.MUSIC_DISC_WARD, 251.0F, 10);

    public static final IJukeboxSong ELEVEN = define("11", Sounds.MUSIC_DISC_11, 71.0F, 11);

    public static final IJukeboxSong WAIT = define("wait", Sounds.MUSIC_DISC_WAIT, 238.0F, 12);

    public static final IJukeboxSong PIGSTEP =
            define("pigstep", Sounds.MUSIC_DISC_PIGSTEP, 149.0F, 13);

    public static final IJukeboxSong OTHERSIDE =
            define("otherside", Sounds.MUSIC_DISC_OTHERSIDE, 195.0F, 14);

    public static final IJukeboxSong FIVE = define("5", Sounds.MUSIC_DISC_5, 178.0F, 15);

    public static final IJukeboxSong RELIC = define("relic", Sounds.MUSIC_DISC_RELIC, 218.0F, 14);

    public static final IJukeboxSong PRECIPICE =
            define("precipice", Sounds.MUSIC_DISC_PRECIPICE, 299.0F, 13);

    public static final IJukeboxSong CREATOR =
            define("creator", Sounds.MUSIC_DISC_CREATOR, 176.0F, 12);

    public static final IJukeboxSong CREATOR_MUSIC_BOX =
            define("creator_music_box", Sounds.MUSIC_DISC_CREATOR_MUSIC_BOX, 73.0F, 11);

    public static final IJukeboxSong TEARS = define("tears", Sounds.MUSIC_DISC_TEARS, 175.0F, 10);

    public static final IJukeboxSong LAVA_CHICKEN =
            define("lava_chicken", Sounds.MUSIC_DISC_LAVA_CHICKEN, 134.0F, 9);

    public static final IJukeboxSong BOUNCE = define("bounce", Sounds.MUSIC_DISC_BOUNCE, 234.0F, 8);

    private JukeboxSongs() {}

    private static String makeDescriptionId(
            String text, @Nullable ResourceLocation resourceLocation) {
        return resourceLocation == null
                ? text + ".unregistered_sadface"
                : text
                        + "."
                        + resourceLocation.getNamespace()
                        + "."
                        + resourceLocation.getKey().replace('/', '.');
    }

    @Internal
    public static IJukeboxSong define(String text, Sound sound, float value, int value2) {
        return REGISTRY.define(
                text,
                result ->
                        new JukeboxSong(
                                result,
                                sound,
                                Component.translatable(
                                        makeDescriptionId("jukebox_song", result.getName())),
                                value,
                                value2));
    }

    public static VersionedRegistry<IJukeboxSong> getRegistry() {
        return REGISTRY;
    }

    public static IJukeboxSong getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static IJukeboxSong getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<IJukeboxSong> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
