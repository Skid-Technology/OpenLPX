package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.Attribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeEntry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityBuilder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.defaulttags.ItemTags;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.TagKey;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public final class SulfurCubeArchtypeBuilder implements MappedEntityBuilder<SulfurCubeArchtype> {

    private static final MappedEntitySet<ItemType> REGULAR_ITEMS =
            new MappedEntitySet<>(
                    new ResourceLocation(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR.getName()));

    private static final SulfurCubeKnockbackModifiers REGULAR_KNOCKBACK_MODIFIERS =
            new SulfurCubeKnockbackModifiers(0.4125F, 0.09F);

    private static final SulfurCubeSoundSettings REGULAR_SOUND_SETTINGS =
            new SulfurCubeSoundSettings(
                    Sounds.ENTITY_SULFUR_CUBE_REGULAR_HIT,
                    Sounds.ENTITY_SULFUR_CUBE_REGULAR_PUSH,
                    0.2F,
                    0.5F);

    private MappedEntitySet<ItemType> items = REGULAR_ITEMS;

    private List<AttributeEntry> attributeModifiers = new ArrayList<>();

    private boolean buoyant = false;

    @Nullable private SulfurCubeExplosionData explosion = null;

    @Nullable private SulfurCubeContactDamage contactDamage = null;

    private SulfurCubeKnockbackModifiers knockbackModifiers = REGULAR_KNOCKBACK_MODIFIERS;

    private SulfurCubeSoundSettings soundSettings = REGULAR_SOUND_SETTINGS;

    private SulfurCubeArchtypeBuilder() {}

    public static SulfurCubeArchtypeBuilder sulfurCubeArchtypeBuilder() {
        return new SulfurCubeArchtypeBuilder();
    }

    @Internal
    public SulfurCubeArchtype build(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticSulfurCubeArchtype(
                typesBuilderData,
                this.items,
                this.attributeModifiers,
                this.buoyant,
                this.explosion,
                this.contactDamage,
                this.knockbackModifiers,
                this.soundSettings);
    }

    public MappedEntitySet<ItemType> getItems() {
        return this.items;
    }

    public SulfurCubeArchtypeBuilder setItems(ItemTags itemTags) {
        return this.setItems(new MappedEntitySet<>(new ResourceLocation(itemTags.getName())));
    }

    public SulfurCubeArchtypeBuilder setItems(TagKey tagKey) {
        return this.setItems(new MappedEntitySet<>(tagKey.getId()));
    }

    public SulfurCubeArchtypeBuilder setItems(MappedEntitySet<ItemType> mappedEntitySet) {
        this.items = mappedEntitySet;
        return this;
    }

    public SulfurCubeArchtypeBuilder addAttributeModifier(
            Attribute attribute, AttributeModifier attributeModifier) {
        return this.addAttributeModifier(new AttributeEntry(attribute, attributeModifier));
    }

    public SulfurCubeArchtypeBuilder addAttributeModifier(AttributeEntry attributeEntry) {
        this.attributeModifiers.add(attributeEntry);
        return this;
    }

    public List<AttributeEntry> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public SulfurCubeArchtypeBuilder setAttributeModifiers(
            List<AttributeEntry> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
        return this;
    }

    public boolean isBuoyant() {
        return this.buoyant;
    }

    public SulfurCubeArchtypeBuilder setBuoyant(boolean buoyant) {
        this.buoyant = buoyant;
        return this;
    }

    @Nullable
    public SulfurCubeExplosionData getExplosion() {
        return this.explosion;
    }

    public SulfurCubeArchtypeBuilder setExplosion(
            @Nullable SulfurCubeExplosionData sulfurCubeExplosionData) {
        this.explosion = sulfurCubeExplosionData;
        return this;
    }

    @Nullable
    public SulfurCubeContactDamage getContactDamage() {
        return this.contactDamage;
    }

    public SulfurCubeArchtypeBuilder setContactDamage(
            @Nullable SulfurCubeContactDamage sulfurCubeContactDamage) {
        this.contactDamage = sulfurCubeContactDamage;
        return this;
    }

    public SulfurCubeKnockbackModifiers getKnockbackModifiers() {
        return this.knockbackModifiers;
    }

    public SulfurCubeArchtypeBuilder setKnockbackModifiers(
            SulfurCubeKnockbackModifiers sulfurCubeKnockbackModifiers) {
        this.knockbackModifiers = sulfurCubeKnockbackModifiers;
        return this;
    }

    public SulfurCubeSoundSettings getSoundSettings() {
        return this.soundSettings;
    }

    public SulfurCubeArchtypeBuilder setSoundSettings(
            SulfurCubeSoundSettings sulfurCubeSoundSettings) {
        this.soundSettings = sulfurCubeSoundSettings;
        return this;
    }
}
