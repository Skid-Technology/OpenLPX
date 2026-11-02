package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

public enum TriState {
    NOT_SET,
    FALSE,
    TRUE;

    @Nullable
    public Boolean toBoolean() {
        switch (this) {
            case TRUE:
                return Boolean.TRUE;
            case FALSE:
                return Boolean.FALSE;
            default:
                return null;
        }
    }

    public boolean toBooleanOrElse(boolean matches) {
        switch (this) {
            case TRUE:
                return true;
            case FALSE:
                return false;
            default:
                return matches;
        }
    }

    public boolean toBooleanOrElseGet(@NotNull BooleanSupplier booleanSupplier) {
        switch (this) {
            case TRUE:
                return true;
            case FALSE:
                return false;
            default:
                return booleanSupplier.getAsBoolean();
        }
    }

    @NotNull
    public static TriState byBoolean(boolean matches) {
        return matches ? TRUE : FALSE;
    }

    @NotNull
    public static TriState byBoolean(@Nullable Boolean matches) {
        return matches == null ? NOT_SET : byBoolean(matches.booleanValue());
    }
}
