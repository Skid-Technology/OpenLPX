package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

final class KeybindComponentImpl extends AbstractComponent implements KeybindComponent {

    private final String keybind;

    static KeybindComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text) {
        return new KeybindComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "keybind"));
    }

    KeybindComponentImpl(
            @NotNull List<Component> values, @NotNull Style style, @NotNull String keybind) {
        super(values, style);
        this.keybind = keybind;
    }

    @NotNull
    @Override
    public String keybind() {
        return this.keybind;
    }

    @NotNull
    @Override
    public KeybindComponent keybind(@NotNull String text) {
        return Objects.equals(this.keybind, text) ? this : create(this.children, this.style, text);
    }

    @NotNull
    public KeybindComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(values, this.style, this.keybind);
    }

    @NotNull
    public KeybindComponent style(@NotNull Style style2) {
        return create(this.children, style2, this.keybind);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof KeybindComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        KeybindComponent keybindComponent = (KeybindComponent) result;
        return Objects.equals(this.keybind, keybindComponent.keybind());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        return -2068675590 * value + this.keybind.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    public KeybindComponent.Builder toBuilder() {
        return new KeybindComponentImpl.BuilderImpl(this);
    }

    static final class BuilderImpl
            extends AbstractComponentBuilder<KeybindComponent, KeybindComponent.Builder>
            implements KeybindComponent.Builder {

        @Nullable private String keybind;

        BuilderImpl() {}

        BuilderImpl(@NotNull KeybindComponent keybindComponent) {
            super(keybindComponent);
            this.keybind = keybindComponent.keybind();
        }

        @NotNull
        @Override
        public KeybindComponent.Builder keybind(@NotNull String text) {
            this.keybind = Objects.requireNonNull(text, "keybind");
            return this;
        }

        @NotNull
        public KeybindComponent build() {
            if (this.keybind == null) {
                throw new IllegalStateException("keybind must be set");
            } else {
                return KeybindComponentImpl.create(this.children, this.buildStyle(), this.keybind);
            }
        }
    }
}
