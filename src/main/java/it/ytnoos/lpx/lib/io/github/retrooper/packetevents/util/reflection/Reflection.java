package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection;

import it.ytnoos.lpx.compat.ReflectionNames;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Reflection {

    public static Field[] getFields(Class<?> classValue) {
        if (classValue == null) {
            return new Field[0];
        }
        Field[] fields;
        Field[] fields2;
        int value = (fields2 = fields = classValue.getDeclaredFields()).length;
        for (int index = 0; index < value; index++) {
            Field field = fields2[index];
            try {
                field.setAccessible(true);
            } catch (Throwable throwable) {
            }
        }
        return fields;
    }

    public static Field getField(Class<?> classValue, String text) {
        if (classValue == null) {
            return null;
        }
        try {
            Field field;
            (field = classValue.getDeclaredField(ReflectionNames.fieldName(classValue, text)))
                    .setAccessible(true);
            return field;
        } catch (NoSuchFieldException noSuchFieldException) {
            if (classValue.getSuperclass() != null) {
                return getField(classValue.getSuperclass(), text);
            }
        } catch (Throwable throwable) {
        }
        return null;
    }

    public static Field getField(Class<?> classValue, Class<?> classValue2, int value) {
        if (classValue2 != null && classValue != null) {
            int value2 = 0;
            Field[] fields;
            int value3 = (fields = getFields(classValue)).length;
            for (int index = 0; index < value3; index++) {
                Field field = fields[index];
                if (classValue2.isAssignableFrom(field.getType()) && value2++ == value) {
                    return field;
                }
            }
            return classValue.getSuperclass() != null
                    ? getField(classValue.getSuperclass(), classValue2, value)
                    : null;
        } else {
            return null;
        }
    }

    public static Field getField(
            Class<?> classValue, Class<?> classValue2, int value, boolean matches) {
        if (classValue2 != null && classValue != null) {
            int value2 = 0;
            Field[] fields;
            int value3 = (fields = getFields(classValue)).length;
            for (int index = 0; index < value3; index++) {
                Field field = fields[index];
                if (classValue2.isAssignableFrom(field.getType())
                        && (!matches || !Modifier.isStatic(field.getModifiers()))
                        && value2++ == value) {
                    return field;
                }
            }
            return classValue.getSuperclass() != null
                    ? getField(classValue.getSuperclass(), classValue2, value)
                    : null;
        } else {
            return null;
        }
    }

    public static Field getField(Class<?> classValue, int value) {
        if (classValue == null) {
            return null;
        }
        try {
            return getFields(classValue)[value];
        } catch (Exception exception) {
            return classValue.getSuperclass() != null
                    ? getFields(classValue.getSuperclass())[value]
                    : null;
        }
    }

    public static Method getMethod(Class<?> classValue, String text, Class<?>... classValue2) {
        if (classValue == null) {
            return null;
        }
        try {
            Method method;
            (method =
                            classValue.getDeclaredMethod(
                                    ReflectionNames.methodName(text, classValue, classValue2),
                                    classValue2))
                    .setAccessible(true);
            return method;
        } catch (NoSuchMethodException noSuchMethodException) {
            try {
                Method method2;
                (method2 =
                                classValue.getMethod(
                                        ReflectionNames.methodName(text, classValue, classValue2),
                                        classValue2))
                        .setAccessible(true);
                return method2;
            } catch (NoSuchMethodException noSuchMethodException2) {
                return classValue.getSuperclass() != null
                        ? getMethod(classValue.getSuperclass(), text, classValue2)
                        : null;
            }
        }
    }

    public static Method getMethod(Class<?> classValue, int value, Class<?>... classValue2) {
        if (classValue == null) {
            return null;
        }
        int value2 = 0;
        Method[] methods;
        int value3 = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value3; index++) {
            Method method = methods[index];
            if ((classValue2 == null || Arrays.equals(method.getParameterTypes(), classValue2))
                    && value == value2++) {
                method.setAccessible(true);
                return method;
            }
        }
        return classValue.getSuperclass() != null
                ? getMethod(classValue.getSuperclass(), value, classValue2)
                : null;
    }

    public static Method getMethod(
            Class<?> classValue, Class<?> classValue2, int value, Class<?>... classValue3) {
        if (classValue == null) {
            return null;
        }
        int value2 = 0;
        Method[] methods;
        int value3 = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value3; index++) {
            Method method;
            if (Arrays.equals((method = methods[index]).getParameterTypes(), classValue3)
                    && (classValue2 == null || method.getReturnType().equals(classValue2))
                    && value == value2++) {
                method.setAccessible(true);
                return method;
            }
        }
        return classValue.getSuperclass() != null
                ? getMethod(classValue.getSuperclass(), null, value, classValue3)
                : null;
    }

    public static Method getMethodExact(
            Class<?> classValue, String text, Class<?> classValue2, Class<?>... classValue3) {
        if (classValue == null) {
            return null;
        }
        Method[] methods;
        int value = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value; index++) {
            Method method;
            if ((method = methods[index]).getName().equals(text)
                    && Arrays.equals(method.getParameterTypes(), classValue3)
                    && (classValue2 == null || method.getReturnType().equals(classValue2))) {
                method.setAccessible(true);
                return method;
            }
        }
        return classValue.getSuperclass() != null
                ? getMethodExact(classValue.getSuperclass(), text, null, classValue3)
                : null;
    }

    public static Method getMethod(Class<?> classValue, String text, int value) {
        if (classValue == null) {
            return null;
        }
        int value2 = 0;
        Method[] methods;
        int value3 = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value3; index++) {
            Method method;
            if ((method = methods[index]).getName().equals(text) && value == value2++) {
                method.setAccessible(true);
                return method;
            }
        }
        return classValue.getSuperclass() != null
                ? getMethod(classValue.getSuperclass(), text, value)
                : null;
    }

    public static Method getMethod(Class<?> classValue, Class<?> classValue2, int value) {
        if (classValue == null) {
            return null;
        }
        int value2 = 0;
        Method[] methods;
        int value3 = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value3; index++) {
            Method method = methods[index];
            if ((classValue2 == null || method.getReturnType().equals(classValue2))
                    && value == value2++) {
                method.setAccessible(true);
                return method;
            }
        }
        return classValue.getSuperclass() != null
                ? getMethod(classValue.getSuperclass(), classValue2, value)
                : null;
    }

    public static Method getMethodCheckContainsString(
            Class<?> classValue, String text, Class<?> classValue2) {
        if (classValue == null) {
            return null;
        }
        Method[] methods;
        int value = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value; index++) {
            Method method;
            if ((method = methods[index]).getName().contains(text)
                    && (classValue2 == null || method.getReturnType().equals(classValue2))) {
                method.setAccessible(true);
                return method;
            }
        }
        return classValue.getSuperclass() != null
                ? getMethodCheckContainsString(classValue.getSuperclass(), text, classValue2)
                : null;
    }

    public static List<Method> getMethods(Class<?> classValue, String text, Class<?> classValue2) {
        if (classValue == null) {
            return null;
        }
        ArrayList<Method> values = new ArrayList<>();
        Method[] methods;
        int value = (methods = classValue.getDeclaredMethods()).length;
        for (int index = 0; index < value; index++) {
            Method method;
            if ((method = methods[index]).getName().equals(text)
                    && (classValue2 == null || method.getReturnType().equals(classValue2))) {
                method.setAccessible(true);
                values.add(method);
            }
        }
        if (classValue.getSuperclass() != null) {
            values.addAll(getMethods(classValue.getSuperclass(), text, classValue2));
        }
        return values;
    }

    @Nullable
    public static Class<?> getClassByNameWithoutException(String text) {
        try {
            return Class.forName(ReflectionNames.className(text));
        } catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static Constructor<?> getConstructor(Class<?> classValue, int value) {
        if (classValue == null) {
            return null;
        }
        Constructor constructor;
        (constructor = classValue.getDeclaredConstructors()[value]).setAccessible(true);
        return constructor;
    }

    public static Constructor<?> getConstructor(Class<?> classValue, Class<?>... classValue2) {
        if (classValue == null) {
            return null;
        }
        try {
            Constructor constructor;
            (constructor = classValue.getDeclaredConstructor(classValue2)).setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException noSuchMethodException) {
            try {
                Constructor constructor2;
                (constructor2 = classValue.getConstructor(classValue2)).setAccessible(true);
                return constructor2;
            } catch (NoSuchMethodException noSuchMethodException2) {
                return null;
            }
        }
    }
}
