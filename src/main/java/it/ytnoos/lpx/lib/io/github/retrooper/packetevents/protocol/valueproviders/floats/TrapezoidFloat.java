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
public final class TrapezoidFloat implements FloatProvider {

    public static final NbtMapCodec<TrapezoidFloat> MAP_CODEC =
            new NbtMapCodec<TrapezoidFloat>() {

                public TrapezoidFloat decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    float value = nBTCompound.getNumberTagValueOrThrow("min").floatValue();
                    float value2 = nBTCompound.getNumberTagValueOrThrow("max").floatValue();
                    float value3 = nBTCompound.getNumberTagValueOrThrow("plateau").floatValue();
                    return new TrapezoidFloat(value, value2, value3);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        TrapezoidFloat trapezoidFloat)
                        throws NbtCodecException {
                    nBTCompound.setTag("min", new NBTFloat(trapezoidFloat.min));
                    nBTCompound.setTag("max", new NBTFloat(trapezoidFloat.max));
                    nBTCompound.setTag("plateau", new NBTFloat(trapezoidFloat.plateau));
                }
            };

    private final float min;

    private final float max;

    private final float plateau;

    public TrapezoidFloat(float min, float max, float plateau) {
        this.min = min;
        this.max = max;
        this.plateau = plateau;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderTypes.TRAPEZOID;
    }

    @Override
    public float getSample(Random random) {
        float value;
        float value2 = ((value = this.max - this.min) - this.plateau) / 2.0F;
        float value3 = value - value2;
        return this.min + random.nextFloat() * value3 + random.nextFloat() * value2;
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public float getMax() {
        return this.max;
    }

    public float getPlateau() {
        return this.plateau;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            TrapezoidFloat trapezoidFloat;
            if (Float.compare((trapezoidFloat = (TrapezoidFloat) result).min, this.min) != 0) {
                return false;
            } else {
                return Float.compare(trapezoidFloat.max, this.max) != 0
                        ? false
                        : Float.compare(trapezoidFloat.plateau, this.plateau) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.min, this.max, this.plateau);
    }
}
