package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception;

public class PacketProcessException extends RuntimeException {

    public PacketProcessException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public PacketProcessException(Throwable throwable) {
        super(throwable);
    }

    public PacketProcessException(String text) {
        super(text);
    }
}
