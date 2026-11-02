package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.BlockStateCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class SimpleStateProvider extends AbstractMappedEntity implements BlockStateProvider {

    public static final NbtMapCodec<SimpleStateProvider> MAP_CODEC =
            new NbtMapCodec<SimpleStateProvider>() {

                public SimpleStateProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    WrappedBlockState orThrow =
                            nBTCompound.getOrThrow("state", BlockStateCodec.CODEC, packetWrapper);
                    return new SimpleStateProvider(null, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        SimpleStateProvider simpleStateProvider)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "state",
                            simpleStateProvider.state,
                            BlockStateCodec.CODEC,
                            packetWrapper);
                }
            };

    private final WrappedBlockState state;

    public SimpleStateProvider(WrappedBlockState wrappedBlockState) {
        this(null, wrappedBlockState);
    }

    @Internal
    public SimpleStateProvider(
            @Nullable TypesBuilderData typesBuilderData, WrappedBlockState wrappedBlockState) {
        super(typesBuilderData);
        this.state = wrappedBlockState;
    }

    public WrappedBlockState getState() {
        return this.state;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.SIMPLE;
    }

    public SimpleStateProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new SimpleStateProvider(typesBuilderData, this.state);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof SimpleStateProvider)) {
            return false;
        }
        SimpleStateProvider simpleStateProvider = (SimpleStateProvider) result;
        return this.state.equals(simpleStateProvider.state);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.state);
    }
}
