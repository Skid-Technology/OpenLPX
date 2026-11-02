package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection;

import java.lang.annotation.Annotation;

public class NestedClassUtil {

    public static Class<?> getNestedClass(Class<?> classValue, String text) {
        if (classValue == null) {
            return null;
        }
        Class[] classs;
        int value = (classs = classValue.getDeclaredClasses()).length;
        for (int index = 0; index < value; index++) {
            Class<?> classValue2;
            if ((classValue2 = classs[index]).getSimpleName().equals(text)) {
                return classValue2;
            }
        }
        return null;
    }

    public static Class<?> getNestedClass(Class<?> classValue, int value) {
        if (classValue == null) {
            return null;
        }
        int value2 = 0;
        Class[] classs;
        int value3 = (classs = classValue.getDeclaredClasses()).length;
        for (int index = 0; index < value3; index++) {
            Class<?> classValue2 = classs[index];
            if (value == value2++) {
                return classValue2;
            }
        }
        return null;
    }

    public static Class<?> getNestedClass(Class<?> classValue, Annotation annotation, int value) {
        int value2 = 0;
        Class[] classs;
        int value3 = (classs = classValue.getDeclaredClasses()).length;
        for (int index = 0; index < value3; index++) {
            Class<?> classValue2;
            if ((classValue2 = classs[index])
                            .isAnnotationPresent(
                                    (Class<? extends Annotation>) annotation.getClass())
                    && value == value2++) {
                return classValue2;
            }
        }
        return null;
    }
}
