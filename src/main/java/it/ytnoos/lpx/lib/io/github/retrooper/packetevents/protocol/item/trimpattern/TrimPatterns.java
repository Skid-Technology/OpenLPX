package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimpattern;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.Nullable;

public final class TrimPatterns {

    private static final VersionedRegistry<TrimPattern> REGISTRY =
            new VersionedRegistry<>("trim_pattern");

    public static final TrimPattern COAST = define("coast");

    public static final TrimPattern DUNE = define("dune");

    public static final TrimPattern EYE = define("eye");

    public static final TrimPattern RIB = define("rib");

    public static final TrimPattern SENTRY = define("sentry");

    public static final TrimPattern SNOUT = define("snout");

    public static final TrimPattern SPIRE = define("spire");

    public static final TrimPattern TIDE = define("tide");

    public static final TrimPattern VEX = define("vex");

    public static final TrimPattern WARD = define("ward");

    public static final TrimPattern WILD = define("wild");

    public static final TrimPattern RAISER = define("raiser");

    public static final TrimPattern HOST = define("host");

    public static final TrimPattern SILENCE = define("silence");

    public static final TrimPattern SHAPER = define("shaper");

    public static final TrimPattern WAYFINDER = define("wayfinder");

    public static final TrimPattern BOLT = define("bolt");

    public static final TrimPattern FLOW = define("flow");

    private TrimPatterns() {}

    @Internal
    public static TrimPattern define(String text) {
        ResourceLocation resourceLocation = ResourceLocation.minecraft(text);
        ItemType byName = ItemTypes.getByName(resourceLocation + "_armor_trim_smithing_template");
        TranslatableComponent translatableComponent =
                Component.translatable("trim_pattern.minecraft." + text);
        return define(text, resourceLocation, byName, translatableComponent, (boolean) false);
    }

    @Internal
    public static TrimPattern define(
            String text,
            ResourceLocation resourceLocation,
            @Nullable ItemType itemType,
            Component component,
            boolean matches) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticTrimPattern(
                                result, resourceLocation, itemType, component, matches));
    }

    public static VersionedRegistry<TrimPattern> getRegistry() {
        return REGISTRY;
    }

    public static TrimPattern getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static TrimPattern getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    static {
        REGISTRY.unloadMappings();
    }
}
