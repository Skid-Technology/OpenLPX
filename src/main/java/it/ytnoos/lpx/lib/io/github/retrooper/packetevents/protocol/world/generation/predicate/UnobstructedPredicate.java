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
public final class UnobstructedPredicate implements BlockPredicate {

    public static final NbtMapCodec<UnobstructedPredicate> MAP_CODEC =
            new NbtMapCodec<UnobstructedPredicate>() {

                public UnobstructedPredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return new UnobstructedPredicate(
                            StateTestingPredicate.OFFSET_CODEC.decode(nBTCompound, packetWrapper));
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        UnobstructedPredicate unobstructedPredicate)
                        throws NbtCodecException {
                    StateTestingPredicate.OFFSET_CODEC.encode(
                            nBTCompound, packetWrapper, unobstructedPredicate.offset);
                }
            };

    private final Vector3i offset;

    public UnobstructedPredicate(Vector3i vector3i) {
        this.offset = vector3i;
    }

    public Vector3i getOffset() {
        return this.offset;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.UNOBSTRUCTED;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            UnobstructedPredicate unobstructedPredicate = (UnobstructedPredicate) result;
            return this.offset.equals(unobstructedPredicate.offset);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset);
    }
}
