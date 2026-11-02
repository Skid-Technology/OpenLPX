package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class SequentialNBTReader implements NBTReader<NBT, DataInputStream> {

    public static final SequentialNBTReader INSTANCE = new SequentialNBTReader();

    private static final Map<NBTType<?>, SequentialNBTReader.TagSkip> TAG_SKIPS = new HashMap<>(16);

    private static final Map<NBTType<?>, SequentialNBTReader.TagBinaryReader> TAG_BINARY_READERS =
            new HashMap<>(16);

    public NBT deserializeTag(
            NBTLimiter nBTLimiter, DataInputStream dataInputStream, boolean matches)
            throws IOException {
        NBTType nBTType = DefaultNBTSerializer.INSTANCE.readTagType(nBTLimiter, dataInputStream);
        if (matches) {
            int value = dataInputStream.readUnsignedShort();
            dataInputStream.skipBytes(value);
        }
        NBT nBT;
        if (nBTType == NBTType.COMPOUND) {
            nBT = new SequentialNBTReader.Compound(dataInputStream, nBTLimiter, () -> {});
        } else if (nBTType == NBTType.LIST) {
            nBT = new SequentialNBTReader.List(dataInputStream, nBTLimiter, () -> {});
        } else {
            nBT = DefaultNBTSerializer.INSTANCE.readTag(nBTLimiter, dataInputStream, nBTType);
        }
        return nBT;
    }

    private static void checkReadable(NBT nBT) {
        if (nBT != null) {
            if (nBT instanceof Iterator && ((Iterator) nBT).hasNext()) {
                throw new IllegalStateException("Previous nbt has not been read completely");
            }
        }
    }

    public static void intToBytes(byte[] values, int value, int value2) {
        values[value2] = (byte) (value >>> 24 & 0xFF);
        values[value2 + 1] = (byte) (value >>> 16 & 0xFF);
        values[value2 + 2] = (byte) (value >>> 8 & 0xFF);
        values[value2 + 3] = (byte) (value & 0xFF);
    }

    static {
        TAG_SKIPS.put(
                NBTType.BYTE,
                (result, result2) -> {
                    result.increment(9);
                    result2.skipBytes(1);
                });
        TAG_SKIPS.put(
                NBTType.SHORT,
                (result, result2) -> {
                    result.increment(10);
                    result2.skipBytes(2);
                });
        TAG_SKIPS.put(
                NBTType.INT,
                (result, result2) -> {
                    result.increment(12);
                    result2.skipBytes(4);
                });
        TAG_SKIPS.put(
                NBTType.LONG,
                (result, result2) -> {
                    result.increment(16);
                    result2.skipBytes(8);
                });
        TAG_SKIPS.put(
                NBTType.FLOAT,
                (result, result2) -> {
                    result.increment(12);
                    result2.skipBytes(4);
                });
        TAG_SKIPS.put(
                NBTType.DOUBLE,
                (result, result2) -> {
                    result.increment(16);
                    result2.skipBytes(8);
                });
        TAG_SKIPS.put(
                NBTType.BYTE_ARRAY,
                (result, result2) -> {
                    result.increment(24);
                    int result5 = result2.readInt();
                    result.increment(result5 * 1);
                    result.checkReadability(result5 * 1);
                    result2.skipBytes(result5);
                });
        TAG_SKIPS.put(
                NBTType.STRING,
                (result, result2) -> {
                    result.increment(36);
                    int result5 = result2.readUnsignedShort();
                    result.increment(result5 * 2);
                    result2.skipBytes(result5);
                });
        TAG_SKIPS.put(
                NBTType.LIST,
                (result, result2) -> {
                    result.enterDepth();
                    try {
                        result.increment(36);
                        NBTType result6 =
                                DefaultNBTSerializer.INSTANCE.readTagType(result, result2);
                        int result7 = result2.readInt();
                        result.increment(result7 * 4);
                        for (int result8 = 0; result8 < result7; result8++) {
                            TAG_SKIPS.get(result6).skip(result, result2);
                        }
                    } finally {
                        result.exitDepth();
                    }
                });
        TAG_SKIPS.put(
                NBTType.COMPOUND,
                (result, result2) -> {
                    result.enterDepth();
                    try {
                        result.increment(48);
                        HashSet result6 = new HashSet();
                        NBTType result7;
                        while ((result7 =
                                        DefaultNBTSerializer.INSTANCE.readTagType(result, result2))
                                != NBTType.END) {
                            String result8 = DefaultNBTSerializer.readString(result, result2);
                            if (result6.add(result8)) {
                                result.increment(36);
                            }
                            TAG_SKIPS.get(result7).skip(result, result2);
                        }
                    } finally {
                        result.exitDepth();
                    }
                });
        TAG_SKIPS.put(
                NBTType.INT_ARRAY,
                (result, result2) -> {
                    result.increment(24);
                    int result5 = result2.readInt();
                    result.increment(result5 * 4);
                    result.checkReadability(result5 * 4);
                    result2.skipBytes(result5 * 4);
                });
        TAG_SKIPS.put(
                NBTType.LONG_ARRAY,
                (result, result2) -> {
                    result.increment(24);
                    int result5 = result2.readInt();
                    result.increment(result5 * 8);
                    result.checkReadability(result5 * 8);
                    result2.skipBytes(result5 * 8);
                });
        TAG_BINARY_READERS.put(
                NBTType.BYTE,
                (result, result2) -> {
                    result.increment(9);
                    return new byte[] {result2.readByte()};
                });
        TAG_BINARY_READERS.put(
                NBTType.SHORT,
                (result, result2) -> {
                    result.increment(10);
                    return new byte[] {result2.readByte(), result2.readByte()};
                });
        TAG_BINARY_READERS.put(
                NBTType.INT,
                (result, result2) -> {
                    result.increment(12);
                    byte[] result5 = new byte[4];
                    result2.readFully(result5);
                    return result5;
                });
        TAG_BINARY_READERS.put(
                NBTType.LONG,
                (result, result2) -> {
                    result.increment(16);
                    byte[] result5 = new byte[8];
                    result2.readFully(result5);
                    return result5;
                });
        TAG_BINARY_READERS.put(
                NBTType.FLOAT,
                (result, result2) -> {
                    result.increment(12);
                    byte[] result5 = new byte[4];
                    result2.readFully(result5);
                    return result5;
                });
        TAG_BINARY_READERS.put(
                NBTType.DOUBLE,
                (result, result2) -> {
                    result.increment(16);
                    byte[] result5 = new byte[8];
                    result2.readFully(result5);
                    return result5;
                });
        TAG_BINARY_READERS.put(
                NBTType.BYTE_ARRAY,
                (result, result2) -> {
                    result.increment(24);
                    int result6;
                    if ((result6 = result2.readInt()) >= 16777216) {
                        throw new IllegalArgumentException(
                                "Byte array length is too large: " + result6);
                    }
                    result.increment(result6 * 1);
                    result.checkReadability(result6 * 1);
                    byte[] result7;
                    intToBytes(result7 = new byte[4 + result6], result6, 0);
                    result2.readFully(result7, 4, result6);
                    return result7;
                });
        TAG_BINARY_READERS.put(
                NBTType.STRING,
                (result, result2) -> {
                    result.increment(36);
                    String result5 = result2.readUTF();
                    result.increment(result5.length() * 2);
                    ByteArrayOutputStream result6 = new ByteArrayOutputStream();
                    byte[] result8;
                    try {
                        DataOutputStream result7 = new DataOutputStream(result6);
                        try {
                            result7.writeUTF(result5);
                            result8 = result6.toByteArray();
                        } catch (Throwable throwable) {
                            try {
                                result7.close();
                            } catch (Throwable index) {
                                throwable.addSuppressed(index);
                            }
                            throw throwable;
                        }
                        result7.close();
                    } catch (Throwable throwable2) {
                        try {
                            result6.close();
                        } catch (Throwable result3) {
                            throwable2.addSuppressed(result3);
                        }
                        throw throwable2;
                    }
                    result6.close();
                    return result8;
                });
        TAG_BINARY_READERS.put(
                NBTType.LIST,
                (result, result2) -> {
                    result.enterDepth();
                    byte[] values;
                    try {
                        result.increment(36);
                        byte result6 = result2.readByte();
                        NBTType result7 =
                                DefaultNBTSerializer.INSTANCE.idToType.get(
                                        Integer.valueOf(result6));
                        int result8 = result2.readInt();
                        result.increment(result8 * 4);
                        ByteArrayOutputStream result9 = new ByteArrayOutputStream();
                        try {
                            DataOutputStream result3 = new DataOutputStream(result9);
                            try {
                                result3.write(result6);
                                result3.writeInt(result8);
                                for (int index = 0; index < result8; index++) {
                                    result3.write(
                                            TAG_BINARY_READERS.get(result7).read(result, result2));
                                }
                                values = result9.toByteArray();
                            } catch (Throwable throwable3) {
                                try {
                                    result3.close();
                                } catch (Throwable throwable4) {
                                    throwable3.addSuppressed(throwable4);
                                }
                                throw throwable3;
                            }
                            result3.close();
                        } catch (Throwable result4) {
                            try {
                                result9.close();
                            } catch (Throwable throwable5) {
                                result4.addSuppressed(throwable5);
                            }
                            throw result4;
                        }
                        result9.close();
                    } finally {
                        result.exitDepth();
                    }
                    return values;
                });
        TAG_BINARY_READERS.put(
                NBTType.COMPOUND,
                (result, result2) -> {
                    result.enterDepth();
                    byte[] result4;
                    try {
                        result.increment(48);
                        ByteArrayOutputStream result6 = new ByteArrayOutputStream();
                        try {
                            DataOutputStream result7 = new DataOutputStream(result6);
                            try {
                                HashSet result8 = new HashSet();
                                byte result9;
                                while ((result9 = result2.readByte()) != 0) {
                                    result7.write(result9);
                                    String result3 =
                                            DefaultNBTSerializer.readString(result, result2);
                                    result7.writeUTF(result3);
                                    result7.write(
                                            TAG_BINARY_READERS
                                                    .get(
                                                            DefaultNBTSerializer.INSTANCE.idToType
                                                                    .get(Integer.valueOf(result9)))
                                                    .read(result, result2));
                                    if (result8.add(result3)) {
                                        result.increment(36);
                                    }
                                }
                                result7.write(result9);
                                result4 = result6.toByteArray();
                            } catch (Throwable throwable5) {
                                try {
                                    result7.close();
                                } catch (Throwable throwable6) {
                                    throwable5.addSuppressed(throwable6);
                                }
                                throw throwable5;
                            }
                            result7.close();
                        } catch (Throwable throwable4) {
                            try {
                                result6.close();
                            } catch (Throwable throwable7) {
                                throwable4.addSuppressed(throwable7);
                            }
                            throw throwable4;
                        }
                        result6.close();
                    } finally {
                        result.exitDepth();
                    }
                    return result4;
                });
        TAG_BINARY_READERS.put(
                NBTType.INT_ARRAY,
                (result, result2) -> {
                    result.increment(24);
                    int result5 = result2.readInt();
                    result.increment(result5 * 4);
                    result.checkReadability(result5 * 4);
                    byte[] result6;
                    intToBytes(result6 = new byte[4 + result5 * 4], result5, 0);
                    result2.readFully(result6, 4, result5 * 4);
                    return result6;
                });
        TAG_BINARY_READERS.put(
                NBTType.LONG_ARRAY,
                (result, result2) -> {
                    result.increment(24);
                    int result5 = result2.readInt();
                    result.increment(result5 * 8);
                    result.checkReadability(result5 * 8);
                    byte[] result6;
                    intToBytes(result6 = new byte[4 + result5 * 8], result5, 0);
                    result2.readFully(result6, 4, result5 * 8);
                    return result6;
                });
    }

    public static class Compound extends NBT
            implements Iterator<Entry<String, NBT>>,
                    Iterable<Entry<String, NBT>>,
                    SequentialNBTReader.Skippable,
                    Closeable {

        private final DataInputStream stream;

        private final NBTLimiter limiter;

        private final Runnable onComplete;

        private NBTType<?> nextType;

        private NBT lastRead;

        private boolean hasReadType;

        private Compound(
                DataInputStream dataInputStream, NBTLimiter nBTLimiter, Runnable runnable) {
            this.stream = dataInputStream;
            this.limiter = nBTLimiter;
            this.onComplete = runnable;
            nBTLimiter.increment(48);
            this.runCompleted();
        }

        @Override
        public NBTType<?> getType() {
            return NBTType.COMPOUND;
        }

        @Override
        public boolean equals(Object result) {
            return this == result;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public NBT copy() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            SequentialNBTReader.checkReadable(this.lastRead);
            if (!this.hasReadType) {
                try {
                    this.nextType =
                            DefaultNBTSerializer.INSTANCE.readTagType(this.limiter, this.stream);
                    this.hasReadType = true;
                } catch (IOException oException) {
                    throw new RuntimeException(oException);
                }
            }
            return this.nextType != NBTType.END;
        }

        public Entry<String, NBT> next() {
            if (!this.hasNext()) {
                throw new IllegalStateException("No more elements in compound");
            }
            try {
                this.hasReadType = false;
                String text = DefaultNBTSerializer.readString(this.limiter, this.stream);
                if (this.nextType == NBTType.COMPOUND) {
                    this.lastRead =
                            new SequentialNBTReader.Compound(
                                    this.stream, this.limiter, this::runCompleted);
                } else if (this.nextType == NBTType.LIST) {
                    this.lastRead =
                            new SequentialNBTReader.List(
                                    this.stream, this.limiter, this::runCompleted);
                } else {
                    this.lastRead =
                            DefaultNBTSerializer.INSTANCE.readTag(
                                    this.limiter, this.stream, this.nextType);
                    this.runCompleted();
                }
                this.limiter.increment(36);
                return new SimpleEntry<>(text, this.lastRead);
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
        }

        private void runCompleted() {
            if (!this.hasNext()) {
                this.onComplete.run();
            }
        }

        @NotNull
        @Override
        public Iterator<Entry<String, NBT>> iterator() {
            return this;
        }

        @Override
        public void skip() {
            if (this.lastRead instanceof SequentialNBTReader.Skippable) {
                ((SequentialNBTReader.Skippable) this.lastRead).skip();
            }
            if (this.hasNext()) {
                try {
                    int value = this.stream.readUnsignedShort();
                    this.stream.skipBytes(value);
                    SequentialNBTReader.TAG_SKIPS
                            .get(this.nextType)
                            .skip(this.limiter, this.stream);
                    this.limiter.increment(36);
                    SequentialNBTReader.TAG_SKIPS
                            .get(NBTType.COMPOUND)
                            .skip(this.limiter, this.stream);
                    this.hasReadType = true;
                    this.nextType = NBTType.END;
                } catch (IOException oException) {
                    throw new RuntimeException(oException);
                }
                this.runCompleted();
            }
        }

        @Override
        public void skipOne() {
            SequentialNBTReader.checkReadable(this.lastRead);
            if (this.hasNext()) {
                try {
                    int value = this.stream.readUnsignedShort();
                    this.stream.skipBytes(value);
                    SequentialNBTReader.TAG_SKIPS
                            .get(this.nextType)
                            .skip(this.limiter, this.stream);
                    this.limiter.increment(36);
                    this.hasReadType = false;
                } catch (IOException oException) {
                    throw new RuntimeException(oException);
                }
                this.runCompleted();
            }
        }

        public NBTCompound readFully() {
            try {
                if (this.lastRead instanceof SequentialNBTReader.Skippable) {
                    ((SequentialNBTReader.Skippable) this.lastRead).skip();
                }
                if (!this.hasNext()) {
                    return new NBTCompound();
                }
                NBTCompound nBTCompound = new NBTCompound();
                do {
                    String text = DefaultNBTSerializer.readString(this.limiter, this.stream);
                    NBT nBT =
                            DefaultNBTSerializer.INSTANCE.readTag(
                                    this.limiter, this.stream, this.nextType);
                    this.limiter.increment(36);
                    nBTCompound.setTag(text, nBT);
                } while ((this.nextType =
                                DefaultNBTSerializer.INSTANCE.readTagType(
                                        this.limiter, this.stream))
                        != NBTType.END);
                this.hasReadType = true;
                this.runCompleted();
                return nBTCompound;
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
        }

        public byte[] readFullyAsBytes() {
            try {
                if (this.lastRead instanceof SequentialNBTReader.Skippable) {
                    ((SequentialNBTReader.Skippable) this.lastRead).skip();
                }
                if (!this.hasNext()) {
                    return new byte[] {10, 0};
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] values;
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    try {
                        dataOutputStream.write(10);
                        do {
                            dataOutputStream.write(
                                    DefaultNBTSerializer.INSTANCE.typeToId.get(this.nextType));
                            values =
                                    SequentialNBTReader.TAG_BINARY_READERS
                                            .get(NBTType.STRING)
                                            .read(NBTLimiter.noop(), this.stream);
                            this.limiter.increment(values.length * 2 + 28);
                            dataOutputStream.write(values);
                            byte[] values2 =
                                    SequentialNBTReader.TAG_BINARY_READERS
                                            .get(this.nextType)
                                            .read(this.limiter, this.stream);
                            this.limiter.increment(36);
                            dataOutputStream.write(values2);
                        } while ((this.nextType =
                                        DefaultNBTSerializer.INSTANCE.readTagType(
                                                this.limiter, this.stream))
                                != NBTType.END);
                        dataOutputStream.write(0);
                        this.hasReadType = true;
                        this.runCompleted();
                        values = byteArrayOutputStream.toByteArray();
                    } catch (Throwable throwable) {
                        try {
                            dataOutputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                    dataOutputStream.close();
                } catch (Throwable throwable3) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable throwable4) {
                        throwable3.addSuppressed(throwable4);
                    }
                    throw throwable3;
                }
                byteArrayOutputStream.close();
                return values;
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
        }

        @Override
        public void close() throws IOException {
            this.stream.close();
        }
    }

    public static class List extends NBT
            implements Iterator<NBT>, Iterable<NBT>, SequentialNBTReader.Skippable, Closeable {

        private final DataInputStream stream;

        private final NBTLimiter limiter;

        private final Runnable onComplete;

        private final NBTType<?> listType;

        private NBT lastRead;

        public int remaining;

        private List(DataInputStream dataInputStream, NBTLimiter nBTLimiter, Runnable runnable) {
            this.stream = dataInputStream;
            this.limiter = nBTLimiter;
            this.onComplete = runnable;
            nBTLimiter.increment(37);
            try {
                this.listType =
                        DefaultNBTSerializer.INSTANCE.readTagType(nBTLimiter, dataInputStream);
                this.remaining = dataInputStream.readInt();
                nBTLimiter.increment(this.remaining * 4);
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
            this.runCompleted();
        }

        @Override
        public NBTType<?> getType() {
            return NBTType.LIST;
        }

        @Override
        public boolean equals(Object result) {
            return this == result;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public NBT copy() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return this.remaining > 0;
        }

        public NBT next() {
            SequentialNBTReader.checkReadable(this.lastRead);
            if (!this.hasNext()) {
                throw new IllegalStateException("No more elements in list");
            }
            try {
                this.remaining--;
                if (this.listType == NBTType.COMPOUND) {
                    this.lastRead =
                            new SequentialNBTReader.Compound(
                                    this.stream, this.limiter, this::runCompleted);
                } else if (this.listType == NBTType.LIST) {
                    this.lastRead =
                            new SequentialNBTReader.List(
                                    this.stream, this.limiter, this::runCompleted);
                } else {
                    this.lastRead =
                            DefaultNBTSerializer.INSTANCE.readTag(
                                    this.limiter, this.stream, this.listType);
                    this.runCompleted();
                }
                return this.lastRead;
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
        }

        private void runCompleted() {
            if (!this.hasNext()) {
                this.onComplete.run();
            }
        }

        @NotNull
        @Override
        public Iterator<NBT> iterator() {
            return this;
        }

        @Override
        public void skip() {
            if (this.lastRead instanceof SequentialNBTReader.Skippable) {
                ((SequentialNBTReader.Skippable) this.lastRead).skip();
            }
            if (this.hasNext()) {
                try {
                    SequentialNBTReader.TagSkip tagSkip =
                            SequentialNBTReader.TAG_SKIPS.get(this.listType);
                    for (int index = 0; index < this.remaining; index++) {
                        tagSkip.skip(this.limiter, this.stream);
                    }
                } catch (IOException oException) {
                    throw new RuntimeException(oException);
                }
                this.remaining = 0;
                this.runCompleted();
            }
        }

        @Override
        public void skipOne() {
            SequentialNBTReader.checkReadable(this.lastRead);
            if (this.hasNext()) {
                try {
                    SequentialNBTReader.TAG_SKIPS
                            .get(this.listType)
                            .skip(this.limiter, this.stream);
                } catch (IOException oException) {
                    throw new RuntimeException(oException);
                }
                this.remaining--;
                this.runCompleted();
            }
        }

        public NBTList<NBT> readFully() {
            try {
                if (this.lastRead instanceof SequentialNBTReader.Skippable) {
                    ((SequentialNBTReader.Skippable) this.lastRead).skip();
                }
                if (!this.hasNext()) {
                    return new NBTList<>((NBTType<NBT>) this.listType, 0);
                }
                NBTList nBTList = new NBTList<>(this.listType, this.remaining);
                for (int index = 0; index < this.remaining; index++) {
                    nBTList.addTag(
                            DefaultNBTSerializer.INSTANCE.readTag(
                                    this.limiter, this.stream, this.listType));
                }
                this.remaining = 0;
                this.runCompleted();
                return nBTList;
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
        }

        public byte[] readFullyAsBinary() {
            try {
                if (this.lastRead instanceof SequentialNBTReader.Skippable) {
                    ((SequentialNBTReader.Skippable) this.lastRead).skip();
                }
                if (!this.hasNext()) {
                    return new byte[] {9};
                }
                byte[] values = null;
                for (int index = 0; index < this.remaining; index++) {
                    byte[] values2 =
                            SequentialNBTReader.TAG_BINARY_READERS
                                    .get(this.listType)
                                    .read(this.limiter, this.stream);
                    if (values == null) {
                        (values = new byte[6 + this.remaining * values2.length])[0] = 9;
                        values[1] =
                                DefaultNBTSerializer.INSTANCE
                                        .typeToId
                                        .get(this.listType)
                                        .byteValue();
                        values[2] = (byte) (this.remaining >>> 24);
                        values[3] = (byte) (this.remaining >>> 16);
                        values[4] = (byte) (this.remaining >>> 8);
                        values[5] = (byte) this.remaining;
                    }
                    System.arraycopy(
                            values2, 0, values, 5 + index * values2.length, values2.length);
                }
                this.remaining = 0;
                this.runCompleted();
                return values;
            } catch (IOException oException) {
                throw new RuntimeException(oException);
            }
        }

        @Override
        public void close() throws IOException {
            this.stream.close();
        }
    }

    interface Skippable {

        void skip();

        void skipOne();
    }

    @FunctionalInterface
    private interface TagBinaryReader {

        byte[] read(NBTLimiter nBTLimiter, DataInput dataInput) throws IOException;
    }

    @FunctionalInterface
    private interface TagSkip {

        void skip(NBTLimiter nBTLimiter, DataInput dataInput) throws IOException;
    }
}
