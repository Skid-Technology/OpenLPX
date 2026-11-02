package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer;

import java.nio.charset.Charset;

public interface ByteBufOperator {

    int capacity(Object result);

    Object capacity(Object result, int value);

    int readerIndex(Object result);

    Object readerIndex(Object result, int value);

    int writerIndex(Object result);

    Object writerIndex(Object result, int value);

    int readableBytes(Object result);

    int writableBytes(Object result);

    Object clear(Object result);

    byte readByte(Object result);

    short readShort(Object result);

    int readMedium(Object result);

    int readInt(Object result);

    long readUnsignedInt(Object result);

    long readLong(Object result);

    void writeByte(Object result, int value);

    void writeShort(Object result, int value);

    void writeShortLE(Object result, int value);

    void writeMedium(Object result, int value);

    void writeInt(Object result, int value);

    void writeLong(Object result, long value);

    Object getBytes(Object result, int value, byte[] values);

    short getUnsignedByte(Object result, int value);

    boolean isReadable(Object result);

    Object copy(Object result);

    Object duplicate(Object result);

    boolean hasArray(Object result);

    byte[] array(Object result);

    Object retain(Object result);

    Object retainedDuplicate(Object result);

    Object readSlice(Object result, int value);

    Object readBytes(Object result, byte[] values, int value, int value2);

    Object readBytes(Object result, int value);

    void readBytes(Object result, byte[] values);

    Object writeBytes(Object result, Object result2);

    Object writeBytes(Object result, byte[] values);

    Object writeBytes(Object result, byte[] values, int value, int value2);

    boolean release(Object result);

    int refCnt(Object result);

    Object skipBytes(Object result, int value);

    String toString(Object result, int value, int value2, Charset charset);

    Object markReaderIndex(Object result);

    Object resetReaderIndex(Object result);

    Object markWriterIndex(Object result);

    Object resetWriterIndex(Object result);

    Object allocateNewBuffer(Object result);

    default float readFloat(Object result) {
        return Float.intBitsToFloat(this.readInt(result));
    }

    default void writeFloat(Object result, float value) {
        this.writeInt(result, Float.floatToIntBits(value));
    }

    default double readDouble(Object result) {
        return Double.longBitsToDouble(this.readLong(result));
    }

    default void writeDouble(Object result, double value) {
        this.writeLong(result, Double.doubleToLongBits(value));
    }

    default char readChar(Object result) {
        return (char) this.readShort(result);
    }

    default void writeChar(Object result, int value) {
        this.writeShort(result, value);
    }

    default int readUnsignedShort(Object result) {
        return this.readShort(result) & 65535;
    }

    default short readUnsignedByte(Object result) {
        return (short) (this.readByte(result) & 0xFF);
    }

    default boolean readBoolean(Object result) {
        return this.readByte(result) != 0;
    }

    default void writeBoolean(Object result, boolean matches) {
        this.writeByte(result, matches ? 1 : 0);
    }
}
