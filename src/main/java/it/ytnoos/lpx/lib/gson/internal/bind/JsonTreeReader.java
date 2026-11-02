package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.JsonArray;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.gson.JsonPrimitive;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;

public final class JsonTreeReader extends JsonReader {

    private static final Reader UNREADABLE_READER =
            new Reader() {

                @Override
                public int read(char[] values, int value, int value2) throws IOException {
                    throw new AssertionError();
                }

                @Override
                public void close() throws IOException {
                    throw new AssertionError();
                }
            };

    private static final Object SENTINEL_CLOSED = new Object();

    private Object[] stack = new Object[32];

    private int stackSize = 0;

    private String[] pathNames = new String[32];

    private int[] pathIndices = new int[32];

    public JsonTreeReader(JsonElement jsonElement) {
        super(UNREADABLE_READER);
        this.push(jsonElement);
    }

    @Override
    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
        JsonArray jsonArray = (JsonArray) this.peekStack();
        this.push(jsonArray.iterator());
        this.pathIndices[this.stackSize - 1] = 0;
    }

    @Override
    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
        this.popStack();
        this.popStack();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
    }

    @Override
    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
        JsonObject jsonObject = (JsonObject) this.peekStack();
        this.push(jsonObject.entrySet().iterator());
    }

    @Override
    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
        this.popStack();
        this.popStack();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
    }

    @Override
    public boolean hasNext() throws IOException {
        JsonToken jsonToken;
        return (jsonToken = this.peek()) != JsonToken.END_OBJECT
                && jsonToken != JsonToken.END_ARRAY;
    }

    @Override
    public JsonToken peek() throws IOException {
        if (this.stackSize == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object result;
        if ((result = this.peekStack()) instanceof Iterator) {
            boolean matches = this.stack[this.stackSize - 2] instanceof JsonObject;
            Iterator iterator;
            if ((iterator = (Iterator) result).hasNext()) {
                if (matches) {
                    return JsonToken.NAME;
                }
                this.push(iterator.next());
                return this.peek();
            } else {
                return matches ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            }
        } else {
            if (result instanceof JsonObject) {
                return JsonToken.BEGIN_OBJECT;
            }
            if (result instanceof JsonArray) {
                return JsonToken.BEGIN_ARRAY;
            }
            if (result instanceof JsonPrimitive) {
                JsonPrimitive jsonPrimitive;
                if ((jsonPrimitive = (JsonPrimitive) result).isString()) {
                    return JsonToken.STRING;
                } else if (jsonPrimitive.isBoolean()) {
                    return JsonToken.BOOLEAN;
                } else if (jsonPrimitive.isNumber()) {
                    return JsonToken.NUMBER;
                } else {
                    throw new AssertionError();
                }
            } else if (result instanceof JsonNull) {
                return JsonToken.NULL;
            } else if (result == SENTINEL_CLOSED) {
                throw new IllegalStateException("JsonReader is closed");
            } else {
                throw new AssertionError();
            }
        }
    }

    private Object peekStack() {
        return this.stack[this.stackSize - 1];
    }

    private Object popStack() {
        Object result = this.stack[--this.stackSize];
        this.stack[this.stackSize] = null;
        return result;
    }

    private void expect(JsonToken jsonToken) throws IOException {
        if (this.peek() != jsonToken) {
            throw new IllegalStateException(
                    "Expected " + jsonToken + " but was " + this.peek() + this.locationString());
        }
    }

    @Override
    public String nextName() throws IOException {
        this.expect(JsonToken.NAME);
        Entry entry;
        String text = (String) (entry = (Entry) ((Iterator) this.peekStack()).next()).getKey();
        this.pathNames[this.stackSize - 1] = text;
        this.push(entry.getValue());
        return text;
    }

    @Override
    public String nextString() throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = this.peek()) != JsonToken.STRING && jsonToken != JsonToken.NUMBER) {
            throw new IllegalStateException(
                    "Expected "
                            + JsonToken.STRING
                            + " but was "
                            + jsonToken
                            + this.locationString());
        }
        String asString = ((JsonPrimitive) this.popStack()).getAsString();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
        return asString;
    }

    @Override
    public boolean nextBoolean() throws IOException {
        this.expect(JsonToken.BOOLEAN);
        boolean asBoolean = ((JsonPrimitive) this.popStack()).getAsBoolean();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
        return asBoolean;
    }

    @Override
    public void nextNull() throws IOException {
        this.expect(JsonToken.NULL);
        this.popStack();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
    }

    @Override
    public double nextDouble() throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = this.peek()) != JsonToken.NUMBER && jsonToken != JsonToken.STRING) {
            throw new IllegalStateException(
                    "Expected "
                            + JsonToken.NUMBER
                            + " but was "
                            + jsonToken
                            + this.locationString());
        }
        double asDouble = ((JsonPrimitive) this.peekStack()).getAsDouble();
        if (this.isLenient() || !Double.isNaN(asDouble) && !Double.isInfinite(asDouble)) {
            this.popStack();
            if (this.stackSize > 0) {
                this.pathIndices[this.stackSize - 1]++;
            }
            return asDouble;
        } else {
            throw new NumberFormatException("JSON forbids NaN and infinities: " + asDouble);
        }
    }

    @Override
    public long nextLong() throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = this.peek()) != JsonToken.NUMBER && jsonToken != JsonToken.STRING) {
            throw new IllegalStateException(
                    "Expected "
                            + JsonToken.NUMBER
                            + " but was "
                            + jsonToken
                            + this.locationString());
        }
        long asLong = ((JsonPrimitive) this.peekStack()).getAsLong();
        this.popStack();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
        return asLong;
    }

    @Override
    public int nextInt() throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = this.peek()) != JsonToken.NUMBER && jsonToken != JsonToken.STRING) {
            throw new IllegalStateException(
                    "Expected "
                            + JsonToken.NUMBER
                            + " but was "
                            + jsonToken
                            + this.locationString());
        }
        int asInt = ((JsonPrimitive) this.peekStack()).getAsInt();
        this.popStack();
        if (this.stackSize > 0) {
            this.pathIndices[this.stackSize - 1]++;
        }
        return asInt;
    }

    @Override
    public void close() throws IOException {
        this.stack = new Object[] {SENTINEL_CLOSED};
        this.stackSize = 1;
    }

    @Override
    public void skipValue() throws IOException {
        if (this.peek() == JsonToken.NAME) {
            this.nextName();
            this.pathNames[this.stackSize - 2] = "null";
        } else {
            this.popStack();
            this.pathNames[this.stackSize - 1] = "null";
        }
        this.pathIndices[this.stackSize - 1]++;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void promoteNameToValue() throws IOException {
        this.expect(JsonToken.NAME);
        Entry entry = (Entry) ((Iterator) this.peekStack()).next();
        this.push(entry.getValue());
        this.push(new JsonPrimitive((String) entry.getKey()));
    }

    private void push(Object result) {
        if (this.stackSize == this.stack.length) {
            Object[] results = new Object[this.stackSize * 2];
            int[] values = new int[this.stackSize * 2];
            String[] texts = new String[this.stackSize * 2];
            System.arraycopy(this.stack, 0, results, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, values, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, texts, 0, this.stackSize);
            this.stack = results;
            this.pathIndices = values;
            this.pathNames = texts;
        }
        this.stack[this.stackSize++] = result;
    }

    @Override
    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder().append('$');
        for (int index = 0; index < this.stackSize; index++) {
            if (this.stack[index] instanceof JsonArray) {
                if (this.stack[++index] instanceof Iterator) {
                    stringBuilder.append('[').append(this.pathIndices[index]).append(']');
                }
            } else if (this.stack[index] instanceof JsonObject) {
                if (this.stack[++index] instanceof Iterator) {
                    stringBuilder.append('.');
                    if (this.pathNames[index] != null) {
                        stringBuilder.append(this.pathNames[index]);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private String locationString() {
        return " at path " + this.getPath();
    }
}
