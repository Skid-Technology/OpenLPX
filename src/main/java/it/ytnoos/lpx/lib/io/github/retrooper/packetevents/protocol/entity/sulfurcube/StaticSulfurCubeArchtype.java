package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeEntry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
@Internal
final class StaticSulfurCubeArchtype extends AbstractMappedEntity implements SulfurCubeArchtype {

    private final MappedEntitySet<ItemType> items;

    private final List<AttributeEntry> attributeModifiers;

    private final boolean buoyant;

    @Nullable private final SulfurCubeExplosionData explosion;

    @Nullable private final SulfurCubeContactDamage contactDamage;

    private final SulfurCubeKnockbackModifiers knockbackModifiers;

    private final SulfurCubeSoundSettings soundSettings;

    @Internal
    public StaticSulfurCubeArchtype(
            @Nullable TypesBuilderData typesBuilderData,
            MappedEntitySet<ItemType> mappedEntitySet,
            List<AttributeEntry> values,
            boolean buoyant,
            @Nullable SulfurCubeExplosionData sulfurCubeExplosionData,
            @Nullable SulfurCubeContactDamage sulfurCubeContactDamage,
            SulfurCubeKnockbackModifiers sulfurCubeKnockbackModifiers,
            SulfurCubeSoundSettings sulfurCubeSoundSettings) {
        super(typesBuilderData);
        this.items = mappedEntitySet;
        this.attributeModifiers = Collections.unmodifiableList(values);
        this.buoyant = buoyant;
        this.explosion = sulfurCubeExplosionData;
        this.contactDamage = sulfurCubeContactDamage;
        this.knockbackModifiers = sulfurCubeKnockbackModifiers;
        this.soundSettings = sulfurCubeSoundSettings;
    }

    public SulfurCubeArchtype copy(@Nullable TypesBuilderData typesBuilderData) {
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

    @Override
    public MappedEntitySet<ItemType> getItems() {
        return this.items;
    }

    @Override
    public List<AttributeEntry> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    @Override
    public boolean isBuoyant() {
        return this.buoyant;
    }

    @Override
    @Nullable
    public SulfurCubeExplosionData getExplosion() {
        return this.explosion;
    }

    @Override
    @Nullable
    public SulfurCubeContactDamage getContactDamage() {
        return this.contactDamage;
    }

    @Override
    public SulfurCubeKnockbackModifiers getKnockbackModifiers() {
        return this.knockbackModifiers;
    }

    @Override
    public SulfurCubeSoundSettings getSoundSettings() {
        return this.soundSettings;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            StaticSulfurCubeArchtype staticSulfurCubeArchtype = (StaticSulfurCubeArchtype) result;
            if (this.buoyant != staticSulfurCubeArchtype.buoyant) {
                return false;
            } else if (!this.items.equals(staticSulfurCubeArchtype.items)) {
                return false;
            } else if (!this.attributeModifiers.equals(
                    staticSulfurCubeArchtype.attributeModifiers)) {
                return false;
            } else if (!Objects.equals(this.explosion, staticSulfurCubeArchtype.explosion)) {
                return false;
            } else if (!Objects.equals(
                    this.contactDamage, staticSulfurCubeArchtype.contactDamage)) {
                return false;
            } else {
                return !this.knockbackModifiers.equals(staticSulfurCubeArchtype.knockbackModifiers)
                        ? false
                        : this.soundSettings.equals(staticSulfurCubeArchtype.soundSettings);
            }
        } else {
            return false;
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.items,
                this.attributeModifiers,
                this.buoyant,
                this.explosion,
                this.contactDamage,
                this.knockbackModifiers,
                this.soundSettings);
    }
}
