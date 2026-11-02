package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

final class GlobalTranslatorImpl implements GlobalTranslator {

    private static final Key NAME = Key.key("adventure", "global");

    static final GlobalTranslatorImpl INSTANCE = new GlobalTranslatorImpl();

    final TranslatableComponentRenderer<Locale> renderer =
            TranslatableComponentRenderer.usingTranslationSource(this);

    private final Set<Translator> sources = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private GlobalTranslatorImpl() {}

    @NotNull
    @Override
    public Key name() {
        return NAME;
    }

    @NotNull
    @Override
    public Iterable<? extends Translator> sources() {
        return Collections.unmodifiableSet(this.sources);
    }

    @Override
    public boolean addSource(@NotNull Translator translator) {
        Objects.requireNonNull(translator, "source");
        if (translator == this) {
            throw new IllegalArgumentException("GlobalTranslationSource");
        } else {
            return this.sources.add(translator);
        }
    }

    @Override
    public boolean removeSource(@NotNull Translator translator) {
        Objects.requireNonNull(translator, "source");
        return this.sources.remove(translator);
    }

    @NotNull
    @Override
    public TriState hasAnyTranslations() {
        return !this.sources.isEmpty() ? TriState.TRUE : TriState.FALSE;
    }

    @Override
    public boolean canTranslate(@NotNull String text, @NotNull Locale locale) {
        Objects.requireNonNull(text, "key");
        Objects.requireNonNull(locale, "locale");
        Iterator<it.ytnoos.lpx.lib.kyori.adventure.translation.Translator> iterator2 =
                this.sources.iterator();
        while (iterator2.hasNext()) {
            if (((Translator) iterator2.next()).canTranslate(text, locale)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public MessageFormat translate(@NotNull String text, @NotNull Locale locale) {
        Objects.requireNonNull(text, "key");
        Objects.requireNonNull(locale, "locale");
        Iterator<it.ytnoos.lpx.lib.kyori.adventure.translation.Translator> iterator2 =
                this.sources.iterator();
        while (iterator2.hasNext()) {
            MessageFormat messageFormat;
            if ((messageFormat = ((Translator) iterator2.next()).translate(text, locale)) != null) {
                return messageFormat;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Component translate(
            @NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
        Objects.requireNonNull(translatableComponent, "component");
        Objects.requireNonNull(locale, "locale");
        Iterator<it.ytnoos.lpx.lib.kyori.adventure.translation.Translator> iterator2 =
                this.sources.iterator();
        while (iterator2.hasNext()) {
            Component component;
            if ((component =
                            ((Translator) iterator2.next())
                                    .translate(translatableComponent, locale))
                    != null) {
                return component;
            }
        }
        return null;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("sources", this.sources));
    }
}
