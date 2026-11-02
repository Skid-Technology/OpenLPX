package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.legacy;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextFormat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class CharacterAndFormatImpl implements CharacterAndFormat {

    private final char character;

    @Nullable private final TextFormat format;

    private final boolean caseInsensitive;

    CharacterAndFormatImpl(
            char character, @Nullable TextFormat textFormat, boolean caseInsensitive) {
        this.character = character;
        this.format = textFormat;
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public char character() {
        return this.character;
    }

    @Nullable
    @Override
    public TextFormat format() {
        return this.format;
    }

    @Override
    public boolean caseInsensitive() {
        return this.caseInsensitive;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof CharacterAndFormatImpl)) {
            return false;
        }
        CharacterAndFormatImpl characterAndFormatImpl = (CharacterAndFormatImpl) result;
        return this.character == characterAndFormatImpl.character
                && this.format.equals(characterAndFormatImpl.format)
                && this.caseInsensitive == characterAndFormatImpl.caseInsensitive;
    }

    @Override
    public int hashCode() {
        int value = this.character;
        value = 31 * value + this.format.hashCode();
        return 31 * value + Boolean.hashCode(this.caseInsensitive);
    }

    @NotNull
    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class Defaults {

        static final List<CharacterAndFormat> DEFAULTS = createDefaults();

        private Defaults() {}

        static List<CharacterAndFormat> createDefaults() {
            ArrayList<
                            it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer
                                    .legacy.CharacterAndFormat>
                    values;
            (values = new ArrayList(22)).add(CharacterAndFormat.BLACK);
            values.add(CharacterAndFormat.DARK_BLUE);
            values.add(CharacterAndFormat.DARK_GREEN);
            values.add(CharacterAndFormat.DARK_AQUA);
            values.add(CharacterAndFormat.DARK_RED);
            values.add(CharacterAndFormat.DARK_PURPLE);
            values.add(CharacterAndFormat.GOLD);
            values.add(CharacterAndFormat.GRAY);
            values.add(CharacterAndFormat.DARK_GRAY);
            values.add(CharacterAndFormat.BLUE);
            values.add(CharacterAndFormat.GREEN);
            values.add(CharacterAndFormat.AQUA);
            values.add(CharacterAndFormat.RED);
            values.add(CharacterAndFormat.LIGHT_PURPLE);
            values.add(CharacterAndFormat.YELLOW);
            values.add(CharacterAndFormat.WHITE);
            values.add(CharacterAndFormat.OBFUSCATED);
            values.add(CharacterAndFormat.BOLD);
            values.add(CharacterAndFormat.STRIKETHROUGH);
            values.add(CharacterAndFormat.UNDERLINED);
            values.add(CharacterAndFormat.ITALIC);
            values.add(CharacterAndFormat.RESET);
            return Collections.unmodifiableList(values);
        }
    }
}
