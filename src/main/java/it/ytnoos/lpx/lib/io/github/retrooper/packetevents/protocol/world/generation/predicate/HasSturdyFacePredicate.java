package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Direction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class HasSturdyFacePredicate implements BlockPredicate {

    public static final NbtMapCodec<HasSturdyFacePredicate> MAP_CODEC =
            new NbtMapCodec<HasSturdyFacePredicate>() {

                public HasSturdyFacePredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    Vector3i vector3i =
                            StateTestingPredicate.OFFSET_CODEC.decode(nBTCompound, packetWrapper);
                    Direction orThrow =
                            nBTCompound.getOrThrow("direction", Direction.CODEC, packetWrapper);
                    return new HasSturdyFacePredicate(vector3i, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        HasSturdyFacePredicate hasSturdyFacePredicate)
                        throws NbtCodecException {
                    StateTestingPredicate.OFFSET_CODEC.encode(
                            nBTCompound, packetWrapper, hasSturdyFacePredicate.offset);
                    nBTCompound.set(
                            "direction",
                            hasSturdyFacePredicate.direction,
                            Direction.CODEC,
                            packetWrapper);
                }
            };

    private final Vector3i offset;

    private final Direction direction;

    public HasSturdyFacePredicate(Vector3i vector3i, Direction direction2) {
        this.offset = vector3i;
        this.direction = direction2;
    }

    public Vector3i getOffset() {
        return this.offset;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.HAS_STURDY_FACE;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            HasSturdyFacePredicate hasSturdyFacePredicate = (HasSturdyFacePredicate) result;
            return !this.offset.equals(hasSturdyFacePredicate.offset)
                    ? false
                    : this.direction == hasSturdyFacePredicate.direction;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset, this.direction);
    }
}
