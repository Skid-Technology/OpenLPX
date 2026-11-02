package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol;

public enum ConnectionState {
    HANDSHAKING,
    STATUS,
    LOGIN,
    PLAY,
    CONFIGURATION;

    public static ConnectionState getById(int value) {
        return value < values().length && value >= 0 ? values()[value] : null;
    }
}
