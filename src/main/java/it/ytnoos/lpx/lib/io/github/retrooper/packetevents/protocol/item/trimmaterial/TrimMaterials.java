package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimmaterial;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial.ArmorMaterial;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial.ArmorMaterials;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.kyori.adventure.key.KeyPattern;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class TrimMaterials {

    private static final VersionedRegistry<TrimMaterial> REGISTRY =
            new VersionedRegistry<>("trim_material");

    public static final TrimMaterial AMETHYST =
            define("amethyst", ItemTypes.AMETHYST_SHARD, 1.0F, 10116294);

    public static final TrimMaterial COPPER =
            define("copper", ItemTypes.COPPER_INGOT, 0.5F, 11823181);

    public static final TrimMaterial DIAMOND = define("diamond", ItemTypes.DIAMOND, 0.8F, 7269586);

    public static final TrimMaterial EMERALD = define("emerald", ItemTypes.EMERALD, 0.7F, 1155126);

    public static final TrimMaterial GOLD = define("gold", ItemTypes.GOLD_INGOT, 0.6F, 14594349);

    public static final TrimMaterial IRON = define("iron", ItemTypes.IRON_INGOT, 0.2F, 15527148);

    public static final TrimMaterial LAPIS = define("lapis", ItemTypes.LAPIS_LAZULI, 0.9F, 4288151);

    public static final TrimMaterial NETHERITE =
            define("netherite", ItemTypes.NETHERITE_INGOT, 0.3F, 6445145);

    public static final TrimMaterial QUARTZ = define("quartz", ItemTypes.QUARTZ, 0.1F, 14931140);

    public static final TrimMaterial REDSTONE =
            define("redstone", ItemTypes.REDSTONE, 0.4F, 9901575);

    public static final TrimMaterial RESIN =
            define("resin", ItemTypes.RESIN_BRICK, 0.11F, 16545810);

    private TrimMaterials() {}

    @Internal
    public static TrimMaterial define(
            @KeyPattern.Value String text, ItemType itemType, float value, int value2) {
        HashMap entries = new HashMap(2);
        ArmorMaterial armorMaterial;
        if ((armorMaterial = ArmorMaterials.getByName(ResourceLocation.minecraft(text).toString()))
                != null) {
            entries.put(armorMaterial, text + "_darker");
        }
        TranslatableComponent translatableComponent =
                Component.translatable("trim_material.minecraft." + text, TextColor.color(value2));
        return define(text, text, itemType, value, entries, translatableComponent);
    }

    @Internal
    public static TrimMaterial define(
            String text,
            @KeyPattern.Value String text2,
            ItemType itemType,
            float value,
            Map<ArmorMaterial, String> entries,
            Component component) {
        return REGISTRY.define(
                text,
                result -> {
                    ResourceLocation resourceLocation = ResourceLocation.minecraft("trim/" + text2);
                    return new StaticTrimMaterial(
                            result,
                            text2,
                            resourceLocation,
                            itemType,
                            value,
                            ((Map) entries),
                            component);
                });
    }

    public static VersionedRegistry<TrimMaterial> getRegistry() {
        return REGISTRY;
    }

    public static TrimMaterial getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static TrimMaterial getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<TrimMaterial> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
