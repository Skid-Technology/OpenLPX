package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class MatchingBlockTagPredicate extends StateTestingPredicate {

    public static final NbtMapCodec<MatchingBlockTagPredicate> MAP_CODEC =
            new NbtMapCodec<MatchingBlockTagPredicate>() {

                public MatchingBlockTagPredicate decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    Vector3i vector3i =
                            StateTestingPredicate.OFFSET_CODEC.decode(nBTCompound, packetWrapper);
                    ResourceLocation orThrow =
                            nBTCompound.getOrThrow("tag", ResourceLocation.CODEC, packetWrapper);
                    return new MatchingBlockTagPredicate(vector3i, orThrow);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        MatchingBlockTagPredicate matchingBlockTagPredicate)
                        throws NbtCodecException {
                    StateTestingPredicate.OFFSET_CODEC.encode(
                            nBTCompound, packetWrapper, matchingBlockTagPredicate.offset);
                    nBTCompound.set(
                            "tag",
                            matchingBlockTagPredicate.tagKey,
                            ResourceLocation.CODEC,
                            packetWrapper);
                }
            };

    private final ResourceLocation tagKey;

    public MatchingBlockTagPredicate(Vector3i vector3i, ResourceLocation resourceLocation) {
        super(vector3i);
        this.tagKey = resourceLocation;
    }

    public ResourceLocation getTagKey() {
        return this.tagKey;
    }

    @Override
    public BlockPredicateType<?> getType() {
        return BlockPredicateTypes.MATCHING_BLOCK_TAG;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            MatchingBlockTagPredicate matchingBlockTagPredicate =
                    (MatchingBlockTagPredicate) result;
            return !this.offset.equals(matchingBlockTagPredicate.offset)
                    ? false
                    : this.tagKey.equals(matchingBlockTagPredicate.tagKey);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.offset, this.tagKey);
    }
}
