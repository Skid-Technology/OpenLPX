package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufInputStream;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufOutputStream;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.codec.NBTCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTag;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTagType;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTagTypes;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.EndBinaryTag;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.TagStringIO;

import org.jspecify.annotations.NullMarked;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@NullMarked
public final class AdventureNbtUtil {

    private static final TagStringIO TAG_STRING_IO;

    private static final Method TAG_TYPE_GET_ID;

    private static final Method TAG_TYPE_READ;

    private static final Method TAG_TYPE_WRITE;

    private static final BinaryTagType<?>[] NBT_TAG_TYPES;

    private AdventureNbtUtil() {}

    private static BinaryTagType<?>[] buildNbtTagTypes() {
        try {
            BinaryTagTypes.class.getField("BYTE").get(null);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException("Error while initializing adventure binary tag types");
        }
        List values;
        try {
            Field field;
            if ((field = Reflection.getField(BinaryTagType.class, "TYPES")) == null) {
                field =
                        Reflection.getField(
                                Class.forName(
                                        "it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTagTypeImpl"),
                                "TYPES");
            }
            values = (List) field.get(null);
        } catch (ReflectiveOperationException reflectiveOperationException2) {
            throw new RuntimeException(
                    "Error while accessing registered binary tag types",
                    reflectiveOperationException2);
        }
        BinaryTagType[] binaryTagTypes = new BinaryTagType[values.size()];
        for (int index = 0; index < binaryTagTypes.length; index++) {
            BinaryTagType binaryTagType;
            byte value;
            if ((value = getTagId(binaryTagType = (BinaryTagType) values.get(index))) != index) {
                throw new IllegalStateException(
                        "Registered nbt tag types are wrong: " + value + " != " + index);
            }
            binaryTagTypes[index] = binaryTagType;
        }
        return binaryTagTypes;
    }

    private static byte getTagId(BinaryTagType<?> binaryTagType) {
        try {
            return (Byte) TAG_TYPE_GET_ID.invoke(binaryTagType);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static BinaryTag readAdventureTag(Object result) {
        byte value;
        if ((value = ByteBufHelper.readByte(result)) == 0) {
            return EndBinaryTag.endBinaryTag();
        }
        BinaryTagType binaryTagType = NBT_TAG_TYPES[value];
        try {
            return (BinaryTag) TAG_TYPE_READ.invoke(binaryTagType, new ByteBufInputStream(result));
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Error while reading adventure nbt tag from buf: " + result, exception);
        }
    }

    public static void writeAdventureTag(Object result, BinaryTag binaryTag) {
        BinaryTagType binaryTagType;
        byte tagId = getTagId(binaryTagType = binaryTag.type());
        ByteBufHelper.writeByte(result, tagId);
        if (tagId != 0) {
            try {
                TAG_TYPE_WRITE.invoke(binaryTagType, binaryTag, new ByteBufOutputStream(result));
            } catch (Exception exception) {
                throw new RuntimeException(
                        "Error while writing adventure nbt tag to buf: " + binaryTag, exception);
            }
        }
    }

    public static NBT fromAdventure(BinaryTag binaryTag) {
        Object result = UnpooledByteBufAllocationHelper.buffer();
        try {
            writeAdventureTag(result, binaryTag);
            return NBTCodec.readNBTFromBuffer(result, ServerVersion.getLatest());
        } finally {
            ByteBufHelper.release(result);
        }
    }

    public static BinaryTag toAdventure(NBT nBT) {
        Object result = UnpooledByteBufAllocationHelper.buffer();
        try {
            NBTCodec.writeNBTToBuffer(result, ServerVersion.getLatest(), nBT);
            return readAdventureTag(result);
        } finally {
            ByteBufHelper.release(result);
        }
    }

    public static NBT fromString(String text) {
        BinaryTag binaryTag;
        try {
            binaryTag = TAG_STRING_IO.asTag(text);
        } catch (IOException oException) {
            throw new RuntimeException("Error while decoding nbt from string: " + text, oException);
        }
        return fromAdventure(binaryTag);
    }

    public static String toString(NBT nBT) {
        BinaryTag binaryTag = toAdventure(nBT);
        try {
            return TAG_STRING_IO.asString(binaryTag);
        } catch (IOException oException) {
            throw new RuntimeException(
                    "Error while encoding nbt to string: " + binaryTag, oException);
        }
    }

    static {
        TagStringIO tagStringIO2;
        try {
            tagStringIO2 = TagStringIO.tagStringIO();
        } catch (Throwable throwable) {
            tagStringIO2 = TagStringIO.get();
        }
        TAG_STRING_IO = tagStringIO2;
        TAG_TYPE_GET_ID = Reflection.getMethodExact(BinaryTagType.class, "id", byte.class);
        TAG_TYPE_READ =
                Reflection.getMethodExact(
                        BinaryTagType.class, "read", BinaryTag.class, DataInput.class);
        TAG_TYPE_WRITE =
                Reflection.getMethodExact(
                        BinaryTagType.class,
                        "write",
                        void.class,
                        BinaryTag.class,
                        DataOutput.class);
        NBT_TAG_TYPES = buildNbtTagTypes();
    }
}
