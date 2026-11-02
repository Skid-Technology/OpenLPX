package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class ReplaceablePredicate extends StateTestingPredicate {

    public static final NbtMapCodec<ReplaceablePredicate> MAP_CODEC =
            OFFSET_CODEC.apply(ReplaceablePredicate::new, StateTestingPredicate::getOffset);

    public ReplaceablePredicate(Vector3i vector3i) {
        super(vector3i);
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.REPLACEABLE;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ReplaceablePredicate replaceablePredicate = (ReplaceablePredicate) result;
            return this.offset.equals(replaceablePredicate.offset);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset);
    }
}
