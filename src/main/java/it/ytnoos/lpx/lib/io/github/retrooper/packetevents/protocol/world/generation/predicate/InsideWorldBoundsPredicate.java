package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class InsideWorldBoundsPredicate implements BlockPredicate {

    public static final NbtMapCodec<InsideWorldBoundsPredicate> MAP_CODEC =
            StateTestingPredicate.OFFSET_CODEC.apply(
                    InsideWorldBoundsPredicate::new, InsideWorldBoundsPredicate::getOffset);

    private final Vector3i offset;

    public InsideWorldBoundsPredicate(Vector3i vector3i) {
        this.offset = vector3i;
    }

    public Vector3i getOffset() {
        return this.offset;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.INSIDE_WORLD_BOUNDS;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            InsideWorldBoundsPredicate insideWorldBoundsPredicate =
                    (InsideWorldBoundsPredicate) result;
            return this.offset.equals(insideWorldBoundsPredicate.offset);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset);
    }
}
