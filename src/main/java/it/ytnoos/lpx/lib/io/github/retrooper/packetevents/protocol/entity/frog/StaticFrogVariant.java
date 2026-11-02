package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.frog;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticFrogVariant extends AbstractMappedEntity implements FrogVariant {

    private final ResourceLocation assetId;

    public StaticFrogVariant(ResourceLocation resourceLocation) {
        this(null, resourceLocation);
    }

    @Internal
    public StaticFrogVariant(
            @Nullable TypesBuilderData typesBuilderData, ResourceLocation resourceLocation) {
        super(typesBuilderData);
        this.assetId = resourceLocation;
    }

    public FrogVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticFrogVariant(typesBuilderData, this.assetId);
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticFrogVariant)) {
            return false;
        }
        StaticFrogVariant staticFrogVariant = (StaticFrogVariant) result;
        return this.assetId.equals(staticFrogVariant.assetId);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.assetId);
    }
}
