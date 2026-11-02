package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

final class MessageFormatTranslationStore
        extends AbstractTranslationStore.StringBased<MessageFormat> implements TranslationRegistry {

    MessageFormatTranslationStore(Key key) {
        super(key);
    }

    @NotNull
    protected MessageFormat parse(@NotNull String text, @NotNull Locale locale) {
        return new MessageFormat(text, locale);
    }

    @Nullable
    @Override
    public MessageFormat translate(@NotNull String text, @NotNull Locale locale) {
        return this.translationValue(text, locale);
    }
}
