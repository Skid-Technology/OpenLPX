package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NullMarked
public abstract class CombiningPredicate implements BlockPredicate {

    protected final List<BlockPredicate> predicates;

    protected CombiningPredicate(List<BlockPredicate> values) {
        this.predicates = Collections.unmodifiableList(values);
    }

    protected static <T extends CombiningPredicate> NbtMapCodec<T> codec(
            final CombiningPredicate.ListDecoder<T> listDecoder) {
        return new NbtMapCodec<T>() {

            public T decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                    throws NbtCodecException {
                List<BlockPredicate> listOrEmpty =
                        nBTCompound.getListOrEmpty(
                                "predicates", BlockPredicate.CODEC, packetWrapper);
                return (T) listDecoder.decode(listOrEmpty);
            }

            public void encode(
                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, T listOrEmpty)
                    throws NbtCodecException {
                nBTCompound.setList(
                        "predicates", listOrEmpty.predicates, BlockPredicate.CODEC, packetWrapper);
            }
        };
    }

    public List<BlockPredicate> getPredicates() {
        return this.predicates;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            CombiningPredicate combiningPredicate = (CombiningPredicate) result;
            return this.predicates.equals(combiningPredicate.predicates);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.predicates);
    }

    @FunctionalInterface
    protected interface ListDecoder<T> {

        T decode(List<BlockPredicate> values);
    }
}
