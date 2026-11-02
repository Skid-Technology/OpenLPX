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
public final class BiasedToBottomInt implements IntProvider {

    public static final NbtMapCodec<BiasedToBottomInt> MAP_CODEC =
            new NbtMapCodec<BiasedToBottomInt>() {

                public BiasedToBottomInt decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int value = nBTCompound.getNumberTagValueOrThrow("min_inclusive").intValue();
                    int value2 = nBTCompound.getNumberTagValueOrThrow("max_inclusive").intValue();
                    return new BiasedToBottomInt(value, value2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        BiasedToBottomInt biasedToBottomInt)
                        throws NbtCodecException {
                    nBTCompound.setTag("min_inclusive", new NBTInt(biasedToBottomInt.minInclusive));
                    nBTCompound.setTag("max_inclusive", new NBTInt(biasedToBottomInt.maxInclusive));
                }
            };

    private final int minInclusive;

    private final int maxInclusive;

    public BiasedToBottomInt(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.BIASED_TO_BOTTOM;
    }

    @Override
    public int getSample(Random random) {
        return this.minInclusive
                + random.nextInt(random.nextInt(this.maxInclusive - this.minInclusive + 1) + 1);
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
            BiasedToBottomInt biasedToBottomInt = (BiasedToBottomInt) result;
            return this.minInclusive != biasedToBottomInt.minInclusive
                    ? false
                    : this.maxInclusive == biasedToBottomInt.maxInclusive;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minInclusive, this.maxInclusive);
    }
}
