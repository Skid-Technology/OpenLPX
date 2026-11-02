package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.JsonArray;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.gson.JsonPrimitive;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class JsonTreeWriter extends JsonWriter {

    private static final Writer UNWRITABLE_WRITER =
            new Writer() {

                @Override
                public void write(char[] values, int value, int value2) {
                    throw new AssertionError();
                }

                @Override
                public void flush() throws IOException {
                    throw new AssertionError();
                }

                @Override
                public void close() throws IOException {
                    throw new AssertionError();
                }
            };

    private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");

    private final List<JsonElement> stack = new ArrayList<>();

    private String pendingName;

    private JsonElement product = JsonNull.INSTANCE;

    public JsonTreeWriter() {
        super(UNWRITABLE_WRITER);
    }

    public JsonElement get() {
        if (!this.stack.isEmpty()) {
            throw new IllegalStateException("Expected one JSON element but was " + this.stack);
        } else {
            return this.product;
        }
    }

    private JsonElement peek() {
        return this.stack.get(this.stack.size() - 1);
    }

    private void put(JsonElement jsonElement) {
        if (this.pendingName != null) {
            if (!jsonElement.isJsonNull() || this.getSerializeNulls()) {
                ((JsonObject) this.peek()).add(this.pendingName, jsonElement);
            }
            this.pendingName = null;
        } else if (this.stack.isEmpty()) {
            this.product = jsonElement;
        } else {
            JsonElement jsonElement2;
            if (!((jsonElement2 = this.peek()) instanceof JsonArray)) {
                throw new IllegalStateException();
            }
            ((JsonArray) jsonElement2).add(jsonElement);
        }
    }

    @Override
    public JsonWriter beginArray() throws IOException {
        JsonArray jsonArray = new JsonArray();
        this.put(jsonArray);
        this.stack.add(jsonArray);
        return this;
    }

    @Override
    public JsonWriter endArray() throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        } else if (this.peek() instanceof JsonArray) {
            this.stack.remove(this.stack.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public JsonWriter beginObject() throws IOException {
        JsonObject jsonObject = new JsonObject();
        this.put(jsonObject);
        this.stack.add(jsonObject);
        return this;
    }

    @Override
    public JsonWriter endObject() throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        } else if (this.peek() instanceof JsonObject) {
            this.stack.remove(this.stack.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public JsonWriter name(String pendingName) throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        } else if (this.peek() instanceof JsonObject) {
            this.pendingName = pendingName;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public JsonWriter value(String text) throws IOException {
        if (text == null) {
            return this.nullValue();
        }
        this.put(new JsonPrimitive(text));
        return this;
    }

    @Override
    public JsonWriter nullValue() throws IOException {
        this.put(JsonNull.INSTANCE);
        return this;
    }

    @Override
    public JsonWriter value(boolean matches) throws IOException {
        this.put(new JsonPrimitive(matches));
        return this;
    }

    @Override
    public JsonWriter value(Boolean matches) throws IOException {
        if (matches == null) {
            return this.nullValue();
        }
        this.put(new JsonPrimitive(matches));
        return this;
    }

    @Override
    public JsonWriter value(double value2) throws IOException {
        if (this.isLenient() || !Double.isNaN(value2) && !Double.isInfinite(value2)) {
            this.put(new JsonPrimitive(value2));
            return this;
        } else {
            throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value2);
        }
    }

    @Override
    public JsonWriter value(long value2) throws IOException {
        this.put(new JsonPrimitive(value2));
        return this;
    }

    @Override
    public JsonWriter value(Number number) throws IOException {
        if (number == null) {
            return this.nullValue();
        } else {
            double value2;
            if (this.isLenient()
                    || !Double.isNaN(value2 = number.doubleValue()) && !Double.isInfinite(value2)) {
                this.put(new JsonPrimitive(number));
                return this;
            } else {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
    }

    @Override
    public void flush() throws IOException {}

    @Override
    public void close() throws IOException {
        if (!this.stack.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.stack.add(SENTINEL_CLOSED);
    }
}
