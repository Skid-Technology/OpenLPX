package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.util.Index;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public enum TextDecoration implements StyleBuilderApplicable, TextFormat {
    OBFUSCATED("obfuscated"),
    BOLD("bold"),
    STRIKETHROUGH("strikethrough"),
    UNDERLINED("underlined"),
    ITALIC("italic");

    public static final Index<String, TextDecoration> NAMES =
            Index.create(TextDecoration.class, result -> result.name);

    private final String name;

    TextDecoration(String name) {
        this.name = name;
    }

    @Deprecated
    @NotNull
    public final TextDecorationAndState as(boolean matches) {
        return this.withState(matches);
    }

    @Deprecated
    @NotNull
    public final TextDecorationAndState as(@NotNull TextDecoration.State state) {
        return this.withState(state);
    }

    @NotNull
    public final TextDecorationAndState withState(boolean matches) {
        return new TextDecorationAndStateImpl(this, TextDecoration.State.byBoolean(matches));
    }

    @NotNull
    public final TextDecorationAndState withState(@NotNull TextDecoration.State state) {
        return new TextDecorationAndStateImpl(this, state);
    }

    @NotNull
    public final TextDecorationAndState withState(@NotNull TriState triState) {
        return new TextDecorationAndStateImpl(this, TextDecoration.State.byTriState(triState));
    }

    @Override
    public void styleApply(Style.@NotNull Builder builder) {
        builder.decorate(this);
    }

    @NotNull
    @Override
    public String toString() {
        return this.name;
    }

    public enum State {
        NOT_SET("not_set"),
        FALSE("false"),
        TRUE("true");

        private final String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @NotNull
        public static TextDecoration.State byBoolean(boolean matches) {
            return matches ? TRUE : FALSE;
        }

        @NotNull
        public static TextDecoration.State byBoolean(@Nullable Boolean matches) {
            return matches == null ? NOT_SET : byBoolean(matches.booleanValue());
        }

        @NotNull
        public static TextDecoration.State byTriState(@NotNull TriState triState) {
            Objects.requireNonNull(triState);
            switch (triState) {
                case TRUE:
                    return TRUE;
                case FALSE:
                    return FALSE;
                case NOT_SET:
                    return NOT_SET;
                default:
                    throw new IllegalArgumentException(
                            "Unable to turn TriState: "
                                    + triState
                                    + " into a TextDecoration.State");
            }
        }
    }
}
