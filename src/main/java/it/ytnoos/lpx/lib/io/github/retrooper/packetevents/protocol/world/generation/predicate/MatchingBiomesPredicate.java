package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biome;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biomes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class MatchingBiomesPredicate implements BlockPredicate {

    public static final NbtMapCodec<MatchingBiomesPredicate> MAP_CODEC =
            new NbtMapCodec<MatchingBiomesPredicate>() {

                public MatchingBiomesPredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    MappedEntitySet<Biome> mappedEntitySet =
                            MappedEntitySet.decode(
                                    nBTCompound.getTagOrThrow("biomes"),
                                    packetWrapper,
                                    Biomes.getRegistry());
                    return new MatchingBiomesPredicate(mappedEntitySet);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        MatchingBiomesPredicate matchingBiomesPredicate)
                        throws NbtCodecException {
                    nBTCompound.setTag(
                            "biomes",
                            MappedEntitySet.encode(packetWrapper, matchingBiomesPredicate.biomes));
                }
            };

    private final MappedEntitySet<Biome> biomes;

    public MatchingBiomesPredicate(MappedEntitySet<Biome> mappedEntitySet) {
        this.biomes = mappedEntitySet;
    }

    public MappedEntitySet<Biome> getBiomes() {
        return this.biomes;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.MATCHING_BIOMES;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            MatchingBiomesPredicate matchingBiomesPredicate = (MatchingBiomesPredicate) result;
            return this.biomes.equals(matchingBiomesPredicate.biomes);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.biomes);
    }
}
