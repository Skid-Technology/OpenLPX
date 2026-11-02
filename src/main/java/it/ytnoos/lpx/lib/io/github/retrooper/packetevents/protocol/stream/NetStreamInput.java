package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Deprecated
public class NetStreamInput extends FilterInputStream {

    public NetStreamInput(InputStream inputStream) {
        super(inputStream);
    }

    public boolean readBoolean() {
        return this.readByte() == 1;
    }

    public byte readByte() {
        return (byte) this.readUnsignedByte();
    }

    public int readUnsignedByte() {
        int value = 0;
        try {
            value = this.read();
        } catch (IOException oException) {
            oException.printStackTrace();
        }
        if (value < 0) {
            throw new IllegalStateException();
        } else {
            return value;
        }
    }

    public short readShort() {
        return (short) this.readUnsignedShort();
    }

    public int readUnsignedShort() {
        int value = this.readUnsignedByte();
        int value2 = this.readUnsignedByte();
        return (value << 8) + value2;
    }

    public char readChar() {
        return (char) this.readUnsignedShort();
    }

    public int readInt() {
        int value = this.readUnsignedByte();
        int value2 = this.readUnsignedByte();
        int value3 = this.readUnsignedByte();
        int value4 = this.readUnsignedByte();
        return (value << 24) + (value2 << 16) + (value3 << 8) + value4;
    }

    public int readVarInt() {
        int value = 0;
        int value2 = 0;
        byte value3;
        while (((value3 = this.readByte()) & 128) == 128) {
            value |= (value3 & 127) << value2++ * 7;
            if (value2 > 5) {
                throw new IllegalStateException("VarInt too long (length must be <= 5)");
            }
        }
        return value | (value3 & 127) << value2 * 7;
    }

    public long readLong() {
        byte[] values;
        return ((long) (values = this.readBytes(8))[0] << 56)
                + ((long) (values[1] & 0xFF) << 48)
                + ((long) (values[2] & 0xFF) << 40)
                + ((long) (values[3] & 0xFF) << 32)
                + ((long) (values[4] & 0xFF) << 24)
                + ((values[5] & 255) << 16)
                + ((values[6] & 255) << 8)
                + ((values[7] & 255) << 0);
    }

    public long readVarLong() {
        long value = 0L;
        int value2 = 0;
        byte value3;
        while (((value3 = this.readByte()) & 128) == 128) {
            value |= (long) (value3 & 127) << value2++ * 7;
            if (value2 > 10) {
                throw new IllegalStateException("VarLong too long (length must be <= 10)");
            }
        }
        return value | (long) (value3 & 127) << value2 * 7;
    }

    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }

    public byte[] readBytes(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        }
        byte[] values = new byte[value];
        int value2 = 0;
        while (value2 < value) {
            int value3 = 0;
            try {
                value3 = this.read(values, value2, value - value2);
            } catch (IOException oException) {
                oException.printStackTrace();
            }
            if (value3 < 0) {
                throw new IllegalStateException();
            }
            value2 += value3;
        }
        return values;
    }

    public int readBytes(byte[] values) {
        try {
            return this.read(values);
        } catch (IOException oException) {
            oException.printStackTrace();
            return -1;
        }
    }

    public int readBytes(byte[] values, int value, int value2) {
        try {
            return this.read(values, value, value2);
        } catch (IOException oException) {
            oException.printStackTrace();
            return -1;
        }
    }

    public short[] readShorts(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        } else {
            short[] values = new short[value];
            if (this.readShorts(values) < value) {
                throw new IllegalStateException();
            } else {
                return values;
            }
        }
    }

    public int readShorts(short[] values) {
        return this.readShorts(values, 0, values.length);
    }

    public int readShorts(short[] values, int value, int value2) {
        for (int value3 = value; value3 < value + value2; value3++) {
            try {
                values[value3] = this.readShort();
            } catch (Exception exception) {
                return value3 - value;
            }
        }
        return value2;
    }

    public int[] readInts(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        } else {
            int[] values = new int[value];
            if (this.readInts(values) < value) {
                throw new IllegalStateException();
            } else {
                return values;
            }
        }
    }

    public int readInts(int[] values) {
        return this.readInts(values, 0, values.length);
    }

    public int readInts(int[] values, int value, int value2) {
        for (int value3 = value; value3 < value + value2; value3++) {
            try {
                values[value3] = this.readInt();
            } catch (Exception exception) {
                return value3 - value;
            }
        }
        return value2;
    }

    public long[] readLongs(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        } else {
            long[] values = new long[value];
            if (this.readLongs(values) < value) {
                throw new IllegalStateException();
            } else {
                return values;
            }
        }
    }

    public int readLongs(long[] values) {
        return this.readLongs(values, 0, values.length);
    }

    public int readLongs(long[] values, int value, int value2) {
        for (int value3 = value; value3 < value + value2; value3++) {
            try {
                values[value3] = this.readLong();
            } catch (Exception exception) {
                return value3 - value;
            }
        }
        return value2;
    }

    public String readString() {
        int value = this.readVarInt();
        byte[] values = this.readBytes(value);
        return new String(values, StandardCharsets.UTF_8);
    }

    public UUID readUUID() {
        return new UUID(this.readLong(), this.readLong());
    }
}
