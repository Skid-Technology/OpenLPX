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
public final class TrapezoidInt implements IntProvider {

    public static final NbtMapCodec<TrapezoidInt> MAP_CODEC =
            new NbtMapCodec<TrapezoidInt>() {

                public TrapezoidInt decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int value = nBTCompound.getNumberTagValueOrThrow("min").intValue();
                    int value2 = nBTCompound.getNumberTagValueOrThrow("max").intValue();
                    int value3 = nBTCompound.getNumberTagValueOrThrow("plateau").intValue();
                    return new TrapezoidInt(value, value2, value3);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        TrapezoidInt trapezoidInt)
                        throws NbtCodecException {
                    nBTCompound.setTag("min", new NBTInt(trapezoidInt.minInclusive));
                    nBTCompound.setTag("max", new NBTInt(trapezoidInt.maxInclusive));
                    nBTCompound.setTag("plateau", new NBTInt(trapezoidInt.plateau));
                }
            };

    private final int minInclusive;

    private final int maxInclusive;

    private final int plateau;

    public TrapezoidInt(int minInclusive, int maxInclusive, int plateau) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
        this.plateau = plateau;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.TRAPEZOID;
    }

    @Override
    public int getSample(Random random) {
        if (this.plateau == 0 && this.maxInclusive == -this.minInclusive) {
            return random.nextInt(this.maxInclusive + 1) - random.nextInt(this.maxInclusive + 1);
        }
        int value = this.maxInclusive - this.minInclusive;
        if (this.plateau == value) {
            return this.minInclusive + random.nextInt(value + 1);
        }
        int value2 = (value - this.plateau) / 2;
        int value3 = value - value2;
        return this.minInclusive + random.nextInt(value3 + 1) + random.nextInt(value2 + 1);
    }

    @Override
    public int getMin() {
        return this.minInclusive;
    }

    @Override
    public int getMax() {
        return this.maxInclusive;
    }

    public int getPlateau() {
        return this.plateau;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (result != null && this.getClass() == result.getClass()) {
            TrapezoidInt trapezoidInt = (TrapezoidInt) result;
            if (this.minInclusive != trapezoidInt.minInclusive) {
                return false;
            } else {
                return this.maxInclusive != trapezoidInt.maxInclusive
                        ? false
                        : this.plateau == trapezoidInt.plateau;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minInclusive, this.maxInclusive, this.plateau);
    }
}
