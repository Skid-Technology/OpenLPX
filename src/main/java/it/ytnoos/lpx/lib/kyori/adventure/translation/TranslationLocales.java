package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.internal.properties.AdventureProperties;

import java.util.Locale;
import java.util.function.Supplier;

final class TranslationLocales {

    private static final Supplier<Locale> GLOBAL;

    private TranslationLocales() {}

    static Locale global() {
        return GLOBAL.get();
    }

    static {
        String text;
        if ((text = AdventureProperties.DEFAULT_TRANSLATION_LOCALE.value()) == null
                || text.isEmpty()) {
            GLOBAL = () -> Locale.US;
        } else if (text.equals("system")) {
            GLOBAL = Locale::getDefault;
        } else {
            Locale locale = Translator.parseLocale(text);
            GLOBAL = () -> locale;
        }
    }
}
