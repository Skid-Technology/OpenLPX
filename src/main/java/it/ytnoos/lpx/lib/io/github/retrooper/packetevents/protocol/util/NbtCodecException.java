package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class NbtCodecException extends RuntimeException {

    private static final boolean DEBUG_TRACES =
            Boolean.getBoolean("packetevents.debug.nbt-codec-trace");

    public NbtCodecException(String text) {
        super(text);
    }

    public NbtCodecException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public NbtCodecException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return DEBUG_TRACES ? super.fillInStackTrace() : this;
    }
}
