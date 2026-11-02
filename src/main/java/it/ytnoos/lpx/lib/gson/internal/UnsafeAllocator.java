package it.ytnoos.lpx.lib.gson.internal;

import it.ytnoos.lpx.compat.ReflectionNames;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class UnsafeAllocator {

    public abstract <T> T newInstance(Class<T> classValue) throws Exception;

    public static UnsafeAllocator create() {
        try {
            Class<?> classValue2;
            Field field;
            (field = (classValue2 = Class.forName("sun.misc.Unsafe")).getDeclaredField("theUnsafe"))
                    .setAccessible(true);
            final Object result2 = field.get(null);
            final Method method = classValue2.getMethod("allocateInstance", Class.class);
            return new UnsafeAllocator() {

                @Override
                public <T> T newInstance(Class<T> result) throws Exception {
                    UnsafeAllocator.assertInstantiable(result);
                    return (T) method.invoke(result2, result);
                }
            };
        } catch (Exception exception) {
            try {
                Class[] classs = new Class[] {Class.class};
                Method method2;
                (method2 =
                                ObjectStreamClass.class.getDeclaredMethod(
                                        ReflectionNames.methodName(
                                                "getConstructorId",
                                                ObjectStreamClass.class,
                                                classs),
                                        classs))
                        .setAccessible(true);
                final int result = (Integer) method2.invoke(null, Object.class);
                classs = new Class[] {Class.class, int.class};
                final Method method3;
                (method3 =
                                ObjectStreamClass.class.getDeclaredMethod(
                                        ReflectionNames.methodName(
                                                "newInstance", ObjectStreamClass.class, classs),
                                        classs))
                        .setAccessible(true);
                return new UnsafeAllocator() {

                    @Override
                    public <T> T newInstance(Class<T> classValue) throws Exception {
                        UnsafeAllocator.assertInstantiable(classValue);
                        return (T) method3.invoke(null, classValue, result);
                    }
                };
            } catch (Exception exception2) {
                try {
                    Class[] classs2 = new Class[] {Class.class, Class.class};
                    final Method method4;
                    (method4 =
                                    ObjectInputStream.class.getDeclaredMethod(
                                            ReflectionNames.methodName(
                                                    "newInstance",
                                                    ObjectInputStream.class,
                                                    classs2),
                                            classs2))
                            .setAccessible(true);
                    return new UnsafeAllocator() {

                        @Override
                        public <T> T newInstance(Class<T> result) throws Exception {
                            UnsafeAllocator.assertInstantiable(result);
                            return (T) method4.invoke(null, result, Object.class);
                        }
                    };
                } catch (Exception exception3) {
                    return new UnsafeAllocator() {

                        @Override
                        public <T> T newInstance(Class<T> result) {
                            throw new UnsupportedOperationException("Cannot allocate " + result);
                        }
                    };
                }
            }
        }
    }

    private static void assertInstantiable(Class<?> classValue) {
        int value;
        if (Modifier.isInterface(value = classValue.getModifiers())) {
            throw new UnsupportedOperationException(
                    "Interface can't be instantiated! Interface name: " + classValue.getName());
        }
        if (Modifier.isAbstract(value)) {
            throw new UnsupportedOperationException(
                    "Abstract class can't be instantiated! Class name: " + classValue.getName());
        }
    }
}
