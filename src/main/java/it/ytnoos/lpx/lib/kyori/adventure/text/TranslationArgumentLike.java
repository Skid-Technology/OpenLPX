package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TranslationArgumentLike extends ComponentLike {

    @NotNull
    TranslationArgument asTranslationArgument();

    @NotNull
    @Override
    default Component asComponent() {
        return this.asTranslationArgument().asComponent();
    }
}
