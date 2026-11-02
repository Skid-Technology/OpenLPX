package it.ytnoos.lpx.lib.kyori.adventure.permission;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointer;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Predicate;

public interface PermissionChecker extends Predicate<String> {

    Pointer<PermissionChecker> POINTER =
            Pointer.pointer(PermissionChecker.class, Key.key("adventure", "permission"));

    @NotNull
    static PermissionChecker always(@NotNull TriState triState) {
        Objects.requireNonNull(triState);
        if (triState == TriState.TRUE) {
            return PermissionCheckers.TRUE;
        } else {
            return triState == TriState.FALSE
                    ? PermissionCheckers.FALSE
                    : PermissionCheckers.NOT_SET;
        }
    }

    @NotNull
    TriState value(@NotNull String text);

    default boolean test(@NotNull String text) {
        return this.value(text) == TriState.TRUE;
    }
}
