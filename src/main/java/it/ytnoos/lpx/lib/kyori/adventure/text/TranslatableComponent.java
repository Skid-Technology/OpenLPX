package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.translation.Translatable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public interface TranslatableComponent
        extends BuildableComponent<TranslatableComponent, TranslatableComponent.Builder>,
                ScopedComponent<TranslatableComponent> {

    @NotNull
    String key();

    @Contract(pure = true)
    @NotNull
    default TranslatableComponent key(@NotNull Translatable translatable) {
        return this.key(Objects.requireNonNull(translatable, "translatable").translationKey());
    }

    @Contract(pure = true)
    @NotNull
    TranslatableComponent key(@NotNull String text);

    @Deprecated
    @NotNull
    List<Component> args();

    @Deprecated
    @Contract(pure = true)
    @NotNull
    default TranslatableComponent args(@NotNull ComponentLike... componentLike) {
        return this.arguments(componentLike);
    }

    @Deprecated
    @Contract(pure = true)
    @NotNull
    default TranslatableComponent args(@NotNull List<? extends ComponentLike> values) {
        return this.arguments(values);
    }

    @NotNull
    List<TranslationArgument> arguments();

    @Contract(pure = true)
    @NotNull
    TranslatableComponent arguments(@NotNull ComponentLike... componentLike);

    @Contract(pure = true)
    @NotNull
    TranslatableComponent arguments(@NotNull List<? extends ComponentLike> values);

    @Nullable
    String fallback();

    @Contract(pure = true)
    @NotNull
    TranslatableComponent fallback(@Nullable String text);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(
                        ExaminableProperty.of("key", this.key()),
                        ExaminableProperty.of("arguments", this.arguments()),
                        ExaminableProperty.of("fallback", this.fallback())),
                BuildableComponent.super.examinableProperties());
    }

    interface Builder
            extends ComponentBuilder<TranslatableComponent, TranslatableComponent.Builder> {

        @Contract(pure = true)
        @NotNull
        default TranslatableComponent.Builder key(@NotNull Translatable translatable) {
            return this.key(Objects.requireNonNull(translatable, "translatable").translationKey());
        }

        @Contract("_ -> this")
        @NotNull
        TranslatableComponent.Builder key(@NotNull String text);

        @Deprecated
        @Contract("_ -> this")
        @NotNull
        default TranslatableComponent.Builder args(
                @NotNull ComponentBuilder<?, ?> componentBuilder) {
            return this.arguments(componentBuilder);
        }

        @Deprecated
        @Contract("_ -> this")
        @NotNull
        default TranslatableComponent.Builder args(
                @NotNull ComponentBuilder<?, ?>... componentBuilder) {
            return this.arguments(componentBuilder);
        }

        @Deprecated
        @Contract("_ -> this")
        @NotNull
        default TranslatableComponent.Builder args(@NotNull Component component) {
            return this.arguments(component);
        }

        @Deprecated
        @Contract("_ -> this")
        @NotNull
        default TranslatableComponent.Builder args(@NotNull ComponentLike... componentLike) {
            return this.arguments(componentLike);
        }

        @Deprecated
        @Contract("_ -> this")
        @NotNull
        default TranslatableComponent.Builder args(@NotNull List<? extends ComponentLike> values) {
            return this.arguments(values);
        }

        @Contract("_ -> this")
        @NotNull
        TranslatableComponent.Builder arguments(@NotNull ComponentLike... componentLike);

        @Contract("_ -> this")
        @NotNull
        TranslatableComponent.Builder arguments(@NotNull List<? extends ComponentLike> values);

        @Contract("_ -> this")
        @NotNull
        TranslatableComponent.Builder fallback(@Nullable String text);
    }
}
