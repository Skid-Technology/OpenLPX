package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.MiniMessage;
import it.ytnoos.lpx.lib.kyori.adventure.translation.AbstractTranslationStore;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;

final class MiniMessageTranslationStoreImpl extends AbstractTranslationStore.StringBased<String>
        implements MiniMessageTranslationStore {

    private final MiniMessageTranslationStoreImpl.Translator translator;

    MiniMessageTranslationStoreImpl(@NotNull Key key, @NotNull MiniMessage miniMessage) {
        super(key);
        this.translator =
                new MiniMessageTranslationStoreImpl.Translator(
                        Objects.requireNonNull(miniMessage, "miniMessage"));
    }

    @NotNull
    protected String parse(@NotNull String text, @NotNull Locale locale) {
        return text;
    }

    @Nullable
    @Override
    public MessageFormat translate(@NotNull String text, @NotNull Locale locale) {
        return null;
    }

    @Nullable
    @Override
    public Component translate(
            @NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
        return this.translator.translate(translatableComponent, locale);
    }

    private final class Translator extends MiniMessageTranslator {

        private Translator(@NotNull MiniMessage miniMessage) {
            super(miniMessage);
        }

        @Nullable
        @Override
        protected String getMiniMessageString(@NotNull String text, @NotNull Locale locale) {
            return MiniMessageTranslationStoreImpl.this.translationValue(text, locale);
        }

        @NotNull
        @Override
        public Key name() {
            return MiniMessageTranslationStoreImpl.this.name();
        }

        @NotNull
        @Override
        public TriState hasAnyTranslations() {
            return MiniMessageTranslationStoreImpl.this.hasAnyTranslations();
        }
    }
}
