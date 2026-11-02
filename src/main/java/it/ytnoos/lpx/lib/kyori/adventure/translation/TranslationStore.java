package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

public interface TranslationStore<T> extends Translator {

    @NotNull
    static TranslationStore<Component> component(@NotNull Key key) {
        return new ComponentTranslationStore(Objects.requireNonNull(key, "name"));
    }

    static TranslationStore.@NotNull StringBased<MessageFormat> messageFormat(@NotNull Key key) {
        return new MessageFormatTranslationStore(Objects.requireNonNull(key, "name"));
    }

    boolean contains(@NotNull String text);

    boolean contains(@NotNull String text, @NotNull Locale locale);

    @Override
    default boolean canTranslate(@NotNull String text, @NotNull Locale locale) {
        return Translator.super.canTranslate(text, locale);
    }

    void defaultLocale(@NotNull Locale locale);

    void register(@NotNull String text, @NotNull Locale locale, T t);

    void registerAll(@NotNull Locale locale, @NotNull Map<String, T> entries);

    void registerAll(
            @NotNull Locale locale, @NotNull Set<String> values, Function<String, T> function);

    void unregister(@NotNull String text);

    interface StringBased<T> extends TranslationStore<T> {

        void registerAll(@NotNull Locale locale, @NotNull Path path, boolean matches);

        void registerAll(
                @NotNull Locale locale, @NotNull ResourceBundle resourceBundle, boolean matches);
    }
}
