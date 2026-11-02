package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.AxolotlVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.CatCollarComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.CatSoundVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.CatVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.ChickenSoundVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.ChickenVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.CowSoundVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.CowVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.CushionColorComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.FoxVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.FrogVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.HorseVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.LlamaVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.MooshroomVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.PaintingVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.ParrotVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.PigSoundVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.PigVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.PotteryPatternComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.RabbitVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.SalmonSizeComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.SheepColorComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.ShulkerColorComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.SignTextComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.SulfurCubeContentComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.TropicalFishBaseColorComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.TropicalFishPatternColorComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.TropicalFishPatternComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.TypedBlockEntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.TypedEntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.VillagerVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.WolfCollarComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.WolfSoundVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.WolfVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.ZombieNautilusVariantComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ArmorTrim;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.BannerLayers;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.BlockTransformerComponent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.BundleContents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ChargedProjectiles;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.CustomData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.DebugStickState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.FireworkExplosion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.FoodProperties;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAdditionalTradeCost;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAdventurePredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAttackRange;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAttributeModifiers;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemBees;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemBlockStateProperties;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemBlocksAttacks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemBreakSound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemBrewingFuel;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemCompostable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemConsumable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemContainerContents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemContainerLoot;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemCookingFuel;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemCustomModelData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemDamageResistant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemDamageType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemDeathProtection;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemDyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemEnchantable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemEnchantments;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemEquippable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemFireworks;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemInstrument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemJukeboxPlayable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemKineticWeapon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemLock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemLore;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemMapDecorations;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemMapPostProcessingState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemMinimumAttackCharge;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemMobVisibility;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemModel;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemPiercingWeapon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemPotionContents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemPotionDurationScale;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProvidesBannerPatterns;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProvidesTrimMaterial;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemRarity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemRecipes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemRepairable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemSwingAnimation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemTool;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemTooltipDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemTooltipStyle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemUnbreakable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemUseCooldown;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemUseEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemUseRemainder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemVillagerFood;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemWaxed;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemWeapon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.LodestoneTracker;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.PotDecorations;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.SuspiciousStewEffects;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.WritableBookContent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.WrittenBookContent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.instrument.Instrument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Dummy;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public final class ComponentTypes {

    private static final VersionedRegistry<ComponentType<?>> REGISTRY =
            new VersionedRegistry<>("data_component_type");

    public static final ComponentType<NBTCompound> CUSTOM_DATA =
            define("custom_data", CustomData::read, CustomData::write);

    public static final ComponentType<Integer> MAX_STACK_SIZE =
            define("max_stack_size", PacketWrapper::readVarInt, PacketWrapper::writeVarInt);

    public static final ComponentType<Integer> MAX_DAMAGE =
            define("max_damage", PacketWrapper::readVarInt, PacketWrapper::writeVarInt);

    public static final ComponentType<Integer> DAMAGE =
            define("damage", PacketWrapper::readVarInt, PacketWrapper::writeVarInt);

    public static final ComponentType<ItemUnbreakable> UNBREAKABLE_MODERN =
            define("unbreakable", ItemUnbreakable::read, ItemUnbreakable::write);

    @Deprecated
    public static final ComponentType<Boolean> UNBREAKABLE =
            UNBREAKABLE_MODERN.legacyMap(ItemUnbreakable::isShowInTooltip, ItemUnbreakable::new);

    public static final ComponentType<Component> CUSTOM_NAME =
            define("custom_name", PacketWrapper::readComponent, PacketWrapper::writeComponent);

    public static final ComponentType<Component> ITEM_NAME =
            define("item_name", PacketWrapper::readComponent, PacketWrapper::writeComponent);

    public static final ComponentType<ItemLore> LORE =
            define("lore", ItemLore::read, ItemLore::write);

    public static final ComponentType<ItemRarity> RARITY =
            define(
                    "rarity",
                    result -> result.readEnum(ItemRarity.values()),
                    PacketWrapper::writeEnum);

    public static final ComponentType<ItemEnchantments> ENCHANTMENTS =
            define("enchantments", ItemEnchantments::read, ItemEnchantments::write);

    public static final ComponentType<ItemAdventurePredicate> CAN_PLACE_ON =
            define("can_place_on", ItemAdventurePredicate::read, ItemAdventurePredicate::write);

    public static final ComponentType<ItemAdventurePredicate> CAN_BREAK =
            define("can_break", ItemAdventurePredicate::read, ItemAdventurePredicate::write);

    public static final ComponentType<ItemAttributeModifiers> ATTRIBUTE_MODIFIERS =
            define(
                    "attribute_modifiers",
                    ItemAttributeModifiers::read,
                    ItemAttributeModifiers::write);

    public static final ComponentType<ItemCustomModelData> CUSTOM_MODEL_DATA_LISTS =
            define("custom_model_data", ItemCustomModelData::read, ItemCustomModelData::write);

    @Deprecated
    public static final ComponentType<Integer> CUSTOM_MODEL_DATA =
            CUSTOM_MODEL_DATA_LISTS.legacyMap(
                    ItemCustomModelData::getLegacyId, ItemCustomModelData::new);

    @Obsolete
    public static final ComponentType<Dummy> HIDE_ADDITIONAL_TOOLTIP =
            define("hide_additional_tooltip", Dummy::dummyRead, Dummy::dummyWrite);

    @Obsolete
    public static final ComponentType<Dummy> HIDE_TOOLTIP =
            define("hide_tooltip", Dummy::dummyRead, Dummy::dummyWrite);

    public static final ComponentType<Integer> REPAIR_COST =
            define("repair_cost", PacketWrapper::readVarInt, PacketWrapper::writeVarInt);

    public static final ComponentType<Dummy> CREATIVE_SLOT_LOCK =
            define("creative_slot_lock", Dummy::dummyRead, Dummy::dummyWrite);

    public static final ComponentType<Boolean> ENCHANTMENT_GLINT_OVERRIDE =
            define(
                    "enchantment_glint_override",
                    PacketWrapper::readBoolean,
                    PacketWrapper::writeBoolean);

    public static final ComponentType<Dummy> INTANGIBLE_PROJECTILE =
            define("intangible_projectile", Dummy::dummyReadNbt, Dummy::dummyWriteNbt);

    public static final ComponentType<FoodProperties> FOOD =
            define("food", FoodProperties::read, FoodProperties::write);

    @Obsolete
    public static final ComponentType<Dummy> FIRE_RESISTANT =
            define("fire_resistant", Dummy::dummyRead, Dummy::dummyWrite);

    public static final ComponentType<ItemTool> TOOL =
            define("tool", ItemTool::read, ItemTool::write);

    public static final ComponentType<ItemEnchantments> STORED_ENCHANTMENTS =
            define("stored_enchantments", ItemEnchantments::read, ItemEnchantments::write);

    public static final ComponentType<ItemDyeColor> DYED_COLOR =
            define("dyed_color", ItemDyeColor::read, ItemDyeColor::write);

    @Obsolete
    public static final ComponentType<Integer> MAP_COLOR =
            define("map_color", PacketWrapper::readInt, PacketWrapper::writeInt);

    public static final ComponentType<Integer> MAP_ID =
            define("map_id", PacketWrapper::readVarInt, PacketWrapper::writeVarInt);

    public static final ComponentType<ItemMapDecorations> MAP_DECORATIONS =
            define("map_decorations", ItemMapDecorations::read, ItemMapDecorations::write);

    public static final ComponentType<ItemMapPostProcessingState> MAP_POST_PROCESSING =
            define(
                    "map_post_processing",
                    result -> result.readEnum(ItemMapPostProcessingState.values()),
                    PacketWrapper::writeEnum);

    public static final ComponentType<ChargedProjectiles> CHARGED_PROJECTILES =
            define("charged_projectiles", ChargedProjectiles::read, ChargedProjectiles::write);

    public static final ComponentType<BundleContents> BUNDLE_CONTENTS =
            define("bundle_contents", BundleContents::read, BundleContents::write);

    public static final ComponentType<ItemPotionContents> POTION_CONTENTS =
            define("potion_contents", ItemPotionContents::read, ItemPotionContents::write);

    public static final ComponentType<SuspiciousStewEffects> SUSPICIOUS_STEW_EFFECTS =
            define(
                    "suspicious_stew_effects",
                    SuspiciousStewEffects::read,
                    SuspiciousStewEffects::write);

    public static final ComponentType<WritableBookContent> WRITABLE_BOOK_CONTENT =
            define("writable_book_content", WritableBookContent::read, WritableBookContent::write);

    public static final ComponentType<WrittenBookContent> WRITTEN_BOOK_CONTENT =
            define("written_book_content", WrittenBookContent::read, WrittenBookContent::write);

    public static final ComponentType<ArmorTrim> TRIM =
            define("trim", ArmorTrim::read, ArmorTrim::write);

    public static final ComponentType<DebugStickState> DEBUG_STICK_STATE =
            define("debug_stick_state", DebugStickState::read, DebugStickState::write);

    public static final ComponentType<TypedEntityData> TYPED_ENTITY_DATA =
            define("entity_data", TypedEntityData::read, TypedEntityData::write);

    @Deprecated
    public static final ComponentType<NBTCompound> ENTITY_DATA =
            TYPED_ENTITY_DATA.legacyMap(TypedEntityData::getCompound, TypedEntityData::new);

    public static final ComponentType<NBTCompound> BUCKET_ENTITY_DATA =
            define("bucket_entity_data", PacketWrapper::readNBT, PacketWrapper::writeNBT);

    public static final ComponentType<TypedBlockEntityData> TYPED_BLOCK_ENTITY_DATA =
            define("block_entity_data", TypedBlockEntityData::read, TypedBlockEntityData::write);

    @Deprecated
    public static final ComponentType<NBTCompound> BLOCK_ENTITY_DATA =
            TYPED_BLOCK_ENTITY_DATA.legacyMap(
                    TypedBlockEntityData::getCompound, TypedBlockEntityData::new);

    public static final ComponentType<ItemInstrument> ITEM_INSTRUMENT =
            define("instrument", ItemInstrument::read, ItemInstrument::write);

    @Deprecated
    public static final ComponentType<Instrument> INSTRUMENT =
            ITEM_INSTRUMENT.legacyMap(
                    result -> result.getInstrument().getValue(), ItemInstrument::new);

    public static final ComponentType<Integer> OMINOUS_BOTTLE_AMPLIFIER =
            define(
                    "ominous_bottle_amplifier",
                    PacketWrapper::readVarInt,
                    PacketWrapper::writeVarInt);

    public static final ComponentType<ItemRecipes> RECIPES =
            define("recipes", ItemRecipes::read, ItemRecipes::write);

    public static final ComponentType<LodestoneTracker> LODESTONE_TRACKER =
            define("lodestone_tracker", LodestoneTracker::read, LodestoneTracker::write);

    public static final ComponentType<FireworkExplosion> FIREWORK_EXPLOSION =
            define("firework_explosion", FireworkExplosion::read, FireworkExplosion::write);

    public static final ComponentType<ItemFireworks> FIREWORKS =
            define("fireworks", ItemFireworks::read, ItemFireworks::write);

    public static final ComponentType<ItemProfile> PROFILE =
            define("profile", ItemProfile::read, ItemProfile::write);

    public static final ComponentType<ResourceLocation> NOTE_BLOCK_SOUND =
            define(
                    "note_block_sound",
                    PacketWrapper::readIdentifier,
                    PacketWrapper::writeIdentifier);

    public static final ComponentType<BannerLayers> BANNER_PATTERNS =
            define("banner_patterns", BannerLayers::read, BannerLayers::write);

    public static final ComponentType<DyeColor> BASE_COLOR =
            define(
                    "base_color",
                    result -> result.readEnum(DyeColor.values()),
                    PacketWrapper::writeEnum);

    public static final ComponentType<PotDecorations> POT_DECORATIONS =
            define("pot_decorations", PotDecorations::read, PotDecorations::write);

    public static final ComponentType<ItemContainerContents> CONTAINER =
            define("container", ItemContainerContents::read, ItemContainerContents::write);

    public static final ComponentType<ItemBlockStateProperties> BLOCK_STATE =
            define("block_state", ItemBlockStateProperties::read, ItemBlockStateProperties::write);

    public static final ComponentType<ItemBees> BEES =
            define("bees", ItemBees::read, ItemBees::write);

    public static final ComponentType<ItemLock> LOCK =
            define("lock", ItemLock::read, ItemLock::write);

    public static final ComponentType<ItemContainerLoot> CONTAINER_LOOT =
            define("container_loot", ItemContainerLoot::read, ItemContainerLoot::write);

    public static final ComponentType<ItemJukeboxPlayable> JUKEBOX_PLAYABLE =
            define("jukebox_playable", ItemJukeboxPlayable::read, ItemJukeboxPlayable::write);

    public static final ComponentType<ItemConsumable> CONSUMABLE =
            define("consumable", ItemConsumable::read, ItemConsumable::write);

    public static final ComponentType<ItemUseRemainder> USE_REMAINDER =
            define("use_remainder", ItemUseRemainder::read, ItemUseRemainder::write);

    public static final ComponentType<ItemUseCooldown> USE_COOLDOWN =
            define("use_cooldown", ItemUseCooldown::read, ItemUseCooldown::write);

    public static final ComponentType<ItemEnchantable> ENCHANTABLE =
            define("enchantable", ItemEnchantable::read, ItemEnchantable::write);

    public static final ComponentType<ItemRepairable> REPAIRABLE =
            define("repairable", ItemRepairable::read, ItemRepairable::write);

    public static final ComponentType<ItemModel> ITEM_MODEL =
            define("item_model", ItemModel::read, ItemModel::write);

    public static final ComponentType<ItemDamageResistant> DAMAGE_RESISTANT =
            define("damage_resistant", ItemDamageResistant::read, ItemDamageResistant::write);

    public static final ComponentType<ItemEquippable> EQUIPPABLE =
            define("equippable", ItemEquippable::read, ItemEquippable::write);

    public static final ComponentType<Dummy> GLIDER =
            define("glider", Dummy::dummyRead, Dummy::dummyWrite);

    public static final ComponentType<ItemDeathProtection> DEATH_PROTECTION =
            define("death_protection", ItemDeathProtection::read, ItemDeathProtection::write);

    public static final ComponentType<ItemTooltipStyle> TOOLTIP_STYLE =
            define("tooltip_style", ItemTooltipStyle::read, ItemTooltipStyle::write);

    public static final ComponentType<ItemTooltipDisplay> TOOLTIP_DISPLAY =
            define("tooltip_display", ItemTooltipDisplay::read, ItemTooltipDisplay::write);

    public static final ComponentType<ItemWeapon> WEAPON =
            define("weapon", ItemWeapon::read, ItemWeapon::write);

    public static final ComponentType<ItemBlocksAttacks> BLOCKS_ATTACKS =
            define("blocks_attacks", ItemBlocksAttacks::read, ItemBlocksAttacks::write);

    public static final ComponentType<ItemPotionDurationScale> POTION_DURATION_SCALE =
            define(
                    "potion_duration_scale",
                    ItemPotionDurationScale::read,
                    ItemPotionDurationScale::write);

    public static final ComponentType<ItemProvidesTrimMaterial> PROVIDES_TRIM_MATERIAL =
            define(
                    "provides_trim_material",
                    ItemProvidesTrimMaterial::read,
                    ItemProvidesTrimMaterial::write);

    public static final ComponentType<ItemProvidesBannerPatterns> PROVIDES_BANNER_PATTERNS =
            define(
                    "provides_banner_patterns",
                    ItemProvidesBannerPatterns::read,
                    ItemProvidesBannerPatterns::write);

    public static final ComponentType<ItemBreakSound> BREAK_SOUND =
            define("break_sound", ItemBreakSound::read, ItemBreakSound::write);

    public static final ComponentType<VillagerVariantComponent> VILLAGER_VARIANT =
            define(
                    "villager/variant",
                    VillagerVariantComponent::read,
                    VillagerVariantComponent::write);

    public static final ComponentType<WolfVariantComponent> WOLF_VARIANT =
            define("wolf/variant", WolfVariantComponent::read, WolfVariantComponent::write);

    public static final ComponentType<WolfSoundVariantComponent> WOLF_SOUND_VARIANT =
            define(
                    "wolf/sound_variant",
                    WolfSoundVariantComponent::read,
                    WolfSoundVariantComponent::write);

    public static final ComponentType<WolfCollarComponent> WOLF_COLLAR =
            define("wolf/collar", WolfCollarComponent::read, WolfCollarComponent::write);

    public static final ComponentType<FoxVariantComponent> FOX_VARIANT =
            define("fox/variant", FoxVariantComponent::read, FoxVariantComponent::write);

    public static final ComponentType<SalmonSizeComponent> SALMON_SIZE =
            define("salmon/size", SalmonSizeComponent::read, SalmonSizeComponent::write);

    public static final ComponentType<ParrotVariantComponent> PARROT_VARIANT =
            define("parrot/variant", ParrotVariantComponent::read, ParrotVariantComponent::write);

    public static final ComponentType<TropicalFishPatternComponent> TROPICAL_FISH_PATTERN =
            define(
                    "tropical_fish/pattern",
                    TropicalFishPatternComponent::read,
                    TropicalFishPatternComponent::write);

    public static final ComponentType<TropicalFishBaseColorComponent> TROPICAL_FISH_BASE_COLOR =
            define(
                    "tropical_fish/base_color",
                    TropicalFishBaseColorComponent::read,
                    TropicalFishBaseColorComponent::write);

    public static final ComponentType<TropicalFishPatternColorComponent>
            TROPICAL_FISH_PATTERN_COLOR =
                    define(
                            "tropical_fish/pattern_color",
                            TropicalFishPatternColorComponent::read,
                            TropicalFishPatternColorComponent::write);

    public static final ComponentType<MooshroomVariantComponent> MOOSHROOM_VARIANT =
            define(
                    "mooshroom/variant",
                    MooshroomVariantComponent::read,
                    MooshroomVariantComponent::write);

    public static final ComponentType<RabbitVariantComponent> RABBIT_VARIANT =
            define("rabbit/variant", RabbitVariantComponent::read, RabbitVariantComponent::write);

    public static final ComponentType<PigVariantComponent> PIG_VARIANT =
            define("pig/variant", PigVariantComponent::read, PigVariantComponent::write);

    public static final ComponentType<CowVariantComponent> COW_VARIANT =
            define("cow/variant", CowVariantComponent::read, CowVariantComponent::write);

    public static final ComponentType<ChickenVariantComponent> CHICKEN_VARIANT =
            define(
                    "chicken/variant",
                    ChickenVariantComponent::read,
                    ChickenVariantComponent::write);

    public static final ComponentType<FrogVariantComponent> FROG_VARIANT =
            define("frog/variant", FrogVariantComponent::read, FrogVariantComponent::write);

    public static final ComponentType<HorseVariantComponent> HORSE_VARIANT =
            define("horse/variant", HorseVariantComponent::read, HorseVariantComponent::write);

    public static final ComponentType<PaintingVariantComponent> PAINTING_VARIANT =
            define(
                    "painting/variant",
                    PaintingVariantComponent::read,
                    PaintingVariantComponent::write);

    public static final ComponentType<LlamaVariantComponent> LLAMA_VARIANT =
            define("llama/variant", LlamaVariantComponent::read, LlamaVariantComponent::write);

    public static final ComponentType<AxolotlVariantComponent> AXOLOTL_VARIANT =
            define(
                    "axolotl/variant",
                    AxolotlVariantComponent::read,
                    AxolotlVariantComponent::write);

    public static final ComponentType<CatVariantComponent> CAT_VARIANT =
            define("cat/variant", CatVariantComponent::read, CatVariantComponent::write);

    public static final ComponentType<CatCollarComponent> CAT_COLLAR =
            define("cat/collar", CatCollarComponent::read, CatCollarComponent::write);

    public static final ComponentType<SheepColorComponent> SHEEP_COLOR =
            define("sheep/color", SheepColorComponent::read, SheepColorComponent::write);

    public static final ComponentType<ShulkerColorComponent> SHULKER_COLOR =
            define("shulker/color", ShulkerColorComponent::read, ShulkerColorComponent::write);

    public static final ComponentType<ItemUseEffects> USE_EFFECTS =
            define("use_effects", ItemUseEffects::read, ItemUseEffects::write);

    public static final ComponentType<ItemMinimumAttackCharge> MINIMUM_ATTACK_CHARGE =
            define(
                    "minimum_attack_charge",
                    ItemMinimumAttackCharge::read,
                    ItemMinimumAttackCharge::write);

    public static final ComponentType<ItemDamageType> DAMAGE_TYPE =
            define("damage_type", ItemDamageType::read, ItemDamageType::write);

    public static final ComponentType<ItemAttackRange> ATTACK_RANGE =
            define("attack_range", ItemAttackRange::read, ItemAttackRange::write);

    public static final ComponentType<ItemPiercingWeapon> PIERCING_WEAPON =
            define("piercing_weapon", ItemPiercingWeapon::read, ItemPiercingWeapon::write);

    public static final ComponentType<ItemKineticWeapon> KINETIC_WEAPON =
            define("kinetic_weapon", ItemKineticWeapon::read, ItemKineticWeapon::write);

    @Obsolete
    public static final ComponentType<ItemSwingAnimation> SWING_ANIMATION =
            define("swing_animation", ItemSwingAnimation::read, ItemSwingAnimation::write);

    public static final ComponentType<ZombieNautilusVariantComponent> ZOMBIE_NAUTILUS_VARIANT =
            define(
                    "zombie_nautilus/variant",
                    ZombieNautilusVariantComponent::read,
                    ZombieNautilusVariantComponent::write);

    public static final ComponentType<DyeColor> DYE =
            define("dye", DyeColor::read, DyeColor::write);

    public static final ComponentType<ItemAdditionalTradeCost> ADDITIONAL_TRADE_COST =
            define(
                    "additional_trade_cost",
                    ItemAdditionalTradeCost::read,
                    ItemAdditionalTradeCost::write);

    public static final ComponentType<CatSoundVariantComponent> CAT_SOUND_VARIANT =
            define(
                    "cat/sound_variant",
                    CatSoundVariantComponent::read,
                    CatSoundVariantComponent::write);

    public static final ComponentType<ChickenSoundVariantComponent> CHICKEN_SOUND_VARIANT =
            define(
                    "chicken/sound_variant",
                    ChickenSoundVariantComponent::read,
                    ChickenSoundVariantComponent::write);

    public static final ComponentType<CowSoundVariantComponent> COW_SOUND_VARIANT =
            define(
                    "cow/sound_variant",
                    CowSoundVariantComponent::read,
                    CowSoundVariantComponent::write);

    public static final ComponentType<PigSoundVariantComponent> PIG_SOUND_VARIANT =
            define(
                    "pig/sound_variant",
                    PigSoundVariantComponent::read,
                    PigSoundVariantComponent::write);

    public static final ComponentType<SulfurCubeContentComponent> SULFUR_CUBE_CONTENT =
            define(
                    "sulfur_cube_content",
                    SulfurCubeContentComponent::read,
                    SulfurCubeContentComponent::write);

    public static final ComponentType<ItemSwingAnimation> ATTACK_ANIMATION =
            define("attack_animation", ItemSwingAnimation::read, ItemSwingAnimation::write);

    public static final ComponentType<ItemSwingAnimation> INTERACT_ANIMATION =
            define("interact_animation", ItemSwingAnimation::read, ItemSwingAnimation::write);

    public static final ComponentType<BlockTransformerComponent> BLOCK_TRANSFORMER =
            define(
                    "block_transformer",
                    BlockTransformerComponent::read,
                    BlockTransformerComponent::write);

    public static final ComponentType<ItemVillagerFood> VILLAGER_FOOD =
            define("villager_food", ItemVillagerFood::read, ItemVillagerFood::write);

    public static final ComponentType<ItemCompostable> COMPOSTABLE =
            define("compostable", ItemCompostable::read, ItemCompostable::write);

    public static final ComponentType<ItemCookingFuel> COOKING_FUEL =
            define("cooking_fuel", ItemCookingFuel::read, ItemCookingFuel::write);

    public static final ComponentType<ItemBrewingFuel> BREWING_FUEL =
            define("brewing_fuel", ItemBrewingFuel::read, ItemBrewingFuel::write);

    public static final ComponentType<ItemMobVisibility> MOB_VISIBILITY =
            define("mob_visibility", ItemMobVisibility::read, ItemMobVisibility::write);

    public static final ComponentType<PotteryPatternComponent> PROVIDES_POTTERY_PATTERN =
            define(
                    "provides_pottery_pattern",
                    PotteryPatternComponent::read,
                    PotteryPatternComponent::write);

    public static final ComponentType<SignTextComponent> SIGN_TEXT_FRONT =
            define("sign_text_front", SignTextComponent::read, SignTextComponent::write);

    public static final ComponentType<SignTextComponent> SIGN_TEXT_BACK =
            define("sign_text_back", SignTextComponent::read, SignTextComponent::write);

    public static final ComponentType<ItemWaxed> WAXED =
            define("waxed", ItemWaxed::read, ItemWaxed::write);

    public static final ComponentType<CushionColorComponent> CUSHION_COLOR =
            define("cushion/color", CushionColorComponent::read, CushionColorComponent::write);

    private ComponentTypes() {}

    @Internal
    public static <T> ComponentType<T> define(String text) {
        return define(text, null, null);
    }

    @Internal
    public static <T> ComponentType<T> define(
            String text,
            @Nullable PacketWrapper.Reader<T> reader,
            @Nullable PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticComponentType<>(
                                result,
                                ((PacketWrapper.Reader) reader),
                                ((PacketWrapper.Writer) writer)));
    }

    public static VersionedRegistry<ComponentType<?>> getRegistry() {
        return REGISTRY;
    }

    public static ComponentType<?> getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static ComponentType<?> getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<ComponentType<?>> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
