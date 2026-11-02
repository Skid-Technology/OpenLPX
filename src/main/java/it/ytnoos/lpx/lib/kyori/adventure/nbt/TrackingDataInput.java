package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.IOException;

final class TrackingDataInput implements DataInput, BinaryTagScope {

    private final DataInput input;

    private final long maxLength;

    private long counter;

    private int depth;

    TrackingDataInput(DataInput dataInput, long maxLength) {
        this.input = dataInput;
        this.maxLength = maxLength;
    }

    public static BinaryTagScope enter(DataInput dataInput) throws IOException {
        return dataInput instanceof TrackingDataInput
                ? ((TrackingDataInput) dataInput).enter()
                : BinaryTagScope.NoOp.INSTANCE;
    }

    public static BinaryTagScope enter(DataInput dataInput, long value) throws IOException {
        return dataInput instanceof TrackingDataInput
                ? ((TrackingDataInput) dataInput).enter(value)
                : BinaryTagScope.NoOp.INSTANCE;
    }

    public DataInput input() {
        return this.input;
    }

    public TrackingDataInput enter(long value) throws IOException {
        if (this.depth++ > 512) {
            throw new IOException("NBT read exceeded maximum depth of 512");
        }
        this.ensureMaxLength(value);
        return this;
    }

    public TrackingDataInput enter() throws IOException {
        return this.enter(0L);
    }

    public void exit() throws IOException {
        this.depth--;
        this.ensureMaxLength(0L);
    }

    private void ensureMaxLength(long value) throws IOException {
        if (this.maxLength > 0L && this.counter + value > this.maxLength) {
            throw new IOException(
                    "The read NBT was longer than the maximum allowed size of "
                            + this.maxLength
                            + " bytes!");
        }
    }

    @Override
    public void readFully(byte @NotNull [] values) throws IOException {
        this.counter += values.length;
        this.input.readFully(values);
    }

    @Override
    public void readFully(byte @NotNull [] values, int value, int counter) throws IOException {
        this.counter += counter;
        this.input.readFully(values, value, counter);
    }

    @Override
    public int skipBytes(int value) throws IOException {
        return this.input.skipBytes(value);
    }

    @Override
    public boolean readBoolean() throws IOException {
        this.counter++;
        return this.input.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        this.counter++;
        return this.input.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        this.counter++;
        return this.input.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        this.counter += 2L;
        return this.input.readShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        this.counter += 2L;
        return this.input.readUnsignedShort();
    }

    @Override
    public char readChar() throws IOException {
        this.counter += 2L;
        return this.input.readChar();
    }

    @Override
    public int readInt() throws IOException {
        this.counter += 4L;
        return this.input.readInt();
    }

    @Override
    public long readLong() throws IOException {
        this.counter += 8L;
        return this.input.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        this.counter += 4L;
        return this.input.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        this.counter += 8L;
        return this.input.readDouble();
    }

    @Nullable
    @Override
    public String readLine() throws IOException {
        String text;
        if ((text = this.input.readLine()) != null) {
            this.counter = this.counter + (text.length() + 1);
        }
        return text;
    }

    @NotNull
    @Override
    public String readUTF() throws IOException {
        String text = this.input.readUTF();
        this.counter = this.counter + text.length() * 2L + 2L;
        return text;
    }

    @Override
    public void close() throws IOException {
        this.exit();
    }
}
