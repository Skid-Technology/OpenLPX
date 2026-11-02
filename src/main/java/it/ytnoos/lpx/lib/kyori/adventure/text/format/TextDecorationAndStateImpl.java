package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

final class TextDecorationAndStateImpl implements TextDecorationAndState {

    private final TextDecoration decoration;

    private final TextDecoration.State state;

    TextDecorationAndStateImpl(TextDecoration textDecoration, TextDecoration.State state2) {
        this.decoration = textDecoration;
        this.state = Objects.requireNonNull(state2, "state");
    }

    @NotNull
    @Override
    public TextDecoration decoration() {
        return this.decoration;
    }

    @Override
    public TextDecoration.@NotNull State state() {
        return this.state;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            TextDecorationAndStateImpl textDecorationAndStateImpl =
                    (TextDecorationAndStateImpl) result;
            return this.decoration == textDecorationAndStateImpl.decoration
                    && this.state == textDecorationAndStateImpl.state;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = this.decoration.hashCode();
        return 31 * value + this.state.hashCode();
    }
}
