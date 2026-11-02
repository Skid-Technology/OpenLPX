package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class TrueBlockPredicate implements BlockPredicate {

    public static final TrueBlockPredicate INSTANCE = new TrueBlockPredicate();

    public static final NbtMapCodec<TrueBlockPredicate> MAP_CODEC =
            NbtCodecs.forUnit(() -> INSTANCE);

    private TrueBlockPredicate() {}

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.TRUE;
    }
}
