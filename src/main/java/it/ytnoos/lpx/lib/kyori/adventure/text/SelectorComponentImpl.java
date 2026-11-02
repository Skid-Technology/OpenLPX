package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

final class SelectorComponentImpl extends AbstractComponent implements SelectorComponent {

    private final String pattern;

    @Nullable private final Component separator;

    static SelectorComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text,
            @Nullable ComponentLike componentLike) {
        return new SelectorComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "pattern"),
                ComponentLike.unbox(componentLike));
    }

    SelectorComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            @NotNull String pattern,
            @Nullable Component component) {
        super(values, style);
        this.pattern = pattern;
        this.separator = component;
    }

    @NotNull
    @Override
    public String pattern() {
        return this.pattern;
    }

    @NotNull
    @Override
    public SelectorComponent pattern(@NotNull String text) {
        return Objects.equals(this.pattern, text)
                ? this
                : create(this.children, this.style, text, this.separator);
    }

    @Nullable
    @Override
    public Component separator() {
        return this.separator;
    }

    @NotNull
    @Override
    public SelectorComponent separator(@Nullable ComponentLike componentLike) {
        return create(this.children, this.style, this.pattern, componentLike);
    }

    @NotNull
    public SelectorComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(values, this.style, this.pattern, this.separator);
    }

    @NotNull
    public SelectorComponent style(@NotNull Style style2) {
        return create(this.children, style2, this.pattern, this.separator);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof SelectorComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        SelectorComponent selectorComponent = (SelectorComponent) result;
        return Objects.equals(this.pattern, selectorComponent.pattern())
                && Objects.equals(this.separator, selectorComponent.separator());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        value = 31 * value + this.pattern.hashCode();
        return 31 * value + Objects.hashCode(this.separator);
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    public SelectorComponent.Builder toBuilder() {
        return new SelectorComponentImpl.BuilderImpl(this);
    }

    static final class BuilderImpl
            extends AbstractComponentBuilder<SelectorComponent, SelectorComponent.Builder>
            implements SelectorComponent.Builder {

        @Nullable private String pattern;

        @Nullable private Component separator;

        BuilderImpl() {}

        BuilderImpl(@NotNull SelectorComponent selectorComponent) {
            super(selectorComponent);
            this.pattern = selectorComponent.pattern();
            this.separator = selectorComponent.separator();
        }

        @NotNull
        @Override
        public SelectorComponent.Builder pattern(@NotNull String text) {
            this.pattern = Objects.requireNonNull(text, "pattern");
            return this;
        }

        @NotNull
        @Override
        public SelectorComponent.Builder separator(@Nullable ComponentLike componentLike) {
            this.separator = ComponentLike.unbox(componentLike);
            return this;
        }

        @NotNull
        public SelectorComponent build() {
            if (this.pattern == null) {
                throw new IllegalStateException("pattern must be set");
            } else {
                return SelectorComponentImpl.create(
                        this.children, this.buildStyle(), this.pattern, this.separator);
            }
        }
    }
}
