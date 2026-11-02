package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;

import java.nio.charset.Charset;

public class ByteBufHelper {

    public static int capacity(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().capacity(result);
    }

    public static Object capacity(Object result, int value) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().capacity(result, value);
    }

    public static int readerIndex(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readerIndex(result);
    }

    public static Object readerIndex(Object result, int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .readerIndex(result, value);
    }

    public static int writerIndex(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().writerIndex(result);
    }

    public static Object writerIndex(Object result, int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .writerIndex(result, value);
    }

    public static int readableBytes(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readableBytes(result);
    }

    public static int writableBytes(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().writableBytes(result);
    }

    public static Object clear(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().clear(result);
    }

    public static String toString(Object result, int value, int value2, Charset charset) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .toString(result, value, value2, charset);
    }

    public static byte readByte(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readByte(result);
    }

    public static void writeByte(Object result, int value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeByte(result, value);
    }

    public static boolean readBoolean(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readBoolean(result);
    }

    public static void writeBoolean(Object result, boolean matches) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeBoolean(result, matches);
    }

    public static short readUnsignedByte(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .readUnsignedByte(result);
    }

    public static char readChar(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readChar(result);
    }

    public static void writeChar(Object result, int value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeChar(result, value);
    }

    public static short readShort(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readShort(result);
    }

    public static int readUnsignedShort(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .readUnsignedShort(result);
    }

    public static void writeShort(Object result, int value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeShort(result, value);
    }

    public static void writeShortLE(Object result, int value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeShortLE(result, value);
    }

    public static int readMedium(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readMedium(result);
    }

    public static void writeMedium(Object result, int value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeMedium(result, value);
    }

    public static int readInt(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readInt(result);
    }

    public static void writeInt(Object result, int value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeInt(result, value);
    }

    public static long readUnsignedInt(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readUnsignedInt(result);
    }

    public static long readLong(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readLong(result);
    }

    public static void writeLong(Object result, long value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeLong(result, value);
    }

    public static float readFloat(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readFloat(result);
    }

    public static void writeFloat(Object result, float value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeFloat(result, value);
    }

    public static double readDouble(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().readDouble(result);
    }

    public static void writeDouble(Object result, double value) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeDouble(result, value);
    }

    public static Object getBytes(Object result, int value, byte[] values) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .getBytes(result, value, values);
    }

    public static short getUnsignedByte(Object result, int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .getUnsignedByte(result, value);
    }

    public static boolean isReadable(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().isReadable(result);
    }

    public static Object copy(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().copy(result);
    }

    public static Object duplicate(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().duplicate(result);
    }

    public static boolean hasArray(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().hasArray(result);
    }

    public static byte[] array(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().array(result);
    }

    public static Object retain(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().retain(result);
    }

    public static Object retainedDuplicate(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .retainedDuplicate(result);
    }

    public static Object readSlice(Object result, int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .readSlice(result, value);
    }

    public static Object readBytes(Object result, byte[] values, int value, int value2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .readBytes(result, values, value, value2);
    }

    public static Object readBytes(Object result, int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .readBytes(result, value);
    }

    public static Object writeBytes(Object result, Object result2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .writeBytes(result, result2);
    }

    public static void readBytes(Object result, byte[] values) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().readBytes(result, values);
    }

    public static void writeBytes(Object result, byte[] values) {
        PacketEvents.getAPI().getNettyManager().getByteBufOperator().writeBytes(result, values);
    }

    public static void writeBytes(Object result, byte[] values, int value, int value2) {
        PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .writeBytes(result, values, value, value2);
    }

    public static boolean release(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().release(result);
    }

    public static int refCnt(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().refCnt(result);
    }

    public static Object skipBytes(Object result, int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .skipBytes(result, value);
    }

    public static Object markReaderIndex(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().markReaderIndex(result);
    }

    public static Object resetReaderIndex(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .resetReaderIndex(result);
    }

    public static Object markWriterIndex(Object result) {
        return PacketEvents.getAPI().getNettyManager().getByteBufOperator().markWriterIndex(result);
    }

    public static Object resetWriterIndex(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .resetWriterIndex(result);
    }

    public static Object allocateNewBuffer(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufOperator()
                .allocateNewBuffer(result);
    }

    public static int getByteSize(int value) {
        for (int index = 1; index < 5; index++) {
            if ((value & -1 << index * 7) == 0) {
                return index;
            }
        }
        return 5;
    }

    public static int readVarInt(Object result) {
        int value = 0;
        int value2 = 0;
        byte value3;
        do {
            value3 = readByte(result);
            value |= (value3 & 127) << value2 * 7;
            if (++value2 > 5) {
                throw new RuntimeException("VarInt is too large. Must be smaller than 5 bytes.");
            }
        } while ((value3 & 128) == 128);
        return value;
    }

    public static void writeVarInt(Object result, int value) {
        while ((value & -128) != 0) {
            writeByte(result, value & 127 | 128);
            value >>>= 7;
        }
        writeByte(result, value);
    }

    public static byte[] copyBytes(Object result) {
        byte[] values = new byte[readableBytes(result)];
        getBytes(result, readerIndex(result), values);
        return values;
    }
}
