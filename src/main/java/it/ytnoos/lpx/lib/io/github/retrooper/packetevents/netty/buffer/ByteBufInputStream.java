package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ByteBufInputStream extends InputStream implements DataInput {

    private final Object buffer;

    private final int startIndex;

    private final int endIndex;

    private final boolean releaseOnClose;

    private final StringBuilder lineBuf = new StringBuilder();

    private boolean closed;

    public ByteBufInputStream(Object result) {
        this(result, ByteBufHelper.readableBytes(result));
    }

    public ByteBufInputStream(Object result, int value) {
        this(result, value, false);
    }

    public ByteBufInputStream(Object result, boolean matches) {
        this(result, ByteBufHelper.readableBytes(result), matches);
    }

    public ByteBufInputStream(Object buffer, int value, boolean releaseOnClose) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        if (value < 0) {
            if (releaseOnClose) {
                ByteBufHelper.release(buffer);
            }
            throw new IllegalArgumentException("maxLength: " + value);
        } else if (ByteBufHelper.readableBytes(buffer) > value) {
            if (releaseOnClose) {
                ByteBufHelper.release(buffer);
            }
            throw new IndexOutOfBoundsException(
                    "Too many bytes to be read - Found "
                            + ByteBufHelper.readableBytes(buffer)
                            + ", maximum is "
                            + value);
        } else {
            this.releaseOnClose = releaseOnClose;
            this.buffer = buffer;
            this.startIndex = ByteBufHelper.readerIndex(buffer);
            this.endIndex = this.startIndex + ByteBufHelper.readableBytes(buffer);
            ByteBufHelper.markReaderIndex(buffer);
        }
    }

    public int readBytes() {
        return ByteBufHelper.readerIndex(this.buffer) - this.startIndex;
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (this.releaseOnClose && !this.closed) {
                this.closed = true;
                ByteBufHelper.release(this.buffer);
            }
        }
    }

    @Override
    public int available() throws IOException {
        return this.endIndex - ByteBufHelper.readerIndex(this.buffer);
    }

    @Override
    public void mark(int value) {
        ByteBufHelper.markReaderIndex(this.buffer);
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() throws IOException {
        return !ByteBufHelper.isReadable(this.buffer)
                ? -1
                : ByteBufHelper.readByte(this.buffer) & 0xFF;
    }

    @Override
    public int read(byte[] values, int value, int value2) throws IOException {
        int value3;
        if ((value3 = this.available()) == 0) {
            return -1;
        }
        value2 = Math.min(value3, value2);
        ByteBufHelper.readBytes(this.buffer, values, value, value2);
        return value2;
    }

    @Override
    public void reset() throws IOException {
        ByteBufHelper.resetReaderIndex(this.buffer);
    }

    @Override
    public long skip(long value) throws IOException {
        return value > 2147483647L
                ? this.skipBytes(Integer.MAX_VALUE)
                : this.skipBytes((int) value);
    }

    @Override
    public boolean readBoolean() throws IOException {
        this.checkAvailable(1);
        return this.read() != 0;
    }

    public byte[] readBytes(int value) {
        byte[] values = new byte[value];
        ByteBufHelper.readBytes(this.buffer, values);
        return values;
    }

    @Override
    public byte readByte() throws IOException {
        if (!ByteBufHelper.isReadable(this.buffer)) {
            throw new EOFException();
        } else {
            return ByteBufHelper.readByte(this.buffer);
        }
    }

    @Override
    public char readChar() throws IOException {
        return (char) this.readShort();
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public void readFully(byte[] values) throws IOException {
        this.readFully(values, 0, values.length);
    }

    @Override
    public void readFully(byte[] values, int value, int value2) throws IOException {
        this.checkAvailable(value2);
        ByteBufHelper.readBytes(this.buffer, values, value, value2);
    }

    @Override
    public int readInt() throws IOException {
        this.checkAvailable(4);
        return ByteBufHelper.readInt(this.buffer);
    }

    @Override
    public String readLine() throws IOException {
        this.lineBuf.setLength(0);
        while (ByteBufHelper.isReadable(this.buffer)) {
            short value;
            switch (value = ByteBufHelper.readUnsignedByte(this.buffer)) {
                case 13:
                    if (ByteBufHelper.isReadable(this.buffer)
                            && (char)
                                            ByteBufHelper.getUnsignedByte(
                                                    this.buffer,
                                                    ByteBufHelper.readerIndex(this.buffer))
                                    == '\n') {
                        ByteBufHelper.skipBytes(this.buffer, 1);
                    }
                case 10:
                    return this.lineBuf.toString();
                default:
                    this.lineBuf.append((char) value);
            }
        }
        return this.lineBuf.length() > 0 ? this.lineBuf.toString() : null;
    }

    @Override
    public long readLong() throws IOException {
        this.checkAvailable(8);
        return ByteBufHelper.readLong(this.buffer);
    }

    public long[] readLongs(int value) throws IOException {
        long[] values = new long[value];
        for (int index = 0; index < values.length; index++) {
            values[index] = this.readLong();
        }
        return values;
    }

    @Override
    public short readShort() throws IOException {
        this.checkAvailable(2);
        return ByteBufHelper.readShort(this.buffer);
    }

    @Override
    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return this.readByte() & 0xFF;
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return this.readShort() & 65535;
    }

    @Override
    public int skipBytes(int value) throws IOException {
        int value2 = Math.min(this.available(), value);
        ByteBufHelper.skipBytes(this.buffer, value2);
        return value2;
    }

    private void checkAvailable(int value) throws IOException {
        if (value < 0) {
            throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
        }
        if (value > this.available()) {
            int value2 = this.available();
            String text =
                    "fieldSize is too long! Length is " + value + ", but maximum is " + value2;
            if (value2 == 0) {
                throw new PacketProcessException(text);
            } else {
                throw new EOFException(text);
            }
        }
    }
}
