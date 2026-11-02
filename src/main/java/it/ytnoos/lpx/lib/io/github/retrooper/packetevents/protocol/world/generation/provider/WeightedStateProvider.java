package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.WeightedList;
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
public final class WeightedStateProvider extends AbstractMappedEntity
        implements BlockStateProvider {

    public static final NbtMapCodec<WeightedStateProvider> MAP_CODEC =
            new NbtMapCodec<WeightedStateProvider>() {

                private final NbtCodec<WeightedList<WrappedBlockState>> entriesCodec =
                        WeightedList.codec(BlockStateCodec.CODEC);

                public WeightedStateProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    WeightedList orThrow =
                            nBTCompound.getOrThrow("entries", this.entriesCodec, packetWrapper);
                    return new WeightedStateProvider(null, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        WeightedStateProvider weightedStateProvider)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "entries",
                            weightedStateProvider.entries,
                            this.entriesCodec,
                            packetWrapper);
                }
            };

    private final WeightedList<WrappedBlockState> entries;

    public WeightedStateProvider(WeightedList<WrappedBlockState> weightedList) {
        this(null, weightedList);
    }

    @Internal
    public WeightedStateProvider(
            @Nullable TypesBuilderData typesBuilderData,
            WeightedList<WrappedBlockState> weightedList) {
        super(typesBuilderData);
        this.entries = weightedList;
    }

    public WeightedList<WrappedBlockState> getEntries() {
        return this.entries;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.WEIGHTED;
    }

    public WeightedStateProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new WeightedStateProvider(typesBuilderData, this.entries);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof WeightedStateProvider)) {
            return false;
        }
        WeightedStateProvider weightedStateProvider = (WeightedStateProvider) result;
        return this.entries.equals(weightedStateProvider.entries);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.entries);
    }
}
