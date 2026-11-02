package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class AbstractTranslationStore<T> implements Examinable, TranslationStore<T> {

    @NotNull private final Key name;

    private final Map<String, AbstractTranslationStore<T>.Translation> translations =
            new ConcurrentHashMap<>();

    @NotNull private volatile Locale defaultLocale = Locale.US;

    protected AbstractTranslationStore(@NotNull Key key) {
        this.name = Objects.requireNonNull(key, "name");
    }

    @Nullable
    protected T translationValue(@NotNull String text, @NotNull Locale locale) {
        AbstractTranslationStore.Translation translation;
        return (T)
                ((translation = this.translations.get(Objects.requireNonNull(text, "key"))) == null
                        ? null
                        : translation.translate(Objects.requireNonNull(locale, "locale")));
    }

    @Override
    public final boolean contains(@NotNull String text) {
        return this.translations.containsKey(text);
    }

    @Override
    public final boolean contains(@NotNull String text, @NotNull Locale locale) {
        AbstractTranslationStore.Translation translation;
        return (translation = this.translations.get(Objects.requireNonNull(text, "key"))) == null
                ? false
                : translation.translations.get(Objects.requireNonNull(locale, "locale")) != null;
    }

    @Override
    public final boolean canTranslate(@NotNull String text, @NotNull Locale locale) {
        AbstractTranslationStore.Translation translation;
        return (translation = this.translations.get(Objects.requireNonNull(text, "key"))) == null
                ? false
                : translation.translate(Objects.requireNonNull(locale, "locale")) != null;
    }

    @Override
    public final void defaultLocale(@NotNull Locale locale) {
        this.defaultLocale = Objects.requireNonNull(locale, "locale");
    }

    @Override
    public final void register(@NotNull String text, @NotNull Locale locale, @NotNull T t) {
        this.translations
                .computeIfAbsent(text, result -> new AbstractTranslationStore.Translation(result))
                .register(locale, (T) t);
    }

    @Override
    public final void registerAll(@NotNull Locale locale, @NotNull Map<String, T> entries) {
        Set<String> values = entries.keySet();
        this.registerAll(locale, values, entries::get);
    }

    @Override
    public final void registerAll(
            @NotNull Locale locale, @NotNull Set<String> values, Function<String, T> function) {
        IllegalArgumentException illegalArgumentException2 = null;
        int value = 0;
        for (String text : values) {
            try {
                this.register(text, locale, (T) function.apply(text));
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

    @Override
    public final void unregister(@NotNull String text) {
        this.translations.remove(text);
    }

    @NotNull
    @Override
    public final Key name() {
        return this.name;
    }

    @NotNull
    @Override
    public final TriState hasAnyTranslations() {
        return TriState.byBoolean(!this.translations.isEmpty());
    }

    @NotNull
    @Override
    public final Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("translations", this.translations));
    }

    @Override
    public final boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof AbstractTranslationStore)) {
            return false;
        }
        AbstractTranslationStore abstractTranslationStore = (AbstractTranslationStore) result;
        return this.name.equals(abstractTranslationStore.name);
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @NotNull
    @Override
    public final String toString() {
        return Internals.toString(this);
    }

    public abstract static class StringBased<T> extends AbstractTranslationStore<T>
            implements TranslationStore.StringBased<T> {

        private static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");

        public StringBased(@NotNull Key key) {
            super(key);
        }

        @NotNull
        protected abstract T parse(@NotNull String text, @NotNull Locale locale);

        @Override
        public final void registerAll(@NotNull Locale locale, @NotNull Path path, boolean matches) {
            try {
                BufferedReader bufferedReader =
                        Files.newBufferedReader(path, StandardCharsets.UTF_8);
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

        @Override
        public final void registerAll(
                @NotNull Locale locale, @NotNull ResourceBundle resourceBundle, boolean matches) {
            Set<String> values = resourceBundle.keySet();
            this.registerAll(
                    locale,
                    values,
                    result -> {
                        String string = resourceBundle.getString(result);
                        return this.parse(
                                matches
                                        ? SINGLE_QUOTE_PATTERN.matcher(string).replaceAll("''")
                                        : string,
                                locale);
                    });
        }
    }

    private final class Translation implements Examinable {

        private final String key;

        private final Map<Locale, T> translations;

        private Translation(@NotNull String text) {
            this.key = Objects.requireNonNull(text, "key");
            this.translations = new ConcurrentHashMap<>();
        }

        @Nullable
        private T translate(@NotNull Locale locale) {
            T t;
            if ((t = this.translations.get(Objects.requireNonNull(locale, "locale"))) == null
                    && (t = this.translations.get(new Locale(locale.getLanguage()))) == null
                    && (t = this.translations.get(AbstractTranslationStore.this.defaultLocale))
                            == null) {
                t = this.translations.get(TranslationLocales.global());
            }
            return (T) t;
        }

        private void register(@NotNull Locale locale, @NotNull T t) {
            if (this.translations.putIfAbsent(
                            Objects.requireNonNull(locale, "locale"),
                            Objects.requireNonNull((T) t, "translation"))
                    != null) {
                throw new IllegalArgumentException(
                        String.format("Translation already exists: %s for %s", this.key, locale));
            }
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("key", this.key),
                    ExaminableProperty.of("translations", this.translations));
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof AbstractTranslationStore.Translation)) {
                return false;
            }
            AbstractTranslationStore.Translation translation =
                    (AbstractTranslationStore.Translation) result;
            return this.key.equals(translation.key)
                    && this.translations.equals(translation.translations);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.key, this.translations);
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }
}
