package it.ytnoos.lpx.lib.gson.internal;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Primitives {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;

    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    private Primitives() {
        throw new UnsupportedOperationException();
    }

    private static void add(
            Map<Class<?>, Class<?>> entries,
            Map<Class<?>, Class<?>> entries2,
            Class<?> classValue,
            Class<?> classValue2) {
        entries.put(classValue, classValue2);
        entries2.put(classValue2, classValue);
    }

    public static boolean isPrimitive(Type type) {
        return PRIMITIVE_TO_WRAPPER_TYPE.containsKey(type);
    }

    public static boolean isWrapperType(Type type) {
        return WRAPPER_TO_PRIMITIVE_TYPE.containsKey($Gson$Preconditions.checkNotNull(type));
    }

    public static <T> Class<T> wrap(Class<T> classValue) {
        Class<?> classValue2;
        return (classValue2 =
                                PRIMITIVE_TO_WRAPPER_TYPE.get(
                                        $Gson$Preconditions.checkNotNull(classValue)))
                        == null
                ? classValue
                : (Class<T>) classValue2;
    }

    public static <T> Class<T> unwrap(Class<T> classValue) {
        Class<?> classValue2;
        return (classValue2 =
                                WRAPPER_TO_PRIMITIVE_TYPE.get(
                                        $Gson$Preconditions.checkNotNull(classValue)))
                        == null
                ? classValue
                : (Class<T>) classValue2;
    }

    static {
        HashMap entries = new HashMap();
        HashMap entries2 = new HashMap();
        add(entries, entries2, boolean.class, Boolean.class);
        add(entries, entries2, byte.class, Byte.class);
        add(entries, entries2, char.class, Character.class);
        add(entries, entries2, double.class, Double.class);
        add(entries, entries2, float.class, Float.class);
        add(entries, entries2, int.class, Integer.class);
        add(entries, entries2, long.class, Long.class);
        add(entries, entries2, short.class, Short.class);
        add(entries, entries2, void.class, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(entries);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(entries2);
    }
}
