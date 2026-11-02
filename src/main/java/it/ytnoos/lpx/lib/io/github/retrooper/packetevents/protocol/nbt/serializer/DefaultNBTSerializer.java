package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTEnd;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTShort;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

public class DefaultNBTSerializer extends NBTSerializer<DataInput, DataOutput> {

    @Internal public static final int OBJECT_HEADER_BYTES = 8;

    @Internal public static final int ARRAY_HEADER_BYTES = 12;

    @Internal public static final int OBJECT_REF_BYTES = 4;

    @Internal public static final int STRING_SIZE_BYTES = 28;

    public static final DefaultNBTSerializer INSTANCE = new DefaultNBTSerializer();

    public DefaultNBTSerializer() {
        super(
                (result, result2) -> result2.readByte(),
                DataOutput::writeByte,
                (result, result2) -> {
                    result2.skipBytes(result2.readUnsignedShort());
                    return "";
                },
                DataOutput::writeUTF);
        this.registerType(
                NBTType.END,
                0,
                (result, result2) -> {
                    result.increment(8);
                    return NBTEnd.INSTANCE;
                },
                (result, result2) -> {});
        this.registerType(
                NBTType.BYTE,
                1,
                (result, result2) -> {
                    result.increment(9);
                    return new NBTByte(result2.readByte());
                },
                (result, result2) -> result.writeByte(result2.getAsByte()));
        this.registerType(
                NBTType.SHORT,
                2,
                (result, result2) -> {
                    result.increment(10);
                    return new NBTShort(result2.readShort());
                },
                (result, result2) -> result.writeShort(result2.getAsShort()));
        this.registerType(
                NBTType.INT,
                3,
                (result, result2) -> {
                    result.increment(12);
                    return new NBTInt(result2.readInt());
                },
                (result, result2) -> result.writeInt(result2.getAsInt()));
        this.registerType(
                NBTType.LONG,
                4,
                (result, result2) -> {
                    result.increment(16);
                    return new NBTLong(result2.readLong());
                },
                (result, result2) -> result.writeLong(result2.getAsLong()));
        this.registerType(
                NBTType.FLOAT,
                5,
                (result, result2) -> {
                    result.increment(12);
                    return new NBTFloat(result2.readFloat());
                },
                (result, result2) -> result.writeFloat(result2.getAsFloat()));
        this.registerType(
                NBTType.DOUBLE,
                6,
                (result, result2) -> {
                    result.increment(16);
                    return new NBTDouble(result2.readDouble());
                },
                (result, result2) -> result.writeDouble(result2.getAsDouble()));
        this.registerType(
                NBTType.BYTE_ARRAY,
                7,
                (result, result2) -> {
                    result.increment(24);
                    int result3;
                    if ((result3 = result2.readInt()) >= 16777216) {
                        throw new IllegalArgumentException(
                                "Byte array length is too large: " + result3);
                    }
                    result.increment(1 * result3);
                    result.checkReadability(1 * result3);
                    byte[] index = new byte[result3];
                    result2.readFully(index);
                    return new NBTByteArray(index);
                },
                (result, result2) -> {
                    byte[] value = result2.getValue();
                    result.writeInt(value.length);
                    result.write(value);
                });
        this.registerType(
                NBTType.STRING,
                8,
                (result, result2) -> {
                    result.increment(36);
                    String result4 = result2.readUTF();
                    result.increment(result4.length() * 2);
                    return new NBTString(result4);
                },
                (result, result2) -> result.writeUTF(result2.getValue()));
        this.registerType(
                NBTType.LIST,
                9,
                (result2, value) -> {
                    result2.enterDepth();
                    try {
                        result2.increment(36);
                        NBTType index = this.readTagType(result2, value);
                        int result5 = value.readInt();
                        if (index == NBTType.END && result5 > 0) {
                            throw new IllegalStateException("Missing nbt list values tag type");
                        }
                        result2.countElements(result5);
                        result2.increment(4 * result5);
                        NBTList result6 = new NBTList(index, result5);
                        for (int result7 = 0; result7 < result5; result7++) {
                            result6.addTag(this.readTag(result2, value, index));
                        }
                        return result6;
                    } finally {
                        result2.exitDepth();
                    }
                },
                (result2, value) -> {
                    this.writeTagType(result2, value.getTagsType());
                    result2.writeInt(value.size());
                    for (NBT result3 : ((Iterable<NBT>) ((Iterable<?>) value.getTags()))) {
                        this.writeTag(result2, result3);
                    }
                });
        this.registerType(
                NBTType.COMPOUND,
                10,
                (result2, value) -> {
                    result2.enterDepth();
                    try {
                        result2.increment(48);
                        NBTCompound index = new NBTCompound();
                        NBTType result5;
                        while ((result5 = this.readTagType(result2, value)) != NBTType.END) {
                            result2.countElements(1);
                            String result6 = readString(result2, value);
                            NBT result7 = this.readTag(result2, value, result5);
                            if (!index.getTags().containsKey(result6)) {
                                result2.increment(36);
                            }
                            index.setTag(result6, result7);
                        }
                        return index;
                    } finally {
                        result2.exitDepth();
                    }
                },
                (result2, value) -> {
                    Iterator<Entry<String, NBT>> result4 = value.getTags().entrySet().iterator();
                    while (result4.hasNext()) {
                        Entry result3;
                        NBT index = (NBT) (result3 = (Entry) result4.next()).getValue();
                        this.writeTagType(result2, index.getType());
                        this.writeTagName(result2, (String) result3.getKey());
                        this.writeTag(result2, index);
                    }
                    this.writeTagType(result2, NBTType.END);
                });
        this.registerType(
                NBTType.INT_ARRAY,
                11,
                (result, result2) -> {
                    result.increment(24);
                    int result3;
                    if ((result3 = result2.readInt()) >= 16777216) {
                        throw new IllegalArgumentException(
                                "Int array length is too large: " + result3);
                    }
                    result.increment(result3 * 4);
                    result.checkReadability(result3 * 4);
                    int[] index = new int[result3];
                    for (int result5 = 0; result5 < index.length; result5++) {
                        index[result5] = result2.readInt();
                    }
                    return new NBTIntArray(index);
                },
                (result, result2) -> {
                    int[] value2 = result2.getValue();
                    result.writeInt(value2.length);
                    int[] result4 = value2;
                    int result3 = value2.length;
                    for (int index = 0; index < result3; index++) {
                        int result5 = result4[index];
                        result.writeInt(result5);
                    }
                });
        this.registerType(
                NBTType.LONG_ARRAY,
                12,
                (result, result2) -> {
                    result.increment(24);
                    int result3;
                    if ((result3 = result2.readInt()) >= 16777216) {
                        throw new IllegalArgumentException(
                                "Long array length is too large: " + result3);
                    }
                    result.increment(result3 * 8);
                    result.checkReadability(result3 * 8);
                    long[] index = new long[result3];
                    for (int result5 = 0; result5 < index.length; result5++) {
                        index[result5] = result2.readLong();
                    }
                    return new NBTLongArray(index);
                },
                (result, result2) -> {
                    long[] value2 = result2.getValue();
                    result.writeInt(value2.length);
                    long[] result4 = value2;
                    int result3 = value2.length;
                    for (int index = 0; index < result3; index++) {
                        long result5 = result4[index];
                        result.writeLong(result5);
                    }
                });
    }

    @Internal
    public static String readString(NBTLimiter nBTLimiter, DataInput dataInput) throws IOException {
        String text = dataInput.readUTF();
        nBTLimiter.increment(28 + 2 * text.length());
        return text;
    }
}
