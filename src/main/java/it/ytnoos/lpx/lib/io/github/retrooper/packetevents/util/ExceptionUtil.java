package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import java.util.Set;

public class ExceptionUtil {

    public static boolean isException(Throwable throwable, Class<?> classValue) {
        while (throwable != null) {
            if (classValue.isAssignableFrom(throwable.getClass())) {
                return true;
            }
            throwable = throwable.getCause();
        }
        return false;
    }

    public static boolean isExceptionContainedIn(
            Throwable throwable, Set<Class<? extends Throwable>> values) {
        return values.contains(throwable.getClass());
    }
}
