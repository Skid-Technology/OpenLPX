package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import java.util.HashMap;
import java.util.Map;

public final class ArmorMaterials {

    private static final Map<String, String> DFU = new HashMap<>();

    private static final VersionedRegistry<ArmorMaterial> REGISTRY =
            new VersionedRegistry<>("equipment_asset");

    public static final ArmorMaterial LEATHER = define("leather");

    public static final ArmorMaterial CHAINMAIL = define("chainmail");

    public static final ArmorMaterial IRON = define("iron");

    public static final ArmorMaterial GOLD = define("gold");

    public static final ArmorMaterial DIAMOND = define("diamond");

    public static final ArmorMaterial TURTLE_SCUTE = define("turtle_scute");

    @Deprecated public static final ArmorMaterial TURTLE = TURTLE_SCUTE;

    public static final ArmorMaterial NETHERITE = define("netherite");

    public static final ArmorMaterial ARMADILLO_SCUTE = define("armadillo_scute");

    @Deprecated public static final ArmorMaterial ARMADILLO = ARMADILLO_SCUTE;

    public static final ArmorMaterial ELYTRA = define("elytra");

    public static final ArmorMaterial WHITE_CARPET = define("white_carpet");

    public static final ArmorMaterial ORANGE_CARPET = define("orange_carpet");

    public static final ArmorMaterial MAGENTA_CARPET = define("magenta_carpet");

    public static final ArmorMaterial LIGHT_BLUE_CARPET = define("light_blue_carpet");

    public static final ArmorMaterial YELLOW_CARPET = define("yellow_carpet");

    public static final ArmorMaterial LIME_CARPET = define("lime_carpet");

    public static final ArmorMaterial PINK_CARPET = define("pink_carpet");

    public static final ArmorMaterial GRAY_CARPET = define("gray_carpet");

    public static final ArmorMaterial LIGHT_GRAY_CARPET = define("light_gray_carpet");

    public static final ArmorMaterial CYAN_CARPET = define("cyan_carpet");

    public static final ArmorMaterial PURPLE_CARPET = define("purple_carpet");

    public static final ArmorMaterial BLUE_CARPET = define("blue_carpet");

    public static final ArmorMaterial BROWN_CARPET = define("brown_carpet");

    public static final ArmorMaterial GREEN_CARPET = define("green_carpet");

    public static final ArmorMaterial RED_CARPET = define("red_carpet");

    public static final ArmorMaterial BLACK_CARPET = define("black_carpet");

    public static final ArmorMaterial TRADER_LLAMA = define("trader_llama");

    public static final ArmorMaterial SADDLE = define("saddle");

    public static final ArmorMaterial WHITE_HARNESS = define("white_harness");

    public static final ArmorMaterial ORANGE_HARNESS = define("orange_harness");

    public static final ArmorMaterial MAGENTA_HARNESS = define("magenta_harness");

    public static final ArmorMaterial LIGHT_BLUE_HARNESS = define("light_blue_harness");

    public static final ArmorMaterial YELLOW_HARNESS = define("yellow_harness");

    public static final ArmorMaterial LIME_HARNESS = define("lime_harness");

    public static final ArmorMaterial PINK_HARNESS = define("pink_harness");

    public static final ArmorMaterial GRAY_HARNESS = define("gray_harness");

    public static final ArmorMaterial LIGHT_GRAY_HARNESS = define("light_gray_harness");

    public static final ArmorMaterial CYAN_HARNESS = define("cyan_harness");

    public static final ArmorMaterial PURPLE_HARNESS = define("purple_harness");

    public static final ArmorMaterial BLUE_HARNESS = define("blue_harness");

    public static final ArmorMaterial BROWN_HARNESS = define("brown_harness");

    public static final ArmorMaterial GREEN_HARNESS = define("green_harness");

    public static final ArmorMaterial RED_HARNESS = define("red_harness");

    public static final ArmorMaterial BLACK_HARNESS = define("black_harness");

    public static final ArmorMaterial COPPER = define("copper");

    public static final ArmorMaterial TRADER_LLAMA_BABY = define("trader_llama_baby");

    private ArmorMaterials() {}

    private static ArmorMaterial define(String text) {
        return REGISTRY.define(text, StaticArmorMaterial::new);
    }

    public static VersionedRegistry<ArmorMaterial> getRegistry() {
        return REGISTRY;
    }

    public static ArmorMaterial getByName(String text) {
        return REGISTRY.getByName(DFU.getOrDefault(text, text));
    }

    public static ArmorMaterial getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    static {
        DFU.put("turtle", "minecraft:turtle_scute");
        DFU.put("minecraft:turtle", "minecraft:turtle_scute");
        DFU.put("armadillo", "minecraft:armadillo_scute");
        DFU.put("minecraft:armadillo", "minecraft:armadillo_scute");
        REGISTRY.unloadMappings();
    }
}
