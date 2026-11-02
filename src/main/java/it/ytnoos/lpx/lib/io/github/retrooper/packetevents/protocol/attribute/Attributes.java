package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

public final class Attributes {

    private static final VersionedRegistry<Attribute> REGISTRY =
            new VersionedRegistry<>("attribute");

    public static final Attribute ARMOR = define("armor", "generic", 0.0, 0.0, 30.0);

    public static final Attribute ARMOR_TOUGHNESS =
            define("armor_toughness", "generic", 0.0, 0.0, 20.0);

    public static final Attribute ATTACK_DAMAGE =
            define("attack_damage", "generic", 2.0, 0.0, 2048.0);

    public static final Attribute ATTACK_KNOCKBACK =
            define("attack_knockback", "generic", 0.0, 0.0, 5.0);

    public static final Attribute ATTACK_SPEED =
            define("attack_speed", "generic", 4.0, 0.0, 1024.0);

    public static final Attribute FLYING_SPEED =
            define("flying_speed", "generic", 0.4, 0.0, 1024.0);

    public static final Attribute FOLLOW_RANGE =
            define("follow_range", "generic", 32.0, 0.0, 2048.0);

    public static final Attribute KNOCKBACK_RESISTANCE =
            define("knockback_resistance", "generic", 0.0, 0.0, 1.0);

    public static final Attribute LUCK = define("luck", "generic", 0.0, -1024.0, 1024.0);

    public static final Attribute MAX_HEALTH = define("max_health", "generic", 20.0, 1.0, 1024.0);

    public static final Attribute MOVEMENT_SPEED =
            define("movement_speed", "generic", 0.7, 0.0, 1024.0);

    public static final Attribute SPAWN_REINFORCEMENTS =
            define("spawn_reinforcements", "zombie", 0.0, 0.0, 1.0);

    public static final Attribute MAX_ABSORPTION =
            define("max_absorption", "generic", 0.0, 0.0, 2048.0);

    public static final Attribute BLOCK_BREAK_SPEED =
            define("block_break_speed", "player", 1.0, 0.0, 1024.0);

    public static final Attribute BLOCK_INTERACTION_RANGE =
            define("block_interaction_range", "player", 4.5, 0.0, 64.0);

    public static final Attribute ENTITY_INTERACTION_RANGE =
            define("entity_interaction_range", "player", 3.0, 0.0, 64.0);

    public static final Attribute FALL_DAMAGE_MULTIPLIER =
            define("fall_damage_multiplier", "generic", 1.0, 0.0, 100.0);

    public static final Attribute GRAVITY = define("gravity", "generic", 0.08, -1.0, 1.0);

    public static final Attribute JUMP_STRENGTH =
            define("jump_strength", "generic", 0.42, 0.0, 32.0);

    public static final Attribute SAFE_FALL_DISTANCE =
            define("safe_fall_distance", "generic", 3.0, 0.0, 1024.0);

    public static final Attribute SCALE = define("scale", "generic", 1.0, 0.0625, 16.0);

    public static final Attribute STEP_HEIGHT = define("step_height", "generic", 0.6, 0.0, 10.0);

    public static final Attribute BURNING_TIME =
            define("burning_time", "generic", 0.0, 1.0, 1024.0);

    public static final Attribute EXPLOSION_KNOCKBACK_RESISTANCE =
            define("explosion_knockback_resistance", "generic", 0.0, 0.0, 1.0);

    public static final Attribute MINING_EFFICIENCY =
            define("mining_efficiency", "player", 0.0, 0.0, 1024.0);

    public static final Attribute MOVEMENT_EFFICIENCY =
            define("movement_efficiency", "generic", 0.0, 0.0, 1.0);

    public static final Attribute OXYGEN_BONUS =
            define("oxygen_bonus", "generic", 0.0, 0.0, 1024.0);

    public static final Attribute SNEAKING_SPEED =
            define("sneaking_speed", "player", 0.3, 0.0, 1.0);

    public static final Attribute SUBMERGED_MINING_SPEED =
            define("submerged_mining_speed", "player", 0.2, 0.0, 20.0);

    public static final Attribute SWEEPING_DAMAGE_RATIO =
            define("sweeping_damage_ratio", "player", 0.0, 0.0, 1.0);

    public static final Attribute WATER_MOVEMENT_EFFICIENCY =
            define("water_movement_efficiency", "generic", 0.0, 0.0, 1.0);

    public static final Attribute TEMPT_RANGE = define("tempt_range", null, 10.0, 0.0, 2048.0);

    public static final Attribute CAMERA_DISTANCE = define("camera_distance", null, 4.0, 0.0, 32.0);

    public static final Attribute WAYPOINT_TRANSMIT_RANGE =
            define("waypoint_transmit_range", null, 0.0, 0.0, 6.0E7);

    public static final Attribute WAYPOINT_RECEIVE_RANGE =
            define("waypoint_receive_range", null, 0.0, 0.0, 6.0E7);

    public static final Attribute AIR_DRAG_MODIFIER =
            define("air_drag_modifier", null, 1.0, 0.0, 2048.0);

