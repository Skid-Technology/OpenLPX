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
public final class UniformFloat implements FloatProvider {

    public static final NbtMapCodec<UniformFloat> MAP_CODEC =
            new NbtMapCodec<UniformFloat>() {

                public UniformFloat decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    float value =
                            nBTCompound.getNumberTagValueOrThrow("min_inclusive").floatValue();
                    float value2 =
                            nBTCompound.getNumberTagValueOrThrow("max_exclusive").floatValue();
                    return new UniformFloat(value, value2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        UniformFloat uniformFloat)
                        throws NbtCodecException {
                    nBTCompound.setTag("min_inclusive", new NBTFloat(uniformFloat.min));
                    nBTCompound.setTag("max_exclusive", new NBTFloat(uniformFloat.max));
                }
            };

    private final float min;

    private final float max;

    public UniformFloat(float min, float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderTypes.UNIFORM;
    }

    @Override
    public float getSample(Random random) {
        return random.nextFloat() * (this.max - this.min) + this.min;
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public float getMax() {
        return this.max;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        }
        UniformFloat uniformFloat;
        return Float.compare((uniformFloat = (UniformFloat) result).min, this.min) != 0
                ? false
                : Float.compare(uniformFloat.max, this.max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.min, this.max);
    }
}
