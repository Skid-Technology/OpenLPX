package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class NetStreamInputWrapper extends NetStreamInput {

    private final PacketWrapper<?> wrapper;

    public NetStreamInputWrapper(PacketWrapper<?> packetWrapper) {
        super(null);
        this.wrapper = packetWrapper;
    }

    @Override
    public byte readByte() {
        return this.wrapper.readByte();
    }

    @Override
    public int readUnsignedByte() {
        return this.wrapper.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return this.wrapper.readShort();
    }

    @Override
    public int readUnsignedShort() {
        return this.wrapper.readUnsignedShort();
    }

    @Override
    public int readVarInt() {
        return this.wrapper.readVarInt();
    }

    @Override
    public long readLong() {
        return this.wrapper.readLong();
    }

    @Override
    public long[] readLongs(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        }
        long[] values = new long[value];
        this.readLongs(values);
        return values;
    }

    @Override
    public int readLongs(long[] values) {
        return this.readLongs(values, 0, values.length);
    }

    @Override
    public int readLongs(long[] values, int value, int value2) {
        for (int value3 = value; value3 < value + value2; value3++) {
            values[value3] = this.wrapper.readLong();
        }
        return value2;
    }

    @Override
    public int read() {
        return this.wrapper.readUnsignedByte();
    }

    @Override
    public int read(byte[] values) {
        return this.read(values, 0, values.length);
    }

    @Override
    public int read(byte[] values, int value, int value2) {
        int value3 = ByteBufHelper.readerIndex(this.wrapper.buffer);
        ByteBufHelper.readBytes(this.wrapper.buffer, values, value, value2);
        return ByteBufHelper.readerIndex(this.wrapper.buffer) - value3;
    }

    @Override
    public long skip(long value) {
        int value2 = ByteBufHelper.readerIndex(this.wrapper.buffer);
        ByteBufHelper.skipBytes(this.wrapper.buffer, (int) value);
        return ByteBufHelper.readerIndex(this.wrapper.buffer) - value2;
    }

    @Override
    public int available() {
        return ByteBufHelper.readableBytes(this.wrapper.buffer);
    }

    @Override
    public void close() {}

    @Override
    public void mark(int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    public PacketWrapper<?> getWrapper() {
        return this.wrapper;
    }
}
