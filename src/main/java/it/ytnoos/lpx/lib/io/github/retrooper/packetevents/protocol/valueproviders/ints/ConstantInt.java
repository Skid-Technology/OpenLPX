package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.ints;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

@NullMarked
public final class ConstantInt implements IntProvider {

    public static final NbtMapCodec<ConstantInt> MAP_CODEC =
            new NbtMapCodec<ConstantInt>() {

                public ConstantInt decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int value = nBTCompound.getNumberTagValueOrThrow("value").intValue();
                    return new ConstantInt(value);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ConstantInt constantInt)
                        throws NbtCodecException {
                    nBTCompound.setTag("value", new NBTInt(constantInt.value));
                }
            };

    private final int value;

    public ConstantInt(int value) {
        this.value = value;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.CONSTANT;
    }

    @Override
    public int getSample(Random random) {
        return this.value;
    }

    @Override
    public int getMin() {
        return this.value;
    }

    @Override
    public int getMax() {
        return this.value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ConstantInt constantInt = (ConstantInt) result;
            return this.value == constantInt.value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }
}
