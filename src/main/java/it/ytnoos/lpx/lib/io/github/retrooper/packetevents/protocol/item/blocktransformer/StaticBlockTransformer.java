package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.blocktransformer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
final class StaticBlockTransformer extends AbstractMappedEntity implements BlockTransformer {

    private final List<BlockTransformData> transforms;

    public StaticBlockTransformer(
            @Nullable TypesBuilderData typesBuilderData, List<BlockTransformData> values) {
        super(typesBuilderData);
        this.transforms = Collections.unmodifiableList(values);
    }

    public BlockTransformer copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticBlockTransformer(typesBuilderData, this.transforms);
    }

    @Override
    public List<BlockTransformData> getTransforms() {
        return this.transforms;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticBlockTransformer)) {
            return false;
        }
        StaticBlockTransformer staticBlockTransformer = (StaticBlockTransformer) result;
        return this.transforms.equals(staticBlockTransformer.transforms);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.transforms);
    }
}
