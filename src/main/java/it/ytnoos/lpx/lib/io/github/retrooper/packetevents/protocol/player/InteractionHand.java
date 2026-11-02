package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

public enum InteractionHand {
    MAIN_HAND,
    OFF_HAND;

    private static final InteractionHand[] VALUES = values();

    public int getId() {
        return this.ordinal();
    }

    public static InteractionHand getById(int value) {
        return VALUES[value];
    }
}
