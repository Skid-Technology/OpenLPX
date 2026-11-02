package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NullMarked
public class RandomWeightedList<T> implements Iterable<RandomWeightedList.Entry<T>> {

    private List<RandomWeightedList.Entry<T>> entries;

    public RandomWeightedList() {
        this(new ArrayList<>());
    }

    public RandomWeightedList(List<RandomWeightedList.Entry<T>> entries) {
        this.entries = entries;
    }

    public RandomWeightedList(T t, int value) {
        this(new RandomWeightedList.Entry<>((T) t, value));
    }

    public RandomWeightedList(RandomWeightedList.Entry<T> entry) {
        this.entries = new ArrayList<>(1);
        this.entries.add(entry);
    }

    public static <T> NbtCodec<RandomWeightedList<T>> codec(NbtCodec<T> nbtCodec) {
        return RandomWeightedList.Entry.<T>codec(nbtCodec)
                .applyList()
                .apply(RandomWeightedList::new, RandomWeightedList::getEntries);
    }

    @Deprecated
    public static <T> RandomWeightedList<T> decode(
            NBT nBT, ClientVersion clientVersion, ComponentType.Decoder<T> decoder) {
        ArrayList values;
        if (nBT instanceof NBTCompound) {
            (values = new ArrayList(1))
                    .add(RandomWeightedList.Entry.decode(nBT, clientVersion, decoder));
        } else {
            if (!(nBT instanceof NBTList)) {
                throw new UnsupportedOperationException(
                        "Can't decode " + nBT + " as random weighted list");
            }
            NBTList nBTList = (NBTList) nBT;
            values = new ArrayList(nBTList.size());
            for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) nBTList.getTags()))) {
                values.add(RandomWeightedList.Entry.decode(nBT2, clientVersion, decoder));
            }
        }
        return new RandomWeightedList<>(values);
    }

    @Deprecated
    public static <T> NBT encode(
            RandomWeightedList<T> randomWeightedList,
            ClientVersion clientVersion,
            ComponentType.Encoder<T> encoder) {
        NBTList nBTList = new NBTList<>(NBTType.COMPOUND, randomWeightedList.entries.size());
        for (RandomWeightedList.Entry entry : randomWeightedList.entries) {
            nBTList.addTag(RandomWeightedList.Entry.encode(entry, clientVersion, encoder));
        }
        return nBTList;
    }

    public List<RandomWeightedList.Entry<T>> getEntries() {
        return this.entries;
    }

    public void setEntries(List<RandomWeightedList.Entry<T>> entries) {
        this.entries = entries;
    }

    public int size() {
        return this.entries.size();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public Iterator<RandomWeightedList.Entry<T>> iterator() {
        return this.entries.iterator();
    }

    public static final class Entry<T> {

        private final T data;

        private final int weight;

        public Entry(T t, int weight) {
            this.data = (T) t;
            this.weight = weight;
        }

        public static <T> NbtCodec<RandomWeightedList.Entry<T>> codec(final NbtCodec<T> nbtCodec) {
            return (new NbtMapCodec<RandomWeightedList.Entry<T>>() {

                        public RandomWeightedList.Entry<T> decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            int asInt = nBTCompound.getNumberTagOrThrow("weight").getAsInt();
                            T orThrow = nBTCompound.getOrThrow("data", nbtCodec, packetWrapper);
                            return new RandomWeightedList.Entry<>((T) orThrow, asInt);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                RandomWeightedList.Entry<T> asInt)
                                throws NbtCodecException {
                            nBTCompound.setTag("weight", new NBTInt(asInt.weight));
                            nBTCompound.set("data", asInt.data, nbtCodec, packetWrapper);
                        }
                    })
                    .codec();
        }

        @Deprecated
        public static <T> RandomWeightedList.Entry<T> decode(
                NBT nBT, ClientVersion clientVersion, ComponentType.Decoder<T> decoder) {
            NBTCompound nBTCompound;
            int asInt = (nBTCompound = (NBTCompound) nBT).getNumberTagOrThrow("weight").getAsInt();
            T t = decoder.decode(nBTCompound.getTagOrThrow("data"), clientVersion);
            return new RandomWeightedList.Entry<>((T) t, asInt);
        }

        @Deprecated
        public static <T> NBTCompound encode(
                RandomWeightedList.Entry<T> entry,
                ClientVersion clientVersion,
                ComponentType.Encoder<T> encoder) {
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound()).setTag("weight", new NBTInt(entry.weight));
            nBTCompound.setTag("data", encoder.encode(entry.data, clientVersion));
            return nBTCompound;
        }

        public T getData() {
            return this.data;
        }

        public int getWeight() {
            return this.weight;
        }
    }
}
