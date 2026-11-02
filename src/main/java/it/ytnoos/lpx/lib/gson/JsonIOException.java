package it.ytnoos.lpx.lib.gson;

public final class JsonIOException extends JsonParseException {

    private static final long serialVersionUID = 1L;

    public JsonIOException(String text) {
        super(text);
    }

    public JsonIOException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public JsonIOException(Throwable throwable) {
        super(throwable);
    }
}
