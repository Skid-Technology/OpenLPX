package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.fluids.Fluid;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.fluids.Fluids;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class MatchingFluidsPredicate extends StateTestingPredicate {

    public static final NbtMapCodec<MatchingFluidsPredicate> MAP_CODEC =
            new NbtMapCodec<MatchingFluidsPredicate>() {

                private final NbtCodec<MappedEntitySet<Fluid>> fluidsCodec =
                        MappedEntitySet.codec(Fluids.getRegistry());

                public MatchingFluidsPredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    Vector3i vector3i =
                            StateTestingPredicate.OFFSET_CODEC.decode(nBTCompound, packetWrapper);
                    MappedEntitySet<Fluid> orThrow =
                            nBTCompound.getOrThrow("fluids", this.fluidsCodec, packetWrapper);
                    return new MatchingFluidsPredicate(vector3i, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        MatchingFluidsPredicate matchingFluidsPredicate)
                        throws NbtCodecException {
                    StateTestingPredicate.OFFSET_CODEC.encode(
                            nBTCompound, packetWrapper, matchingFluidsPredicate.offset);
                    nBTCompound.set(
                            "fluids",
                            matchingFluidsPredicate.fluids,
                            this.fluidsCodec,
                            packetWrapper);
                }
            };

    private final MappedEntitySet<Fluid> fluids;

    public MatchingFluidsPredicate(Vector3i vector3i, MappedEntitySet<Fluid> mappedEntitySet) {
        super(vector3i);
        this.fluids = mappedEntitySet;
    }

    public MappedEntitySet<Fluid> getFluids() {
        return this.fluids;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.MATCHING_FLUIDS;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            MatchingFluidsPredicate matchingFluidsPredicate = (MatchingFluidsPredicate) result;
            return !this.offset.equals(matchingFluidsPredicate.offset)
                    ? false
                    : this.fluids.equals(matchingFluidsPredicate.fluids);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset, this.fluids);
    }
}
