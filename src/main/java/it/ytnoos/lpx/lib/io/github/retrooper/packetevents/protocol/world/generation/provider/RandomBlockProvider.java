package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class RandomBlockProvider extends AbstractMappedEntity implements BlockStateProvider {

    public static final NbtMapCodec<RandomBlockProvider> MAP_CODEC =
            new NbtMapCodec<RandomBlockProvider>() {

                public RandomBlockProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    MappedEntitySet<StateType.Mapped> mappedEntitySet =
                            MappedEntitySet.decode(
                                    nBTCompound.getTagOrThrow("blocks"),
                                    packetWrapper,
                                    StateTypes.getRegistry());
                    return new RandomBlockProvider(null, mappedEntitySet);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        RandomBlockProvider randomBlockProvider)
                        throws NbtCodecException {
                    nBTCompound.setTag(
                            "blocks",
                            MappedEntitySet.encode(packetWrapper, randomBlockProvider.blocks));
                }
            };

    private final MappedEntitySet<StateType.Mapped> blocks;

    public RandomBlockProvider(MappedEntitySet<StateType.Mapped> mappedEntitySet) {
        this(null, mappedEntitySet);
    }

    @Internal
    public RandomBlockProvider(
            @Nullable TypesBuilderData typesBuilderData,
            MappedEntitySet<StateType.Mapped> mappedEntitySet) {
        super(typesBuilderData);
        this.blocks = mappedEntitySet;
    }

    public MappedEntitySet<StateType.Mapped> getBlocks() {
        return this.blocks;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.RANDOM_BLOCK;
    }

    public RandomBlockProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new RandomBlockProvider(typesBuilderData, this.blocks);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof RandomBlockProvider)) {
            return false;
        }
        RandomBlockProvider randomBlockProvider = (RandomBlockProvider) result;
        return this.blocks.equals(randomBlockProvider.blocks);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.blocks);
    }
}
