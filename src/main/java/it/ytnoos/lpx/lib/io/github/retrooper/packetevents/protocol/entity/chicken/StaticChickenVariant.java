package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticChickenVariant extends AbstractMappedEntity implements ChickenVariant {

    private final ChickenVariant.ModelType modelType;

    private final ResourceLocation assetId;

    private final ResourceLocation babyAssetId;

    @Obsolete
    public StaticChickenVariant(
            ChickenVariant.ModelType modelType, ResourceLocation resourceLocation) {
        this(null, modelType, resourceLocation, resourceLocation);
    }

    public StaticChickenVariant(
            ChickenVariant.ModelType modelType,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        this(null, modelType, resourceLocation, resourceLocation2);
    }

    @Internal
    public StaticChickenVariant(
            @Nullable TypesBuilderData typesBuilderData,
            ChickenVariant.ModelType modelType2,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        super(typesBuilderData);
        this.modelType = modelType2;
        this.assetId = resourceLocation;
        this.babyAssetId = resourceLocation2;
    }

    public ChickenVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticChickenVariant(
                typesBuilderData, this.modelType, this.assetId, this.babyAssetId);
    }

    @Override
    public ChickenVariant.ModelType getModelType() {
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
        if (!(result instanceof StaticChickenVariant)) {
            return false;
        } else {
            StaticChickenVariant staticChickenVariant = (StaticChickenVariant) result;
            if (!this.modelType.equals(staticChickenVariant.modelType)) {
                return false;
            } else {
                return !this.assetId.equals(staticChickenVariant.assetId)
                        ? false
                        : this.babyAssetId.equals(staticChickenVariant.babyAssetId);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.modelType, this.assetId, this.babyAssetId);
    }
}
