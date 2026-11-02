package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticCatVariant extends AbstractMappedEntity implements CatVariant {

    private final ResourceLocation assetId;

    private final ResourceLocation babyAssetId;

    @Obsolete
    public StaticCatVariant(ResourceLocation resourceLocation) {
        this(null, resourceLocation, resourceLocation);
    }

    public StaticCatVariant(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        this(null, resourceLocation, resourceLocation2);
    }

    @Internal
    public StaticCatVariant(
            @Nullable TypesBuilderData typesBuilderData,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        super(typesBuilderData);
        this.assetId = resourceLocation;
        this.babyAssetId = resourceLocation2;
    }

    public CatVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticCatVariant(typesBuilderData, this.assetId, this.babyAssetId);
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    public ResourceLocation getBabyAssetId() {
        return this.babyAssetId;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticCatVariant)) {
            return false;
        }
        StaticCatVariant staticCatVariant = (StaticCatVariant) result;
        return !this.assetId.equals(staticCatVariant.assetId)
                ? false
                : this.babyAssetId.equals(staticCatVariant.babyAssetId);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.assetId, this.babyAssetId);
    }
}
