package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class AllOfPredicate extends CombiningPredicate {

    public static final NbtMapCodec<AllOfPredicate> MAP_CODEC = codec(AllOfPredicate::new);

    public AllOfPredicate(List<BlockPredicate> values) {
        super(values);
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.ALL_OF;
    }
}
