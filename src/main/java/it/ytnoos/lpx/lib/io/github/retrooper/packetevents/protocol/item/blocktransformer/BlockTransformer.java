package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.blocktransformer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public interface BlockTransformer
        extends MappedEntity, CopyableEntity<BlockTransformer>, DeepComparableEntity {

    NbtCodec<BlockTransformer> DIRECT_CODEC =
            BlockTransformData.CODEC
                    .applyList()
                    .apply(
                            result -> new StaticBlockTransformer(null, result),
                            BlockTransformer::getTransforms);

    List<BlockTransformData> getTransforms();

    static BlockTransformer read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(BlockTransformers.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, BlockTransformer blockTransformer) {
        packetWrapper.writeMappedEntity(blockTransformer);
    }
}
