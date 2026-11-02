package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.legacy;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextFormat;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.stream.Stream;

@NonExtendable
public interface CharacterAndFormat extends Examinable {

    CharacterAndFormat BLACK = characterAndFormat('0', NamedTextColor.BLACK, true);

    CharacterAndFormat DARK_BLUE = characterAndFormat('1', NamedTextColor.DARK_BLUE, true);

    CharacterAndFormat DARK_GREEN = characterAndFormat('2', NamedTextColor.DARK_GREEN, true);

    CharacterAndFormat DARK_AQUA = characterAndFormat('3', NamedTextColor.DARK_AQUA, true);

    CharacterAndFormat DARK_RED = characterAndFormat('4', NamedTextColor.DARK_RED, true);

    CharacterAndFormat DARK_PURPLE = characterAndFormat('5', NamedTextColor.DARK_PURPLE, true);

    CharacterAndFormat GOLD = characterAndFormat('6', NamedTextColor.GOLD, true);

    CharacterAndFormat GRAY = characterAndFormat('7', NamedTextColor.GRAY, true);

    CharacterAndFormat DARK_GRAY = characterAndFormat('8', NamedTextColor.DARK_GRAY, true);

    CharacterAndFormat BLUE = characterAndFormat('9', NamedTextColor.BLUE, true);

    CharacterAndFormat GREEN = characterAndFormat('a', NamedTextColor.GREEN, true);

    CharacterAndFormat AQUA = characterAndFormat('b', NamedTextColor.AQUA, true);

    CharacterAndFormat RED = characterAndFormat('c', NamedTextColor.RED, true);

    CharacterAndFormat LIGHT_PURPLE = characterAndFormat('d', NamedTextColor.LIGHT_PURPLE, true);

    CharacterAndFormat YELLOW = characterAndFormat('e', NamedTextColor.YELLOW, true);

    CharacterAndFormat WHITE = characterAndFormat('f', NamedTextColor.WHITE, true);

    CharacterAndFormat OBFUSCATED = characterAndFormat('k', TextDecoration.OBFUSCATED, true);

    CharacterAndFormat BOLD = characterAndFormat('l', TextDecoration.BOLD, true);

    CharacterAndFormat STRIKETHROUGH = characterAndFormat('m', TextDecoration.STRIKETHROUGH, true);

    CharacterAndFormat UNDERLINED = characterAndFormat('n', TextDecoration.UNDERLINED, true);

    CharacterAndFormat ITALIC = characterAndFormat('o', TextDecoration.ITALIC, true);

    CharacterAndFormat RESET = characterAndFormat('r', Reset.INSTANCE, true);

    @NotNull
    static CharacterAndFormat characterAndFormat(char value, @NotNull TextFormat textFormat) {
        return characterAndFormat(value, textFormat, false);
    }

    @NotNull
    static CharacterAndFormat characterAndFormat(
            char value, @NotNull TextFormat textFormat, boolean matches) {
        return new CharacterAndFormatImpl(value, textFormat, matches);
    }

    @NotNull
    @Unmodifiable
    static List<CharacterAndFormat> defaults() {
        return CharacterAndFormatImpl.Defaults.DEFAULTS;
    }

    char character();

    @NotNull
    TextFormat format();

    boolean caseInsensitive();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("character", this.character()),
                ExaminableProperty.of("format", this.format()),
                ExaminableProperty.of("caseInsensitive", this.caseInsensitive()));
    }
}
