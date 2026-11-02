package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.VerticalAnchor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class HeightRangePredicate implements BlockPredicate {

    public static final NbtMapCodec<HeightRangePredicate> MAP_CODEC =
            new NbtMapCodec<HeightRangePredicate>() {

                public HeightRangePredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    VerticalAnchor orThrow =
                            nBTCompound.getOrThrow(
                                    "min_inclusive",
                                    VerticalAnchor.MAP_CODEC.codec(),
                                    packetWrapper);
                    VerticalAnchor orThrow2 =
                            nBTCompound.getOrThrow(
                                    "max_inclusive",
                                    VerticalAnchor.MAP_CODEC.codec(),
                                    packetWrapper);
                    return new HeightRangePredicate(orThrow, orThrow2);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        HeightRangePredicate heightRangePredicate)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "min_inclusive",
                            heightRangePredicate.minInclusive,
                            VerticalAnchor.MAP_CODEC.codec(),
                            packetWrapper);
                    nBTCompound.set(
                            "max_inclusive",
                            heightRangePredicate.maxInclusive,
                            VerticalAnchor.MAP_CODEC.codec(),
                            packetWrapper);
                }
            };

    private final VerticalAnchor minInclusive;

    private final VerticalAnchor maxInclusive;

    public HeightRangePredicate(VerticalAnchor verticalAnchor, VerticalAnchor verticalAnchor2) {
        this.minInclusive = verticalAnchor;
        this.maxInclusive = verticalAnchor2;
    }

    public VerticalAnchor getMinInclusive() {
        return this.minInclusive;
    }

    public VerticalAnchor getMaxInclusive() {
        return this.maxInclusive;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.HEIGHT_RANGE;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            HeightRangePredicate heightRangePredicate = (HeightRangePredicate) result;
            return !this.minInclusive.equals(heightRangePredicate.minInclusive)
                    ? false
                    : this.maxInclusive.equals(heightRangePredicate.maxInclusive);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.minInclusive, this.maxInclusive);
    }
}
