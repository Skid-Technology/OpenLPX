package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.codec;

import it.ytnoos.lpx.lib.gson.JsonArray;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.gson.JsonPrimitive;
import it.ytnoos.lpx.lib.gson.internal.LazilyParsedNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufInputStream;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufOutputStream;
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
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTShort;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.DefaultNBTSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTCodec {

    @Internal
    public static NBT jsonToNBT(JsonElement jsonElement) {
        if (jsonElement instanceof JsonPrimitive) {
            if (((JsonPrimitive) jsonElement).isBoolean()) {
                return new NBTByte(jsonElement.getAsBoolean());
            }
            if (((JsonPrimitive) jsonElement).isString()) {
                return new NBTString(jsonElement.getAsString());
            }
            if (((JsonPrimitive) jsonElement).isNumber()) {
                Number number;
                if ((number = jsonElement.getAsNumber()) instanceof Float) {
                    return new NBTFloat(number.floatValue());
                }
                if (number instanceof Double) {
                    return new NBTDouble(number.doubleValue());
                }
                if (number instanceof Byte) {
                    return new NBTByte(number.byteValue());
                }
                if (number instanceof Short) {
                    return new NBTShort(number.shortValue());
                }
                if (number instanceof Integer || number instanceof LazilyParsedNumber) {
                    return new NBTInt(number.intValue());
                }
                if (number instanceof Long) {
                    return new NBTLong(number.longValue());
                }
            }
        } else {
            if (jsonElement instanceof JsonArray) {
                ArrayList<NBT> values = new ArrayList<>();
                for (JsonElement jsonElement2 :
                        (Iterable<JsonElement>) ((Iterable) (JsonArray) jsonElement)) {
                    values.add(jsonToNBT(jsonElement2));
                }
                if (values.isEmpty()) {
                    return new NBTList<>(NBTType.COMPOUND);
                }
                return NbtCodecs.GENERIC_LIST.encode(null, values);
            }
            if (jsonElement instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                NBTCompound nBTCompound = new NBTCompound();
                for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    nBTCompound.setTag(
                            (String) entry.getKey(), jsonToNBT((JsonElement) entry.getValue()));
                }
                return nBTCompound;
            }
            if (jsonElement instanceof JsonNull || jsonElement == null) {
                return new NBTCompound();
            }
        }
        throw new IllegalStateException("Failed to convert JSON to NBT " + jsonElement);
    }

    @Internal
    public static JsonElement nbtToJson(NBT nBT, boolean matches) {
        if (nBT instanceof NBTNumber) {
            if (nBT instanceof NBTByte && matches) {
                byte value;
                if ((value = ((NBTByte) nBT).getAsByte()) == 0) {
                    return new JsonPrimitive(false);
                }
                if (value == 1) {
                    return new JsonPrimitive(true);
                }
            }
            return new JsonPrimitive(((NBTNumber) nBT).getAsNumber());
        } else {
            if (nBT instanceof NBTString) {
                return new JsonPrimitive(((NBTString) nBT).getValue());
            }
            if (nBT instanceof NBTList) {
                NBTList nBTList = (NBTList) nBT;
                JsonArray jsonArray = new JsonArray(nBTList.size());
                for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) nBTList.unwrapTags()))) {
                    jsonArray.add(nbtToJson(nBT2, matches));
                }
                return jsonArray;
            } else {
                if (nBT instanceof NBTEnd) {
                    return JsonNull.INSTANCE;
                }
                if (nBT instanceof NBTCompound) {
                    JsonObject jsonObject = new JsonObject();
                    Iterator<Entry<String, NBT>> iterator2 =
                            ((NBTCompound) nBT).getTags().entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Entry entry;
                        JsonElement jsonElement =
                                nbtToJson(
                                        (NBT) (entry = (Entry) iterator2.next()).getValue(),
                                        matches);
                        jsonObject.add((String) entry.getKey(), jsonElement);
                    }
                    return jsonObject;
                } else if (nBT instanceof NBTByteArray) {
                    byte[] value2 = ((NBTByteArray) nBT).getValue();
                    JsonArray jsonArray2 = new JsonArray(value2.length);
                    int value3 = value2.length;
                    for (int index = 0; index < value3; index++) {
                        byte value4 = value2[index];
                        jsonArray2.add(value4);
                    }
                    return jsonArray2;
                } else if (nBT instanceof NBTIntArray) {
                    int[] value5 = ((NBTIntArray) nBT).getValue();
                    JsonArray jsonArray3 = new JsonArray(value5.length);
                    int value6 = value5.length;
                    for (int index2 = 0; index2 < value6; index2++) {
                        int value7 = value5[index2];
                        jsonArray3.add(value7);
                    }
                    return jsonArray3;
                } else {
                    if (!(nBT instanceof NBTLongArray)) {
                        throw new IllegalStateException("Failed to convert NBT to JSON: " + nBT);
                    }
                    long[] value8 = ((NBTLongArray) nBT).getValue();
                    JsonArray jsonArray4 = new JsonArray(value8.length);
                    int value9 = value8.length;
                    for (int index3 = 0; index3 < value9; index3++) {
                        long value10 = value8[index3];
                        jsonArray4.add(value10);
                    }
                    return jsonArray4;
                }
            }
        }
    }

    public static NBT readNBTFromBuffer(Object result, ServerVersion serverVersion) {
        NBTLimiter nBTLimiter = NBTLimiter.forBuffer(result);
        return readNBTFromBuffer(result, serverVersion, nBTLimiter);
    }

    public static NBT readNBTFromBuffer(
            Object result, ServerVersion serverVersion, NBTLimiter nBTLimiter) {
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            try {
                boolean olderThan = serverVersion.isOlderThan(ServerVersion.V_1_20_2);
                return DefaultNBTSerializer.INSTANCE.deserializeTag(
                        nBTLimiter, new ByteBufInputStream(result), olderThan);
            } catch (IOException oException) {
                throw new IllegalStateException(oException);
            }
        } else {
            NBT nBT;
            try {
                short value;
                if ((value = ByteBufHelper.readShort(result)) < 0) {
                    return null;
                }
                Object result2 = ByteBufHelper.readSlice(result, value);
                DataInputStream dataInputStream =
                        new DataInputStream(new GZIPInputStream(new ByteBufInputStream(result2)));
                try {
                    nBT = DefaultNBTSerializer.INSTANCE.deserializeTag(nBTLimiter, dataInputStream);
                } catch (Throwable throwable) {
                    try {
                        dataInputStream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                dataInputStream.close();
            } catch (IOException oException2) {
                throw new IllegalStateException(oException2);
            }
            return nBT;
        }
    }

    public static void writeNBTToBuffer(
            Object result, ServerVersion serverVersion, NBTCompound nBTCompound) {
        writeNBTToBuffer(result, serverVersion, (NBT) nBTCompound);
    }

    public static void writeNBTToBuffer(Object result, ServerVersion serverVersion, NBT nBT) {
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            try {
                ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(result);
                try {
                    if (nBT != null) {
                        boolean olderThan = serverVersion.isOlderThan(ServerVersion.V_1_20_2);
                        DefaultNBTSerializer.INSTANCE.serializeTag(
                                byteBufOutputStream, nBT, olderThan);
                    } else {
                        DefaultNBTSerializer.INSTANCE.serializeTag(
                                byteBufOutputStream, NBTEnd.INSTANCE);
                    }
                } catch (Throwable throwable) {
                    try {
                        byteBufOutputStream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                byteBufOutputStream.close();
            } catch (IOException oException) {
                throw new IllegalStateException(oException);
            }
        } else if (nBT == null) {
            ByteBufHelper.writeShort(result, -1);
        } else {
            int value = ByteBufHelper.writerIndex(result);
            ByteBufHelper.writeShort(result, 0);
            int value2 = ByteBufHelper.writerIndex(result);
            try {
                DataOutputStream dataOutputStream =
                        new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(result)));
                try {
                    DefaultNBTSerializer.INSTANCE.serializeTag(dataOutputStream, nBT);
                } catch (Throwable throwable3) {
                    try {
                        dataOutputStream.close();
                    } catch (Throwable throwable4) {
                        throwable3.addSuppressed(throwable4);
                    }
                    throw throwable3;
                }
                dataOutputStream.close();
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
            int value3 = ByteBufHelper.writerIndex(result);
            ByteBufHelper.writerIndex(result, value);
            ByteBufHelper.writeShort(result, value3 - value2);
            ByteBufHelper.writerIndex(result, value3);
        }
    }
}
