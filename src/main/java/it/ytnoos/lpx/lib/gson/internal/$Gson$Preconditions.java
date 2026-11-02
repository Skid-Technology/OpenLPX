package it.ytnoos.lpx.lib.gson.internal;

public final class $Gson$Preconditions {

    private $Gson$Preconditions() {
        throw new UnsupportedOperationException();
    }

    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        } else {
            return (T) t;
        }
    }

    public static void checkArgument(boolean matches) {
        if (!matches) {
            throw new IllegalArgumentException();
        }
    }
}
