package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.ints;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

@NullMarked
public final class ClampedNormalInt implements IntProvider {

    public static final NbtMapCodec<ClampedNormalInt> MAP_CODEC =
            new NbtMapCodec<ClampedNormalInt>() {

                public ClampedNormalInt decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    float value = nBTCompound.getNumberTagValueOrThrow("mean").floatValue();
                    float value2 = nBTCompound.getNumberTagValueOrThrow("deviation").floatValue();
                    int value3 = nBTCompound.getNumberTagValueOrThrow("min_inclusive").intValue();
                    int value4 = nBTCompound.getNumberTagValueOrThrow("max_inclusive").intValue();
                    return new ClampedNormalInt(value, value2, value3, value4);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ClampedNormalInt clampedNormalInt)
                        throws NbtCodecException {
                    nBTCompound.setTag("mean", new NBTFloat(clampedNormalInt.mean));
                    nBTCompound.setTag("deviation", new NBTFloat(clampedNormalInt.deviation));
                    nBTCompound.setTag("min_inclusive", new NBTInt(clampedNormalInt.minInclusive));
                    nBTCompound.setTag("max_inclusive", new NBTInt(clampedNormalInt.maxInclusive));
                }
            };

    private final float mean;

    private final float deviation;

    private final int minInclusive;

    private final int maxInclusive;

    public ClampedNormalInt(float mean, float deviation, int minInclusive, int maxInclusive) {
        this.mean = mean;
        this.deviation = deviation;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.CLAMPED_NORMAL;
    }

    @Override
    public int getSample(Random random) {
        return (int)
                Math.max(
                        this.minInclusive,
                        Math.min(
                                this.maxInclusive,
                                random.nextGaussian() * this.deviation + this.mean));
    }

    @Override
    public int getMin() {
        return this.minInclusive;
    }

    @Override
    public int getMax() {
        return this.maxInclusive;
    }

    public float getMean() {
        return this.mean;
    }

    public float getDeviation() {
        return this.deviation;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            ClampedNormalInt clampedNormalInt;
            if (Float.compare((clampedNormalInt = (ClampedNormalInt) result).mean, this.mean)
                    != 0) {
                return false;
            } else if (Float.compare(clampedNormalInt.deviation, this.deviation) != 0) {
                return false;
            } else {
                return this.minInclusive != clampedNormalInt.minInclusive
                        ? false
                        : this.maxInclusive == clampedNormalInt.maxInclusive;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mean, this.deviation, this.minInclusive, this.maxInclusive);
    }
}
