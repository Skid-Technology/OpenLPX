package it.ytnoos.lpx.lib.gson;

public class JsonParseException extends RuntimeException {

    static final long serialVersionUID = -4086729973971783390L;

    public JsonParseException(String text) {
        super(text);
    }

    public JsonParseException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public JsonParseException(Throwable throwable) {
        super(throwable);
    }
}
