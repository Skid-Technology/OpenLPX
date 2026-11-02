package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.decopot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
final class StaticDecoratedPotPattern extends AbstractMappedEntity implements DecoratedPotPattern {

    private final ResourceLocation assetId;

    public StaticDecoratedPotPattern(
            @Nullable TypesBuilderData typesBuilderData, ResourceLocation resourceLocation) {
        super(typesBuilderData);
        this.assetId = resourceLocation;
    }

    public DecoratedPotPattern copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticDecoratedPotPattern(typesBuilderData, this.assetId);
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticDecoratedPotPattern)) {
            return false;
        }
        StaticDecoratedPotPattern staticDecoratedPotPattern = (StaticDecoratedPotPattern) result;
        return this.assetId.equals(staticDecoratedPotPattern.assetId);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.assetId);
    }
}
