package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.nautilus;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticZombieNautilusVariant extends AbstractMappedEntity
        implements ZombieNautilusVariant {

    private final ZombieNautilusVariant.ModelType modelType;

    private final ResourceLocation assetId;

    public StaticZombieNautilusVariant(
            ZombieNautilusVariant.ModelType modelType, ResourceLocation resourceLocation) {
        this(null, modelType, resourceLocation);
    }

    @Internal
    public StaticZombieNautilusVariant(
            @Nullable TypesBuilderData typesBuilderData,
            ZombieNautilusVariant.ModelType modelType2,
            ResourceLocation resourceLocation) {
        super(typesBuilderData);
        this.modelType = modelType2;
        this.assetId = resourceLocation;
    }

    public ZombieNautilusVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticZombieNautilusVariant(typesBuilderData, this.modelType, this.assetId);
    }

    @Override
    public ZombieNautilusVariant.ModelType getModelType() {
        return this.modelType;
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            StaticZombieNautilusVariant staticZombieNautilusVariant =
                    (StaticZombieNautilusVariant) result;
            return this.modelType != staticZombieNautilusVariant.modelType
                    ? false
                    : this.assetId.equals(staticZombieNautilusVariant.assetId);
        } else {
            return false;
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.modelType, this.assetId);
    }
}
