package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.netty.buffer;

import io.netty.buffer.ByteBuf;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufOperator;

import java.nio.charset.Charset;

public class ByteBufOperatorModernImpl implements ByteBufOperator {

    @Override
    public int capacity(Object result) {
        return ((ByteBuf) result).capacity();
    }

    @Override
    public Object capacity(Object result, int value) {
        return ((ByteBuf) result).capacity(value);
    }

    @Override
    public int readerIndex(Object result) {
        return ((ByteBuf) result).readerIndex();
    }

    @Override
    public Object readerIndex(Object result, int value) {
        return ((ByteBuf) result).readerIndex(value);
    }

    @Override
    public int writerIndex(Object result) {
        return ((ByteBuf) result).writerIndex();
    }

    @Override
    public Object writerIndex(Object result, int value) {
        return ((ByteBuf) result).writerIndex(value);
    }

    @Override
    public int readableBytes(Object result) {
        return ((ByteBuf) result).readableBytes();
    }

    @Override
    public int writableBytes(Object result) {
        return ((ByteBuf) result).writableBytes();
    }

    @Override
    public Object clear(Object result) {
        return ((ByteBuf) result).clear();
    }

    @Override
    public byte readByte(Object result) {
        return ((ByteBuf) result).readByte();
    }

    @Override
    public short readShort(Object result) {
        return ((ByteBuf) result).readShort();
    }

    @Override
    public int readMedium(Object result) {
        return ((ByteBuf) result).readMedium();
    }

    @Override
    public int readInt(Object result) {
        return ((ByteBuf) result).readInt();
    }

    @Override
    public long readUnsignedInt(Object result) {
        return ((ByteBuf) result).readUnsignedInt();
    }

    @Override
    public long readLong(Object result) {
        return ((ByteBuf) result).readLong();
    }

    @Override
    public void writeByte(Object result, int value) {
        ((ByteBuf) result).writeByte(value);
    }

    @Override
    public void writeShort(Object result, int value) {
        ((ByteBuf) result).writeShort(value);
    }

    @Override
    public void writeShortLE(Object result, int value) {
        ((ByteBuf) result).writeShortLE(value);
    }

    @Override
    public void writeMedium(Object result, int value) {
        ((ByteBuf) result).writeMedium(value);
    }

    @Override
    public void writeInt(Object result, int value) {
        ((ByteBuf) result).writeInt(value);
    }

    @Override
    public void writeLong(Object result, long value) {
        ((ByteBuf) result).writeLong(value);
    }

    @Override
    public Object getBytes(Object result, int value, byte[] values) {
        return ((ByteBuf) result).getBytes(value, values);
    }

    @Override
    public short getUnsignedByte(Object result, int value) {
        return ((ByteBuf) result).getUnsignedByte(value);
    }

    @Override
    public boolean isReadable(Object result) {
        return ((ByteBuf) result).isReadable();
    }

    @Override
    public Object copy(Object result) {
        return ((ByteBuf) result).copy();
    }

    @Override
    public Object duplicate(Object result) {
        return ((ByteBuf) result).duplicate();
    }

    @Override
    public boolean hasArray(Object result) {
        return ((ByteBuf) result).hasArray();
    }

    @Override
    public byte[] array(Object result) {
        return ((ByteBuf) result).array();
    }

    @Override
    public Object retain(Object result) {
        return ((ByteBuf) result).retain();
    }

    @Override
    public Object retainedDuplicate(Object result) {
        return ((ByteBuf) result).duplicate().retain();
    }

    @Override
    public Object readSlice(Object result, int value) {
        return ((ByteBuf) result).readSlice(value);
    }

    @Override
    public Object readBytes(Object result, byte[] values, int value, int value2) {
        return ((ByteBuf) result).readBytes(values, value, value2);
    }

    @Override
    public Object readBytes(Object result, int value) {
        return ((ByteBuf) result).readBytes(value);
    }

    @Override
    public Object writeBytes(Object result, Object result2) {
        return ((ByteBuf) result).writeBytes((ByteBuf) result2);
    }

    @Override
    public Object writeBytes(Object result, byte[] values) {
        return ((ByteBuf) result).writeBytes(values);
    }

    @Override
    public Object writeBytes(Object result, byte[] values, int value, int value2) {
        return ((ByteBuf) result).writeBytes(values, value, value2);
    }

    @Override
    public void readBytes(Object result, byte[] values) {
        ((ByteBuf) result).readBytes(values);
    }

    @Override
    public boolean release(Object result) {
        return ((ByteBuf) result).release();
    }

    @Override
    public int refCnt(Object result) {
        return ((ByteBuf) result).refCnt();
    }

    @Override
    public Object skipBytes(Object result, int value) {
        return ((ByteBuf) result).skipBytes(value);
    }

    @Override
    public String toString(Object result, int value, int value2, Charset charset) {
        return ((ByteBuf) result).toString(value, value2, charset);
    }

    @Override
    public Object markReaderIndex(Object result) {
        return ((ByteBuf) result).markReaderIndex();
    }

    @Override
    public Object resetReaderIndex(Object result) {
        return ((ByteBuf) result).resetReaderIndex();
    }

    @Override
    public Object markWriterIndex(Object result) {
        return ((ByteBuf) result).markWriterIndex();
    }

    @Override
    public Object resetWriterIndex(Object result) {
        return ((ByteBuf) result).resetWriterIndex();
    }

    @Override
    public Object allocateNewBuffer(Object result) {
        return ((ByteBuf) result).alloc().buffer();
    }
}
