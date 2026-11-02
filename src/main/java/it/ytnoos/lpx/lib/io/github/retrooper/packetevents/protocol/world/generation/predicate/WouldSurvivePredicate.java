package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.BlockStateCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class WouldSurvivePredicate implements BlockPredicate {

    public static final NbtMapCodec<WouldSurvivePredicate> MAP_CODEC =
            new NbtMapCodec<WouldSurvivePredicate>() {

                public WouldSurvivePredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    Vector3i vector3i =
                            StateTestingPredicate.OFFSET_CODEC.decode(nBTCompound, packetWrapper);
                    WrappedBlockState orThrow =
                            nBTCompound.getOrThrow("state", BlockStateCodec.CODEC, packetWrapper);
                    return new WouldSurvivePredicate(vector3i, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        WouldSurvivePredicate wouldSurvivePredicate)
                        throws NbtCodecException {
                    StateTestingPredicate.OFFSET_CODEC.encode(
                            nBTCompound, packetWrapper, wouldSurvivePredicate.offset);
                    nBTCompound.set(
                            "state",
                            wouldSurvivePredicate.state,
                            BlockStateCodec.CODEC,
                            packetWrapper);
                }
            };

    private final Vector3i offset;

    private final WrappedBlockState state;

    public WouldSurvivePredicate(Vector3i vector3i, WrappedBlockState wrappedBlockState) {
        this.offset = vector3i;
        this.state = wrappedBlockState;
    }

    public Vector3i getOffset() {
        return this.offset;
    }

    public WrappedBlockState getState() {
        return this.state;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.WOULD_SURVIVE;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            WouldSurvivePredicate wouldSurvivePredicate = (WouldSurvivePredicate) result;
            return !this.offset.equals(wouldSurvivePredicate.offset)
                    ? false
                    : this.state.equals(wouldSurvivePredicate.state);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset, this.state);
    }
}
