package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public interface KeybindComponent
        extends BuildableComponent<KeybindComponent, KeybindComponent.Builder>,
                ScopedComponent<KeybindComponent> {

    @NotNull
    String keybind();

    @Contract(pure = true)
    @NotNull
    KeybindComponent keybind(@NotNull String text);

    @Contract(pure = true)
    @NotNull
    default KeybindComponent keybind(@NotNull KeybindComponent.KeybindLike keybindLike) {
        return this.keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind());
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("keybind", this.keybind())),
                BuildableComponent.super.examinableProperties());
    }

    interface Builder extends ComponentBuilder<KeybindComponent, KeybindComponent.Builder> {

        @Contract("_ -> this")
        @NotNull
        KeybindComponent.Builder keybind(@NotNull String text);

        @Contract(pure = true)
        @NotNull
        default KeybindComponent.Builder keybind(
                @NotNull KeybindComponent.KeybindLike keybindLike) {
            return this.keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind());
        }
    }

    interface KeybindLike {

        @NotNull
        String asKeybind();
    }
}
