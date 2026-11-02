package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.painting;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticPaintingVariant extends AbstractMappedEntity implements PaintingVariant {

    private final int width;

    private final int height;

    private final ResourceLocation assetId;

    @Nullable private final Component title;

    @Nullable private final Component author;

    public StaticPaintingVariant(int value, int value2, ResourceLocation resourceLocation) {
        this(null, value, value2, resourceLocation, null, null);
    }

    public StaticPaintingVariant(
            int value,
            int value2,
            ResourceLocation resourceLocation,
            @Nullable Component component,
            @Nullable Component component2) {
        this(null, value, value2, resourceLocation, component, component2);
    }

    @Internal
    public StaticPaintingVariant(
            @Nullable TypesBuilderData typesBuilderData,
            int width,
            int height,
            ResourceLocation resourceLocation,
            @Nullable Component component,
            @Nullable Component component2) {
        super(typesBuilderData);
        this.width = width;
        this.height = height;
        this.assetId = resourceLocation;
        this.title = component;
        this.author = component2;
    }

    public PaintingVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticPaintingVariant(
                typesBuilderData, this.width, this.height, this.assetId, this.title, this.author);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    @Nullable
    public Component getTitle() {
        return this.title;
    }

    @Override
    @Nullable
    public Component getAuthor() {
        return this.author;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticPaintingVariant)) {
            return false;
        } else {
            StaticPaintingVariant staticPaintingVariant = (StaticPaintingVariant) result;
            if (this.width != staticPaintingVariant.width) {
                return false;
            } else if (this.height != staticPaintingVariant.height) {
                return false;
            } else if (!this.assetId.equals(staticPaintingVariant.assetId)) {
                return false;
            } else {
                return !Objects.equals(this.title, staticPaintingVariant.title)
                        ? false
                        : Objects.equals(this.author, staticPaintingVariant.author);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.width, this.height, this.assetId, this.title, this.author);
    }

    @Override
    public String toString() {
        return "StaticPaintingVariant{width="
                + this.width
                + ", height="
                + this.height
                + ", assetId="
                + this.assetId
                + ", title="
                + this.title
                + ", author="
                + this.author
                + -1609769934;
    }
}
