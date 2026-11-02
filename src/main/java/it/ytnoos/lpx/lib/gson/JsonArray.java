package it.ytnoos.lpx.lib.gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {

    private final List<JsonElement> elements;

    public JsonArray() {
        this.elements = new ArrayList<>();
    }

    public JsonArray(int capacity) {
        this.elements = new ArrayList<>(capacity);
    }

    JsonArray deepCopy() {
        JsonArray jsonArray = new JsonArray();
        for (JsonElement jsonElement : this.elements) {
            jsonArray.add(jsonElement.deepCopy());
        }
        return jsonArray;
    }

    public void add(Boolean matches) {
        this.elements.add(matches == null ? JsonNull.INSTANCE : new JsonPrimitive(matches));
    }

    public void add(Character character) {
        this.elements.add(character == null ? JsonNull.INSTANCE : new JsonPrimitive(character));
    }

    public void add(Number number) {
        this.elements.add(number == null ? JsonNull.INSTANCE : new JsonPrimitive(number));
    }

    public void add(String text) {
        this.elements.add(text == null ? JsonNull.INSTANCE : new JsonPrimitive(text));
    }

    public void add(JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.elements.add(jsonElement);
    }

    public void addAll(JsonArray jsonArray) {
        this.elements.addAll(jsonArray.elements);
    }

    public JsonElement set(int value, JsonElement jsonElement) {
        return this.elements.set(value, jsonElement);
    }

    public boolean remove(JsonElement jsonElement) {
        return this.elements.remove(jsonElement);
    }

    public JsonElement remove(int value) {
        return this.elements.remove(value);
    }

    public boolean contains(JsonElement jsonElement) {
        return this.elements.contains(jsonElement);
    }

    public int size() {
        return this.elements.size();
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return this.elements.iterator();
    }

    public JsonElement get(int value) {
        return this.elements.get(value);
    }

    @Override
    public Number getAsNumber() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsNumber();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public String getAsString() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsString();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public double getAsDouble() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsDouble();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBigDecimal();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public BigInteger getAsBigInteger() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBigInteger();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public float getAsFloat() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsFloat();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public long getAsLong() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsLong();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public int getAsInt() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsInt();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public byte getAsByte() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsByte();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public char getAsCharacter() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsCharacter();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public short getAsShort() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsShort();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean getAsBoolean() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBoolean();
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object result) {
        return result == this
                || result instanceof JsonArray
                        && ((JsonArray) result).elements.equals(this.elements);
    }

    @Override
    public int hashCode() {
        return this.elements.hashCode();
    }
}
