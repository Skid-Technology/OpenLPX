package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection;

public interface ReflectionObjectWriter {

    void writeBoolean(int value, boolean matches);

    void writeByte(int value, byte value2);

    void writeShort(int value, short value2);

    void writeInt(int value, int value2);

    void writeLong(int value, long value2);

    void writeFloat(int value, float value2);

    void writeDouble(int value, double value2);

    void writeString(int value, String text);

    void writeBooleanArray(int value, boolean[] matchess);

    void writeByteArray(int value, byte[] values);

    void writeShortArray(int value, short[] values);

    void writeIntArray(int value, int[] values);

    void writeLongArray(int value, long[] values);

    void writeFloatArray(int value, float[] values);

    void writeDoubleArray(int value, double[] values);

    void writeStringArray(int value, String[] texts);

    void writeObject(int value, Object result);

    void writeAnyObject(int value, Object result);

    void writeEnumConstant(int value, Enum<?> enumValue);
}
