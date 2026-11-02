package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Locale;

final class TextColorSerializer extends TypeAdapter<TextColor> {

    static final TypeAdapter<TextColor> INSTANCE = new TextColorSerializer(false).nullSafe();

    static final TypeAdapter<TextColor> DOWNSAMPLE_COLOR = new TextColorSerializer(true).nullSafe();

    private final boolean downsampleColor;

    private TextColorSerializer(boolean downsampleColor) {
        this.downsampleColor = downsampleColor;
    }

    public void write(JsonWriter jsonWriter, TextColor textColor) throws IOException {
        if (textColor instanceof NamedTextColor) {
            jsonWriter.value(NamedTextColor.NAMES.key((NamedTextColor) textColor));
        } else if (this.downsampleColor) {
            jsonWriter.value(NamedTextColor.NAMES.key(NamedTextColor.nearestTo(textColor)));
        } else {
            jsonWriter.value(asUpperCaseHexString(textColor));
        }
    }

    private static String asUpperCaseHexString(TextColor textColor) {
        return String.format(
                Locale.ROOT, "%c%06X", Character.valueOf((char) (35)), textColor.value());
    }

    @Nullable
    public TextColor read(JsonReader jsonReader) throws IOException {
        TextColor textColor;
        if ((textColor = fromString(jsonReader.nextString())) == null) {
            return null;
        } else {
            return this.downsampleColor ? NamedTextColor.nearestTo(textColor) : textColor;
        }
    }

    @Nullable
    static TextColor fromString(@NotNull String text) {
        return text.startsWith("#")
                ? TextColor.fromHexString(text)
                : NamedTextColor.NAMES.value(text);
    }
}