    public static final Attribute BELOW_NAME_DISTANCE =
            define("below_name_distance", null, 10.0, 0.0, 512.0);

    public static final Attribute BOUNCINESS = define("bounciness", null, 0.0, 0.0, 1.0);

    public static final Attribute FRICTION_MODIFIER =
            define("friction_modifier", null, 1.0, 0.0, 2048.0);

    public static final Attribute NAME_TAG_DISTANCE =
            define("name_tag_distance", null, 64.0, 0.0, 512.0);

    @Obsolete
    public static final Attribute HORSE_JUMP_STRENGTH =
            define("horse.jump_strength", null, 0.7, 0.0, 2.0);

    @Deprecated public static final Attribute GENERIC_ARMOR = ARMOR;

    @Deprecated public static final Attribute GENERIC_ARMOR_TOUGHNESS = ARMOR_TOUGHNESS;

    @Deprecated public static final Attribute GENERIC_ATTACK_DAMAGE = ATTACK_DAMAGE;

    @Deprecated public static final Attribute GENERIC_ATTACK_KNOCKBACK = ATTACK_KNOCKBACK;

    @Deprecated public static final Attribute GENERIC_ATTACK_SPEED = ATTACK_SPEED;

    @Deprecated public static final Attribute GENERIC_FLYING_SPEED = FLYING_SPEED;

    @Deprecated public static final Attribute GENERIC_FOLLOW_RANGE = FOLLOW_RANGE;

    @Deprecated public static final Attribute GENERIC_KNOCKBACK_RESISTANCE = KNOCKBACK_RESISTANCE;

    @Deprecated public static final Attribute GENERIC_LUCK = LUCK;

    @Deprecated public static final Attribute GENERIC_MAX_HEALTH = MAX_HEALTH;

    @Deprecated public static final Attribute GENERIC_MOVEMENT_SPEED = MOVEMENT_SPEED;

    @Deprecated public static final Attribute ZOMBIE_SPAWN_REINFORCEMENTS = SPAWN_REINFORCEMENTS;

    @Deprecated public static final Attribute GENERIC_MAX_ABSORPTION = MAX_ABSORPTION;

    @Deprecated public static final Attribute PLAYER_BLOCK_BREAK_SPEED = BLOCK_BREAK_SPEED;

    @Deprecated
    public static final Attribute PLAYER_BLOCK_INTERACTION_RANGE = BLOCK_INTERACTION_RANGE;

    @Deprecated
    public static final Attribute PLAYER_ENTITY_INTERACTION_RANGE = ENTITY_INTERACTION_RANGE;

    @Deprecated
    public static final Attribute GENERIC_FALL_DAMAGE_MULTIPLIER = FALL_DAMAGE_MULTIPLIER;

    @Deprecated public static final Attribute GENERIC_GRAVITY = GRAVITY;

    @Deprecated public static final Attribute GENERIC_JUMP_STRENGTH = JUMP_STRENGTH;

    @Deprecated public static final Attribute GENERIC_SAFE_FALL_DISTANCE = SAFE_FALL_DISTANCE;

    @Deprecated public static final Attribute GENERIC_SCALE = SCALE;

    @Deprecated public static final Attribute GENERIC_STEP_HEIGHT = STEP_HEIGHT;

    @Deprecated public static final Attribute GENERIC_BURNING_TIME = BURNING_TIME;

    @Deprecated
    public static final Attribute GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE =
            EXPLOSION_KNOCKBACK_RESISTANCE;

    @Deprecated public static final Attribute PLAYER_MINING_EFFICIENCY = MINING_EFFICIENCY;

    @Deprecated public static final Attribute GENERIC_MOVEMENT_EFFICIENCY = MOVEMENT_EFFICIENCY;

    @Deprecated public static final Attribute GENERIC_OXYGEN_BONUS = OXYGEN_BONUS;

    @Deprecated public static final Attribute PLAYER_SNEAKING_SPEED = SNEAKING_SPEED;

    @Deprecated
    public static final Attribute PLAYER_SUBMERGED_MINING_SPEED = SUBMERGED_MINING_SPEED;

    @Deprecated public static final Attribute PLAYER_SWEEPING_DAMAGE_RATIO = SWEEPING_DAMAGE_RATIO;

    @Deprecated
    public static final Attribute GENERIC_WATER_MOVEMENT_EFFICIENCY = WATER_MOVEMENT_EFFICIENCY;

    private Attributes() {}

    private static Attribute define(
            String text, @Nullable String text2, double value, double value2, double value3) {
        return REGISTRY.define(
                text, result -> new StaticAttribute(result, text2, value, value2, value3));
    }

    public static VersionedRegistry<Attribute> getRegistry() {
        return REGISTRY;
    }

    public static Attribute getByName(String text) {
        String text2;
        if ((text2 = ResourceLocation.normString(text)).startsWith("minecraft:generic.")
                || text2.startsWith("minecraft:player.")
                || text2.startsWith("minecraft:zombie.")) {
            text2 = text2.substring(text2.indexOf(46) + 1);
        }
        return REGISTRY.getByName(text2);
    }

    public static Attribute getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    static {
        REGISTRY.unloadMappings();
    }
}
