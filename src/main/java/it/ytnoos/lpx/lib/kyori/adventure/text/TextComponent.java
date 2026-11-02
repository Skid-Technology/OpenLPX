package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface TextComponent
        extends BuildableComponent<TextComponent, TextComponent.Builder>,
                ScopedComponent<TextComponent> {

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static TextComponent ofChildren(@NotNull ComponentLike... componentLike) {
        return Component.textOfChildren(componentLike);
    }

    @NotNull
    String content();

    @Contract(pure = true)
    @NotNull
    TextComponent content(@NotNull String text);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("content", this.content())),
                BuildableComponent.super.examinableProperties());
    }

    interface Builder extends ComponentBuilder<TextComponent, TextComponent.Builder> {

        @NotNull
        String content();

        @Contract("_ -> this")
        @NotNull
        TextComponent.Builder content(@NotNull String text);
    }
}
