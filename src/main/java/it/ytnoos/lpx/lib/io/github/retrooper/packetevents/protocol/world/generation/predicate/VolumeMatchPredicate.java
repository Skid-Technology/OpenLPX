package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class VolumeMatchPredicate implements BlockPredicate {

    public static final NbtMapCodec<VolumeMatchPredicate> MAP_CODEC =
            new NbtMapCodec<VolumeMatchPredicate>() {

                public VolumeMatchPredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    Vector3i orThrow = nBTCompound.getOrThrow("min", Vector3i.CODEC, packetWrapper);
                    Vector3i orThrow2 =
                            nBTCompound.getOrThrow("max", Vector3i.CODEC, packetWrapper);
                    BlockPredicate orThrow3 =
                            nBTCompound.getOrThrow("match", BlockPredicate.CODEC, packetWrapper);
                    return new VolumeMatchPredicate(orThrow, orThrow2, orThrow3);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        VolumeMatchPredicate volumeMatchPredicate)
                        throws NbtCodecException {
                    nBTCompound.set("min", volumeMatchPredicate.min, Vector3i.CODEC, packetWrapper);
                    nBTCompound.set("max", volumeMatchPredicate.max, Vector3i.CODEC, packetWrapper);
                    nBTCompound.set(
                            "match",
                            volumeMatchPredicate.match,
                            BlockPredicate.CODEC,
                            packetWrapper);
                }
            };

    private final Vector3i min;

    private final Vector3i max;

    private final BlockPredicate match;

    public VolumeMatchPredicate(
            Vector3i vector3i, Vector3i vector3i2, BlockPredicate blockPredicate) {
        this.min = vector3i;
        this.max = vector3i2;
        this.match = blockPredicate;
    }

    public Vector3i getMin() {
        return this.min;
    }

    public Vector3i getMax() {
        return this.max;
    }

    public BlockPredicate getMatch() {
        return this.match;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.VOLUME_MATCH;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (result != null && this.getClass() == result.getClass()) {
            VolumeMatchPredicate volumeMatchPredicate = (VolumeMatchPredicate) result;
            if (!this.min.equals(volumeMatchPredicate.min)) {
                return false;
            } else {
                return !this.max.equals(volumeMatchPredicate.max)
                        ? false
                        : this.match.equals(volumeMatchPredicate.match);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.min, this.max, this.match);
    }
}
