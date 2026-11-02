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
public final class NoiseThresholdProvider extends AbstractMappedEntity
        implements BlockStateProvider {

    public static final NbtMapCodec<NoiseThresholdProvider> MAP_CODEC =
            new NbtMapCodec<NoiseThresholdProvider>() {

                public NoiseThresholdProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    long value = nBTCompound.getNumberTagValueOrThrow("seed").longValue();
                    NormalNoise orThrow =
                            nBTCompound.getOrThrow("noise", NormalNoise.CODEC, packetWrapper);
                    float value2 = nBTCompound.getNumberTagValueOrThrow("scale").floatValue();
                    float value3 = nBTCompound.getNumberTagValueOrThrow("threshold").floatValue();
                    float value4 = nBTCompound.getNumberTagValueOrThrow("high_chance").floatValue();
                    WrappedBlockState orThrow2 =
                            nBTCompound.getOrThrow(
                                    "default_state", BlockStateCodec.CODEC, packetWrapper);
                    List<WrappedBlockState> listOrThrow =
                            nBTCompound.getListOrThrow(
                                    "low_states", BlockStateCodec.CODEC, packetWrapper);
                    List<WrappedBlockState> listOrThrow2 =
                            nBTCompound.getListOrThrow(
                                    "high_states", BlockStateCodec.CODEC, packetWrapper);
                    return new NoiseThresholdProvider(
                            null,
                            value,
                            orThrow,
                            value2,
                            value3,
                            value4,
                            orThrow2,
                            listOrThrow,
                            listOrThrow2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        NoiseThresholdProvider noiseThresholdProvider)
                        throws NbtCodecException {
                    nBTCompound.setTag("seed", new NBTLong(noiseThresholdProvider.seed));
                    nBTCompound.set(
                            "noise",
                            noiseThresholdProvider.noise,
                            NormalNoise.CODEC,
                            packetWrapper);
                    nBTCompound.setTag("scale", new NBTFloat(noiseThresholdProvider.scale));
                    nBTCompound.setTag("threshold", new NBTFloat(noiseThresholdProvider.threshold));
                    nBTCompound.setTag(
                            "high_chance", new NBTFloat(noiseThresholdProvider.highChance));
                    nBTCompound.set(
                            "default_state",
                            noiseThresholdProvider.defaultState,
                            BlockStateCodec.CODEC,
                            packetWrapper);
                    nBTCompound.setList(
                            "low_states",
                            noiseThresholdProvider.lowStates,
                            BlockStateCodec.CODEC,
                            packetWrapper);
                    nBTCompound.setList(
                            "high_states",
                            noiseThresholdProvider.highStates,
                            BlockStateCodec.CODEC,
                            packetWrapper);
                }
            };

    private final long seed;

    private final NormalNoise noise;

    private final float scale;

    private final float threshold;

    private final float highChance;

    private final WrappedBlockState defaultState;

    private final List<WrappedBlockState> lowStates;

    private final List<WrappedBlockState> highStates;

    public NoiseThresholdProvider(
            long value,
            NormalNoise normalNoise,
            float value2,
            float value3,
            float value4,
            WrappedBlockState wrappedBlockState,
            List<WrappedBlockState> values,
            List<WrappedBlockState> values2) {
        this(null, value, normalNoise, value2, value3, value4, wrappedBlockState, values, values2);
    }

    @Internal
    public NoiseThresholdProvider(
            @Nullable TypesBuilderData typesBuilderData,
            long seed,
            NormalNoise normalNoise,
            float scale,
            float threshold,
            float highChance,
            WrappedBlockState wrappedBlockState,
            List<WrappedBlockState> values,
            List<WrappedBlockState> values2) {
        super(typesBuilderData);
        this.seed = seed;
        this.noise = normalNoise;
        this.scale = scale;
        this.threshold = threshold;
        this.highChance = highChance;
        this.defaultState = wrappedBlockState;
        this.lowStates = Collections.unmodifiableList(values);
        this.highStates = Collections.unmodifiableList(values2);
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

    public float getThreshold() {
        return this.threshold;
    }

    public float getHighChance() {
        return this.highChance;
    }

    public WrappedBlockState getDefaultState() {
        return this.defaultState;
    }

    public List<WrappedBlockState> getLowStates() {
        return this.lowStates;
    }

    public List<WrappedBlockState> getHighStates() {
        return this.highStates;
    }

    @Override
    public BlockStateProviderType<?> getType() {
        return BlockStateProviderTypes.NOISE_THRESHOLD;
    }

    public NoiseThresholdProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new NoiseThresholdProvider(
                typesBuilderData,
                this.seed,
                this.noise,
                this.scale,
                this.threshold,
                this.highChance,
                this.defaultState,
                this.lowStates,
                this.highStates);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof NoiseThresholdProvider)) {
            return false;
        } else {
            NoiseThresholdProvider noiseThresholdProvider = (NoiseThresholdProvider) result;
            if (this.seed != noiseThresholdProvider.seed) {
                return false;
            } else if (Float.compare(noiseThresholdProvider.scale, this.scale) != 0) {
                return false;
            } else if (Float.compare(noiseThresholdProvider.threshold, this.threshold) != 0) {
                return false;
            } else if (Float.compare(noiseThresholdProvider.highChance, this.highChance) != 0) {
                return false;
            } else if (!this.noise.equals(noiseThresholdProvider.noise)) {
                return false;
            } else if (!this.defaultState.equals(noiseThresholdProvider.defaultState)) {
                return false;
            } else {
                return !this.lowStates.equals(noiseThresholdProvider.lowStates)
                        ? false
                        : this.highStates.equals(noiseThresholdProvider.highStates);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.seed,
                this.noise,
                this.scale,
                this.threshold,
                this.highChance,
                this.defaultState,
                this.lowStates,
                this.highStates);
    }
}
