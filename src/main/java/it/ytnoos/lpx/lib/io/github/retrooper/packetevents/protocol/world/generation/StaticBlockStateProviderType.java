package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@Internal
final class StaticBlockStateProviderType<T extends BlockStateProvider> extends AbstractMappedEntity
        implements BlockStateProviderType<T> {

    private final NbtMapCodec<T> codec;

    public StaticBlockStateProviderType(
            @Nullable TypesBuilderData typesBuilderData, NbtMapCodec<T> nbtMapCodec) {
        super(typesBuilderData);
        this.codec = nbtMapCodec;
    }

    @Override
    public NbtMapCodec<T> getCodec() {
        return this.codec;
    }
}
