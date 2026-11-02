package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class MatchingBlocksPredicate extends StateTestingPredicate {

    public static final NbtMapCodec<MatchingBlocksPredicate> MAP_CODEC =
            new NbtMapCodec<MatchingBlocksPredicate>() {

                private final NbtCodec<MappedEntitySet<StateType.Mapped>> blocksCodec =
                        MappedEntitySet.codec(StateTypes.getRegistry());

                public MatchingBlocksPredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    Vector3i vector3i =
                            StateTestingPredicate.OFFSET_CODEC.decode(nBTCompound, packetWrapper);
                    MappedEntitySet<StateType.Mapped> orThrow =
                            nBTCompound.getOrThrow("blocks", this.blocksCodec, packetWrapper);
                    return new MatchingBlocksPredicate(vector3i, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        MatchingBlocksPredicate matchingBlocksPredicate)
                        throws NbtCodecException {
                    StateTestingPredicate.OFFSET_CODEC.encode(
                            nBTCompound, packetWrapper, matchingBlocksPredicate.offset);
                    nBTCompound.set(
                            "blocks",
                            matchingBlocksPredicate.blocks,
                            this.blocksCodec,
                            packetWrapper);
                }
            };

    private final MappedEntitySet<StateType.Mapped> blocks;

    public MatchingBlocksPredicate(
            Vector3i vector3i, MappedEntitySet<StateType.Mapped> mappedEntitySet) {
        super(vector3i);
        this.blocks = mappedEntitySet;
    }

    public MappedEntitySet<StateType.Mapped> getBlocks() {
        return this.blocks;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.MATCHING_BLOCKS;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            MatchingBlocksPredicate matchingBlocksPredicate = (MatchingBlocksPredicate) result;
            return !this.offset.equals(matchingBlocksPredicate.offset)
                    ? false
                    : this.blocks.equals(matchingBlocksPredicate.blocks);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset, this.blocks);
    }
}
