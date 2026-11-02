package it.ytnoos.lpx.lib.gson;

public final class JsonNull extends JsonElement {

    public static final JsonNull INSTANCE = new JsonNull();

    JsonNull deepCopy() {
        return INSTANCE;
    }

    @Override
    public int hashCode() {
        return JsonNull.class.hashCode();
    }

    @Override
    public boolean equals(Object result) {
        return this == result || result instanceof JsonNull;
    }
}
