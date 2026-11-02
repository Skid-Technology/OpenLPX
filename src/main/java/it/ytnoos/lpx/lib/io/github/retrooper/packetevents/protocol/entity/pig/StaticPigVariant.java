package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticPigVariant extends AbstractMappedEntity implements PigVariant {

    private final PigVariant.ModelType modelType;

    private final ResourceLocation assetId;

    private final ResourceLocation babyAssetId;

    @Obsolete
    public StaticPigVariant(PigVariant.ModelType modelType, ResourceLocation resourceLocation) {
        this(null, modelType, resourceLocation, resourceLocation);
    }

    public StaticPigVariant(
            PigVariant.ModelType modelType,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        this(null, modelType, resourceLocation, resourceLocation2);
    }

    @Internal
    public StaticPigVariant(
            @Nullable TypesBuilderData typesBuilderData,
            PigVariant.ModelType modelType2,
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2) {
        super(typesBuilderData);
        this.modelType = modelType2;
        this.assetId = resourceLocation;
        this.babyAssetId = resourceLocation2;
    }

    public PigVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticPigVariant(
                typesBuilderData, this.modelType, this.assetId, this.babyAssetId);
    }

    @Override
    public PigVariant.ModelType getModelType() {
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
        if (!(result instanceof StaticPigVariant)) {
            return false;
        } else {
            StaticPigVariant staticPigVariant = (StaticPigVariant) result;
            if (!this.modelType.equals(staticPigVariant.modelType)) {
                return false;
            } else {
                return !this.assetId.equals(staticPigVariant.assetId)
                        ? false
                        : this.babyAssetId.equals(staticPigVariant.babyAssetId);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.modelType, this.assetId, this.babyAssetId);
    }
}
