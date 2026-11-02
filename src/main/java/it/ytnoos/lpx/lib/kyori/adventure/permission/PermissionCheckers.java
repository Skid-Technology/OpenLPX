package it.ytnoos.lpx.lib.kyori.adventure.permission;

import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class PermissionCheckers {

    static final PermissionChecker NOT_SET = new PermissionCheckers.Always(TriState.NOT_SET);

    static final PermissionChecker FALSE = new PermissionCheckers.Always(TriState.FALSE);

    static final PermissionChecker TRUE = new PermissionCheckers.Always(TriState.TRUE);

    private PermissionCheckers() {}

    private static final class Always implements PermissionChecker {

        private final TriState value;

        private Always(TriState triState) {
            this.value = triState;
        }

        @NotNull
        @Override
        public TriState value(@NotNull String text) {
            return this.value;
        }

        @Override
        public String toString() {
            return PermissionChecker.class.getSimpleName() + ".always(" + this.value + ")";
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                PermissionCheckers.Always always = (PermissionCheckers.Always) result;
                return this.value == always.value;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
    }
}
