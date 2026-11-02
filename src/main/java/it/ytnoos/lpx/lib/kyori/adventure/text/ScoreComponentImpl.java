package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

final class ScoreComponentImpl extends AbstractComponent implements ScoreComponent {

    private final String name;

    private final String objective;

    @Deprecated @Nullable private final String value;

    static ScoreComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text,
            @NotNull String text2,
            @Nullable String text3) {
        return new ScoreComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "name"),
                Objects.requireNonNull(text2, "objective"),
                text3);
    }

    ScoreComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            @NotNull String name,
            @NotNull String objective,
            @Nullable String value) {
        super(values, style);
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    @NotNull
    @Override
    public String name() {
        return this.name;
    }

    @NotNull
    @Override
    public ScoreComponent name(@NotNull String text) {
        return Objects.equals(this.name, text)
                ? this
                : create(this.children, this.style, text, this.objective, this.value);
    }

    @NotNull
    @Override
    public String objective() {
        return this.objective;
    }

    @NotNull
    @Override
    public ScoreComponent objective(@NotNull String text) {
        return Objects.equals(this.objective, text)
                ? this
                : create(this.children, this.style, this.name, text, this.value);
    }

    @Deprecated
    @Nullable
    @Override
    public String value() {
        return this.value;
    }

    @Deprecated
    @NotNull
    @Override
    public ScoreComponent value(@Nullable String text) {
        return Objects.equals(this.value, text)
                ? this
                : create(this.children, this.style, this.name, this.objective, text);
    }

    @NotNull
    public ScoreComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(values, this.style, this.name, this.objective, this.value);
    }

    @NotNull
    public ScoreComponent style(@NotNull Style style2) {
        return create(this.children, style2, this.name, this.objective, this.value);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ScoreComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        ScoreComponent scoreComponent = (ScoreComponent) result;
        return Objects.equals(this.name, scoreComponent.name())
                && Objects.equals(this.objective, scoreComponent.objective())
                && Objects.equals(this.value, scoreComponent.value());
    }

    @Override
    public int hashCode() {
        int value2 = super.hashCode();
        value2 = 31 * value2 + this.name.hashCode();
        value2 = 31 * value2 + this.objective.hashCode();
        return 31 * value2 + Objects.hashCode(this.value);
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    public ScoreComponent.Builder toBuilder() {
        return new ScoreComponentImpl.BuilderImpl(this);
    }

    static final class BuilderImpl
            extends AbstractComponentBuilder<ScoreComponent, ScoreComponent.Builder>
            implements ScoreComponent.Builder {

        @Nullable private String name;

        @Nullable private String objective;

        @Nullable private String value;

        BuilderImpl() {}

        BuilderImpl(@NotNull ScoreComponent scoreComponent) {
            super(scoreComponent);
            this.name = scoreComponent.name();
            this.objective = scoreComponent.objective();
            this.value = scoreComponent.value();
        }

        @NotNull
        @Override
        public ScoreComponent.Builder name(@NotNull String text) {
            this.name = Objects.requireNonNull(text, "name");
            return this;
        }

        @NotNull
        @Override
        public ScoreComponent.Builder objective(@NotNull String text) {
            this.objective = Objects.requireNonNull(text, "objective");
            return this;
        }

        @Deprecated
        @NotNull
        @Override
        public ScoreComponent.Builder value(@Nullable String value) {
            this.value = value;
            return this;
        }

        @NotNull
        public ScoreComponent build() {
            if (this.name == null) {
                throw new IllegalStateException("name must be set");
            } else if (this.objective == null) {
                throw new IllegalStateException("objective must be set");
            } else {
                return ScoreComponentImpl.create(
                        this.children, this.buildStyle(), this.name, this.objective, this.value);
            }
        }
    }
}
