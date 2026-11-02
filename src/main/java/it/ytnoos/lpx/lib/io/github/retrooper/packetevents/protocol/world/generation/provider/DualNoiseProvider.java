package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.IntRange;
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
public final class DualNoiseProvider extends AbstractMappedEntity implements BlockStateProvider {

    public static final NbtMapCodec<DualNoiseProvider> MAP_CODEC =
            new NbtMapCodec<DualNoiseProvider>() {

                public DualNoiseProvider decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    IntRange orThrow =
                            nBTCompound.getOrThrow("variety", IntRange.CODEC, packetWrapper);
                    NormalNoise orThrow2 =
                            nBTCompound.getOrThrow("slow_noise", NormalNoise.CODEC, packetWrapper);
                    float value = nBTCompound.getNumberTagValueOrThrow("slow_scale").floatValue();
                    long value2 = nBTCompound.getNumberTagValueOrThrow("seed").longValue();
                    NormalNoise orThrow3 =
                            nBTCompound.getOrThrow("noise", NormalNoise.CODEC, packetWrapper);
                    float value3 = nBTCompound.getNumberTagValueOrThrow("scale").floatValue();
                    List<WrappedBlockState> listOrThrow =
                            nBTCompound.getListOrThrow(
                                    "states", BlockStateCodec.CODEC, packetWrapper);
                    return new DualNoiseProvider(
                            null, orThrow, orThrow2, value, value2, orThrow3, value3, listOrThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        DualNoiseProvider dualNoiseProvider)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "variety", dualNoiseProvider.variety, IntRange.CODEC, packetWrapper);
                    nBTCompound.set(
                            "slow_noise",
                            dualNoiseProvider.slowNoise,
                            NormalNoise.CODEC,
                            packetWrapper);
                    nBTCompound.setTag("slow_scale", new NBTFloat(dualNoiseProvider.slowScale));
                    nBTCompound.setTag("seed", new NBTLong(dualNoiseProvider.seed));
                    nBTCompound.set(
                            "noise", dualNoiseProvider.noise, NormalNoise.CODEC, packetWrapper);
                    nBTCompound.setTag("scale", new NBTFloat(dualNoiseProvider.scale));
                    nBTCompound.setList(
                            "states",
                            dualNoiseProvider.states,
                            BlockStateCodec.CODEC,
                            packetWrapper);
                }
            };

    private final IntRange variety;

    private final NormalNoise slowNoise;

    private final float slowScale;

    private final long seed;

    private final NormalNoise noise;

    private final float scale;

    private final List<WrappedBlockState> states;

    public DualNoiseProvider(
            IntRange intRange,
            NormalNoise normalNoise,
            float value,
            long value2,
            NormalNoise normalNoise2,
            float value3,
            List<WrappedBlockState> values) {
        this(null, intRange, normalNoise, value, value2, normalNoise2, value3, values);
    }

    @Internal
    public DualNoiseProvider(
            @Nullable TypesBuilderData typesBuilderData,
            IntRange intRange,
            NormalNoise normalNoise,
            float slowScale,
            long seed,
            NormalNoise normalNoise2,
            float scale,
            List<WrappedBlockState> values) {
        super(typesBuilderData);
        this.variety = intRange;
        this.slowNoise = normalNoise;
        this.slowScale = slowScale;
        this.seed = seed;
        this.noise = normalNoise2;
        this.scale = scale;
        this.states = Collections.unmodifiableList(values);
    }

    public IntRange getVariety() {
        return this.variety;
    }

    public NormalNoise getSlowNoise() {
        return this.slowNoise;
    }

    public float getSlowScale() {
        return this.slowScale;
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
        return BlockStateProviderTypes.DUAL_NOISE;
    }

    public DualNoiseProvider copy(@Nullable TypesBuilderData typesBuilderData) {
        return new DualNoiseProvider(
                typesBuilderData,
                this.variety,
                this.slowNoise,
                this.slowScale,
                this.seed,
                this.noise,
                this.scale,
                this.states);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof DualNoiseProvider)) {
            return false;
        } else {
            DualNoiseProvider dualNoiseProvider = (DualNoiseProvider) result;
            if (this.seed != dualNoiseProvider.seed) {
                return false;
            } else if (Float.compare(dualNoiseProvider.slowScale, this.slowScale) != 0) {
                return false;
            } else if (Float.compare(dualNoiseProvider.scale, this.scale) != 0) {
                return false;
            } else if (!this.variety.equals(dualNoiseProvider.variety)) {
                return false;
            } else if (!this.slowNoise.equals(dualNoiseProvider.slowNoise)) {
                return false;
            } else {
                return !this.noise.equals(dualNoiseProvider.noise)
                        ? false
                        : this.states.equals(dualNoiseProvider.states);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.variety,
                this.slowNoise,
                this.slowScale,
                this.seed,
                this.noise,
                this.scale,
                this.states);
    }
}
