package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Direction;
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
public final class RotatedBlockProvider extends AbstractMappedEntity implements BlockStateProvider {

    public static final NbtMapCodec<RotatedBlockProvider> MAP_CODEC =
            new NbtMapCodec<RotatedBlockProvider>() {

                public RotatedBlockProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    BlockStateProvider orThrow =
                            nBTCompound.getOrThrow(
                                    "state", BlockStateProvider.CODEC, packetWrapper);
                    Direction orNull =
                            nBTCompound.getOrNull("direction", Direction.CODEC, packetWrapper);
                    return new RotatedBlockProvider(null, orThrow, orNull);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        RotatedBlockProvider rotatedBlockProvider)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "state",
                            rotatedBlockProvider.state,
                            BlockStateProvider.CODEC,
                            packetWrapper);
                    if (rotatedBlockProvider.direction != null) {
                        nBTCompound.set(
                                "direction",
                                rotatedBlockProvider.direction,
                                Direction.CODEC,
                                packetWrapper);
                    }
                }
            };

    private final BlockStateProvider state;

    @Nullable private final Direction direction;

    public RotatedBlockProvider(
            BlockStateProvider blockStateProvider, @Nullable Direction direction) {
        this(null, blockStateProvider, direction);
    }

    @Internal
    public RotatedBlockProvider(
            @Nullable TypesBuilderData typesBuilderData,
            BlockStateProvider blockStateProvider,
            @Nullable Direction direction2) {
        super(typesBuilderData);
        this.state = blockStateProvider;
        this.direction = direction2;
    }

    public BlockStateProvider getState() {
        return this.state;
    }

    @Nullable
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.ROTATED;
    }

    public RotatedBlockProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new RotatedBlockProvider(typesBuilderData, this.state, this.direction);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof RotatedBlockProvider)) {
            return false;
        }
        RotatedBlockProvider rotatedBlockProvider = (RotatedBlockProvider) result;
        return !this.state.equals(rotatedBlockProvider.state)
                ? false
                : this.direction == rotatedBlockProvider.direction;
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.state, this.direction);
    }
}
