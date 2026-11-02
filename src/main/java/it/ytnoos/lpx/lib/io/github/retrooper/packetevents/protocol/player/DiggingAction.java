package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;

public enum DiggingAction {
    START_DIGGING,
    CHANGE_DESTROY_DIRECTION,
    CANCELLED_DIGGING,
    FINISHED_DIGGING,
    DROP_ITEM_STACK,
    DROP_ITEM,
    RELEASE_USE_ITEM,
    SWAP_ITEM_WITH_OFFHAND,
    STAB;

    private static final DiggingAction[] VALUES = values();

    public int getId(ServerVersion serverVersion) {
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3) || this == START_DIGGING) {
            return this.ordinal();
        } else if (this == CHANGE_DESTROY_DIRECTION) {
            throw new IllegalStateException("CHANGE_DESTROY_DIRECTION has no id before 26.3");
        } else {
            return this.ordinal() - 1;
        }
    }

    @Deprecated
    public int getId() {
        return this.getId(ServerVersion.V_26_2);
    }

    public static DiggingAction getById(ServerVersion serverVersion, int value) {
        return !serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3) && value > 0
                ? VALUES[value + 1]
                : VALUES[value];
    }

    @Deprecated
    public static DiggingAction getById(int value) {
        return getById(ServerVersion.V_26_2, value);
    }
}
