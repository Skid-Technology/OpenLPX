package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@NonExtendable
public interface TranslationArgument extends TranslationArgumentLike, Examinable {

    @NotNull
    static TranslationArgument bool(boolean matches) {
        return new TranslationArgumentImpl(matches);
    }

    @NotNull
    static TranslationArgument numeric(@NotNull Number number) {
        return new TranslationArgumentImpl(Objects.requireNonNull(number, "value"));
    }

    @NotNull
    static TranslationArgument component(@NotNull ComponentLike componentLike) {
        return componentLike instanceof TranslationArgumentLike
                ? ((TranslationArgumentLike) componentLike).asTranslationArgument()
                : new TranslationArgumentImpl(
                        Objects.requireNonNull(
                                Objects.requireNonNull(componentLike, "value").asComponent(),
                                "value.asComponent()"));
    }

    @NotNull
    Object value();

    @NotNull
    @Override
    default TranslationArgument asTranslationArgument() {
        return this;
    }
}
