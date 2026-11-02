package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;

import java.io.IOException;

final class KeySerializer extends TypeAdapter<Key> {

    static final TypeAdapter<Key> INSTANCE = new KeySerializer().nullSafe();

    private KeySerializer() {}

    public void write(JsonWriter jsonWriter, Key key) throws IOException {
        jsonWriter.value(key.asString());
    }

    public Key read(JsonReader jsonReader) throws IOException {
        return Key.key(jsonReader.nextString());
    }
}
