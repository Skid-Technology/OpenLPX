package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimpattern;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticTrimPattern extends AbstractMappedEntity implements TrimPattern {

    private final ResourceLocation assetId;

    @Nullable private final ItemType templateItem;

    private final Component description;

    private final boolean decal;

    public StaticTrimPattern(
            ResourceLocation resourceLocation,
            @Nullable ItemType itemType,
            Component component,
            boolean matches) {
        this(null, resourceLocation, itemType, component, matches);
    }

    @Internal
    public StaticTrimPattern(
            @Nullable TypesBuilderData typesBuilderData,
            ResourceLocation resourceLocation,
            @Nullable ItemType itemType,
            Component component,
            boolean decal) {
        super(typesBuilderData);
        this.assetId = resourceLocation;
        this.templateItem = itemType;
        this.description = component;
        this.decal = decal;
    }

    public TrimPattern copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticTrimPattern(
                typesBuilderData, this.assetId, this.templateItem, this.description, this.decal);
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Obsolete
    @Override
    public ItemType getTemplateItem() {
        return this.templateItem != null ? this.templateItem : ItemTypes.AIR;
    }

    @Override
    public Component getDescription() {
        return this.description;
    }

    @Override
    public boolean isDecal() {
        return this.decal;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticTrimPattern)) {
            return false;
        } else {
            StaticTrimPattern staticTrimPattern = (StaticTrimPattern) result;
            if (this.decal != staticTrimPattern.decal) {
                return false;
            } else if (!this.assetId.equals(staticTrimPattern.assetId)) {
                return false;
            } else {
                return !Objects.equals(this.templateItem, staticTrimPattern.templateItem)
                        ? false
                        : this.description.equals(staticTrimPattern.description);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.assetId, this.templateItem, this.description, this.decal);
    }

    @Override
    public String toString() {
        return "StaticTrimPattern{assetId="
                + this.assetId
                + ", templateItem="
                + this.templateItem
                + ", description="
                + this.description
                + ", decal="
                + this.decal
                + 1198948204;
    }
}
