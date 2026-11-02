package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JsonObjectBuilder {

    private StringBuilder builder = new StringBuilder();

    private boolean hasAtLeastOneField = false;

    public JsonObjectBuilder() {
        this.builder.append("{");
    }

    public JsonObjectBuilder appendNull(String text) {
        this.appendFieldUnescaped(text, "null");
        return this;
    }

    public JsonObjectBuilder appendField(String text, String text2) {
        if (text2 == null) {
            throw new IllegalArgumentException("JSON value must not be null");
        }
        this.appendFieldUnescaped(text, "\"" + escape(text2) + "\"");
        return this;
    }

    public JsonObjectBuilder appendField(String text, int value) {
        this.appendFieldUnescaped(text, String.valueOf(value));
        return this;
    }

    public JsonObjectBuilder appendField(String text, JsonObjectBuilder.JsonObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object must not be null");
        }
        this.appendFieldUnescaped(text, jsonObject.toString());
        return this;
    }

    public JsonObjectBuilder appendField(String text, String[] texts) {
        if (texts == null) {
            throw new IllegalArgumentException("JSON values must not be null");
        }
        String text2 =
                Arrays.stream(texts)
                        .map(result -> "\"" + escape(result) + "\"")
                        .collect(Collectors.joining(","));
        this.appendFieldUnescaped(text, "[" + text2 + "]");
        return this;
    }

    public JsonObjectBuilder appendField(String text, int[] values) {
        if (values == null) {
            throw new IllegalArgumentException("JSON values must not be null");
        }
        String text2 =
                Arrays.stream(values).mapToObj(String::valueOf).collect(Collectors.joining(","));
        this.appendFieldUnescaped(text, "[" + text2 + "]");
        return this;
    }

    public JsonObjectBuilder appendField(String text, JsonObjectBuilder.JsonObject[] jsonObjects) {
        if (jsonObjects == null) {
            throw new IllegalArgumentException("JSON values must not be null");
        }
        String text2 =
                Arrays.stream(jsonObjects)
                        .map(JsonObjectBuilder.JsonObject::toString)
                        .collect(Collectors.joining(","));
        this.appendFieldUnescaped(text, "[" + text2 + "]");
        return this;
    }

    private void appendFieldUnescaped(String text, String text2) {
        if (this.builder == null) {
            throw new IllegalStateException("JSON has already been built");
        }
        if (text == null) {
            throw new IllegalArgumentException("JSON key must not be null");
        }
        if (this.hasAtLeastOneField) {
            this.builder.append(",");
        }
        this.builder.append("\"").append(escape(text)).append("\":").append(text2);
        this.hasAtLeastOneField = true;
    }

    public JsonObjectBuilder.JsonObject build() {
        if (this.builder == null) {
            throw new IllegalStateException("JSON has already been built");
        }
        JsonObjectBuilder.JsonObject jsonObject =
                new JsonObjectBuilder.JsonObject(this.builder.append("}").toString());
        this.builder = null;
        return jsonObject;
    }

    private static String escape(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < text.length(); index++) {
            char value;
            if ((value = text.charAt(index)) == '"') {
                stringBuilder.append("\\\"");
            } else if (value == '\\') {
                stringBuilder.append("\\\\");
            } else if (value <= 15) {
                stringBuilder.append("\\u000").append(Integer.toHexString(value));
            } else if (value <= 31) {
                stringBuilder.append("\\u00").append(Integer.toHexString(value));
            } else {
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    public static class JsonObject {

        private final String value;

        private JsonObject(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
