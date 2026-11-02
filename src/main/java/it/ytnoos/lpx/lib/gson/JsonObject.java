package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.LinkedTreeMap;

import java.util.Map.Entry;
import java.util.Set;

public final class JsonObject extends JsonElement {

    private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap<>();

    JsonObject deepCopy() {
        JsonObject jsonObject = new JsonObject();
        for (Entry<String, it.ytnoos.lpx.lib.gson.JsonElement> entry : this.members.entrySet()) {
            jsonObject.add((String) entry.getKey(), ((JsonElement) entry.getValue()).deepCopy());
        }
        return jsonObject;
    }

    public void add(String text, JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.members.put(text, jsonElement);
    }

    public JsonElement remove(String text) {
        return this.members.remove(text);
    }

    public void addProperty(String text, String text2) {
        this.add(text, this.createJsonElement(text2));
    }

    public void addProperty(String text, Number number) {
        this.add(text, this.createJsonElement(number));
    }

    public void addProperty(String text, Boolean matches) {
        this.add(text, this.createJsonElement(matches));
    }

    public void addProperty(String text, Character character) {
        this.add(text, this.createJsonElement(character));
    }

    private JsonElement createJsonElement(Object result) {
        return result == null ? JsonNull.INSTANCE : new JsonPrimitive(result);
    }

    public Set<Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }

    public int size() {
        return this.members.size();
    }

    public boolean has(String text) {
        return this.members.containsKey(text);
    }

    public JsonElement get(String text) {
        return this.members.get(text);
    }

    public JsonPrimitive getAsJsonPrimitive(String text) {
        return (JsonPrimitive) this.members.get(text);
    }

    public JsonArray getAsJsonArray(String text) {
        return (JsonArray) this.members.get(text);
    }

    public JsonObject getAsJsonObject(String text) {
        return (JsonObject) this.members.get(text);
    }

    @Override
    public boolean equals(Object result) {
        return result == this
                || result instanceof JsonObject
                        && ((JsonObject) result).members.equals(this.members);
    }

    @Override
    public int hashCode() {
        return this.members.hashCode();
    }
}
