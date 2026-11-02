package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimmaterial;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial.ArmorMaterial;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.kyori.adventure.key.KeyPattern;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@NullMarked
public class StaticTrimMaterial extends AbstractMappedEntity implements TrimMaterial {

    private final String assetName;

    private final ResourceLocation paletteId;

    @Nullable private final ItemType ingredient;

    private final float itemModelIndex;

    private final Map<ArmorMaterial, String> overrideArmorMaterials;

    private final Component description;

    public StaticTrimMaterial(
            @KeyPattern.Value String text,
            @Nullable ItemType itemType,
            Map<ArmorMaterial, String> entries,
            Component component) {
        this(
                null,
                text,
                ResourceLocation.minecraft("trim/" + text),
                itemType,
                0.0F,
                entries,
                component);
    }

    public StaticTrimMaterial(
            @KeyPattern.Value String text,
            @Nullable ItemType itemType,
            float value,
            Map<ArmorMaterial, String> entries,
            Component component) {
        this(
                null,
                text,
                ResourceLocation.minecraft("trim/" + text),
                itemType,
                value,
                entries,
                component);
    }

    public StaticTrimMaterial(ResourceLocation resourceLocation, Component component) {
        this(
                null,
                resourceLocation.getKey(),
                resourceLocation,
                null,
                0.0F,
                Collections.emptyMap(),
                component);
    }

    @Internal
    public StaticTrimMaterial(
            @Nullable TypesBuilderData typesBuilderData,
            String assetName,
            ResourceLocation resourceLocation,
            @Nullable ItemType itemType,
            float itemModelIndex,
            Map<ArmorMaterial, String> entries,
            Component component) {
        super(typesBuilderData);
        this.assetName = assetName;
        this.paletteId = resourceLocation;
        this.ingredient = itemType;
        this.itemModelIndex = itemModelIndex;
        this.overrideArmorMaterials = entries;
        this.description = component;
    }

    public TrimMaterial copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticTrimMaterial(
                typesBuilderData,
                this.assetName,
                this.paletteId,
                this.ingredient,
                this.itemModelIndex,
                this.overrideArmorMaterials,
                this.description);
    }

    @Override
    public String getAssetName() {
        return this.assetName;
    }

    @Override
    public ResourceLocation getPaletteId() {
        return this.paletteId;
    }

    @Obsolete
    @Override
    public ItemType getIngredient() {
        return this.ingredient != null ? this.ingredient : ItemTypes.AIR;
    }

    @Override
    public float getItemModelIndex() {
        return this.itemModelIndex;
    }

    @Override
    public Map<ArmorMaterial, String> getOverrideArmorMaterials() {
        return this.overrideArmorMaterials;
    }

    @Override
    public Component getDescription() {
        return this.description;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticTrimMaterial)) {
            return false;
        } else {
            StaticTrimMaterial staticTrimMaterial;
            if (Float.compare(
                            (staticTrimMaterial = (StaticTrimMaterial) result).itemModelIndex,
                            this.itemModelIndex)
                    != 0) {
                return false;
            } else if (!this.assetName.equals(staticTrimMaterial.assetName)) {
                return false;
            } else if (!this.paletteId.equals(staticTrimMaterial.paletteId)) {
                return false;
            } else if (!Objects.equals(this.ingredient, staticTrimMaterial.ingredient)) {
                return false;
            } else {
                return !this.overrideArmorMaterials.equals(
                                staticTrimMaterial.overrideArmorMaterials)
                        ? false
                        : this.description.equals(staticTrimMaterial.description);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.assetName,
                this.paletteId,
                this.ingredient,
                this.itemModelIndex,
                this.overrideArmorMaterials,
                this.description);
    }

    @Override
    public String toString() {
        return "StaticTrimMaterial{assetName='"
                + this.assetName
                + '\''
                + ", paletteId='"
                + this.paletteId
                + '\''
                + ", ingredient="
                + this.ingredient
                + ", itemModelIndex="
                + this.itemModelIndex
                + ", overrideArmorMaterials="
                + this.overrideArmorMaterials
                + ", description="
                + this.description
                + '}';
    }
}
