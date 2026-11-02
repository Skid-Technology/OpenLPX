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
public final class VeryBiasedToBottomInt implements IntProvider {

    public static final NbtMapCodec<VeryBiasedToBottomInt> MAP_CODEC =
            new NbtMapCodec<VeryBiasedToBottomInt>() {

                public VeryBiasedToBottomInt decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int value = nBTCompound.getNumberTagValueOrThrow("min_inclusive").intValue();
                    int value2 = nBTCompound.getNumberTagValueOrThrow("max_inclusive").intValue();
                    return new VeryBiasedToBottomInt(value, value2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        VeryBiasedToBottomInt veryBiasedToBottomInt)
                        throws NbtCodecException {
                    nBTCompound.setTag(
                            "min_inclusive", new NBTInt(veryBiasedToBottomInt.minInclusive));
                    nBTCompound.setTag(
                            "max_inclusive", new NBTInt(veryBiasedToBottomInt.maxInclusive));
                }
            };

    private final int minInclusive;

    private final int maxInclusive;

    public VeryBiasedToBottomInt(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.VERY_BIASED_TO_BOTTOM;
    }

    @Override
    public int getSample(Random random) {
        int value = this.maxInclusive - this.minInclusive + 1;
        return this.minInclusive + random.nextInt(random.nextInt(random.nextInt(value) + 1) + 1);
    }

    @Override
    public int getMin() {
        return this.minInclusive;
    }

    @Override
    public int getMax() {
        return this.maxInclusive;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            VeryBiasedToBottomInt veryBiasedToBottomInt = (VeryBiasedToBottomInt) result;
            return this.minInclusive != veryBiasedToBottomInt.minInclusive
                    ? false
                    : this.maxInclusive == veryBiasedToBottomInt.maxInclusive;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minInclusive, this.maxInclusive);
    }
}
