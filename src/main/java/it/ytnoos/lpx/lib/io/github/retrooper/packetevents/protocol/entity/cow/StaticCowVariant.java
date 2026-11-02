package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticCowVariant extends AbstractMappedEntity implements CowVariant {

    private final CowVariant.ModelType modelType;

    private final ResourceLocation assetId;

    private final ResourceLocation babyAssetId;

    @Obsolete
    public StaticCowVariant(CowVariant.ModelType modelType, ResourceLocation resourceLocation) {
        this(null, modelType, resourceLocation, resourceLocation);
    }

    public StaticCowVariant(
            CowVariant.ModelType modelType,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        this(null, modelType, resourceLocation, resourceLocation2);
    }

    @Internal
    public StaticCowVariant(
            @Nullable TypesBuilderData typesBuilderData,
            CowVariant.ModelType modelType2,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        super(typesBuilderData);
        this.modelType = modelType2;
        this.assetId = resourceLocation;
        this.babyAssetId = resourceLocation2;
    }

    public CowVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticCowVariant(
                typesBuilderData, this.modelType, this.assetId, this.babyAssetId);
    }

    @Override
    public CowVariant.ModelType getModelType() {
        return this.modelType;
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
        if (!(result instanceof StaticCowVariant)) {
            return false;
        } else {
            StaticCowVariant staticCowVariant = (StaticCowVariant) result;
            if (!this.modelType.equals(staticCowVariant.modelType)) {
                return false;
            } else {
                return !this.assetId.equals(staticCowVariant.assetId)
                        ? false
                        : this.babyAssetId.equals(staticCowVariant.babyAssetId);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.modelType, this.assetId, this.babyAssetId);
    }
}
