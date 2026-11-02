package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.ints;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.WeightedList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

@NullMarked
public final class WeightedListInt implements IntProvider {

    public static final NbtMapCodec<WeightedListInt> MAP_CODEC =
            new NbtMapCodec<WeightedListInt>() {

                private final NbtCodec<WeightedList<IntProvider>> codec =
                        WeightedList.codec(IntProvider.CODEC);

                public WeightedListInt decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    WeightedList orThrow =
                            nBTCompound.getOrThrow("distribution", this.codec, packetWrapper);
                    return new WeightedListInt(orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        WeightedListInt weightedListInt)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "distribution",
                            weightedListInt.distribution,
                            this.codec,
                            packetWrapper);
                }
            };

    private final WeightedList<IntProvider> distribution;

    private final int minValue;

    private final int maxValue;

    public WeightedListInt(WeightedList<IntProvider> weightedList) {
        this.distribution = weightedList;
        int value = Integer.MAX_VALUE;
        int value2 = Integer.MIN_VALUE;
        Iterator<
                        WeightedList.Entry<
                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol
                                        .valueproviders.ints.IntProvider>>
                iterator2 = weightedList.getEntries().iterator();
        while (iterator2.hasNext()) {
            IntProvider intProvider =
                    (IntProvider) ((WeightedList.Entry) iterator2.next()).getValue();
            value = Math.min(value, intProvider.getMin());
            value2 = Math.max(value2, intProvider.getMax());
        }
        this.minValue = value;
        this.maxValue = value2;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderTypes.WEIGHTED_LIST;
    }

    @Override
    public int getSample(Random random) {
        int value = 0;
        for (WeightedList.Entry entry : this.distribution.getEntries()) {
            value += entry.getWeight();
        }
        if (value == 0) {
            throw new IllegalStateException("Weighted list has no elements");
        }
        int value2 = random.nextInt(value);
        for (WeightedList.Entry entry2 : this.distribution.getEntries()) {
            if ((value2 -= entry2.getWeight()) < 0) {
                return ((IntProvider) entry2.getValue()).getSample(random);
            }
        }
        throw new IllegalStateException(value2 + " exceeded total weight " + value);
    }

    @Override
    public int getMin() {
        return this.minValue;
    }

    @Override
    public int getMax() {
        return this.maxValue;
    }

    public WeightedList<IntProvider> getDistribution() {
        return this.distribution;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            WeightedListInt weightedListInt = (WeightedListInt) result;
            return this.distribution.equals(weightedListInt.distribution);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.distribution);
    }
}
