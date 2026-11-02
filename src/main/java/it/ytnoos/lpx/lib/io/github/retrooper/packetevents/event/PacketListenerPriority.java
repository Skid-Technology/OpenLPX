package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

public enum PacketListenerPriority {
    LOWEST,
    LOW,
    NORMAL,
    HIGH,
    HIGHEST,
    MONITOR;

    public static PacketListenerPriority getById(byte value) {
        return values()[value];
    }

    public byte getId() {
        return (byte) this.ordinal();
    }
}
