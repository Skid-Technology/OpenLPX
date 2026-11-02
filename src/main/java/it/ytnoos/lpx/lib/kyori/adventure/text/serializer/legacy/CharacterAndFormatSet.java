package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.legacy;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CharacterAndFormatSet {

    static final CharacterAndFormatSet DEFAULT = of(CharacterAndFormat.defaults());

    final List<TextFormat> formats;

    final List<TextColor> colors;

    final String characters;

    static CharacterAndFormatSet of(List<CharacterAndFormat> values) {
        int size2 = values.size();
        ArrayList<TextColor> values2 = new ArrayList<>();
        ArrayList<TextFormat> values3 = new ArrayList<>(size2);
        StringBuilder stringBuilder = new StringBuilder(size2);
        for (int index = 0; index < size2; index++) {
            CharacterAndFormat characterAndFormat;
            char value = (characterAndFormat = (CharacterAndFormat) values.get(index)).character();
            TextFormat textFormat;
            boolean matches = (textFormat = characterAndFormat.format()) instanceof TextColor;
            stringBuilder.append(value);
            values3.add(textFormat);
            if (matches) {
                values2.add((TextColor) textFormat);
            }
            if (characterAndFormat.caseInsensitive()) {
                boolean matches2 = false;
                if (Character.isUpperCase(value)) {
                    stringBuilder.append(Character.toLowerCase(value));
                    matches2 = true;
                } else if (Character.isLowerCase(value)) {
                    stringBuilder.append(Character.toUpperCase(value));
                    matches2 = true;
                }
                if (matches2) {
                    values3.add(textFormat);
                    if (matches) {
                        values2.add((TextColor) textFormat);
                    }
                }
            }
        }
        if (values3.size() != stringBuilder.length()) {
            throw new IllegalStateException("formats length differs from characters length");
        } else {
            return new CharacterAndFormatSet(
                    Collections.unmodifiableList(values3),
                    Collections.unmodifiableList(values2),
                    stringBuilder.toString());
        }
    }

    CharacterAndFormatSet(List<TextFormat> formats, List<TextColor> colors, String characters) {
        this.formats = formats;
        this.colors = colors;
        this.characters = characters;
    }
}
