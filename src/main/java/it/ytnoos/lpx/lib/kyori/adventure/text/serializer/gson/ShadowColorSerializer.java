package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.ShadowColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.kyori.option.OptionState;

import java.io.IOException;

final class ShadowColorSerializer extends TypeAdapter<ShadowColor> {

    private final boolean emitArray;

    static TypeAdapter<ShadowColor> create(OptionState optionState) {
        return new ShadowColorSerializer(
                        optionState.value(JSONOptions.SHADOW_COLOR_MODE)
                                == JSONOptions.ShadowColorEmitMode.EMIT_ARRAY)
                .nullSafe();
    }

    private ShadowColorSerializer(boolean emitArray) {
        this.emitArray = emitArray;
    }

    public void write(JsonWriter jsonWriter, ShadowColor shadowColor) throws IOException {
        if (this.emitArray) {
            jsonWriter
                    .beginArray()
                    .value(componentAsFloat(shadowColor.red()))
                    .value(componentAsFloat(shadowColor.green()))
                    .value(componentAsFloat(shadowColor.blue()))
                    .value(componentAsFloat(shadowColor.alpha()))
                    .endArray();
        } else {
            jsonWriter.value(shadowColor.value());
        }
    }

    public ShadowColor read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            jsonReader.beginArray();
            double value = jsonReader.nextDouble();
            double value2 = jsonReader.nextDouble();
            double value3 = jsonReader.nextDouble();
            double value4 = jsonReader.nextDouble();
            if (jsonReader.peek() != JsonToken.END_ARRAY) {
                throw new JsonParseException(
                        "Failed to parse shadow colour at "
                                + jsonReader.getPath()
                                + ": expected end of 4-element array but got "
                                + jsonReader.peek()
                                + " instead.");
            }
            jsonReader.endArray();
            return ShadowColor.shadowColor(
                    componentFromFloat(value),
                    componentFromFloat(value2),
                    componentFromFloat(value3),
                    componentFromFloat(value4));
        } else {
            return ShadowColor.shadowColor(jsonReader.nextInt());
        }
    }

    static float componentAsFloat(int value) {
        return value / 255.0F;
    }

    static int componentFromFloat(double value) {
        return (int) ((float) value * 255.0F);
    }
}
