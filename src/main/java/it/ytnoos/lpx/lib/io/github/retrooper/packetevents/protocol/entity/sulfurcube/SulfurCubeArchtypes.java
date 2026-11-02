package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeEntry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeOperation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.floats.ConstantFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.defaulttags.ItemTags;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jspecify.annotations.NullMarked;

import java.util.Arrays;
import java.util.List;

@NullMarked
public final class SulfurCubeArchtypes {

    private static final VersionedRegistry<SulfurCubeArchtype> REGISTRY =
            new VersionedRegistry<>("sulfur_cube_archetype");

    public static final SulfurCubeArchtype BOUNCY =
            REGISTRY.defineWithBuilder(
                    "bouncy",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_BOUNCY)
                            .setAttributeModifiers(
                                    createAttributes("bouncy", 2.0F, 0.9F, 0.3F, 0.01F))
                            .setBuoyant(true)
                            .setKnockbackModifiers(
                                    new SulfurCubeKnockbackModifiers(0.4125F, 0.105F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_BOUNCY_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_BOUNCY_PUSH,
                                            0.3F,
                                            0.7F)));

    public static final SulfurCubeArchtype EXPLOSIVE =
            REGISTRY.defineWithBuilder(
                    "explosive",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_EXPLOSIVE)
                            .setAttributeModifiers(
                                    createAttributes("explosive", 1.0F, 0.5F, 0.3F, 0.3F))
                            .setBuoyant(true)
                            .setExplosion(new SulfurCubeExplosionData(3, false, 120))
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_EXPLOSIVE_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_EXPLOSIVE_PUSH,
                                            0.1F,
                                            0.7F)));

    public static final SulfurCubeArchtype FAST_FLAT =
            REGISTRY.defineWithBuilder(
                    "fast_flat",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_FLAT)
                            .setAttributeModifiers(
                                    createAttributes("fast_flat", 1.0F, 0.5F, 0.2F, 0.01F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.9125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_FAST_FLAT_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_FAST_FLAT_PUSH,
                                            0.03F,
                                            0.9F)));

    public static final SulfurCubeArchtype FAST_SLIDING =
            REGISTRY.defineWithBuilder(
                    "fast_sliding",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_SLIDING)
                            .setAttributeModifiers(
                                    createAttributes("fast_sliding", -0.5F, 0.1F, 0.05F, 0.01F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.6625F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_FAST_SLIDING_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_FAST_SLIDING_PUSH,
                                            0.05F,
                                            1.0F)));

    public static final SulfurCubeArchtype HIGH_RESISTANCE =
            REGISTRY.defineWithBuilder(
                    "high_resistance",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE)
                            .setAttributeModifiers(
                                    createAttributes("high_resistance", -0.7F, 0.2F, 1.0F, 0.01F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_HIGH_RESISTANCE_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_HIGH_RESISTANCE_PUSH,
                                            0.03F,
                                            0.7F)));

    public static final SulfurCubeArchtype HOT =
            REGISTRY.defineWithBuilder(
                    "hot",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_HOT)
                            .setAttributeModifiers(createAttributes("hot", 1.0F, 0.5F, 0.3F, 0.1F))
                            .setBuoyant(true)
                            .setContactDamage(
                                    new SulfurCubeContactDamage(
                                            DamageTypes.SULFUR_CUBE_HOT,
                                            new ConstantFloat(1.0F),
                                            false))
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_HOT_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_HOT_PUSH,
                                            0.2F,
                                            0.7F)));

    public static final SulfurCubeArchtype LIGHT =
            REGISTRY.defineWithBuilder(
                    "light",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_LIGHT)
                            .setAttributeModifiers(
                                    createAttributes("light", 1.0F, 1.0F, 0.3F, 1.8F))
                            .setBuoyant(true)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.18F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_LIGHT_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_LIGHT_PUSH,
                                            0.2F,
                                            0.7F)));

    public static final SulfurCubeArchtype REGULAR =
            REGISTRY.defineWithBuilder(
                    "regular",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR)
                            .setAttributeModifiers(
                                    createAttributes("regular", 1.0F, 0.5F, 0.3F, 0.1F))
                            .setBuoyant(true)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_REGULAR_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_REGULAR_PUSH,
                                            0.2F,
                                            0.5F)));

    public static final SulfurCubeArchtype SLOW_BOUNCY =
            REGISTRY.defineWithBuilder(
                    "slow_bouncy",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
                            .setAttributeModifiers(
                                    createAttributes("slow_bouncy", -0.4F, 0.6F, 0.3F, 0.05F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.24F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_SLOW_BOUNCY_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_SLOW_BOUNCY_PUSH,
                                            0.05F,
                                            0.5F)));

    public static final SulfurCubeArchtype SLOW_FLAT =
            REGISTRY.defineWithBuilder(
                    "slow_flat",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_FLAT)
                            .setAttributeModifiers(
                                    createAttributes("slow_flat", -0.5F, 0.4F, 0.4F, 0.1F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(
                                    new SulfurCubeKnockbackModifiers(0.4125F, 0.105F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_SLOW_FLAT_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_SLOW_FLAT_PUSH,
                                            0.03F,
                                            0.9F)));

    public static final SulfurCubeArchtype SLOW_SLIDING =
            REGISTRY.defineWithBuilder(
                    "slow_sliding",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING)
                            .setAttributeModifiers(
                                    createAttributes("slow_sliding", -0.8F, 0.1F, 0.05F, 0.01F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_SLOW_SLIDING_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_SLOW_SLIDING_PUSH,
                                            0.02F,
                                            1.0F)));

    public static final SulfurCubeArchtype STICKY =
            REGISTRY.defineWithBuilder(
                    "sticky",
                    SulfurCubeArchtypeBuilder.sulfurCubeArchtypeBuilder()
                            .setItems(ItemTags.SULFUR_CUBE_ARCHETYPE_STICKY)
                            .setAttributeModifiers(
                                    createAttributes("sticky", 2.0F, 0.0F, 2.0F, 0.01F))
                            .setBuoyant(false)
                            .setKnockbackModifiers(new SulfurCubeKnockbackModifiers(0.4125F, 0.09F))
                            .setSoundSettings(
                                    new SulfurCubeSoundSettings(
                                            Sounds.ENTITY_SULFUR_CUBE_STICKY_HIT,
                                            Sounds.ENTITY_SULFUR_CUBE_STICKY_PUSH,
                                            0.05F,
                                            0.5F)));

    private SulfurCubeArchtypes() {}

    private static AttributeEntry createAttributeAdd(
            String text, Attribute attribute, double value) {
        return createAttribute(text + "_add", attribute, value, AttributeOperation.ADDITION);
    }

    private static AttributeEntry createAttributeMultiply(
            String text, Attribute attribute, double value) {
        return createAttribute(
                text + "_mul", attribute, value - 1.0, AttributeOperation.MULTIPLY_TOTAL);
    }

    private static AttributeEntry createAttribute(
            String text, Attribute attribute, double value, AttributeOperation attributeOperation) {
        ResourceLocation resourceLocation =
                new ResourceLocation(text + "_" + attribute.getName().getKey());
        return new AttributeEntry(
                attribute, new AttributeModifier(resourceLocation, value, attributeOperation));
    }

    private static List<AttributeEntry> createAttributes(
            String text, float value, float value2, float value3, float value4) {
        return Arrays.asList(
                createAttributeAdd(text, Attributes.KNOCKBACK_RESISTANCE, -value),
                createAttributeAdd(text, Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, -value),
                createAttributeAdd(text, Attributes.BOUNCINESS, value2),
                createAttributeMultiply(text, Attributes.FRICTION_MODIFIER, value3),
                createAttributeMultiply(text, Attributes.AIR_DRAG_MODIFIER, value4));
    }

    public static VersionedRegistry<SulfurCubeArchtype> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
