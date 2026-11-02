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
public final class UniformInt implements IntProvider {

    public static final NbtMapCodec<UniformInt> MAP_CODEC =
            new NbtMapCodec<UniformInt>() {

                public UniformInt decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int value = nBTCompound.getNumberTagValueOrThrow("min_inclusive").intValue();
                    int value2 = nBTCompound.getNumberTagValueOrThrow("max_inclusive").intValue();
                    return new UniformInt(value, value2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        UniformInt uniformInt)
                        throws NbtCodecException {
                    nBTCompound.setTag("min_inclusive", new NBTInt(uniformInt.minInclusive));
                    nBTCompound.setTag("max_inclusive", new NBTInt(uniformInt.maxInclusive));
                }
            };

    private final int minInclusive;

    private final int maxInclusive;

    public UniformInt(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.UNIFORM;
    }

    @Override
    public int getSample(Random random) {
        return this.minInclusive + random.nextInt(this.maxInclusive - this.minInclusive + 1);
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
            UniformInt uniformInt = (UniformInt) result;
            return this.minInclusive != uniformInt.minInclusive
                    ? false
                    : this.maxInclusive == uniformInt.maxInclusive;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minInclusive, this.maxInclusive);
    }
}
