package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface SelectorComponent
        extends BuildableComponent<SelectorComponent, SelectorComponent.Builder>,
                ScopedComponent<SelectorComponent> {

    @NotNull
    String pattern();

    @Contract(pure = true)
    @NotNull
    SelectorComponent pattern(@NotNull String text);

    @Nullable
    Component separator();

    @NotNull
    SelectorComponent separator(@Nullable ComponentLike componentLike);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(
                        ExaminableProperty.of("pattern", this.pattern()),
                        ExaminableProperty.of("separator", this.separator())),
                BuildableComponent.super.examinableProperties());
    }

    interface Builder extends ComponentBuilder<SelectorComponent, SelectorComponent.Builder> {

        @Contract("_ -> this")
        @NotNull
        SelectorComponent.Builder pattern(@NotNull String text);

        @Contract("_ -> this")
        @NotNull
        SelectorComponent.Builder separator(@Nullable ComponentLike componentLike);
    }
}
