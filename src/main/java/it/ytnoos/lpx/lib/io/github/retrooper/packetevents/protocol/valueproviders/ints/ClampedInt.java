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
public final class ClampedInt implements IntProvider {

    public static final NbtMapCodec<ClampedInt> MAP_CODEC =
            new NbtMapCodec<ClampedInt>() {

                public ClampedInt decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    IntProvider orThrow =
                            nBTCompound.getOrThrow("source", IntProvider.CODEC, packetWrapper);
                    int value = nBTCompound.getNumberTagValueOrThrow("min_inclusive").intValue();
                    int value2 = nBTCompound.getNumberTagValueOrThrow("max_inclusive").intValue();
                    return new ClampedInt(orThrow, value, value2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ClampedInt clampedInt)
                        throws NbtCodecException {
                    nBTCompound.set("source", clampedInt.source, IntProvider.CODEC, packetWrapper);
                    nBTCompound.setTag("min_inclusive", new NBTInt(clampedInt.minInclusive));
                    nBTCompound.setTag("max_inclusive", new NBTInt(clampedInt.maxInclusive));
                }
            };

    private final IntProvider source;

    private final int minInclusive;

    private final int maxInclusive;

    public ClampedInt(IntProvider intProvider, int minInclusive, int maxInclusive) {
        this.source = intProvider;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.CLAMPED;
    }

    @Override
    public int getSample(Random random) {
        return Math.max(
                this.minInclusive, Math.min(this.maxInclusive, this.source.getSample(random)));
    }

    @Override
    public int getMin() {
        return Math.max(this.minInclusive, this.source.getMin());
    }

    @Override
    public int getMax() {
        return Math.min(this.maxInclusive, this.source.getMax());
    }

    public IntProvider getSource() {
        return this.source;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (result != null && this.getClass() == result.getClass()) {
            ClampedInt clampedInt = (ClampedInt) result;
            if (this.minInclusive != clampedInt.minInclusive) {
                return false;
            } else {
                return this.maxInclusive != clampedInt.maxInclusive
                        ? false
                        : this.source.equals(clampedInt.source);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.minInclusive, this.maxInclusive);
    }
}
