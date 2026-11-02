package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockStateProviderTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.NormalNoise;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.BlockStateCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public final class NoiseProvider extends AbstractMappedEntity implements BlockStateProvider {

    public static final NbtMapCodec<NoiseProvider> MAP_CODEC =
            new NbtMapCodec<NoiseProvider>() {

                public NoiseProvider decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    long value = nBTCompound.getNumberTagValueOrThrow("seed").longValue();
                    NormalNoise orThrow =
                            nBTCompound.getOrThrow("noise", NormalNoise.CODEC, packetWrapper);
                    float value2 = nBTCompound.getNumberTagValueOrThrow("scale").floatValue();
                    List<WrappedBlockState> listOrThrow =
                            nBTCompound.getListOrThrow(
                                    "states", BlockStateCodec.CODEC, packetWrapper);
                    return new NoiseProvider(null, value, orThrow, value2, listOrThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        NoiseProvider noiseProvider)
                        throws NbtCodecException {
                    nBTCompound.setTag("seed", new NBTLong(noiseProvider.seed));
                    nBTCompound.set("noise", noiseProvider.noise, NormalNoise.CODEC, packetWrapper);
                    nBTCompound.setTag("scale", new NBTFloat(noiseProvider.scale));
                    nBTCompound.setList(
                            "states", noiseProvider.states, BlockStateCodec.CODEC, packetWrapper);
                }
            };

    private final long seed;

    private final NormalNoise noise;

    private final float scale;

    private final List<WrappedBlockState> states;

    public NoiseProvider(
            long value, NormalNoise normalNoise, float value2, List<WrappedBlockState> values) {
        this(null, value, normalNoise, value2, values);
    }

    @Internal
    public NoiseProvider(
            @Nullable TypesBuilderData typesBuilderData,
            long seed,
            NormalNoise normalNoise,
            float scale,
            List<WrappedBlockState> values) {
        super(typesBuilderData);
        this.seed = seed;
        this.noise = normalNoise;
        this.scale = scale;
        this.states = Collections.unmodifiableList(values);
    }

    public long getSeed() {
        return this.seed;
    }

    public NormalNoise getNoise() {
        return this.noise;
    }

    public float getScale() {
        return this.scale;
    }

    public List<WrappedBlockState> getStates() {
        return this.states;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.NOISE;
    }

    public NoiseProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new NoiseProvider(typesBuilderData, this.seed, this.noise, this.scale, this.states);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof NoiseProvider)) {
            return false;
        } else {
            NoiseProvider noiseProvider = (NoiseProvider) result;
            if (this.seed != noiseProvider.seed) {
                return false;
            } else if (Float.compare(noiseProvider.scale, this.scale) != 0) {
                return false;
            } else {
                return !this.noise.equals(noiseProvider.noise)
                        ? false
                        : this.states.equals(noiseProvider.states);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.seed, this.noise, this.scale, this.states);
    }
}
