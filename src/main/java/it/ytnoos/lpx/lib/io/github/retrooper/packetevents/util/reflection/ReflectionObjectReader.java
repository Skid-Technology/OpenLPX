package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection;

public interface ReflectionObjectReader {

    boolean readBoolean(int value);

    byte readByte(int value);

    short readShort(int value);

    int readInt(int value);

    long readLong(int value);

    float readFloat(int value);

    double readDouble(int value);

    boolean[] readBooleanArray(int value);

    byte[] readByteArray(int value);

    short[] readShortArray(int value);

    int[] readIntArray(int value);

    long[] readLongArray(int value);

    float[] readFloatArray(int value);

    double[] readDoubleArray(int value);

    String[] readStringArray(int value);

    String readString(int value);

    <T> T readObject(int value, Class<? extends T> classValue);

    <T> T[] readObjectArray(int value, Class<? extends T> classValue);

    Enum<?> readEnumConstant(int value, Class<? extends Enum<?>> classValue);

    Object readAnyObject(int value);
}
