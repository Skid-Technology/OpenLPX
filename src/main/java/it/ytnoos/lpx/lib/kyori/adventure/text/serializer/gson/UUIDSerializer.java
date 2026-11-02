package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.kyori.option.OptionState;

import java.io.IOException;
import java.util.UUID;

final class UUIDSerializer extends TypeAdapter<UUID> {

    private final boolean emitIntArray;

    static TypeAdapter<UUID> uuidSerializer(OptionState optionState) {
        return new UUIDSerializer(
                        optionState.value(JSONOptions.EMIT_HOVER_SHOW_ENTITY_ID_AS_INT_ARRAY))
                .nullSafe();
    }

    private UUIDSerializer(boolean emitIntArray) {
        this.emitIntArray = emitIntArray;
    }

    public void write(JsonWriter jsonWriter, UUID uUID) throws IOException {
        if (this.emitIntArray) {
            int value2 = (int) (uUID.getMostSignificantBits() >> 32);
            int value3 = (int) (uUID.getMostSignificantBits() & 4294967295L);
            int value4 = (int) (uUID.getLeastSignificantBits() >> 32);
            int value5 = (int) (uUID.getLeastSignificantBits() & 4294967295L);
            jsonWriter
                    .beginArray()
                    .value(value2)
                    .value(value3)
                    .value(value4)
                    .value(value5)
                    .endArray();
        } else {
            jsonWriter.value(uUID.toString());
        }
    }

    public UUID read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            jsonReader.beginArray();
            int value = jsonReader.nextInt();
            int value2 = jsonReader.nextInt();
            int value3 = jsonReader.nextInt();
            int value4 = jsonReader.nextInt();
            jsonReader.endArray();
            return new UUID(
                    (long) value << 32 | value2 & 4294967295L,
                    (long) value3 << 32 | value4 & 4294967295L);
        } else {
            return UUID.fromString(jsonReader.nextString());
        }
    }
}
