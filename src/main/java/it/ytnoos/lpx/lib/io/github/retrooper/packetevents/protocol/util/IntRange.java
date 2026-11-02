package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

@NullMarked
public final class IntRange {

    public static final NbtCodec<IntRange> CODEC =
            new NbtCodec<IntRange>() {

                public IntRange decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    if (nBT instanceof NBTNumber) {
                        int asInt = ((NBTNumber) nBT).getAsInt();
                        return new IntRange(asInt, asInt);
                    } else if (nBT instanceof NBTCompound) {
                        NBTCompound nBTCompound;
                        int value =
                                (nBTCompound = (NBTCompound) nBT)
                                        .getNumberTagValueOrThrow("min_inclusive")
                                        .intValue();
                        int value2 =
                                nBTCompound.getNumberTagValueOrThrow("max_inclusive").intValue();
                        return new IntRange(value, value2);
                    } else {
                        int[] values;
                        if ((values = NbtCodecs.INT_ARRAY.decode(nBT, packetWrapper)).length != 2) {
                            throw new NbtCodecException(
                                    "Expected 2 range components, but got " + values.length);
                        } else {
                            return new IntRange(values[0], values[1]);
                        }
                    }
                }

                public NBT encode(PacketWrapper<?> packetWrapper, IntRange intRange) {
                    return intRange.minInclusive == intRange.maxInclusive
                            ? new NBTInt(intRange.minInclusive)
                            : new NBTList<>(
                                    NBTType.INT,
                                    Arrays.asList(
                                            new NBTInt(intRange.minInclusive),
                                            new NBTInt(intRange.maxInclusive)));
                }
            };

    private final int minInclusive;

    private final int maxInclusive;

    public IntRange(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    public boolean contains(int value) {
        return value >= this.minInclusive && value <= this.maxInclusive;
    }

    public int getMinInclusive() {
        return this.minInclusive;
    }

    public int getMaxInclusive() {
        return this.maxInclusive;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            IntRange intRange = (IntRange) result;
            return this.minInclusive != intRange.minInclusive
                    ? false
                    : this.maxInclusive == intRange.maxInclusive;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minInclusive, this.maxInclusive);
    }

    @Override
    public String toString() {
        return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
    }
}
