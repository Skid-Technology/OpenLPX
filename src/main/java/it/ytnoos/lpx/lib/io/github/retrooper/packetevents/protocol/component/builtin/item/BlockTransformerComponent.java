package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.blocktransformer.BlockTransformer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class BlockTransformerComponent {

    private final BlockTransformer transformer;

    public BlockTransformerComponent(BlockTransformer blockTransformer) {
        this.transformer = blockTransformer;
    }

    public static BlockTransformerComponent read(PacketWrapper<?> packetWrapper) {
        return new BlockTransformerComponent(BlockTransformer.read(packetWrapper));
    }

    public static void write(
            PacketWrapper<?> packetWrapper, BlockTransformerComponent blockTransformerComponent) {
        BlockTransformer.write(packetWrapper, blockTransformerComponent.transformer);
    }

    public BlockTransformer getTransformer() {
        return this.transformer;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (!(result instanceof BlockTransformerComponent)) {
            return false;
        }
        BlockTransformerComponent blockTransformerComponent = (BlockTransformerComponent) result;
        return this.transformer.equals(blockTransformerComponent.transformer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.transformer);
    }
}
