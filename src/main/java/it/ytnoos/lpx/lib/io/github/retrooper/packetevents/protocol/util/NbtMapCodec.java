package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

@NullMarked
public interface NbtMapCodec<T> extends NbtMapEncoder<T>, NbtMapDecoder<T> {

    static <K, V> NbtMapCodec<Map<K, V>> codecOfMap(
            NbtCodec<K> nbtCodec, NbtCodec<? extends V> nbtCodec2) {
        return codecOfMap(nbtCodec, result -> ((NbtCodec) nbtCodec2));
    }

    static <K, V> NbtMapCodec<Map<K, V>> codecOfMap(
            NbtCodec<K> nbtCodec, Function<K, NbtCodec<? extends V>> function) {
        return codecOfMap(nbtCodec, null, function);
    }

    static <K, V> NbtMapCodec<Map<K, V>> codecOfMap(
            final NbtCodec<K> nbtCodec,
            @Nullable final Predicate<K> predicate,
            final Function<K, NbtCodec<? extends V>> function) {
        return new NbtMapCodec<Map<K, V>>() {

            public Map<K, V> decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                    throws NbtCodecException {
                HashMap entries2 = new HashMap(nBTCompound.size());
                for (Entry<String, NBT> result : nBTCompound.getTags().entrySet()) {
                    K value =
                            nbtCodec.decode(new NBTString((String) result.getKey()), packetWrapper);
                    if (predicate == null || predicate.test(value)) {
                        Object result2 =
                                ((NbtCodec) function.apply(value))
                                        .decode((NBT) result.getValue(), packetWrapper);
                        entries2.put(value, result2);
                    }
                }
                return Collections.unmodifiableMap(entries2);
            }

            public void encode(
                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, Map<K, V> entries)
                    throws NbtCodecException {
                for (Entry<K, V> result : entries.entrySet()) {
                    if (predicate == null || predicate.test(result.getKey())) {
                        String value =
                                nbtCodec.encode(packetWrapper, result.getKey())
                                        .castOrThrow(NBTString.class)
                                        .getValue();
                        NBT nBT =
                                ((NbtCodec) function.apply(result.getKey()))
                                        .encode(packetWrapper, result.getValue());
                        nBTCompound.setTag(value, nBT);
                    }
                }
            }
        };
    }

    default NbtCodec<T> codec() {
        return new NbtCodec<T>() {

            @Override
            public T decode(NBT result, PacketWrapper<?> result2) throws NbtCodecException {
                NBTCompound nBTCompound = result.castOrThrow(NBTCompound.class);
                return (T) NbtMapCodec.this.decode(nBTCompound, result2);
            }

            @Override
            public NBT encode(PacketWrapper<?> result, T result2) throws NbtCodecException {
                NBTCompound nBTCompound = new NBTCompound();
                NbtMapCodec.this.encode(nBTCompound, result, result2);
                return nBTCompound;
            }
        };
    }

    default <Z> NbtMapCodec<Z> apply(
            final Function<T, Z> function, final Function<Z, T> function2) {
        return new NbtMapCodec<Z>() {

            @Override
            public Z decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                    throws NbtCodecException {
                return (Z) function.apply(NbtMapCodec.this.decode(nBTCompound, packetWrapper));
            }

            @Override
            public void encode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, Z z)
                    throws NbtCodecException {
                NbtMapCodec.this.encode(nBTCompound, packetWrapper, function2.apply(z));
            }
        };
    }
}
