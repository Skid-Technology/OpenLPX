package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class NotPredicate implements BlockPredicate {

    public static final NbtMapCodec<NotPredicate> MAP_CODEC =
            new NbtMapCodec<NotPredicate>() {

                public NotPredicate decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    BlockPredicate orThrow =
                            nBTCompound.getOrThrow(
                                    "predicate", BlockPredicate.CODEC, packetWrapper);
                    return new NotPredicate(orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        NotPredicate notPredicate)
                        throws NbtCodecException {
                    nBTCompound.set(
                            "predicate",
                            notPredicate.predicate,
                            BlockPredicate.CODEC,
                            packetWrapper);
                }
            };

    private final BlockPredicate predicate;

    public NotPredicate(BlockPredicate blockPredicate) {
        this.predicate = blockPredicate;
    }

    public BlockPredicate getPredicate() {
        return this.predicate;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.NOT;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            NotPredicate notPredicate = (NotPredicate) result;
            return this.predicate.equals(notPredicate.predicate);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.predicate);
    }
}
