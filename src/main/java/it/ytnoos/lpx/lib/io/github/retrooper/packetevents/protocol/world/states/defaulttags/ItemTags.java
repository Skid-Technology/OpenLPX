package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.defaulttags;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ItemTags {

    private static final HashMap<String, ItemTags> byName = new HashMap<>();

    public static final ItemTags WOOL = bind("wool");

    public static final ItemTags PLANKS = bind("planks");

    public static final ItemTags STONE_BRICKS = bind("stone_bricks");

    public static final ItemTags WOODEN_BUTTONS = bind("wooden_buttons");

    public static final ItemTags STONE_BUTTONS = bind("stone_buttons");

    public static final ItemTags BUTTONS = bind("buttons");

    public static final ItemTags WOOL_CARPETS = bind("wool_carpets");

    public static final ItemTags WOODEN_DOORS = bind("wooden_doors");

    public static final ItemTags WOODEN_STAIRS = bind("wooden_stairs");

    public static final ItemTags WOODEN_SLABS = bind("wooden_slabs");

    public static final ItemTags WOODEN_FENCES = bind("wooden_fences");

    public static final ItemTags FENCE_GATES = bind("fence_gates");

    public static final ItemTags WOODEN_PRESSURE_PLATES = bind("wooden_pressure_plates");

    public static final ItemTags WOODEN_TRAPDOORS = bind("wooden_trapdoors");

    public static final ItemTags DOORS = bind("doors");

    public static final ItemTags SAPLINGS = bind("saplings");

    public static final ItemTags LOGS_THAT_BURN = bind("logs_that_burn");

    public static final ItemTags LOGS = bind("logs");

    public static final ItemTags DARK_OAK_LOGS = bind("dark_oak_logs");

    public static final ItemTags OAK_LOGS = bind("oak_logs");

    public static final ItemTags BIRCH_LOGS = bind("birch_logs");

    public static final ItemTags ACACIA_LOGS = bind("acacia_logs");

    public static final ItemTags CHERRY_LOGS = bind("cherry_logs");

    public static final ItemTags JUNGLE_LOGS = bind("jungle_logs");

    public static final ItemTags SPRUCE_LOGS = bind("spruce_logs");

    public static final ItemTags MANGROVE_LOGS = bind("mangrove_logs");

    public static final ItemTags CRIMSON_STEMS = bind("crimson_stems");

    public static final ItemTags WARPED_STEMS = bind("warped_stems");

    public static final ItemTags BAMBOO_BLOCKS = bind("bamboo_blocks");

    public static final ItemTags WART_BLOCKS = bind("wart_blocks");

    public static final ItemTags BANNERS = bind("banners");

    public static final ItemTags SAND = bind("sand");

    public static final ItemTags SMELTS_TO_GLASS = bind("smelts_to_glass");

    public static final ItemTags STAIRS = bind("stairs");

    public static final ItemTags SLABS = bind("slabs");

    public static final ItemTags WALLS = bind("walls");

    public static final ItemTags ANVIL = bind("anvil");

    public static final ItemTags RAILS = bind("rails");

    public static final ItemTags LEAVES = bind("leaves");

    public static final ItemTags TRAPDOORS = bind("trapdoors");

    public static final ItemTags SMALL_FLOWERS = bind("small_flowers");

    public static final ItemTags BEDS = bind("beds");

    public static final ItemTags FENCES = bind("fences");

    @Deprecated public static final ItemTags TALL_FLOWERS = bind("tall_flowers");

    public static final ItemTags FLOWERS = bind("flowers");

    public static final ItemTags PIGLIN_REPELLENTS = bind("piglin_repellents");

    public static final ItemTags PIGLIN_LOVED = bind("piglin_loved");

    public static final ItemTags IGNORED_BY_PIGLIN_BABIES = bind("ignored_by_piglin_babies");

    public static final ItemTags PIGLIN_FOOD = bind("piglin_food");

    public static final ItemTags FOX_FOOD = bind("fox_food");

    public static final ItemTags GOLD_ORES = bind("gold_ores");

    public static final ItemTags IRON_ORES = bind("iron_ores");

    public static final ItemTags DIAMOND_ORES = bind("diamond_ores");

    public static final ItemTags REDSTONE_ORES = bind("redstone_ores");

    public static final ItemTags LAPIS_ORES = bind("lapis_ores");

    public static final ItemTags COAL_ORES = bind("coal_ores");

    public static final ItemTags EMERALD_ORES = bind("emerald_ores");

    public static final ItemTags COPPER_ORES = bind("copper_ores");

    public static final ItemTags NON_FLAMMABLE_WOOD = bind("non_flammable_wood");

    public static final ItemTags SOUL_FIRE_BASE_BLOCKS = bind("soul_fire_base_blocks");

    public static final ItemTags CANDLES = bind("candles");

    public static final ItemTags DIRT = bind("dirt");

    public static final ItemTags TERRACOTTA = bind("terracotta");

    public static final ItemTags COMPLETES_FIND_TREE_TUTORIAL =
            bind("completes_find_tree_tutorial");

    public static final ItemTags BOATS = bind("boats");

    public static final ItemTags CHEST_BOATS = bind("chest_boats");

    public static final ItemTags FISHES = bind("fishes");

    public static final ItemTags SIGNS = bind("signs");

    @Deprecated public static final ItemTags MUSIC_DISCS = bind("music_discs");

    public static final ItemTags CREEPER_DROP_MUSIC_DISCS = bind("creeper_drop_music_discs");

    public static final ItemTags COALS = bind("coals");

    public static final ItemTags ARROWS = bind("arrows");

    public static final ItemTags LECTERN_BOOKS = bind("lectern_books");

    public static final ItemTags BOOKSHELF_BOOKS = bind("bookshelf_books");

    public static final ItemTags BEACON_PAYMENT_ITEMS = bind("beacon_payment_items");

    public static final ItemTags STONE_TOOL_MATERIALS = bind("stone_tool_materials");

    public static final ItemTags STONE_CRAFTING_MATERIALS = bind("stone_crafting_materials");

    public static final ItemTags FREEZE_IMMUNE_WEARABLES = bind("freeze_immune_wearables");

    public static final ItemTags DAMPENS_VIBRATIONS = bind("dampens_vibrations");

    public static final ItemTags CLUSTER_MAX_HARVESTABLES = bind("cluster_max_harvestables");

    public static final ItemTags COMPASSES = bind("compasses");

    public static final ItemTags HANGING_SIGNS = bind("hanging_signs");

    public static final ItemTags CREEPER_IGNITERS = bind("creeper_igniters");

    public static final ItemTags NOTEBLOCK_TOP_INSTRUMENTS = bind("noteblock_top_instruments");

    public static final ItemTags TRIMMABLE_ARMOR = bind("trimmable_armor");

    public static final ItemTags TRIM_MATERIALS = bind("trim_materials");

    @Deprecated public static final ItemTags TRIM_TEMPLATES = bind("trim_templates");

    public static final ItemTags SNIFFER_FOOD = bind("sniffer_food");

    public static final ItemTags DECORATED_POT_SHERDS = bind("decorated_pot_sherds");

    public static final ItemTags DECORATED_POT_INGREDIENTS = bind("decorated_pot_ingredients");

    public static final ItemTags SWORDS = bind("swords");

    public static final ItemTags AXES = bind("axes");

    public static final ItemTags HOES = bind("hoes");

    public static final ItemTags PICKAXES = bind("pickaxes");

    public static final ItemTags SHOVELS = bind("shovels");

    public static final ItemTags BREAKS_DECORATED_POTS = bind("breaks_decorated_pots");

    @Deprecated public static final ItemTags TOOLS = BREAKS_DECORATED_POTS;

    public static final ItemTags VILLAGER_PLANTABLE_SEEDS = bind("villager_plantable_seeds");

    public static final ItemTags ARMADILLO_FOOD = bind("armadillo_food");

    public static final ItemTags AXOLOTL_FOOD = bind("axolotl_food");

    @Deprecated public static final ItemTags AXOLOTL_TEMPT_ITEMS = AXOLOTL_FOOD;

    public static final ItemTags BEE_FOOD = bind("bee_food");

    public static final ItemTags CAMEL_FOOD = bind("camel_food");

    public static final ItemTags CAT_FOOD = bind("cat_food");

    public static final ItemTags CHEST_ARMOR = bind("chest_armor");

    public static final ItemTags CHICKEN_FOOD = bind("chicken_food");

    public static final ItemTags COW_FOOD = bind("cow_food");

    public static final ItemTags ENCHANTABLE_ARMOR = bind("enchantable/armor");

    public static final ItemTags ENCHANTABLE_BOW = bind("enchantable/bow");

    public static final ItemTags ENCHANTABLE_CHEST_ARMOR = bind("enchantable/chest_armor");

    public static final ItemTags ENCHANTABLE_CROSSBOW = bind("enchantable/crossbow");

    public static final ItemTags ENCHANTABLE_DURABILITY = bind("enchantable/durability");

    public static final ItemTags ENCHANTABLE_EQUIPPABLE = bind("enchantable/equippable");

    public static final ItemTags ENCHANTABLE_FIRE_ASPECT = bind("enchantable/fire_aspect");

    public static final ItemTags ENCHANTABLE_FISHING = bind("enchantable/fishing");

    public static final ItemTags ENCHANTABLE_FOOT_ARMOR = bind("enchantable/foot_armor");

    public static final ItemTags ENCHANTABLE_HEAD_ARMOR = bind("enchantable/head_armor");

    public static final ItemTags ENCHANTABLE_LEG_ARMOR = bind("enchantable/leg_armor");

    public static final ItemTags ENCHANTABLE_MACE = bind("enchantable/mace");

    public static final ItemTags ENCHANTABLE_MINING = bind("enchantable/mining");

    public static final ItemTags ENCHANTABLE_MINING_LOOT = bind("enchantable/mining_loot");

    public static final ItemTags ENCHANTABLE_SHARP_WEAPON = bind("enchantable/sharp_weapon");

    public static final ItemTags ENCHANTABLE_SWORD = bind("enchantable/sword");

    public static final ItemTags ENCHANTABLE_TRIDENT = bind("enchantable/trident");

    public static final ItemTags ENCHANTABLE_VANISHING = bind("enchantable/vanishing");

    public static final ItemTags ENCHANTABLE_WEAPON = bind("enchantable/weapon");

    public static final ItemTags FOOT_ARMOR = bind("foot_armor");

    public static final ItemTags FROG_FOOD = bind("frog_food");

    public static final ItemTags GOAT_FOOD = bind("goat_food");

    public static final ItemTags HEAD_ARMOR = bind("head_armor");

    public static final ItemTags HOGLIN_FOOD = bind("hoglin_food");

    public static final ItemTags HORSE_FOOD = bind("horse_food");

    public static final ItemTags HORSE_TEMPT_ITEMS = bind("horse_tempt_items");

    public static final ItemTags LEG_ARMOR = bind("leg_armor");

    public static final ItemTags LLAMA_FOOD = bind("llama_food");

    public static final ItemTags LLAMA_TEMPT_ITEMS = bind("llama_tempt_items");

    public static final ItemTags MEAT = bind("meat");

    public static final ItemTags OCELOT_FOOD = bind("ocelot_food");

    public static final ItemTags PANDA_FOOD = bind("panda_food");

    public static final ItemTags PARROT_FOOD = bind("parrot_food");

    public static final ItemTags PARROT_POISONOUS_FOOD = bind("parrot_poisonous_food");

    public static final ItemTags PIG_FOOD = bind("pig_food");

    public static final ItemTags RABBIT_FOOD = bind("rabbit_food");

    public static final ItemTags SHEEP_FOOD = bind("sheep_food");

    public static final ItemTags SKULLS = bind("skulls");

    public static final ItemTags STRIDER_FOOD = bind("strider_food");

    public static final ItemTags STRIDER_TEMPT_ITEMS = bind("strider_tempt_items");

    public static final ItemTags TURTLE_FOOD = bind("turtle_food");

    public static final ItemTags WOLF_FOOD = bind("wolf_food");

    public static final ItemTags PALE_OAK_LOGS = bind("pale_oak_logs");

    public static final ItemTags PIGLIN_SAFE_ARMOR = bind("piglin_safe_armor");

    public static final ItemTags DUPLICATES_ALLAYS = bind("duplicates_allays");

    @Obsolete public static final ItemTags BREWING_FUEL = bind("brewing_fuel");

    public static final ItemTags SHULKER_BOXES = bind("shulker_boxes");

    public static final ItemTags IRON_TOOL_MATERIALS = bind("iron_tool_materials");

    public static final ItemTags GOLD_TOOL_MATERIALS = bind("gold_tool_materials");

    public static final ItemTags DIAMOND_TOOL_MATERIALS = bind("diamond_tool_materials");

    public static final ItemTags NETHERITE_TOOL_MATERIALS = bind("netherite_tool_materials");

    public static final ItemTags REPAIRS_LEATHER_ARMOR = bind("repairs_leather_armor");

    public static final ItemTags REPAIRS_CHAIN_ARMOR = bind("repairs_chain_armor");

    public static final ItemTags REPAIRS_IRON_ARMOR = bind("repairs_iron_armor");

    public static final ItemTags REPAIRS_GOLD_ARMOR = bind("repairs_gold_armor");

    public static final ItemTags REPAIRS_DIAMOND_ARMOR = bind("repairs_diamond_armor");

    public static final ItemTags REPAIRS_NETHERITE_ARMOR = bind("repairs_netherite_armor");

    public static final ItemTags REPAIRS_TURTLE_HELMET = bind("repairs_turtle_helmet");

    public static final ItemTags REPAIRS_WOLF_ARMOR = bind("repairs_wolf_armor");

    public static final ItemTags FURNACE_MINECART_FUEL = bind("furnace_minecart_fuel");

    public static final ItemTags BUNDLES = bind("bundles");

    public static final ItemTags MAP_INVISIBILITY_EQUIPMENT = bind("map_invisibility_equipment");

    public static final ItemTags GAZE_DISGUISE_EQUIPMENT = bind("gaze_disguise_equipment");

    public static final ItemTags PANDA_EATS_FROM_GROUND = bind("panda_eats_from_ground");

    public static final ItemTags WOODEN_TOOL_MATERIALS = bind("wooden_tool_materials");

    public static final ItemTags VILLAGER_PICKS_UP = bind("villager_picks_up");

    public static final ItemTags SKELETON_PREFERRED_WEAPONS = bind("skeleton_preferred_weapons");

    public static final ItemTags DROWNED_PREFERRED_WEAPONS = bind("drowned_preferred_weapons");

    public static final ItemTags PIGLIN_PREFERRED_WEAPONS = bind("piglin_preferred_weapons");

    public static final ItemTags PILLAGER_PREFERRED_WEAPONS = bind("pillager_preferred_weapons");

    public static final ItemTags WITHER_SKELETON_DISLIKED_WEAPONS =
            bind("wither_skeleton_disliked_weapons");

    public static final ItemTags EGGS = bind("eggs");

    public static final ItemTags BOOK_CLONING_TARGET = bind("book_cloning_target");

    public static final ItemTags HARNESSES = bind("harnesses");

    public static final ItemTags HAPPY_GHAST_FOOD = bind("happy_ghast_food");

    public static final ItemTags HAPPY_GHAST_TEMPT_ITEMS = bind("happy_ghast_tempt_items");

    public static final ItemTags WOODEN_SHELVES = bind("wooden_shelves");

    public static final ItemTags COPPER_CHESTS = bind("copper_chests");

    public static final ItemTags LIGHTNING_RODS = bind("lightning_rods");

    public static final ItemTags COPPER_GOLEM_STATUES = bind("copper_golem_statues");

    public static final ItemTags COPPER = bind("copper");

    public static final ItemTags CHAINS = bind("chains");

    public static final ItemTags LANTERNS = bind("lanterns");

    public static final ItemTags BARS = bind("bars");

    public static final ItemTags COPPER_TOOL_MATERIALS = bind("copper_tool_materials");

    public static final ItemTags REPAIRS_COPPER_ARMOR = bind("repairs_copper_armor");

    public static final ItemTags SHEARABLE_FROM_COPPER_GOLEM = bind("shearable_from_copper_golem");

    public static final ItemTags ZOMBIE_HORSE_FOOD = bind("zombie_horse_food");

    public static final ItemTags CAMEL_HUSK_FOOD = bind("camel_husk_food");

    public static final ItemTags NAUTILUS_BUCKET_FOOD = bind("nautilus_bucket_food");

    public static final ItemTags NAUTILUS_TAMING_ITEMS = bind("nautilus_taming_items");

    public static final ItemTags SPEARS = bind("spears");

    public static final ItemTags NAUTILUS_FOOD = bind("nautilus_food");

    public static final ItemTags ENCHANTABLE_MELEE_WEAPON = bind("enchantable/melee_weapon");

    public static final ItemTags ENCHANTABLE_SWEEPING = bind("enchantable/sweeping");

    public static final ItemTags ENCHANTABLE_LUNGE = bind("enchantable/lunge");

    public static final ItemTags MUD = bind("mud");

    public static final ItemTags GRASS_BLOCKS = bind("grass_blocks");

    public static final ItemTags MOSS_BLOCKS = bind("moss_blocks");

    public static final ItemTags DYES = bind("dyes");

    public static final ItemTags LOOM_PATTERNS = bind("loom_patterns");

    public static final ItemTags CAULDRON_CAN_REMOVE_DYE = bind("cauldron_can_remove_dye");

    public static final ItemTags METAL_NUGGETS = bind("metal_nuggets");

    public static final ItemTags LOOM_DYES = bind("loom_dyes");

    public static final ItemTags CAT_COLLAR_DYES = bind("cat_collar_dyes");

    public static final ItemTags WOLF_COLLAR_DYES = bind("wolf_collar_dyes");

    public static final ItemTags CONCRETE = bind("concrete");

    public static final ItemTags CONCRETE_POWDERS = bind("concrete_powders");

    public static final ItemTags GLAZED_TERRACOTTA = bind("glazed_terracotta");

    public static final ItemTags SULFUR_CUBE_FOOD = bind("sulfur_cube_food");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_BOUNCY =
            bind("sulfur_cube_archetype/bouncy");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY =
            bind("sulfur_cube_archetype/slow_bouncy");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_REGULAR =
            bind("sulfur_cube_archetype/regular");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_SLOW_FLAT =
            bind("sulfur_cube_archetype/slow_flat");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_FAST_FLAT =
            bind("sulfur_cube_archetype/fast_flat");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_LIGHT = bind("sulfur_cube_archetype/light");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_FAST_SLIDING =
            bind("sulfur_cube_archetype/fast_sliding");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING =
            bind("sulfur_cube_archetype/slow_sliding");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_STICKY =
            bind("sulfur_cube_archetype/sticky");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE =
            bind("sulfur_cube_archetype/high_resistance");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_EXPLOSIVE =
            bind("sulfur_cube_archetype/explosive");

    public static final ItemTags SULFUR_CUBE_ARCHETYPE_HOT = bind("sulfur_cube_archetype/hot");

    public static final ItemTags SULFUR_CUBE_SWALLOWABLE = bind("sulfur_cube_swallowable");

    public static final ItemTags MUSHROOMS = bind("mushrooms");

    public static final ItemTags CLONABLE_MAPS = bind("clonable_maps");

    public static final ItemTags EXTENDABLE_MAPS = bind("extendable_maps");

    public static final ItemTags CUSHIONS = bind("cushions");

    public static final ItemTags DOUSES_CAMPFIRES = bind("douses_campfires");

    public static final ItemTags BREWING_POTION_INPUTS = bind("brewing_potion_inputs");

    public static final ItemTags FURNACE_FUEL_BOTTOM_TAKEABLE =
            bind("furnace_fuel_bottom_takeable");

    public static final ItemTags POPLAR_LOGS = bind("poplar_logs");

    public static final ItemTags ORES = bind("ores");

    public static final ItemTags CONCRETE_STAIRS = bind("concrete_stairs");

    public static final ItemTags CONCRETE_SLABS = bind("concrete_slabs");

    public static final ItemTags WOOL_STAIRS = bind("wool_stairs");

    public static final ItemTags WOOL_SLABS = bind("wool_slabs");

    @Deprecated public static final ItemTags DYEABLE = DYES;

    String name;

    Set<ItemType> states = new HashSet<>();

    boolean reallyEmpty;

    public ItemTags(String name) {
        byName.put(name, this);
        this.name = name;
    }

    private static ItemTags bind(String text) {
        return new ItemTags(text);
    }

    private static void copy(ItemTags itemTags, ItemTags itemTags2) {
        itemTags2.states.addAll(itemTags.states);
    }

    private static void copy(BlockTags blockTags, ItemTags itemTags) {
        for (StateType stateType : blockTags.getStates()) {
            itemTags.states.add(ItemTypes.getTypePlacingState(stateType));
        }
        itemTags.states.remove(null);
    }

    private ItemTags add(ItemType... itemType) {
        Collections.addAll(this.states, itemType);
        return this;
    }

    private ItemTags addTag(ItemTags itemTags) {
        if (itemTags.states.isEmpty()) {
            throw new IllegalArgumentException(
                    "Tag "
                            + itemTags.name
                            + " is empty when adding to "
                            + this.name
                            + ", you (packetevents updater) probably messed up the item tags order!!");
        }
        this.states.addAll(itemTags.states);
        return this;
    }

    public boolean contains(ItemType itemType) {
        return this.states.contains(itemType);
    }

    public String getName() {
        return this.name;
    }

    public ItemTags getByName(String text) {
        return byName.get(text);
    }

    public Set<ItemType> getStates() {
        return this.states;
    }

    @VisibleForTesting
    public boolean isReallyEmpty() {
        return this.reallyEmpty;
    }

    static {
        copy(BlockTags.WOOL, WOOL);
        copy(BlockTags.PLANKS, PLANKS);
        copy(BlockTags.STONE_BRICKS, STONE_BRICKS);
        copy(BlockTags.WOODEN_BUTTONS, WOODEN_BUTTONS);
        copy(BlockTags.WOOL_CARPETS, WOOL_CARPETS);
        copy(BlockTags.WOOL_STAIRS, WOOL_STAIRS);
        copy(BlockTags.WOOL_SLABS, WOOL_SLABS);
        copy(BlockTags.WOODEN_DOORS, WOODEN_DOORS);
        copy(BlockTags.WOODEN_STAIRS, WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES, FENCE_GATES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SHELVES, WOODEN_SHELVES);
        copy(BlockTags.SAPLINGS, SAPLINGS);
        copy(BlockTags.BAMBOO_BLOCKS, BAMBOO_BLOCKS);
        copy(BlockTags.OAK_LOGS, OAK_LOGS);
        copy(BlockTags.DARK_OAK_LOGS, DARK_OAK_LOGS);
        copy(BlockTags.PALE_OAK_LOGS, PALE_OAK_LOGS);
        copy(BlockTags.BIRCH_LOGS, BIRCH_LOGS);
        copy(BlockTags.ACACIA_LOGS, ACACIA_LOGS);
        copy(BlockTags.SPRUCE_LOGS, SPRUCE_LOGS);
        copy(BlockTags.MANGROVE_LOGS, MANGROVE_LOGS);
        copy(BlockTags.POPLAR_LOGS, POPLAR_LOGS);
        copy(BlockTags.JUNGLE_LOGS, JUNGLE_LOGS);
        copy(BlockTags.CHERRY_LOGS, CHERRY_LOGS);
        copy(BlockTags.CRIMSON_STEMS, CRIMSON_STEMS);
        copy(BlockTags.WARPED_STEMS, WARPED_STEMS);
        copy(BlockTags.WART_BLOCKS, WART_BLOCKS);
        copy(BlockTags.SAND, SAND);
        copy(BlockTags.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS, SMELTS_TO_GLASS);
        copy(BlockTags.WALLS, WALLS);
        copy(BlockTags.ANVIL, ANVIL);
        copy(BlockTags.RAILS, RAILS);
        copy(BlockTags.LEAVES, LEAVES);
        copy(BlockTags.WOODEN_TRAPDOORS, WOODEN_TRAPDOORS);
        copy(BlockTags.BEDS, BEDS);
        copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, SOUL_FIRE_BASE_BLOCKS);
        copy(BlockTags.CANDLES, CANDLES);
        copy(BlockTags.GOLD_ORES, GOLD_ORES);
        copy(BlockTags.IRON_ORES, IRON_ORES);
        copy(BlockTags.DIAMOND_ORES, DIAMOND_ORES);
        copy(BlockTags.REDSTONE_ORES, REDSTONE_ORES);
        copy(BlockTags.LAPIS_ORES, LAPIS_ORES);
        copy(BlockTags.COAL_ORES, COAL_ORES);
        copy(BlockTags.EMERALD_ORES, EMERALD_ORES);
        copy(BlockTags.COPPER_ORES, COPPER_ORES);
        copy(BlockTags.DIRT, DIRT);
        copy(BlockTags.MUD, MUD);
        copy(BlockTags.MOSS_BLOCKS, MOSS_BLOCKS);
        copy(BlockTags.TERRACOTTA, TERRACOTTA);
        copy(BlockTags.GLAZED_TERRACOTTA, GLAZED_TERRACOTTA);
        copy(BlockTags.CONCRETE, CONCRETE);
        copy(BlockTags.CONCRETE_STAIRS, CONCRETE_STAIRS);
        copy(BlockTags.CONCRETE_SLABS, CONCRETE_SLABS);
        copy(BlockTags.CONCRETE_POWDERS, CONCRETE_POWDERS);
        copy(BlockTags.SHULKER_BOXES, SHULKER_BOXES);
        copy(BlockTags.COPPER, COPPER);
        copy(BlockTags.SKULLS, SKULLS);
        copy(BlockTags.STANDING_SIGNS, SIGNS);
        copy(BlockTags.CEILING_HANGING_SIGNS, HANGING_SIGNS);
        copy(BlockTags.BEE_ATTRACTIVE, BEE_FOOD);
        SULFUR_CUBE_ARCHETYPE_FAST_SLIDING.add(
                ItemTypes.BLUE_ICE, ItemTypes.PACKED_ICE, ItemTypes.SNOW_BLOCK);
        SULFUR_CUBE_ARCHETYPE_STICKY.add(ItemTypes.HONEYCOMB_BLOCK);
        copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE);
        SULFUR_CUBE_ARCHETYPE_EXPLOSIVE.add(ItemTypes.TNT);
        copy(BlockTags.CAUSES_PERIODIC_GEYSER_ERUPTIONS, SULFUR_CUBE_ARCHETYPE_HOT);
        BANNERS.add(
                ItemTypes.WHITE_BANNER,
                ItemTypes.ORANGE_BANNER,
                ItemTypes.MAGENTA_BANNER,
                ItemTypes.LIGHT_BLUE_BANNER,
                ItemTypes.YELLOW_BANNER,
                ItemTypes.LIME_BANNER,
                ItemTypes.PINK_BANNER,
                ItemTypes.GRAY_BANNER,
                ItemTypes.LIGHT_GRAY_BANNER,
                ItemTypes.CYAN_BANNER,
                ItemTypes.PURPLE_BANNER,
                ItemTypes.BLUE_BANNER,
                ItemTypes.BROWN_BANNER,
                ItemTypes.GREEN_BANNER,
                ItemTypes.RED_BANNER,
                ItemTypes.BLACK_BANNER);
        PIGLIN_REPELLENTS.add(
                ItemTypes.SOUL_TORCH, ItemTypes.SOUL_LANTERN, ItemTypes.SOUL_CAMPFIRE);
        IGNORED_BY_PIGLIN_BABIES.add(ItemTypes.LEATHER);
        PIGLIN_SAFE_ARMOR.add(
                ItemTypes.GOLDEN_HELMET,
                ItemTypes.GOLDEN_CHESTPLATE,
                ItemTypes.GOLDEN_LEGGINGS,
                ItemTypes.GOLDEN_BOOTS);
        DUPLICATES_ALLAYS.add(ItemTypes.AMETHYST_SHARD);
        EGGS.add(ItemTypes.EGG, ItemTypes.BLUE_EGG, ItemTypes.BROWN_EGG);
        MEAT.add(
                ItemTypes.BEEF,
                ItemTypes.CHICKEN,
                ItemTypes.COOKED_BEEF,
                ItemTypes.COOKED_CHICKEN,
                ItemTypes.COOKED_MUTTON,
                ItemTypes.COOKED_PORKCHOP,
                ItemTypes.COOKED_RABBIT,
                ItemTypes.MUTTON,
                ItemTypes.PORKCHOP,
                ItemTypes.RABBIT,
                ItemTypes.ROTTEN_FLESH);
        SNIFFER_FOOD.add(ItemTypes.TORCHFLOWER_SEEDS);
        PIGLIN_FOOD.add(ItemTypes.PORKCHOP, ItemTypes.COOKED_PORKCHOP);
        FOX_FOOD.add(ItemTypes.SWEET_BERRIES, ItemTypes.GLOW_BERRIES);
        COW_FOOD.add(ItemTypes.WHEAT);
        copy(COW_FOOD, GOAT_FOOD);
        copy(COW_FOOD, SHEEP_FOOD);
        CAT_FOOD.add(ItemTypes.COD, ItemTypes.SALMON);
        HORSE_FOOD.add(
                ItemTypes.WHEAT,
                ItemTypes.SUGAR,
                ItemTypes.HAY_BLOCK,
                ItemTypes.APPLE,
                ItemTypes.CARROT,
                ItemTypes.GOLDEN_CARROT,
                ItemTypes.GOLDEN_APPLE,
                ItemTypes.ENCHANTED_GOLDEN_APPLE);
        ZOMBIE_HORSE_FOOD.add(ItemTypes.RED_MUSHROOM);
        HORSE_TEMPT_ITEMS.add(
                ItemTypes.GOLDEN_CARROT, ItemTypes.GOLDEN_APPLE, ItemTypes.ENCHANTED_GOLDEN_APPLE);
        HARNESSES.add(
                ItemTypes.WHITE_HARNESS,
                ItemTypes.ORANGE_HARNESS,
                ItemTypes.MAGENTA_HARNESS,
                ItemTypes.LIGHT_BLUE_HARNESS,
                ItemTypes.YELLOW_HARNESS,
                ItemTypes.LIME_HARNESS,
                ItemTypes.PINK_HARNESS,
                ItemTypes.GRAY_HARNESS,
                ItemTypes.LIGHT_GRAY_HARNESS,
                ItemTypes.CYAN_HARNESS,
                ItemTypes.PURPLE_HARNESS,
                ItemTypes.BLUE_HARNESS,
                ItemTypes.BROWN_HARNESS,
                ItemTypes.GREEN_HARNESS,
                ItemTypes.RED_HARNESS,
                ItemTypes.BLACK_HARNESS);
        HAPPY_GHAST_FOOD.add(ItemTypes.SNOWBALL);
        CAMEL_FOOD.add(ItemTypes.CACTUS);
        CAMEL_HUSK_FOOD.add(ItemTypes.RABBIT_FOOT);
        ARMADILLO_FOOD.add(ItemTypes.SPIDER_EYE);
        CHICKEN_FOOD.add(
                ItemTypes.WHEAT_SEEDS,
                ItemTypes.MELON_SEEDS,
                ItemTypes.PUMPKIN_SEEDS,
                ItemTypes.BEETROOT_SEEDS,
                ItemTypes.TORCHFLOWER_SEEDS,
                ItemTypes.PITCHER_POD);
        FROG_FOOD.add(ItemTypes.SLIME_BALL);
        HOGLIN_FOOD.add(ItemTypes.CRIMSON_FUNGUS);
        LLAMA_FOOD.add(ItemTypes.WHEAT, ItemTypes.HAY_BLOCK);
        LLAMA_TEMPT_ITEMS.add(ItemTypes.HAY_BLOCK);
        copy(CAT_FOOD, OCELOT_FOOD);
        PANDA_FOOD.add(ItemTypes.BAMBOO);
        PIG_FOOD.add(ItemTypes.CARROT, ItemTypes.POTATO, ItemTypes.BEETROOT);
        RABBIT_FOOD.add(ItemTypes.CARROT, ItemTypes.GOLDEN_CARROT, ItemTypes.DANDELION);
        STRIDER_FOOD.add(ItemTypes.WARPED_FUNGUS);
        TURTLE_FOOD.add(ItemTypes.SEAGRASS);
        copy(CHICKEN_FOOD, PARROT_FOOD);
        PARROT_POISONOUS_FOOD.add(ItemTypes.COOKIE);
        AXOLOTL_FOOD.add(ItemTypes.TROPICAL_FISH_BUCKET);
        NAUTILUS_BUCKET_FOOD.add(
                ItemTypes.PUFFERFISH_BUCKET,
                ItemTypes.COD_BUCKET,
                ItemTypes.SALMON_BUCKET,
                ItemTypes.TROPICAL_FISH_BUCKET);
        NAUTILUS_TAMING_ITEMS.add(ItemTypes.PUFFERFISH_BUCKET, ItemTypes.PUFFERFISH);
        copy(FROG_FOOD, SULFUR_CUBE_FOOD);
        MUSHROOMS.add(ItemTypes.BROWN_MUSHROOM, ItemTypes.RED_MUSHROOM, ItemTypes.SHELF_MUSHROOM);
        NON_FLAMMABLE_WOOD.add(
                ItemTypes.WARPED_STEM,
                ItemTypes.STRIPPED_WARPED_STEM,
                ItemTypes.WARPED_HYPHAE,
                ItemTypes.STRIPPED_WARPED_HYPHAE,
                ItemTypes.CRIMSON_STEM,
                ItemTypes.STRIPPED_CRIMSON_STEM,
                ItemTypes.CRIMSON_HYPHAE,
                ItemTypes.STRIPPED_CRIMSON_HYPHAE,
                ItemTypes.CRIMSON_PLANKS,
                ItemTypes.WARPED_PLANKS,
                ItemTypes.CRIMSON_SLAB,
                ItemTypes.WARPED_SLAB,
                ItemTypes.CRIMSON_PRESSURE_PLATE,
                ItemTypes.WARPED_PRESSURE_PLATE,
                ItemTypes.CRIMSON_FENCE,
                ItemTypes.WARPED_FENCE,
                ItemTypes.CRIMSON_TRAPDOOR,
                ItemTypes.WARPED_TRAPDOOR,
                ItemTypes.CRIMSON_FENCE_GATE,
                ItemTypes.WARPED_FENCE_GATE,
                ItemTypes.CRIMSON_STAIRS,
                ItemTypes.WARPED_STAIRS,
                ItemTypes.CRIMSON_BUTTON,
                ItemTypes.WARPED_BUTTON,
                ItemTypes.CRIMSON_DOOR,
                ItemTypes.WARPED_DOOR,
                ItemTypes.CRIMSON_SIGN,
                ItemTypes.WARPED_SIGN,
                ItemTypes.WARPED_HANGING_SIGN,
                ItemTypes.CRIMSON_HANGING_SIGN,
                ItemTypes.WARPED_SHELF,
                ItemTypes.CRIMSON_SHELF);
        CHEST_BOATS.add(
                ItemTypes.OAK_CHEST_BOAT,
                ItemTypes.SPRUCE_CHEST_BOAT,
                ItemTypes.BIRCH_CHEST_BOAT,
                ItemTypes.JUNGLE_CHEST_BOAT,
                ItemTypes.ACACIA_CHEST_BOAT,
                ItemTypes.DARK_OAK_CHEST_BOAT,
                ItemTypes.PALE_OAK_CHEST_BOAT,
                ItemTypes.MANGROVE_CHEST_BOAT,
                ItemTypes.BAMBOO_CHEST_RAFT,
                ItemTypes.CHERRY_CHEST_BOAT,
                ItemTypes.POPLAR_CHEST_BOAT);
        FISHES.add(
                ItemTypes.COD,
                ItemTypes.COOKED_COD,
                ItemTypes.SALMON,
                ItemTypes.COOKED_SALMON,
                ItemTypes.PUFFERFISH,
                ItemTypes.TROPICAL_FISH);
        CREEPER_DROP_MUSIC_DISCS.add(
                ItemTypes.MUSIC_DISC_13,
                ItemTypes.MUSIC_DISC_CAT,
                ItemTypes.MUSIC_DISC_BLOCKS,
                ItemTypes.MUSIC_DISC_CHIRP,
                ItemTypes.MUSIC_DISC_FAR,
                ItemTypes.MUSIC_DISC_MALL,
                ItemTypes.MUSIC_DISC_MELLOHI,
                ItemTypes.MUSIC_DISC_STAL,
                ItemTypes.MUSIC_DISC_STRAD,
                ItemTypes.MUSIC_DISC_WARD,
                ItemTypes.MUSIC_DISC_11,
                ItemTypes.MUSIC_DISC_WAIT);
        COALS.add(ItemTypes.COAL, ItemTypes.CHARCOAL);
        ARROWS.add(ItemTypes.ARROW, ItemTypes.TIPPED_ARROW, ItemTypes.SPECTRAL_ARROW);
        LECTERN_BOOKS.add(ItemTypes.WRITTEN_BOOK, ItemTypes.WRITABLE_BOOK);
        BOOKSHELF_BOOKS.add(
                ItemTypes.BOOK,
                ItemTypes.WRITTEN_BOOK,
                ItemTypes.ENCHANTED_BOOK,
                ItemTypes.WRITABLE_BOOK,
                ItemTypes.KNOWLEDGE_BOOK);
        BEACON_PAYMENT_ITEMS.add(
                ItemTypes.NETHERITE_INGOT,
                ItemTypes.EMERALD,
                ItemTypes.DIAMOND,
                ItemTypes.GOLD_INGOT,
                ItemTypes.IRON_INGOT);
        STONE_TOOL_MATERIALS.add(
                ItemTypes.COBBLESTONE, ItemTypes.BLACKSTONE, ItemTypes.COBBLED_DEEPSLATE);
        COPPER_TOOL_MATERIALS.add(ItemTypes.COPPER_INGOT);
        IRON_TOOL_MATERIALS.add(ItemTypes.IRON_INGOT);
        GOLD_TOOL_MATERIALS.add(ItemTypes.GOLD_INGOT);
        DIAMOND_TOOL_MATERIALS.add(ItemTypes.DIAMOND);
        NETHERITE_TOOL_MATERIALS.add(ItemTypes.NETHERITE_INGOT);
        copy(IGNORED_BY_PIGLIN_BABIES, REPAIRS_LEATHER_ARMOR);
        copy(COPPER_TOOL_MATERIALS, REPAIRS_COPPER_ARMOR);
        copy(IRON_TOOL_MATERIALS, REPAIRS_CHAIN_ARMOR);
        copy(IRON_TOOL_MATERIALS, REPAIRS_IRON_ARMOR);
        copy(GOLD_TOOL_MATERIALS, REPAIRS_GOLD_ARMOR);
        copy(DIAMOND_TOOL_MATERIALS, REPAIRS_DIAMOND_ARMOR);
        copy(NETHERITE_TOOL_MATERIALS, REPAIRS_NETHERITE_ARMOR);
        REPAIRS_TURTLE_HELMET.add(ItemTypes.TURTLE_SCUTE);
        REPAIRS_WOLF_ARMOR.add(ItemTypes.ARMADILLO_SCUTE);
        copy(STONE_TOOL_MATERIALS, STONE_CRAFTING_MATERIALS);
        FREEZE_IMMUNE_WEARABLES.add(
                ItemTypes.LEATHER_BOOTS,
                ItemTypes.LEATHER_LEGGINGS,
                ItemTypes.LEATHER_CHESTPLATE,
                ItemTypes.LEATHER_HELMET,
                ItemTypes.LEATHER_HORSE_ARMOR);
        CLUSTER_MAX_HARVESTABLES.add(
                ItemTypes.DIAMOND_PICKAXE,
                ItemTypes.GOLDEN_PICKAXE,
                ItemTypes.IRON_PICKAXE,
                ItemTypes.NETHERITE_PICKAXE,
                ItemTypes.STONE_PICKAXE,
                ItemTypes.WOODEN_PICKAXE,
                ItemTypes.COPPER_PICKAXE);
        COMPASSES.add(ItemTypes.COMPASS, ItemTypes.RECOVERY_COMPASS);
        CLONABLE_MAPS.add(
                ItemTypes.FILLED_MAP,
                ItemTypes.OCEAN_MONUMENT_MAP,
                ItemTypes.WOODLAND_MANSION_MAP,
                ItemTypes.BURIED_TRIAL_CHAMBERS_MAP,
                ItemTypes.JUNGLE_PYRAMID_MAP,
                ItemTypes.SWAMP_HUT_MAP,
                ItemTypes.DESERT_VILLAGE_MAP,
                ItemTypes.PLAINS_VILLAGE_MAP,
                ItemTypes.SAVANNA_VILLAGE_MAP,
                ItemTypes.SNOWY_VILLAGE_MAP,
                ItemTypes.TAIGA_VILLAGE_MAP,
                ItemTypes.BURIED_TREASURE_MAP,
                ItemTypes.BURIED_ANCIENT_CITY_MAP,
                ItemTypes.BURIED_MINESHAFT_MAP,
                ItemTypes.DESERT_PYRAMID_MAP,
                ItemTypes.ABANDONED_CAMP_MAP,
                ItemTypes.WARM_OCEAN_RUINS_MAP);
        EXTENDABLE_MAPS.add(ItemTypes.FILLED_MAP);
        CREEPER_IGNITERS.add(ItemTypes.FLINT_AND_STEEL, ItemTypes.FIRE_CHARGE);
        NOTEBLOCK_TOP_INSTRUMENTS.add(
                ItemTypes.ZOMBIE_HEAD,
                ItemTypes.SKELETON_SKULL,
                ItemTypes.CREEPER_HEAD,
                ItemTypes.DRAGON_HEAD,
                ItemTypes.WITHER_SKELETON_SKULL,
                ItemTypes.PIGLIN_HEAD,
                ItemTypes.PLAYER_HEAD);
        FOOT_ARMOR.add(
                ItemTypes.LEATHER_BOOTS,
                ItemTypes.COPPER_BOOTS,
                ItemTypes.CHAINMAIL_BOOTS,
                ItemTypes.GOLDEN_BOOTS,
                ItemTypes.IRON_BOOTS,
                ItemTypes.DIAMOND_BOOTS,
                ItemTypes.NETHERITE_BOOTS);
        LEG_ARMOR.add(
                ItemTypes.LEATHER_LEGGINGS,
                ItemTypes.COPPER_LEGGINGS,
                ItemTypes.CHAINMAIL_LEGGINGS,
                ItemTypes.GOLDEN_LEGGINGS,
                ItemTypes.IRON_LEGGINGS,
                ItemTypes.DIAMOND_LEGGINGS,
                ItemTypes.NETHERITE_LEGGINGS);
        CHEST_ARMOR.add(
                ItemTypes.LEATHER_CHESTPLATE,
                ItemTypes.COPPER_CHESTPLATE,
                ItemTypes.CHAINMAIL_CHESTPLATE,
                ItemTypes.GOLDEN_CHESTPLATE,
                ItemTypes.IRON_CHESTPLATE,
                ItemTypes.DIAMOND_CHESTPLATE,
                ItemTypes.NETHERITE_CHESTPLATE);
        HEAD_ARMOR.add(
                ItemTypes.LEATHER_HELMET,
                ItemTypes.COPPER_HELMET,
                ItemTypes.CHAINMAIL_HELMET,
                ItemTypes.GOLDEN_HELMET,
                ItemTypes.IRON_HELMET,
                ItemTypes.DIAMOND_HELMET,
                ItemTypes.NETHERITE_HELMET,
                ItemTypes.TURTLE_HELMET);
        TRIM_MATERIALS.add(
                ItemTypes.AMETHYST_SHARD,
                ItemTypes.COPPER_INGOT,
                ItemTypes.DIAMOND,
                ItemTypes.EMERALD,
                ItemTypes.GOLD_INGOT,
                ItemTypes.IRON_INGOT,
                ItemTypes.LAPIS_LAZULI,
                ItemTypes.NETHERITE_INGOT,
                ItemTypes.QUARTZ,
                ItemTypes.REDSTONE,
                ItemTypes.RESIN_BRICK);
        DECORATED_POT_SHERDS.add(
                ItemTypes.ANGLER_POTTERY_SHERD,
                ItemTypes.ARCHER_POTTERY_SHERD,
                ItemTypes.ARMS_UP_POTTERY_SHERD,
                ItemTypes.BLADE_POTTERY_SHERD,
                ItemTypes.BREWER_POTTERY_SHERD,
                ItemTypes.BURN_POTTERY_SHERD,
                ItemTypes.DANGER_POTTERY_SHERD,
                ItemTypes.EXPLORER_POTTERY_SHERD,
                ItemTypes.FRIEND_POTTERY_SHERD,
                ItemTypes.HEART_POTTERY_SHERD,
                ItemTypes.HEARTBREAK_POTTERY_SHERD,
                ItemTypes.HOWL_POTTERY_SHERD,
                ItemTypes.MINER_POTTERY_SHERD,
                ItemTypes.MOURNER_POTTERY_SHERD,
                ItemTypes.PLENTY_POTTERY_SHERD,
                ItemTypes.PRIZE_POTTERY_SHERD,
                ItemTypes.SHEAF_POTTERY_SHERD,
                ItemTypes.SHELTER_POTTERY_SHERD,
                ItemTypes.SKULL_POTTERY_SHERD,
                ItemTypes.SNORT_POTTERY_SHERD,
                ItemTypes.FLOW_POTTERY_SHERD,
                ItemTypes.GUSTER_POTTERY_SHERD,
                ItemTypes.SCRAPE_POTTERY_SHERD);
        SWORDS.add(
                ItemTypes.DIAMOND_SWORD,
                ItemTypes.STONE_SWORD,
                ItemTypes.GOLDEN_SWORD,
                ItemTypes.NETHERITE_SWORD,
                ItemTypes.WOODEN_SWORD,
                ItemTypes.IRON_SWORD,
                ItemTypes.COPPER_SWORD);
        AXES.add(
                ItemTypes.DIAMOND_AXE,
                ItemTypes.STONE_AXE,
                ItemTypes.GOLDEN_AXE,
                ItemTypes.NETHERITE_AXE,
                ItemTypes.WOODEN_AXE,
                ItemTypes.IRON_AXE,
                ItemTypes.COPPER_AXE);
        HOES.add(
                ItemTypes.DIAMOND_HOE,
                ItemTypes.STONE_HOE,
                ItemTypes.GOLDEN_HOE,
                ItemTypes.NETHERITE_HOE,
                ItemTypes.WOODEN_HOE,
                ItemTypes.IRON_HOE,
                ItemTypes.COPPER_HOE);
        PICKAXES.add(
                ItemTypes.DIAMOND_PICKAXE,
                ItemTypes.STONE_PICKAXE,
                ItemTypes.GOLDEN_PICKAXE,
                ItemTypes.NETHERITE_PICKAXE,
                ItemTypes.WOODEN_PICKAXE,
                ItemTypes.IRON_PICKAXE,
                ItemTypes.COPPER_PICKAXE);
        SHOVELS.add(
                ItemTypes.DIAMOND_SHOVEL,
                ItemTypes.STONE_SHOVEL,
                ItemTypes.GOLDEN_SHOVEL,
                ItemTypes.NETHERITE_SHOVEL,
                ItemTypes.WOODEN_SHOVEL,
                ItemTypes.IRON_SHOVEL,
                ItemTypes.COPPER_SHOVEL);
        SPEARS.add(
                ItemTypes.DIAMOND_SPEAR,
                ItemTypes.STONE_SPEAR,
                ItemTypes.GOLDEN_SPEAR,
                ItemTypes.NETHERITE_SPEAR,
                ItemTypes.WOODEN_SPEAR,
                ItemTypes.IRON_SPEAR,
                ItemTypes.COPPER_SPEAR);
        VILLAGER_PLANTABLE_SEEDS.add(
                ItemTypes.WHEAT_SEEDS,
                ItemTypes.POTATO,
                ItemTypes.CARROT,
                ItemTypes.BEETROOT_SEEDS,
                ItemTypes.TORCHFLOWER_SEEDS,
                ItemTypes.PITCHER_POD);
        copy(COALS, FURNACE_MINECART_FUEL);
        BUNDLES.add(
                ItemTypes.BUNDLE,
                ItemTypes.WHITE_BUNDLE,
                ItemTypes.ORANGE_BUNDLE,
                ItemTypes.MAGENTA_BUNDLE,
                ItemTypes.LIGHT_BLUE_BUNDLE,
                ItemTypes.YELLOW_BUNDLE,
                ItemTypes.LIME_BUNDLE,
                ItemTypes.PINK_BUNDLE,
                ItemTypes.GRAY_BUNDLE,
                ItemTypes.LIGHT_GRAY_BUNDLE,
                ItemTypes.CYAN_BUNDLE,
                ItemTypes.PURPLE_BUNDLE,
                ItemTypes.BLUE_BUNDLE,
                ItemTypes.BROWN_BUNDLE,
                ItemTypes.GREEN_BUNDLE,
                ItemTypes.RED_BUNDLE,
                ItemTypes.BLACK_BUNDLE);
        BOOK_CLONING_TARGET.add(ItemTypes.WRITABLE_BOOK);
        DYES.add(
                ItemTypes.WHITE_DYE,
                ItemTypes.ORANGE_DYE,
                ItemTypes.MAGENTA_DYE,
                ItemTypes.LIGHT_BLUE_DYE,
                ItemTypes.YELLOW_DYE,
                ItemTypes.LIME_DYE,
                ItemTypes.PINK_DYE,
                ItemTypes.GRAY_DYE,
                ItemTypes.LIGHT_GRAY_DYE,
                ItemTypes.CYAN_DYE,
                ItemTypes.PURPLE_DYE,
                ItemTypes.BLUE_DYE,
                ItemTypes.BROWN_DYE,
                ItemTypes.GREEN_DYE,
                ItemTypes.RED_DYE,
                ItemTypes.BLACK_DYE);
        LOOM_PATTERNS.add(
                ItemTypes.FLOWER_BANNER_PATTERN,
                ItemTypes.CREEPER_BANNER_PATTERN,
                ItemTypes.SKULL_BANNER_PATTERN,
                ItemTypes.MOJANG_BANNER_PATTERN,
                ItemTypes.GLOBE_BANNER_PATTERN,
                ItemTypes.PIGLIN_BANNER_PATTERN,
                ItemTypes.FLOW_BANNER_PATTERN,
                ItemTypes.GUSTER_BANNER_PATTERN,
                ItemTypes.FIELD_MASONED_BANNER_PATTERN,
                ItemTypes.BORDURE_INDENTED_BANNER_PATTERN);
        CAULDRON_CAN_REMOVE_DYE.add(
                ItemTypes.LEATHER_HELMET,
                ItemTypes.LEATHER_CHESTPLATE,
                ItemTypes.LEATHER_LEGGINGS,
                ItemTypes.LEATHER_BOOTS,
                ItemTypes.LEATHER_HORSE_ARMOR,
                ItemTypes.WOLF_ARMOR);
        CUSHIONS.add(
                ItemTypes.WHITE_CUSHION,
                ItemTypes.ORANGE_CUSHION,
                ItemTypes.MAGENTA_CUSHION,
                ItemTypes.LIGHT_BLUE_CUSHION,
                ItemTypes.YELLOW_CUSHION,
                ItemTypes.LIME_CUSHION,
                ItemTypes.PINK_CUSHION,
                ItemTypes.GRAY_CUSHION,
                ItemTypes.LIGHT_GRAY_CUSHION,
                ItemTypes.CYAN_CUSHION,
                ItemTypes.PURPLE_CUSHION,
                ItemTypes.BLUE_CUSHION,
                ItemTypes.BROWN_CUSHION,
                ItemTypes.GREEN_CUSHION,
                ItemTypes.RED_CUSHION,
                ItemTypes.BLACK_CUSHION);
        SKELETON_PREFERRED_WEAPONS.add(ItemTypes.BOW);
        DROWNED_PREFERRED_WEAPONS.add(ItemTypes.TRIDENT);
        PIGLIN_PREFERRED_WEAPONS.add(ItemTypes.CROSSBOW, ItemTypes.GOLDEN_SPEAR);
        PILLAGER_PREFERRED_WEAPONS.add(ItemTypes.CROSSBOW);
        WITHER_SKELETON_DISLIKED_WEAPONS.add(ItemTypes.BOW, ItemTypes.CROSSBOW);
        SHEARABLE_FROM_COPPER_GOLEM.add(ItemTypes.POPPY);
        METAL_NUGGETS.add(ItemTypes.COPPER_NUGGET, ItemTypes.IRON_NUGGET, ItemTypes.GOLD_NUGGET);
        BREWING_POTION_INPUTS.add(
                ItemTypes.POTION,
                ItemTypes.SPLASH_POTION,
                ItemTypes.LINGERING_POTION,
                ItemTypes.GLASS_BOTTLE);
        FURNACE_FUEL_BOTTOM_TAKEABLE.add(ItemTypes.WATER_BUCKET, ItemTypes.BUCKET);
        ENCHANTABLE_FISHING.add(ItemTypes.FISHING_ROD);
        copy(DROWNED_PREFERRED_WEAPONS, ENCHANTABLE_TRIDENT);
        copy(SKELETON_PREFERRED_WEAPONS, ENCHANTABLE_BOW);
        copy(PILLAGER_PREFERRED_WEAPONS, ENCHANTABLE_CROSSBOW);
        ENCHANTABLE_MACE.add(ItemTypes.MACE);
        MAP_INVISIBILITY_EQUIPMENT.add(ItemTypes.CARVED_PUMPKIN);
        copy(MAP_INVISIBILITY_EQUIPMENT, GAZE_DISGUISE_EQUIPMENT);
        copy(BlockTags.STONE_BUTTONS, STONE_BUTTONS);
        copy(BlockTags.SMALL_FLOWERS, SMALL_FLOWERS);
        copy(BlockTags.GRASS_BLOCKS, GRASS_BLOCKS);
        copy(BlockTags.COPPER_CHESTS, COPPER_CHESTS);
        copy(BlockTags.LIGHTNING_RODS, LIGHTNING_RODS);
        copy(BlockTags.COPPER_GOLEM_STATUES, COPPER_GOLEM_STATUES);
        copy(BlockTags.CHAINS, CHAINS);
        copy(BlockTags.LANTERNS, LANTERNS);
        copy(BlockTags.BARS, BARS);
        copy(BlockTags.LOGS_THAT_BURN, LOGS_THAT_BURN);
        copy(BlockTags.DAMPENS_VIBRATIONS, DAMPENS_VIBRATIONS);
        SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY
                .addTag(CONCRETE)
                .addTag(COAL_ORES)
                .addTag(LAPIS_ORES)
                .addTag(REDSTONE_ORES)
                .addTag(DIAMOND_ORES)
                .addTag(EMERALD_ORES)
                .addTag(TERRACOTTA)
                .addTag(GLAZED_TERRACOTTA)
                .add(
                        ItemTypes.AMETHYST_BLOCK,
                        ItemTypes.ANDESITE,
                        ItemTypes.BASALT,
                        ItemTypes.BLACKSTONE,
                        ItemTypes.BRICKS,
                        ItemTypes.CALCITE,
                        ItemTypes.CHISELED_CINNABAR,
                        ItemTypes.CHISELED_DEEPSLATE,
                        ItemTypes.CHISELED_NETHER_BRICKS,
                        ItemTypes.CHISELED_POLISHED_BLACKSTONE,
                        ItemTypes.CHISELED_QUARTZ_BLOCK,
                        ItemTypes.CHISELED_RED_SANDSTONE,
                        ItemTypes.CHISELED_SANDSTONE,
                        ItemTypes.CHISELED_STONE_BRICKS,
                        ItemTypes.CHISELED_SULFUR,
                        ItemTypes.CHISELED_TUFF,
                        ItemTypes.CHISELED_TUFF_BRICKS,
                        ItemTypes.CINNABAR,
                        ItemTypes.CINNABAR_BRICKS,
                        ItemTypes.COBBLED_DEEPSLATE,
                        ItemTypes.COBBLESTONE,
                        ItemTypes.CRACKED_DEEPSLATE_BRICKS,
                        ItemTypes.CRACKED_DEEPSLATE_TILES,
                        ItemTypes.CRACKED_NETHER_BRICKS,
                        ItemTypes.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                        ItemTypes.CRACKED_STONE_BRICKS,
                        ItemTypes.CRIMSON_NYLIUM,
                        ItemTypes.CRYING_OBSIDIAN,
                        ItemTypes.CUT_RED_SANDSTONE,
                        ItemTypes.CUT_SANDSTONE,
                        ItemTypes.DARK_PRISMARINE,
                        ItemTypes.DEEPSLATE,
                        ItemTypes.DEEPSLATE_BRICKS,
                        ItemTypes.DEEPSLATE_TILES,
                        ItemTypes.DIAMOND_BLOCK,
                        ItemTypes.DIORITE,
                        ItemTypes.DRIPSTONE_BLOCK,
                        ItemTypes.EMERALD_BLOCK,
                        ItemTypes.END_STONE,
                        ItemTypes.END_STONE_BRICKS,
                        ItemTypes.GILDED_BLACKSTONE,
                        ItemTypes.GLOWSTONE,
                        ItemTypes.GRANITE,
                        ItemTypes.LAPIS_BLOCK,
                        ItemTypes.MOSSY_COBBLESTONE,
                        ItemTypes.MOSSY_STONE_BRICKS,
                        ItemTypes.MUD_BRICKS,
                        ItemTypes.NETHER_BRICKS,
                        ItemTypes.NETHERRACK,
                        ItemTypes.OBSERVER,
                        ItemTypes.OBSIDIAN,
                        ItemTypes.POLISHED_ANDESITE,
                        ItemTypes.POLISHED_BASALT,
                        ItemTypes.POLISHED_BLACKSTONE,
                        ItemTypes.POLISHED_BLACKSTONE_BRICKS,
                        ItemTypes.POLISHED_CINNABAR,
                        ItemTypes.POLISHED_DEEPSLATE,
                        ItemTypes.POLISHED_DIORITE,
                        ItemTypes.POLISHED_GRANITE,
                        ItemTypes.POLISHED_SULFUR,
                        ItemTypes.POLISHED_TUFF,
                        ItemTypes.PRISMARINE,
                        ItemTypes.PRISMARINE_BRICKS,
                        ItemTypes.PURPUR_BLOCK,
                        ItemTypes.PURPUR_PILLAR,
                        ItemTypes.QUARTZ_BLOCK,
                        ItemTypes.QUARTZ_BRICKS,
                        ItemTypes.NETHER_QUARTZ_ORE,
                        ItemTypes.QUARTZ_PILLAR,
                        ItemTypes.RED_NETHER_BRICKS,
                        ItemTypes.RED_SANDSTONE,
                        ItemTypes.REDSTONE_LAMP,
                        ItemTypes.SANDSTONE,
                        ItemTypes.SEA_LANTERN,
                        ItemTypes.SMOOTH_BASALT,
                        ItemTypes.SMOOTH_QUARTZ,
                        ItemTypes.SMOOTH_RED_SANDSTONE,
                        ItemTypes.SMOOTH_SANDSTONE,
                        ItemTypes.SMOOTH_STONE,
                        ItemTypes.STONE,
                        ItemTypes.STONE_BRICKS,
                        ItemTypes.SULFUR,
                        ItemTypes.SULFUR_BRICKS,
                        ItemTypes.TUFF,
                        ItemTypes.TUFF_BRICKS,
                        ItemTypes.WARPED_NYLIUM);
        SULFUR_CUBE_ARCHETYPE_REGULAR
                .addTag(CONCRETE_POWDERS)
                .add(
                        ItemTypes.MUD,
                        ItemTypes.MUDDY_MANGROVE_ROOTS,
                        ItemTypes.PACKED_MUD,
                        ItemTypes.COAL_BLOCK,
                        ItemTypes.DIRT,
                        ItemTypes.COARSE_DIRT,
                        ItemTypes.ROOTED_DIRT,
                        ItemTypes.PODZOL,
                        ItemTypes.GRASS_BLOCK,
                        ItemTypes.CLAY,
                        ItemTypes.BONE_BLOCK);
        SULFUR_CUBE_ARCHETYPE_SLOW_FLAT
                .addTag(GOLD_ORES)
                .addTag(IRON_ORES)
                .addTag(COPPER_ORES)
                .add(
                        ItemTypes.IRON_BLOCK,
                        ItemTypes.GOLD_BLOCK,
                        ItemTypes.RAW_COPPER_BLOCK,
                        ItemTypes.RAW_GOLD_BLOCK,
                        ItemTypes.RAW_IRON_BLOCK,
                        ItemTypes.NETHERITE_BLOCK,
                        ItemTypes.ANCIENT_DEBRIS,
                        ItemTypes.COPPER_BLOCK,
                        ItemTypes.EXPOSED_COPPER,
                        ItemTypes.WEATHERED_COPPER,
                        ItemTypes.OXIDIZED_COPPER,
                        ItemTypes.WAXED_COPPER_BLOCK,
                        ItemTypes.WAXED_EXPOSED_COPPER,
                        ItemTypes.WAXED_WEATHERED_COPPER,
                        ItemTypes.WAXED_OXIDIZED_COPPER,
                        ItemTypes.COPPER_BULB,
                        ItemTypes.EXPOSED_COPPER_BULB,
                        ItemTypes.WEATHERED_COPPER_BULB,
                        ItemTypes.OXIDIZED_COPPER_BULB,
                        ItemTypes.WAXED_COPPER_BULB,
                        ItemTypes.WAXED_EXPOSED_COPPER_BULB,
                        ItemTypes.WAXED_WEATHERED_COPPER_BULB,
                        ItemTypes.WAXED_OXIDIZED_COPPER_BULB,
                        ItemTypes.CUT_COPPER,
                        ItemTypes.EXPOSED_CUT_COPPER,
                        ItemTypes.WEATHERED_CUT_COPPER,
                        ItemTypes.OXIDIZED_CUT_COPPER,
                        ItemTypes.WAXED_CUT_COPPER,
                        ItemTypes.WAXED_EXPOSED_CUT_COPPER,
                        ItemTypes.WAXED_WEATHERED_CUT_COPPER,
                        ItemTypes.WAXED_OXIDIZED_CUT_COPPER,
                        ItemTypes.CHISELED_COPPER,
                        ItemTypes.EXPOSED_CHISELED_COPPER,
                        ItemTypes.WEATHERED_CHISELED_COPPER,
                        ItemTypes.OXIDIZED_CHISELED_COPPER,
                        ItemTypes.WAXED_CHISELED_COPPER,
                        ItemTypes.WAXED_EXPOSED_CHISELED_COPPER,
                        ItemTypes.WAXED_WEATHERED_CHISELED_COPPER,
                        ItemTypes.WAXED_OXIDIZED_CHISELED_COPPER);
        SULFUR_CUBE_ARCHETYPE_FAST_FLAT
                .addTag(MOSS_BLOCKS)
                .add(
                        ItemTypes.TUBE_CORAL_BLOCK,
                        ItemTypes.BRAIN_CORAL_BLOCK,
                        ItemTypes.BUBBLE_CORAL_BLOCK,
                        ItemTypes.FIRE_CORAL_BLOCK,
                        ItemTypes.HORN_CORAL_BLOCK,
                        ItemTypes.DEAD_TUBE_CORAL_BLOCK,
                        ItemTypes.DEAD_BRAIN_CORAL_BLOCK,
                        ItemTypes.DEAD_BUBBLE_CORAL_BLOCK,
                        ItemTypes.DEAD_FIRE_CORAL_BLOCK,
                        ItemTypes.DEAD_HORN_CORAL_BLOCK,
                        ItemTypes.SPONGE,
                        ItemTypes.WET_SPONGE,
                        ItemTypes.DRIED_KELP_BLOCK,
                        ItemTypes.RESIN_BLOCK,
                        ItemTypes.RESIN_BRICKS,
                        ItemTypes.CHISELED_RESIN_BRICKS,
                        ItemTypes.MELON,
                        ItemTypes.HAY_BLOCK,
                        ItemTypes.PUMPKIN,
                        ItemTypes.CARVED_PUMPKIN,
                        ItemTypes.JACK_O_LANTERN,
                        ItemTypes.OCHRE_FROGLIGHT,
                        ItemTypes.PEARLESCENT_FROGLIGHT,
                        ItemTypes.VERDANT_FROGLIGHT);
        copy(BlockTags.OCCLUDES_VIBRATION_SIGNALS, SULFUR_CUBE_ARCHETYPE_LIGHT);
        SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING
                .addTag(WART_BLOCKS)
                .add(
                        ItemTypes.BROWN_MUSHROOM_BLOCK,
                        ItemTypes.RED_MUSHROOM_BLOCK,
                        ItemTypes.MUSHROOM_STEM,
                        ItemTypes.MYCELIUM,
                        ItemTypes.SHROOMLIGHT);
        PIGLIN_LOVED
                .addTag(GOLD_ORES)
                .add(
                        ItemTypes.GOLD_BLOCK,
                        ItemTypes.GILDED_BLACKSTONE,
                        ItemTypes.LIGHT_WEIGHTED_PRESSURE_PLATE,
                        ItemTypes.GOLD_INGOT,
                        ItemTypes.BELL,
                        ItemTypes.CLOCK,
                        ItemTypes.GOLDEN_CARROT,
                        ItemTypes.GLISTERING_MELON_SLICE,
                        ItemTypes.GOLDEN_APPLE,
                        ItemTypes.ENCHANTED_GOLDEN_APPLE,
                        ItemTypes.GOLDEN_HELMET,
                        ItemTypes.GOLDEN_CHESTPLATE,
                        ItemTypes.GOLDEN_LEGGINGS,
                        ItemTypes.GOLDEN_BOOTS,
                        ItemTypes.GOLDEN_HORSE_ARMOR,
                        ItemTypes.GOLDEN_NAUTILUS_ARMOR,
                        ItemTypes.GOLDEN_SWORD,
                        ItemTypes.GOLDEN_SPEAR,
                        ItemTypes.GOLDEN_PICKAXE,
                        ItemTypes.GOLDEN_SHOVEL,
                        ItemTypes.GOLDEN_AXE,
                        ItemTypes.GOLDEN_HOE,
                        ItemTypes.RAW_GOLD,
                        ItemTypes.RAW_GOLD_BLOCK,
                        ItemTypes.GOLDEN_DANDELION);
        WOLF_FOOD
                .addTag(MEAT)
                .add(
                        ItemTypes.COD,
                        ItemTypes.COOKED_COD,
                        ItemTypes.SALMON,
                        ItemTypes.COOKED_SALMON,
                        ItemTypes.TROPICAL_FISH,
                        ItemTypes.PUFFERFISH,
                        ItemTypes.RABBIT_STEW);
        HAPPY_GHAST_TEMPT_ITEMS.addTag(HAPPY_GHAST_FOOD).addTag(HARNESSES);
        PANDA_EATS_FROM_GROUND.addTag(PANDA_FOOD).add(ItemTypes.CAKE);
        STRIDER_TEMPT_ITEMS.addTag(STRIDER_FOOD).add(ItemTypes.WARPED_FUNGUS_ON_A_STICK);
        NAUTILUS_FOOD.addTag(FISHES).addTag(NAUTILUS_BUCKET_FOOD);
        BOATS.addTag(CHEST_BOATS)
                .add(
                        ItemTypes.OAK_BOAT,
                        ItemTypes.SPRUCE_BOAT,
                        ItemTypes.BIRCH_BOAT,
                        ItemTypes.JUNGLE_BOAT,
                        ItemTypes.ACACIA_BOAT,
                        ItemTypes.DARK_OAK_BOAT,
                        ItemTypes.PALE_OAK_BOAT,
                        ItemTypes.MANGROVE_BOAT,
                        ItemTypes.BAMBOO_RAFT,
                        ItemTypes.CHERRY_BOAT,
                        ItemTypes.POPLAR_BOAT);
        WOODEN_TOOL_MATERIALS.addTag(PLANKS);
        TRIMMABLE_ARMOR.addTag(FOOT_ARMOR).addTag(LEG_ARMOR).addTag(CHEST_ARMOR).addTag(HEAD_ARMOR);
        DECORATED_POT_INGREDIENTS.addTag(DECORATED_POT_SHERDS).add(ItemTypes.BRICK);
        BREAKS_DECORATED_POTS
                .addTag(SWORDS)
                .addTag(AXES)
                .addTag(PICKAXES)
                .addTag(SHOVELS)
                .addTag(HOES)
                .add(ItemTypes.TRIDENT, ItemTypes.MACE);
        VILLAGER_PICKS_UP
                .addTag(VILLAGER_PLANTABLE_SEEDS)
                .add(ItemTypes.BREAD, ItemTypes.WHEAT, ItemTypes.BEETROOT);
        LOOM_DYES.addTag(DYES);
        copy(LOOM_DYES, CAT_COLLAR_DYES);
        copy(LOOM_DYES, WOLF_COLLAR_DYES);
        DOUSES_CAMPFIRES.addTag(SHOVELS);
        ENCHANTABLE_FOOT_ARMOR.addTag(FOOT_ARMOR);
        ENCHANTABLE_LEG_ARMOR.addTag(LEG_ARMOR);
        ENCHANTABLE_CHEST_ARMOR.addTag(CHEST_ARMOR);
        ENCHANTABLE_HEAD_ARMOR.addTag(HEAD_ARMOR);
        ENCHANTABLE_MELEE_WEAPON.addTag(SWORDS).addTag(SPEARS);
        ENCHANTABLE_SWEEPING.addTag(SWORDS);
        ENCHANTABLE_MINING
                .addTag(AXES)
                .addTag(PICKAXES)
                .addTag(SHOVELS)
                .addTag(HOES)
                .add(ItemTypes.SHEARS);
        ENCHANTABLE_MINING_LOOT.addTag(AXES).addTag(PICKAXES).addTag(SHOVELS).addTag(HOES);
        ENCHANTABLE_LUNGE.addTag(SPEARS);
        ENCHANTABLE_DURABILITY
                .addTag(FOOT_ARMOR)
                .addTag(LEG_ARMOR)
                .addTag(CHEST_ARMOR)
                .addTag(HEAD_ARMOR)
                .addTag(SWORDS)
                .addTag(AXES)
                .addTag(PICKAXES)
                .addTag(SHOVELS)
                .addTag(HOES)
                .addTag(SPEARS)
                .add(
                        ItemTypes.ELYTRA,
                        ItemTypes.SHIELD,
                        ItemTypes.BOW,
                        ItemTypes.CROSSBOW,
                        ItemTypes.TRIDENT,
                        ItemTypes.FLINT_AND_STEEL,
                        ItemTypes.SHEARS,
                        ItemTypes.BRUSH,
                        ItemTypes.FISHING_ROD,
                        ItemTypes.CARROT_ON_A_STICK,
                        ItemTypes.WARPED_FUNGUS_ON_A_STICK,
                        ItemTypes.MACE);
        ENCHANTABLE_EQUIPPABLE
                .addTag(FOOT_ARMOR)
                .addTag(LEG_ARMOR)
                .addTag(CHEST_ARMOR)
                .addTag(HEAD_ARMOR)
                .addTag(SKULLS)
                .add(ItemTypes.ELYTRA, ItemTypes.CARVED_PUMPKIN);
        copy(BlockTags.BUTTONS, BUTTONS);
        copy(BlockTags.DOORS, DOORS);
        copy(BlockTags.SLABS, SLABS);
        copy(BlockTags.STAIRS, STAIRS);
        copy(BlockTags.TRAPDOORS, TRAPDOORS);
        copy(BlockTags.FLOWERS, FLOWERS);
        copy(BlockTags.FENCES, FENCES);
        copy(BlockTags.ORES, ORES);
        copy(BlockTags.LOGS, LOGS);
        ENCHANTABLE_ARMOR
                .addTag(ENCHANTABLE_FOOT_ARMOR)
                .addTag(ENCHANTABLE_LEG_ARMOR)
                .addTag(ENCHANTABLE_CHEST_ARMOR)
                .addTag(ENCHANTABLE_HEAD_ARMOR);
        ENCHANTABLE_FIRE_ASPECT.addTag(ENCHANTABLE_MELEE_WEAPON).add(ItemTypes.MACE);
        ENCHANTABLE_SHARP_WEAPON.addTag(ENCHANTABLE_MELEE_WEAPON).addTag(AXES);
        ENCHANTABLE_VANISHING
                .addTag(ENCHANTABLE_DURABILITY)
                .addTag(SKULLS)
                .add(ItemTypes.COMPASS, ItemTypes.CARVED_PUMPKIN);
        copy(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, COMPLETES_FIND_TREE_TUTORIAL);
        SULFUR_CUBE_ARCHETYPE_BOUNCY
                .addTag(PLANKS)
                .addTag(LOGS)
                .addTag(BAMBOO_BLOCKS)
                .add(ItemTypes.BAMBOO_MOSAIC);
        ENCHANTABLE_WEAPON.addTag(ENCHANTABLE_SHARP_WEAPON).add(ItemTypes.MACE);
        SULFUR_CUBE_SWALLOWABLE
                .addTag(SULFUR_CUBE_ARCHETYPE_BOUNCY)
                .addTag(SULFUR_CUBE_ARCHETYPE_REGULAR)
                .addTag(SULFUR_CUBE_ARCHETYPE_SLOW_FLAT)
                .addTag(SULFUR_CUBE_ARCHETYPE_FAST_FLAT)
                .addTag(SULFUR_CUBE_ARCHETYPE_LIGHT)
                .addTag(SULFUR_CUBE_ARCHETYPE_FAST_SLIDING)
                .addTag(SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING)
                .addTag(SULFUR_CUBE_ARCHETYPE_STICKY)
                .addTag(SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE)
                .addTag(SULFUR_CUBE_ARCHETYPE_EXPLOSIVE)
                .addTag(SULFUR_CUBE_ARCHETYPE_HOT)
                .addTag(SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY);
        Iterator<ItemType> iterator2 = ItemTypes.getRegistry().getEntries().iterator();
        while (iterator2.hasNext()) {
            ItemType itemType;
            if ((itemType = (ItemType) iterator2.next())
                    .getComponents()
                    .has(ComponentTypes.JUKEBOX_PLAYABLE)) {
                MUSIC_DISCS.add(itemType);
            }
        }
        copy(BlockTags.TALL_FLOWERS, TALL_FLOWERS);
        copy(BlockTags.FLOWERS, FLOWERS);
        TRIM_TEMPLATES.add(
                ItemTypes.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE,
                ItemTypes.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE);
        ENCHANTABLE_SWORD.addTag(SWORDS);
        BREWING_FUEL.add(ItemTypes.BLAZE_POWDER);
    }
}
