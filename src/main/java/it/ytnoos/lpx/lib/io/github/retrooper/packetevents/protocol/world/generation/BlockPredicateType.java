package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface BlockPredicateType<T extends BlockPredicate> extends MappedEntity {

    NbtCodec<BlockPredicateType<?>> CODEC =
            NbtCodecs.forRegistry(BlockPredicateTypes.getRegistry());

    NbtMapCodec<T> getCodec();
}
