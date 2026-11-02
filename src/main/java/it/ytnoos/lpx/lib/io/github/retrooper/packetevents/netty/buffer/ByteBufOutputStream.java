package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteBufOutputStream extends OutputStream implements DataOutput {

    private final Object buffer;

    private final int startIndex;

    private final DataOutputStream utf8out = new DataOutputStream(this);

    public ByteBufOutputStream(Object buffer) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        this.buffer = buffer;
        this.startIndex = ByteBufHelper.writerIndex(buffer);
    }

    public int writtenBytes() {
        return ByteBufHelper.writerIndex(this.buffer) - this.startIndex;
    }

    @Override
    public void write(byte[] values, int value, int value2) throws IOException {
        if (value2 != 0) {
            ByteBufHelper.writeBytes(this.buffer, values, value, value2);
        }
    }

    @Override
    public void write(byte[] values) throws IOException {
        ByteBufHelper.writeBytes(this.buffer, values);
    }

    @Override
    public void write(int value) throws IOException {
        ByteBufHelper.writeByte(this.buffer, value);
    }

    @Override
    public void writeBoolean(boolean matches) throws IOException {
        ByteBufHelper.writeBoolean(this.buffer, matches);
    }

    @Override
    public void writeByte(int value) throws IOException {
        ByteBufHelper.writeByte(this.buffer, value);
    }

    @Deprecated
    @Override
    public void writeBytes(String text) throws IOException {
        throw new IllegalStateException("This operation is not supported!");
    }

    @Override
    public void writeChar(int value) throws IOException {
        ByteBufHelper.writeChar(this.buffer, value);
    }

    @Override
    public void writeChars(String text) throws IOException {
        int size = text.length();
        for (int index = 0; index < size; index++) {
            ByteBufHelper.writeChar(this.buffer, text.charAt(index));
        }
    }

    @Override
    public void writeDouble(double value) throws IOException {
        ByteBufHelper.writeDouble(this.buffer, value);
    }

    @Override
    public void writeFloat(float value) throws IOException {
        ByteBufHelper.writeFloat(this.buffer, value);
    }

    @Override
    public void writeInt(int value) throws IOException {
        ByteBufHelper.writeInt(this.buffer, value);
    }

    @Override
    public void writeLong(long value) throws IOException {
        ByteBufHelper.writeLong(this.buffer, value);
    }

    @Override
    public void writeShort(int value) throws IOException {
        ByteBufHelper.writeShort(this.buffer, value);
    }

    @Override
    public void writeUTF(String text) throws IOException {
        this.utf8out.writeUTF(text);
    }

    public Object buffer() {
        return this.buffer;
    }
}
