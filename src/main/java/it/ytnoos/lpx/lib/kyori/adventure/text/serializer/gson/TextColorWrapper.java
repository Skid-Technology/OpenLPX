package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

final class TextColorWrapper {

    @Nullable final TextColor color;

    @Nullable final TextDecoration decoration;

    final boolean reset;

    TextColorWrapper(
            @Nullable TextColor textColor, @Nullable TextDecoration textDecoration, boolean reset) {
        this.color = textColor;
        this.decoration = textDecoration;
        this.reset = reset;
    }

    static final class Serializer extends TypeAdapter<TextColorWrapper> {

        static final TextColorWrapper.Serializer INSTANCE = new TextColorWrapper.Serializer();

        private Serializer() {}

        public void write(JsonWriter jsonWriter, TextColorWrapper textColorWrapper) {
            throw new JsonSyntaxException("Cannot write TextColorWrapper instances");
        }

        public TextColorWrapper read(JsonReader jsonReader) throws IOException {
            String text;
            TextColor textColor = TextColorSerializer.fromString(text = jsonReader.nextString());
            TextDecoration textDecoration;
            boolean matches =
                    (textDecoration = TextDecoration.NAMES.value(text)) == null
                            && text.equals("reset");
            if (textColor == null && textDecoration == null && !matches) {
                throw new JsonParseException(
                        "Don't know how to parse " + text + " at " + jsonReader.getPath());
            } else {
                return new TextColorWrapper(textColor, textDecoration, matches);
            }
        }
    }
}
