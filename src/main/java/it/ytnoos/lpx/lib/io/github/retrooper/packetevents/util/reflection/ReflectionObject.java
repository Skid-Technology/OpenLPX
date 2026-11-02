package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionObject implements ReflectionObjectReader, ReflectionObjectWriter {

    private static final Map<Class<?>, Map<Class<?>, Field[]>> FIELD_CACHE =
            new ConcurrentHashMap<>();

    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    protected final Object object;

    private final Class<?> clazz;

    public ReflectionObject() {
        this.object = null;
        this.clazz = null;
    }

    public ReflectionObject(Object object) {
        this.object = object;
        this.clazz = object.getClass();
    }

    public ReflectionObject(Object object, Class<?> classValue) {
        this.object = object;
        this.clazz = classValue;
    }

    @Override
    public boolean readBoolean(int value) {
        return this.read(value, boolean.class);
    }

    @Override
    public byte readByte(int value) {
        return this.read(value, byte.class);
    }

    @Override
    public short readShort(int value) {
        return this.read(value, short.class);
    }

    @Override
    public int readInt(int value) {
        return this.read(value, int.class);
    }

    @Override
    public long readLong(int value) {
        return this.read(value, long.class);
    }

    @Override
    public float readFloat(int value) {
        return this.read(value, float.class);
    }

    @Override
    public double readDouble(int value) {
        return this.read(value, double.class);
    }

    @Override
    public boolean[] readBooleanArray(int value) {
        return this.read(value, boolean[].class);
    }

    @Override
    public byte[] readByteArray(int value) {
        return this.read(value, byte[].class);
    }

    @Override
    public short[] readShortArray(int value) {
        return this.read(value, short[].class);
    }

    @Override
    public int[] readIntArray(int value) {
        return this.read(value, int[].class);
    }

    @Override
    public long[] readLongArray(int value) {
        return this.read(value, long[].class);
    }

    @Override
    public float[] readFloatArray(int value) {
        return this.read(value, float[].class);
    }

    @Override
    public double[] readDoubleArray(int value) {
        return this.read(value, double[].class);
    }

    @Override
    public String[] readStringArray(int value) {
        return this.read(value, String[].class);
    }

    @Override
    public String readString(int value) {
        return this.read(value, String.class);
    }

    @Override
    public Object readAnyObject(int value) {
        try {
            Field field;
            if (!(field = this.clazz.getDeclaredFields()[value]).isAccessible()) {
                field.setAccessible(true);
            }
            try {
                return field.get(this.object);
            } catch (IllegalAccessException
                    | NullPointerException
                    | ArrayIndexOutOfBoundsException exception) {
                exception.printStackTrace();
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IllegalStateException(
                    "PacketEvents failed to find any field indexed "
                            + value
                            + " in the "
                            + this.clazz.getSimpleName()
                            + " class!");
        }
    }

    @Override
    public <T> T readObject(int value, Class<? extends T> classValue) {
        return this.read(value, classValue);
    }

    @Override
    public <T> T[] readObjectArray(int value, Class<? extends T> classValue) {
        return (T[]) ((Object[]) this.read(0, Array.newInstance(classValue, 0).getClass()));
    }

    @Override
    public Enum<?> readEnumConstant(int value, Class<? extends Enum<?>> classValue) {
        return this.read(value, classValue);
    }

    public <T> T read(int value, Class<? extends T> classValue) {
        try {
            return (T) this.getField(classValue, value).get(this.object);
        } catch (IllegalAccessException
                | NullPointerException
                | ArrayIndexOutOfBoundsException exception) {
            throw new IllegalStateException(
                    "PacketEvents failed to find a "
                            + classValue.getSimpleName()
                            + " indexed "
                            + value
                            + " by its type in the "
                            + this.clazz.getName()
                            + " class!");
        }
    }

    @Override
    public void writeBoolean(int value, boolean matches) {
        this.write(boolean.class, value, matches);
    }

    @Override
    public void writeByte(int value, byte value2) {
        this.write(byte.class, value, value2);
    }

    @Override
    public void writeShort(int value, short value2) {
        this.write(short.class, value, value2);
    }

    @Override
    public void writeInt(int value, int value2) {
        this.write(int.class, value, value2);
    }

    @Override
    public void writeLong(int value, long value2) {
        this.write(long.class, value, value2);
    }

    @Override
    public void writeFloat(int value, float value2) {
        this.write(float.class, value, value2);
    }

    @Override
    public void writeDouble(int value, double value2) {
        this.write(double.class, value, value2);
    }

    @Override
    public void writeString(int value, String text) {
        this.write(String.class, value, text);
    }

    @Override
    public void writeObject(int value, Object result) {
        this.write(result.getClass(), value, result);
    }

    @Override
    public void writeBooleanArray(int value, boolean[] matchess) {
        this.write(boolean[].class, value, matchess);
    }

    @Override
    public void writeByteArray(int value, byte[] values) {
        this.write(byte[].class, value, values);
    }

    @Override
    public void writeShortArray(int value, short[] values) {
        this.write(short[].class, value, values);
    }

    @Override
    public void writeIntArray(int value, int[] values) {
        this.write(int[].class, value, values);
    }

    @Override
    public void writeLongArray(int value, long[] values) {
        this.write(long[].class, value, values);
    }

    @Override
    public void writeFloatArray(int value, float[] values) {
        this.write(float[].class, value, values);
    }

    @Override
    public void writeDoubleArray(int value, double[] values) {
        this.write(double[].class, value, values);
    }

    @Override
    public void writeStringArray(int value, String[] texts) {
        this.write(String[].class, value, texts);
    }

    @Override
    public void writeAnyObject(int value, Object result) {
        try {
            this.clazz.getDeclaredFields()[value].set(this.object, result);
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "PacketEvents failed to find any field indexed "
                            + value
                            + " in the "
                            + this.clazz.getSimpleName()
                            + " class!");
        }
    }

    @Override
    public void writeEnumConstant(int value, Enum<?> enumValue) {
        try {
            this.write(enumValue.getClass(), value, enumValue);
        } catch (IllegalStateException illegalStateException) {
            this.write(enumValue.getDeclaringClass(), value, enumValue);
        }
    }

    public void write(Class<?> classValue, int value, Object result) throws IllegalStateException {
        Field field;
        if ((field = this.getField(classValue, value)) == null) {
            throw new IllegalStateException(
                    "PacketEvents failed to find a "
                            + classValue.getSimpleName()
                            + " indexed "
                            + value
                            + " by its type in the "
                            + this.clazz.getName()
                            + " class!");
        }
        try {
            field.set(this.object, result);
        } catch (IllegalAccessException | NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    public <T> List<T> readList(int value) {
        return this.read(value, List.class);
    }

    public void writeList(int value, List<?> values) {
        this.write(List.class, value, values);
    }

    private Field getField(Class<?> classValue, int value) {
        Map entries = FIELD_CACHE.computeIfAbsent(this.clazz, result -> new ConcurrentHashMap<>());
        Field[] fields;
        if ((fields =
                                ((Field[])
                                        entries.computeIfAbsent(
                                                classValue,
                                                result2 ->
                                                        this.getFields(
                                                                (Class<?>) result2,
                                                                this.clazz.getDeclaredFields()))))
                        .length
                >= value + 1) {
            return fields[value];
        } else {
            throw new IllegalStateException(
                    "PacketEvents failed to find a "
                            + classValue.getSimpleName()
                            + " indexed "
                            + value
                            + " by its type in the "
                            + this.clazz.getName()
                            + " class!");
        }
    }

    private Field[] getFields(Class<?> classValue, Field[] fields) {
        ArrayList<Field> values = new ArrayList<>();
        int value = fields.length;
        for (int index = 0; index < value; index++) {
            Field field;
            if ((field = fields[index]).getType().equals(classValue)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                values.add(field);
            }
        }
        return values.toArray(EMPTY_FIELD_ARRAY);
    }
}
