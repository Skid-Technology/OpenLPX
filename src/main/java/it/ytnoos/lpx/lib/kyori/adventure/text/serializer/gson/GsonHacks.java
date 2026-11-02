package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

final class GsonHacks {

    private GsonHacks() {}

    static boolean isNullOrEmpty(@Nullable JsonElement jsonElement) {
        return jsonElement == null
                || jsonElement.isJsonNull()
                || jsonElement.isJsonArray() && jsonElement.getAsJsonArray().size() == 0
                || jsonElement.isJsonObject() && jsonElement.getAsJsonObject().entrySet().isEmpty();
    }

    static boolean readBoolean(JsonReader jsonReader) throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = jsonReader.peek()) == JsonToken.BOOLEAN) {
            return jsonReader.nextBoolean();
        } else if (jsonToken == JsonToken.STRING) {
            return Boolean.parseBoolean(jsonReader.nextString());
        } else if (jsonToken == JsonToken.NUMBER) {
            return jsonReader.nextString().equals("1");
        } else {
            throw new JsonParseException(
                    "Token of type " + jsonToken + " cannot be interpreted as a boolean");
        }
    }

    static String readString(JsonReader jsonReader) throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = jsonReader.peek()) == JsonToken.STRING || jsonToken == JsonToken.NUMBER) {
            return jsonReader.nextString();
        } else if (jsonToken == JsonToken.BOOLEAN) {
            return String.valueOf(jsonReader.nextBoolean());
        } else {
            throw new JsonParseException(
                    "Token of type " + jsonToken + " cannot be interpreted as a string");
        }
    }
}
