package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Deprecated
public class NetStreamOutput extends FilterOutputStream {

    public NetStreamOutput(OutputStream outputStream) {
        super(outputStream);
    }

    public void writeBoolean(boolean matches) {
        this.writeByte(matches ? 1 : 0);
    }

    public void writeByte(int value) {
        try {
            this.write(value);
        } catch (IOException oException) {
            oException.printStackTrace();
        }
    }

    public void writeShort(int value) {
        this.writeByte((byte) (value >>> 8 & 0xFF));
        this.writeByte((byte) (value >>> 0 & 0xFF));
    }

    public void writeChar(int value) {
        this.writeShort(value);
    }

    public void writeInt(int value) {
        this.writeByte((byte) (value >>> 24 & 0xFF));
        this.writeByte((byte) (value >>> 16 & 0xFF));
        this.writeByte((byte) (value >>> 8 & 0xFF));
        this.writeByte((byte) (value >>> 0 & 0xFF));
    }

    public void writeVarInt(int value) {
        while ((value & -128) != 0) {
            this.writeByte(value & 127 | 128);
            value >>>= 7;
        }
        this.writeByte(value);
    }

    public void writeLong(long value) {
        this.writeByte((byte) (value >>> 56));
        this.writeByte((byte) (value >>> 48));
        this.writeByte((byte) (value >>> 40));
        this.writeByte((byte) (value >>> 32));
        this.writeByte((byte) (value >>> 24));
        this.writeByte((byte) (value >>> 16));
        this.writeByte((byte) (value >>> 8));
        this.writeByte((byte) (value >>> 0));
    }

    public void writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            this.writeByte((int) (value & 127L) | 128);
            value >>>= 7;
        }
        this.writeByte((int) value);
    }

    public void writeFloat(float value) {
        this.writeInt(Float.floatToIntBits(value));
    }

    public void writeDouble(double value) {
        this.writeLong(Double.doubleToLongBits(value));
    }

    public void writeBytes(byte[] values) {
        this.writeBytes(values, values.length);
    }

    public void writeBytes(byte[] values, int value) {
        try {
            this.write(values, 0, value);
        } catch (IOException oException) {
            oException.printStackTrace();
        }
    }

    public void writeShorts(short[] values) {
        this.writeShorts(values, values.length);
    }

    public void writeShorts(short[] values, int value) {
        for (int index = 0; index < value; index++) {
            this.writeShort(values[index]);
        }
    }

    public void writeInts(int[] values) {
        this.writeInts(values, values.length);
    }

    public void writeInts(int[] values, int value) {
        for (int index = 0; index < value; index++) {
            this.writeInt(values[index]);
        }
    }

    public void writeLongs(long[] values) {
        this.writeLongs(values, values.length);
    }

    public void writeLongs(long[] values, int value) {
        for (int index = 0; index < value; index++) {
            this.writeLong(values[index]);
        }
    }

    public void writeString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("String cannot be null!");
        }
        byte[] values;
        if ((values = text.getBytes(StandardCharsets.UTF_8)).length > 32767) {
            try {
                throw new IOException(
                        "String too big (was "
                                + text.length()
                                + " bytes encoded, max "
                                + 32767
                                + ")");
            } catch (IOException oException) {
                oException.printStackTrace();
            }
        } else {
            this.writeVarInt(values.length);
            this.writeBytes(values);
        }
    }

    public void writeUUID(UUID uUID) {
        this.writeLong(uUID.getMostSignificantBits());
        this.writeLong(uUID.getLeastSignificantBits());
    }
}
