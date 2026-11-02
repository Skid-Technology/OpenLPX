package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;

public interface Translator {

    @Nullable
    static Locale parseLocale(@NotNull String text) {
        String[] texts;
        int value;
        if ((value = (texts = text.split("_", 3)).length) == 1) {
            return new Locale(text);
        } else if (value == 2) {
            return new Locale(texts[0], texts[1]);
        } else {
            return value == 3 ? new Locale(texts[0], texts[1], texts[2]) : null;
        }
    }

    @NotNull
    Key name();

    @NotNull
    default TriState hasAnyTranslations() {
        return TriState.NOT_SET;
    }

    default boolean canTranslate(@NotNull String text, @NotNull Locale locale) {
        return this.translate(
                                Component.translatable(Objects.requireNonNull(text, "key")),
                                Objects.requireNonNull(locale, "locale"))
                        != null
                ? true
                : this.translate(text, locale) != null;
    }

    @Nullable
    MessageFormat translate(@NotNull String text, @NotNull Locale locale);

    @Nullable
    default Component translate(
            @NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
        return null;
    }
}
