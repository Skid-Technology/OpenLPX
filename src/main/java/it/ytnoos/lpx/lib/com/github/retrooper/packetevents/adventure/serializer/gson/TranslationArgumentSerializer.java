package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;

import java.io.IOException;

final class TranslationArgumentSerializer extends TypeAdapter<TranslationArgument> {

    private final Gson gson;

    static TypeAdapter<TranslationArgument> create(Gson gson) {
        return new TranslationArgumentSerializer(gson).nullSafe();
    }

    private TranslationArgumentSerializer(Gson gson2) {
        this.gson = gson2;
    }

    public void write(JsonWriter jsonWriter, TranslationArgument translationArgument)
            throws IOException {
        Object result;
        if ((result = translationArgument.value()) instanceof Boolean) {
            jsonWriter.value((Boolean) result);
        } else if (result instanceof Number) {
            jsonWriter.value((Number) result);
        } else {
            if (!(result instanceof Component)) {
                throw new IllegalStateException(
                        "Unable to serialize translatable argument of type "
                                + result.getClass()
                                + ": "
                                + result);
            }
            this.gson.toJson(result, SerializerFactory.COMPONENT_TYPE, jsonWriter);
        }
    }

    public TranslationArgument read(JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case BOOLEAN:
                return TranslationArgument.bool(jsonReader.nextBoolean());
            case NUMBER:
                return TranslationArgument.numeric(this.gson.fromJson(jsonReader, Number.class));
            default:
                return TranslationArgument.component(
                        this.gson.fromJson(jsonReader, SerializerFactory.COMPONENT_TYPE));
        }
    }
}
