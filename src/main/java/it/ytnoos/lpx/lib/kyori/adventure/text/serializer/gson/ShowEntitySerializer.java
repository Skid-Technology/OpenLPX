package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.key.InvalidKeyException;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.kyori.option.OptionState;

import java.io.IOException;
import java.util.UUID;

final class ShowEntitySerializer extends TypeAdapter<HoverEvent.ShowEntity> {

    private final Gson gson;

    private final boolean emitKeyAsTypeAndUuidAsId;

    static TypeAdapter<HoverEvent.ShowEntity> create(Gson gson, OptionState optionState) {
        return new ShowEntitySerializer(
                        gson,
                        optionState.value(
                                JSONOptions.EMIT_HOVER_SHOW_ENTITY_KEY_AS_TYPE_AND_UUID_AS_ID))
                .nullSafe();
    }

    private ShowEntitySerializer(Gson gson2, boolean emitKeyAsTypeAndUuidAsId) {
        this.gson = gson2;
        this.emitKeyAsTypeAndUuidAsId = emitKeyAsTypeAndUuidAsId;
    }

    public HoverEvent.ShowEntity read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Key key2 = null;
        UUID uUID = null;
        Component component = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "id":
                    if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                        uUID = this.gson.fromJson(jsonReader, UUID.class);
                    } else {
                        String text;
                        if ((text = jsonReader.nextString()).contains(":")) {
                            key2 = Key.key(text);
                        }
                        try {
                            uUID = UUID.fromString(text);
                        } catch (IllegalArgumentException illegalArgumentException) {
                            try {
                                key2 = Key.key(text);
                            } catch (InvalidKeyException invalidKeyException) {
                            }
                        }
                    }
                    break;
                case "type":
                    key2 = this.gson.fromJson(jsonReader, Key.class);
                    break;
                case "uuid":
                    uUID = this.gson.fromJson(jsonReader, UUID.class);
                    break;
                case "name":
                    component = this.gson.fromJson(jsonReader, SerializerFactory.COMPONENT_TYPE);
                    break;
                default:
                    jsonReader.skipValue();
            }
        }
        if (key2 != null && uUID != null) {
            jsonReader.endObject();
            return HoverEvent.ShowEntity.showEntity(key2, uUID, component);
        } else {
            throw new JsonParseException(
                    "A show entity hover event needs type and id fields to be deserialized");
        }
    }

    public void write(JsonWriter jsonWriter, HoverEvent.ShowEntity showEntity) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name(this.emitKeyAsTypeAndUuidAsId ? "type" : "id");
        this.gson.toJson(showEntity.type(), SerializerFactory.KEY_TYPE, jsonWriter);
        jsonWriter.name(this.emitKeyAsTypeAndUuidAsId ? "id" : "uuid");
        this.gson.toJson(showEntity.id(), SerializerFactory.UUID_TYPE, jsonWriter);
        Component component;
        if ((component = showEntity.name()) != null) {
            jsonWriter.name("name");
            this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE, jsonWriter);
        }
        jsonWriter.endObject();
    }
}
