package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@NonExtendable
public class NBTSerializer<IN, OUT> implements NBTReader<NBT, IN>, NBTWriter<NBT, OUT> {

    protected final NBTSerializer.IdReader<IN> idReader;

    protected final NBTSerializer.IdWriter<OUT> idWriter;

    protected final NBTSerializer.NameReader<IN> nameReader;

    protected final NBTSerializer.NameWriter<OUT> nameWriter;

    protected final Map<Integer, NBTType<? extends NBT>> idToType = new HashMap<>();

    protected final Map<NBTType<? extends NBT>, Integer> typeToId = new HashMap<>();

    protected final Map<NBTType<? extends NBT>, NBTSerializer.TagReader<IN, ? extends NBT>>
            typeReaders = new HashMap<>();

    protected final Map<NBTType<? extends NBT>, NBTSerializer.TagWriter<OUT, ? extends NBT>>
            typeWriters = new HashMap<>();

    public NBTSerializer(
            NBTSerializer.IdReader<IN> idReader2,
            NBTSerializer.IdWriter<OUT> idWriter2,
            NBTSerializer.NameReader<IN> nameReader2,
            NBTSerializer.NameWriter<OUT> nameWriter2) {
        this.idReader = idReader2;
        this.idWriter = idWriter2;
        this.nameReader = nameReader2;
        this.nameWriter = nameWriter2;
    }

    @Override
    public NBT deserializeTag(NBTLimiter nBTLimiter, IN n, boolean matches) throws IOException {
        NBTType nBTType;
        if ((nBTType = this.readTagType(nBTLimiter, (IN) n)) == NBTType.END) {
            return null;
        }
        if (matches) {
            this.readTagName(nBTLimiter, (IN) n);
        }
        return this.readTag(nBTLimiter, (IN) n, nBTType);
    }

    @Override
    public void serializeTag(OUT oUT, NBT nBT, boolean matches) throws IOException {
        NBTType type = nBT.getType();
        this.writeTagType((OUT) oUT, type);
        if (nBT.getType() != NBTType.END) {
            if (matches) {
                this.writeTagName((OUT) oUT, "");
            }
            this.writeTag((OUT) oUT, nBT);
        }
    }

    protected <T extends NBT> void registerType(
            NBTType<T> nBTType,
            int value,
            NBTSerializer.TagReader<IN, T> tagReader,
            NBTSerializer.TagWriter<OUT, T> tagWriter) {
        if (this.typeToId.containsKey(nBTType)) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Nbt type {0} is already registered", nBTType));
        }
        if (this.idToType.containsKey(value)) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Nbt type id {0} is already registered", value));
        }
        this.idToType.put(value, nBTType);
        this.typeToId.put(nBTType, value);
        this.typeReaders.put(nBTType, tagReader);
        this.typeWriters.put(nBTType, tagWriter);
    }

    NBTType<?> readTagType(NBTLimiter nBTLimiter, IN n) throws IOException {
        int value = this.idReader.readId(nBTLimiter, (IN) n);
        NBTType nBTType;
        if ((nBTType = this.idToType.get(value)) == null) {
            throw new IOException(MessageFormat.format("Unknown nbt type id {0}", value));
        } else {
            return nBTType;
        }
    }

    @Internal
    String readTagName(NBTLimiter nBTLimiter, IN n) throws IOException {
        return this.nameReader.readName(nBTLimiter, (IN) n);
    }

    NBT readTag(NBTLimiter nBTLimiter, IN n, NBTType<?> nBTType) throws IOException {
        NBTSerializer.TagReader tagReader;
        if ((tagReader = this.typeReaders.get(nBTType)) == null) {
            throw new IOException(
                    MessageFormat.format("No reader registered for nbt type {0}", nBTType));
        } else {
            return tagReader.readTag(nBTLimiter, n);
        }
    }

    void writeTagType(OUT oUT, NBTType<?> nBTType) throws IOException {
        int value;
        if ((value = this.typeToId.getOrDefault(nBTType, -1)) == -1) {
            throw new IOException(MessageFormat.format("Unknown nbt type {0}", nBTType));
        }
        this.idWriter.writeId((OUT) oUT, value);
    }

    void writeTagName(OUT oUT, String text) throws IOException {
        this.nameWriter.writeName((OUT) oUT, text);
    }

    void writeTag(OUT oUT, NBT nBT) throws IOException {
        NBTSerializer.TagWriter tagWriter;
        if ((tagWriter = this.typeWriters.get(nBT.getType())) == null) {
            throw new IOException(
                    MessageFormat.format("No writer registered for nbt type {0}", nBT.getType()));
        }
        tagWriter.writeTag(oUT, nBT);
    }

    @FunctionalInterface
    protected interface IdReader<T> {

        int readId(NBTLimiter nBTLimiter, T t) throws IOException;
    }

    @FunctionalInterface
    protected interface IdWriter<T> {

        void writeId(T t, int value) throws IOException;
    }

    @FunctionalInterface
    protected interface NameReader<T> {

        String readName(NBTLimiter nBTLimiter, T t) throws IOException;
    }

    @FunctionalInterface
    protected interface NameWriter<T> {

        void writeName(T t, String text) throws IOException;
    }

    @FunctionalInterface
    protected interface TagReader<IN, T extends NBT> {

        T readTag(NBTLimiter nBTLimiter, IN n) throws IOException;
    }

    @FunctionalInterface
    public interface TagWriter<OUT, T extends NBT> {

        void writeTag(OUT oUT, T t) throws IOException;
    }
}
