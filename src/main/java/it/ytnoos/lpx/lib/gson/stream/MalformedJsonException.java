package it.ytnoos.lpx.lib.gson.stream;

import java.io.IOException;

public final class MalformedJsonException extends IOException {

    private static final long serialVersionUID = 1L;

    public MalformedJsonException(String text) {
        super(text);
    }

    public MalformedJsonException(String text, Throwable throwable) {
        super(text);
        this.initCause(throwable);
    }

    public MalformedJsonException(Throwable throwable) {
        this.initCause(throwable);
    }
}
