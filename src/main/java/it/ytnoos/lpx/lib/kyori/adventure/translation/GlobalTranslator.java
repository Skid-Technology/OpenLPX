package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface GlobalTranslator extends Translator, Examinable {

    @NotNull
    static GlobalTranslator translator() {
        return GlobalTranslatorImpl.INSTANCE;
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static GlobalTranslator get() {
        return GlobalTranslatorImpl.INSTANCE;
    }

    @NotNull
    static TranslatableComponentRenderer<Locale> renderer() {
        return GlobalTranslatorImpl.INSTANCE.renderer;
    }

    @NotNull
    static Component render(@NotNull Component component, @NotNull Locale locale) {
        return renderer().render(component, locale);
    }

    @NotNull
    Iterable<? extends Translator> sources();

    boolean addSource(@NotNull Translator translator);

    boolean removeSource(@NotNull Translator translator);
}
