package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter;

public enum FilterMaskType {
    PASS_THROUGH,
    FULLY_FILTERED,
    PARTIALLY_FILTERED;

    public static final FilterMaskType[] VALUES = values();

    public int getId() {
        return this.ordinal();
    }

    public static FilterMaskType getById(int value) {
        return VALUES[value];
    }
}
