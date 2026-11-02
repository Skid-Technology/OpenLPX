package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public final class EnchantEffectComponentTypes {

    private static final VersionedRegistry<ComponentType<?>> REGISTRY =
            new VersionedRegistry<>("enchantment_effect_component_type");

    public static ComponentType<NBT> DAMAGE_PROTECTION =
            define("damage_protection", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> DAMAGE_IMMUNITY =
            define("damage_immunity", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> DAMAGE =
            define("damage", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> SMASH_DAMAGE_PER_FALLEN_BLOCK =
            define(
                    "smash_damage_per_fallen_block",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> KNOCKBACK =
            define("knockback", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> ARMOR_EFFECTIVENESS =
            define("armor_effectiveness", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> POST_ATTACK =
            define("post_attack", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> HIT_BLOCK =
            define("hit_block", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> ITEM_DAMAGE =
            define("item_damage", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> ATTRIBUTES =
            define("attributes", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> EQUIPMENT_DROPS =
            define("equipment_drops", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> LOCATION_CHANGED =
            define("location_changed", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> TICK =
            define("tick", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> AMMO_USE =
            define("ammo_use", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> PROJECTILE_PIERCING =
            define("projectile_piercing", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> PROJECTILE_SPAWNED =
            define("projectile_spawned", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> PROJECTILE_SPREAD =
            define("projectile_spread", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> PROJECTILE_COUNT =
            define("projectile_count", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> TRIDENT_RETURN_ACCELERATION =
            define(
                    "trident_return_acceleration",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> FISHING_TIME_REDUCTION =
            define(
                    "fishing_time_reduction",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> FISHING_LUCK_BONUS =
            define("fishing_luck_bonus", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> BLOCK_EXPERIENCE =
            define("block_experience", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> MOB_EXPERIENCE =
            define("mob_experience", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> REPAIR_WITH_XP =
            define("repair_with_xp", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> CROSSBOW_CHARGE_TIME =
            define(
                    "crossbow_charge_time",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> CROSSBOW_CHARGING_SOUNDS =
            define(
                    "crossbow_charging_sounds",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> TRIDENT_SOUND =
            define("trident_sound", (result, result2) -> result, (result, result2) -> result);

    public static ComponentType<NBT> PREVENT_EQUIPMENT_DROP =
            define(
                    "prevent_equipment_drop",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> PREVENT_ARMOR_CHANGE =
            define(
                    "prevent_armor_change",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> TRIDENT_SPIN_ATTACK_STRENGTH =
            define(
                    "trident_spin_attack_strength",
                    (result, result2) -> result,
                    (result, result2) -> result);

    public static ComponentType<NBT> POST_PIERCING_ATTACK =
            define(
                    "post_piercing_attack",
                    (result, result2) -> result,
                    (result, result2) -> result);

    private EnchantEffectComponentTypes() {}

    @Internal
    public static <T> ComponentType<T> define(String text) {
        return define(text, null, null);
    }

    @Internal
    public static <T> ComponentType<T> define(
            String text,
            @Nullable ComponentType.Decoder<T> decoder,
            @Nullable ComponentType.Encoder<T> encoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticComponentType<>(
                                result,
                                ((ComponentType.Decoder) decoder),
                                ((ComponentType.Encoder) encoder)));
    }

    public static VersionedRegistry<ComponentType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
