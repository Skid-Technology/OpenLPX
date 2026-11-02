package it.ytnoos.lpx.lib.gson;

public final class JsonSyntaxException extends JsonParseException {

    private static final long serialVersionUID = 1L;

    public JsonSyntaxException(String text) {
        super(text);
    }

    public JsonSyntaxException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public JsonSyntaxException(Throwable throwable) {
        super(throwable);
    }
}
