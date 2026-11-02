package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public final class MapDecorationTypes {

    private static final VersionedRegistry<MapDecorationType> REGISTRY =
            new VersionedRegistry<>("map_decoration_type");

    public static final MapDecorationType PLAYER = define("player", false, true);

    public static final MapDecorationType FRAME = define("frame", true, true);

    public static final MapDecorationType RED_MARKER = define("red_marker", false, true);

    public static final MapDecorationType BLUE_MARKER = define("blue_marker", false, true);

    public static final MapDecorationType TARGET_X = define("target_x", true, false);

    public static final MapDecorationType TARGET_POINT = define("target_point", true, false);

    public static final MapDecorationType PLAYER_OFF_MAP = define("player_off_map", false, true);

    public static final MapDecorationType PLAYER_OFF_LIMITS =
            define("player_off_limits", false, true);

    public static final MapDecorationType MANSION = define("mansion", true, 5393476, true, false);

    public static final MapDecorationType MONUMENT = define("monument", true, 3830373, true, false);

    public static final MapDecorationType BANNER_WHITE =
            define("banner_white", ResourceLocation.minecraft("white_banner"), true, true);

    public static final MapDecorationType BANNER_ORANGE =
            define("banner_orange", ResourceLocation.minecraft("orange_banner"), true, true);

    public static final MapDecorationType BANNER_MAGENTA =
            define("banner_magenta", ResourceLocation.minecraft("magenta_banner"), true, true);

    public static final MapDecorationType BANNER_LIGHT_BLUE =
            define(
                    "banner_light_blue",
                    ResourceLocation.minecraft("light_blue_banner"),
                    true,
                    true);

    public static final MapDecorationType BANNER_YELLOW =
            define("banner_yellow", ResourceLocation.minecraft("yellow_banner"), true, true);

    public static final MapDecorationType BANNER_LIME =
            define("banner_lime", ResourceLocation.minecraft("lime_banner"), true, true);

    public static final MapDecorationType BANNER_PINK =
            define("banner_pink", ResourceLocation.minecraft("pink_banner"), true, true);

    public static final MapDecorationType BANNER_GRAY =
            define("banner_gray", ResourceLocation.minecraft("gray_banner"), true, true);

    public static final MapDecorationType BANNER_LIGHT_GRAY =
            define(
                    "banner_light_gray",
                    ResourceLocation.minecraft("light_gray_banner"),
                    true,
                    true);

    public static final MapDecorationType BANNER_CYAN =
            define("banner_cyan", ResourceLocation.minecraft("cyan_banner"), true, true);

    public static final MapDecorationType BANNER_PURPLE =
            define("banner_purple", ResourceLocation.minecraft("purple_banner"), true, true);

    public static final MapDecorationType BANNER_BLUE =
            define("banner_blue", ResourceLocation.minecraft("blue_banner"), true, true);

    public static final MapDecorationType BANNER_BROWN =
            define("banner_brown", ResourceLocation.minecraft("brown_banner"), true, true);

    public static final MapDecorationType BANNER_GREEN =
            define("banner_green", ResourceLocation.minecraft("green_banner"), true, true);

    public static final MapDecorationType BANNER_RED =
            define("banner_red", ResourceLocation.minecraft("red_banner"), true, true);

    public static final MapDecorationType BANNER_BLACK =
            define("banner_black", ResourceLocation.minecraft("black_banner"), true, true);

    public static final MapDecorationType RED_X = define("red_x", true, false);

    public static final MapDecorationType VILLAGE_DESERT =
            define(
                    "village_desert",
                    ResourceLocation.minecraft("desert_village"),
                    true,
                    10066329,
                    true,
                    false);

    public static final MapDecorationType VILLAGE_PLAINS =
            define(
                    "village_plains",
                    ResourceLocation.minecraft("plains_village"),
                    true,
                    10066329,
                    true,
                    false);

    public static final MapDecorationType VILLAGE_SAVANNA =
            define(
                    "village_savanna",
                    ResourceLocation.minecraft("savanna_village"),
                    true,
                    10066329,
                    true,
                    false);

    public static final MapDecorationType VILLAGE_SNOWY =
            define(
                    "village_snowy",
                    ResourceLocation.minecraft("snowy_village"),
                    true,
                    10066329,
                    true,
                    false);

    public static final MapDecorationType VILLAGE_TAIGA =
            define(
                    "village_taiga",
                    ResourceLocation.minecraft("taiga_village"),
                    true,
                    10066329,
                    true,
                    false);

    public static final MapDecorationType JUNGLE_TEMPLE =
            define("jungle_temple", true, 10066329, true, false);

    public static final MapDecorationType SWAMP_HUT =
            define("swamp_hut", true, 10066329, true, false);

    public static final MapDecorationType TRIAL_CHAMBERS =
            define("trial_chambers", true, 12741452, true, false);

    public static final MapDecorationType ABANDONED_CAMP = define("abandoned_camp", true, false);

    public static final MapDecorationType ANCIENT_CITY = define("ancient_city", true, false);

    public static final MapDecorationType DESERT_PYRAMID = define("desert_pyramid", true, false);

    public static final MapDecorationType MINESHAFT = define("mineshaft", true, false);

    public static final MapDecorationType OCEAN_RUIN_WARM =
            define("ocean_ruin_warm", ResourceLocation.minecraft("warm_ocean_ruins"), true, false);

    private MapDecorationTypes() {}

    @Internal
    public static MapDecorationType define(String text, boolean matches, boolean matches2) {
        return define(text, ResourceLocation.minecraft(text), matches, matches2);
    }

    @Internal
    public static MapDecorationType define(
            String text, ResourceLocation resourceLocation, boolean matches, boolean matches2) {
        return define(text, resourceLocation, matches, -1, false, matches2);
    }

    @Internal
    public static MapDecorationType define(
            String text, boolean matches, int value, boolean matches2, boolean matches3) {
        return define(text, ResourceLocation.minecraft(text), matches, value, matches2, matches3);
    }

    @Internal
    public static MapDecorationType define(
            String text,
            ResourceLocation resourceLocation,
            boolean matches,
            int value,
            boolean matches2,
            boolean matches3) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticMapDecorationType(
                                result, resourceLocation, matches, value, matches2, matches3));
    }

    public static VersionedRegistry<MapDecorationType> getRegistry() {
        return REGISTRY;
    }

    @Nullable
    public static MapDecorationType getByName(String text) {
        return REGISTRY.getByName(text);
    }

    @Nullable
    public static MapDecorationType getById(int value, ClientVersion clientVersion) {
        return REGISTRY.getById(clientVersion, value);
    }

    @Nullable
    public static MapDecorationType getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<MapDecorationType> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
