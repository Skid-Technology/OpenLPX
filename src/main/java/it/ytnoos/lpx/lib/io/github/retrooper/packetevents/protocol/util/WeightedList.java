package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NullMarked
public final class WeightedList<T> {

    private final List<WeightedList.Entry<T>> entries;

    public WeightedList() {
        this(new ArrayList<>());
    }

    public WeightedList(List<WeightedList.Entry<T>> entries) {
        this.entries = entries;
    }

    public static <T> NbtCodec<WeightedList<T>> codec(NbtCodec<T> nbtCodec) {
        return WeightedList.Entry.<T>codec(nbtCodec)
                .applyList()
                .apply(WeightedList::new, WeightedList::getEntries);
    }

    public static <T> WeightedList<T> read(
            PacketWrapper<?> packetWrapper, PacketWrapper.Reader<T> reader) {
        List values =
                packetWrapper.readList(
                        result ->
                                WeightedList.Entry.read(
                                        ((PacketWrapper) packetWrapper),
                                        ((PacketWrapper.Reader) reader)));
        return new WeightedList<>(values);
    }

    public static <T> void write(
            PacketWrapper<?> packetWrapper,
            WeightedList<T> weightedList,
            PacketWrapper.Writer<T> writer) {
        packetWrapper.writeList(
                weightedList.entries,
                (result, result2) ->
                        WeightedList.Entry.write(result, result2, ((PacketWrapper.Writer) writer)));
    }

    public List<WeightedList.Entry<T>> getEntries() {
        return this.entries;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (!(result instanceof WeightedList)) {
            return false;
        }
        WeightedList weightedList = (WeightedList) result;
        return this.entries.equals(weightedList.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.entries);
    }

    public static final class Entry<T> {

        private final int weight;

        private final T value;

        public Entry(int weight, T t) {
            this.weight = weight;
            this.value = (T) t;
        }

        public static <T> NbtCodec<WeightedList.Entry<T>> codec(final NbtCodec<T> nbtCodec) {
            return (new NbtMapCodec<WeightedList.Entry<T>>() {

                        public WeightedList.Entry<T> decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            T orThrow = nBTCompound.getOrThrow("data", nbtCodec, packetWrapper);
                            int value = nBTCompound.getNumberTagValueOrThrow("weight").intValue();
                            return new WeightedList.Entry<>(value, (T) orThrow);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                WeightedList.Entry<T> orThrow)
                                throws NbtCodecException {
                            nBTCompound.set("data", orThrow.getValue(), nbtCodec, packetWrapper);
                            nBTCompound.setTag("weight", new NBTInt(orThrow.getWeight()));
                        }
                    })
                    .codec();
        }

        public static <T> WeightedList.Entry<T> read(
                PacketWrapper<?> packetWrapper, PacketWrapper.Reader<T> reader) {
            T t = reader.apply(packetWrapper);
            int value = packetWrapper.readVarInt();
            return new WeightedList.Entry<>(value, (T) t);
        }

        public static <T> void write(
                PacketWrapper<?> packetWrapper,
                WeightedList.Entry<T> entry,
                PacketWrapper.Writer<T> writer) {
            writer.accept(packetWrapper, entry.value);
            packetWrapper.writeVarInt(entry.weight);
        }

        public int getWeight() {
            return this.weight;
        }

        public T getValue() {
            return this.value;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (!(result instanceof WeightedList.Entry)) {
                return false;
            }
            WeightedList.Entry entry = (WeightedList.Entry) result;
            return this.weight != entry.weight ? false : this.value.equals(entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.weight, this.value);
        }
    }
}
