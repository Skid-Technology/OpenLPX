package it.ytnoos.lpx.lib.kyori.adventure.translation;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

final class ComponentTranslationStore extends AbstractTranslationStore<Component> {

    ComponentTranslationStore(@NotNull Key key) {
        super(key);
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
        Component component;
        return (component = this.translationValue(translatableComponent.key(), locale)) == null
                ? null
                : component.append(translatableComponent.children());
    }
}
