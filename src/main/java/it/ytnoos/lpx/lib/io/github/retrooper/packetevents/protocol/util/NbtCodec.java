package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@NullMarked
public interface NbtCodec<T> extends NbtEncoder<T>, NbtDecoder<T> {

    static <T> NbtCodec<T> codec(final NbtEncoder<T> nbtEncoder, final NbtDecoder<T> nbtDecoder) {
        return new NbtCodec<T>() {

            @Override
            public T decode(NBT result, PacketWrapper<?> result2) {
                return (T) nbtDecoder.decode(result, result2);
            }

            @Override
            public NBT encode(PacketWrapper<?> result, T result2) {
                return nbtEncoder.encode(result, result2);
            }
        };
    }

    default NbtCodec<T> validate(final Predicate<T> predicate) {
        return new NbtCodec<T>() {

            @Override
            public T decode(NBT result, PacketWrapper<?> result2) throws NbtCodecException {
                T t = NbtCodec.this.decode(result, result2);
                if (!predicate.test(t)) {
                    throw new NbtCodecException("Decode predicate failed " + predicate);
                } else {
                    return (T) t;
                }
            }

            @Override
            public NBT encode(PacketWrapper<?> result, T result2) throws NbtCodecException {
                if (!predicate.test(result2)) {
                    throw new NbtCodecException("Encode predicate failed " + predicate);
                } else {
                    return NbtCodec.this.encode(result, result2);
                }
            }
        };
    }

    default <Z> NbtCodec<Z> apply(final Function<T, Z> function, final Function<Z, T> function2) {
        return new NbtCodec<Z>() {

            @Override
            public Z decode(NBT result, PacketWrapper<?> result2) {
                return (Z) function.apply(NbtCodec.this.decode(result, result2));
            }

            @Override
            public NBT encode(PacketWrapper<?> result, Z result2) {
                return NbtCodec.this.encode(result, function2.apply(result2));
            }
        };
    }

    default NbtCodec<List<T>> applyFixedList(int value) {
        NbtCodec<List<T>> nbtCodec = this.applyList();
        return nbtCodec.validate(result -> result.size() == value);
    }

    default NbtCodec<List<T>> applyList() {
        return new NbtCodec<List<T>>() {

            public List<T> decode(NBT result, PacketWrapper<?> result2) throws NbtCodecException {
                try {
                    List<NBT> result3 =
                            ((List<NBT>) NbtCodecs.GENERIC_LIST.decode(result, result2));
                    ArrayList<T> values = new ArrayList<>(result3.size());
                    for (NBT nBT : result3) {
                        values.add(NbtCodec.this.decode(nBT, result2));
                    }
                    return values;
                } catch (NbtCodecException nbtCodecException) {
                    try {
                        return Collections.singletonList((T) NbtCodec.this.decode(result, result2));
                    } catch (NbtCodecException nbtCodecException2) {
                        nbtCodecException.addSuppressed(nbtCodecException2);
                        throw nbtCodecException;
                    }
                }
            }

            public NBT encode(PacketWrapper<?> result, List<T> result2) {
                ArrayList<NBT> result3 = new ArrayList<>(result2.size());
                for (Object result4 : result2) {
                    result3.add(NbtCodec.this.encode(result, ((T) result4)));
                }
                return NbtCodecs.GENERIC_LIST.encode(result, result3);
            }
        };
    }

    default NbtCodec<T> withAlternative(final NbtDecoder<T> nbtDecoder) {
        return new NbtCodec<T>() {

            @Override
            public T decode(NBT result, PacketWrapper<?> result2) throws NbtCodecException {
                try {
                    return (T) NbtCodec.this.decode(result, result2);
                } catch (NbtCodecException nbtCodecException) {
                    try {
                        return (T) nbtDecoder.decode(result, result2);
                    } catch (NbtCodecException nbtCodecException2) {
                        nbtCodecException.addSuppressed(nbtCodecException2);
                        throw nbtCodecException;
                    }
                }
            }

            @Override
            public NBT encode(PacketWrapper<?> result, T result2) throws NbtCodecException {
                return NbtCodec.this.encode(result, result2);
            }
        };
    }
}
