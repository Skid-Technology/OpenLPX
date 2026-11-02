package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class CopyPropertiesProvider extends AbstractMappedEntity
        implements BlockStateProvider {

    public static final NbtMapCodec<CopyPropertiesProvider> MAP_CODEC =
            new NbtMapCodec<CopyPropertiesProvider>() {

                public CopyPropertiesProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    BlockStateProvider orThrow =
                            nBTCompound.getOrThrow(
                                    "source", BlockStateProvider.CODEC, packetWrapper);
                    return new CopyPropertiesProvider(null, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        CopyPropertiesProvider copyPropertiesProvider)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "source",
                            copyPropertiesProvider.source,
                            BlockStateProvider.CODEC,
                            packetWrapper);
                }
            };

    private final BlockStateProvider source;

    public CopyPropertiesProvider(BlockStateProvider blockStateProvider) {
        this(null, blockStateProvider);
    }

    @Internal
    public CopyPropertiesProvider(
            @Nullable TypesBuilderData typesBuilderData, BlockStateProvider blockStateProvider) {
        super(typesBuilderData);
        this.source = blockStateProvider;
    }

    public BlockStateProvider getSource() {
        return this.source;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.COPY_PROPERTIES;
    }

    public CopyPropertiesProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new CopyPropertiesProvider(typesBuilderData, this.source);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof CopyPropertiesProvider)) {
            return false;
        }
        CopyPropertiesProvider copyPropertiesProvider = (CopyPropertiesProvider) result;
        return this.source.equals(copyPropertiesProvider.source);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.source);
    }
}
