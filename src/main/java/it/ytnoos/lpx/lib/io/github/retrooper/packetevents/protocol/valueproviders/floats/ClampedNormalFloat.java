package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.floats;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

@NullMarked
public final class ClampedNormalFloat implements FloatProvider {

    public static final NbtMapCodec<ClampedNormalFloat> MAP_CODEC =
            new NbtMapCodec<ClampedNormalFloat>() {

                public ClampedNormalFloat decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    float value = nBTCompound.getNumberTagValueOrThrow("mean").floatValue();
                    float value2 = nBTCompound.getNumberTagValueOrThrow("deviation").floatValue();
                    float value3 = nBTCompound.getNumberTagValueOrThrow("min").floatValue();
                    float value4 = nBTCompound.getNumberTagValueOrThrow("max").floatValue();
                    return new ClampedNormalFloat(value, value2, value3, value4);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ClampedNormalFloat clampedNormalFloat)
                        throws NbtCodecException {
                    nBTCompound.setTag("mean", new NBTFloat(clampedNormalFloat.mean));
                    nBTCompound.setTag("deviation", new NBTFloat(clampedNormalFloat.deviation));
                    nBTCompound.setTag("min", new NBTFloat(clampedNormalFloat.min));
                    nBTCompound.setTag("max", new NBTFloat(clampedNormalFloat.max));
                }
            };

    private final float mean;

    private final float deviation;

    private final float min;

    private final float max;

    public ClampedNormalFloat(float mean, float deviation, float min, float max) {
        this.mean = mean;
        this.deviation = deviation;
        this.min = min;
        this.max = max;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderTypes.CLAMPED_NORMAL;
    }

    @Override
    public float getSample(Random random) {
        float value = this.mean + (float) random.nextGaussian() * this.deviation;
        return Math.min(this.max, Math.max(value, this.min));
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public float getMax() {
        return this.max;
    }

    public float getMean() {
        return this.mean;
    }

    public float getDeviation() {
        return this.deviation;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            ClampedNormalFloat clampedNormalFloat;
            if (Float.compare((clampedNormalFloat = (ClampedNormalFloat) result).mean, this.mean)
                    != 0) {
                return false;
            } else if (Float.compare(clampedNormalFloat.deviation, this.deviation) != 0) {
                return false;
            } else {
                return Float.compare(clampedNormalFloat.min, this.min) != 0
                        ? false
                        : Float.compare(clampedNormalFloat.max, this.max) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mean, this.deviation, this.min, this.max);
    }
}
