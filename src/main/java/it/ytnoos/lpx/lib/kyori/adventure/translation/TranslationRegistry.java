package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

@Deprecated
public interface TranslationRegistry
        extends Translator, TranslationStore.StringBased<MessageFormat> {

    @Deprecated Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");

    @Deprecated
    @NotNull
    static TranslationRegistry create(Key key) {
        return new MessageFormatTranslationStore(Objects.requireNonNull(key, "name"));
    }

    @Deprecated
    @Override
    boolean contains(@NotNull String text);

    @Deprecated
    @Nullable
    @Override
    MessageFormat translate(@NotNull String text, @NotNull Locale locale);

    @Deprecated
    @Override
    void defaultLocale(@NotNull Locale locale);

    @Deprecated
    void register(
            @NotNull String text, @NotNull Locale locale, @NotNull MessageFormat messageFormat);

    @Deprecated
    @Override
    default void registerAll(@NotNull Locale locale, @NotNull Map<String, MessageFormat> entries) {
        Set<String> values = entries.keySet();
        this.registerAll(locale, values, entries::get);
    }

    @Deprecated
    @Override
    default void registerAll(@NotNull Locale locale, @NotNull Path path, boolean matches) {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            try {
                this.registerAll(locale, new PropertyResourceBundle(bufferedReader), matches);
            } catch (Throwable throwable) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException oException) {
        }
    }

    @Deprecated
    @Override
    default void registerAll(
            @NotNull Locale locale, @NotNull ResourceBundle resourceBundle, boolean matches) {
        Set<String> values = resourceBundle.keySet();
        this.registerAll(
                locale,
                values,
                result -> {
                    String string = resourceBundle.getString(result);
                    return new MessageFormat(
                            matches
                                    ? SINGLE_QUOTE_PATTERN.matcher(string).replaceAll("''")
                                    : string,
                            locale);
                });
    }

    @Deprecated
    @Override
    default void registerAll(
            @NotNull Locale locale,
            @NotNull Set<String> values,
            Function<String, MessageFormat> function) {
        IllegalArgumentException illegalArgumentException2 = null;
        int value = 0;
        for (String text : values) {
            try {
                this.register(text, locale, (MessageFormat) function.apply(text));
            } catch (IllegalArgumentException illegalArgumentException) {
                if (illegalArgumentException2 == null) {
                    illegalArgumentException2 = illegalArgumentException;
                }
                value++;
            }
        }
        if (illegalArgumentException2 != null) {
            if (value == 1) {
                throw illegalArgumentException2;
            }
            if (value > 1) {
                throw new IllegalArgumentException(
                        String.format("Invalid key (and %d more)", value - 1),
                        illegalArgumentException2);
            }
        }
    }

    @Deprecated
    @Override
    void unregister(@NotNull String text);
}
