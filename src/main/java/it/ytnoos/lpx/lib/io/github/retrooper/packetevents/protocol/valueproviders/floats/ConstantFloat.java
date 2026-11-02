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
public final class ConstantFloat implements FloatProvider {

    public static final NbtMapCodec<ConstantFloat> MAP_CODEC =
            new NbtMapCodec<ConstantFloat>() {

                public ConstantFloat decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    float value = nBTCompound.getNumberTagValueOrThrow("value").floatValue();
                    return new ConstantFloat(value);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ConstantFloat constantFloat)
                        throws NbtCodecException {
                    nBTCompound.setTag("value", new NBTFloat(constantFloat.value));
                }
            };

    private final float value;

    public ConstantFloat(float value) {
        this.value = value;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderTypes.CONSTANT;
    }

    @Override
    public float getSample(Random random) {
        return this.value;
    }

    @Override
    public float getMin() {
        return this.value;
    }

    @Override
    public float getMax() {
        return this.value;
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        return result != null && this.getClass() == result.getClass()
                ? Float.compare(((ConstantFloat) result).value, this.value) == 0
                : false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
