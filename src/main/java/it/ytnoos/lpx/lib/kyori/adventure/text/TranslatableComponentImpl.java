package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class TranslatableComponentImpl extends AbstractComponent implements TranslatableComponent {

    private final String key;

    @Nullable private final String fallback;

    private final List<TranslationArgument> args;

    static TranslatableComponent create(
            @NotNull List<Component> values,
            @NotNull Style style,
            @NotNull String text,
            @Nullable String text2,
            @NotNull ComponentLike[] componentLikes) {
        Objects.requireNonNull(componentLikes, "args");
        return create(values, style, text, text2, Arrays.asList(componentLikes));
    }

    static TranslatableComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text,
            @Nullable String text2,
            @NotNull List<? extends ComponentLike> values2) {
        return new TranslatableComponentImpl(
                ComponentLike.asComponents(values, IS_NOT_EMPTY),
                Objects.requireNonNull(style, "style"),
                Objects.requireNonNull(text, "key"),
                text2,
                asArguments(values2));
    }

    TranslatableComponentImpl(
            @NotNull List<Component> values,
            @NotNull Style style,
            @NotNull String key,
            @Nullable String fallback,
            @NotNull List<TranslationArgument> args) {
        super(values, style);
        this.key = key;
        this.fallback = fallback;
        this.args = args;
    }

    @NotNull
    @Override
    public String key() {
        return this.key;
    }

    @NotNull
    @Override
    public TranslatableComponent key(@NotNull String text) {
        return Objects.equals(this.key, text)
                ? this
                : create(this.children, this.style, text, this.fallback, this.args);
    }

    @Deprecated
    @NotNull
    @Override
    public List<Component> args() {
        return ComponentLike.asComponents(this.args);
    }

    @NotNull
    @Override
    public List<TranslationArgument> arguments() {
        return this.args;
    }

    @NotNull
    @Override
    public TranslatableComponent arguments(@NotNull ComponentLike... componentLike) {
        return create(this.children, this.style, this.key, this.fallback, componentLike);
    }

    @NotNull
    @Override
    public TranslatableComponent arguments(@NotNull List<? extends ComponentLike> values) {
        return create(this.children, this.style, this.key, this.fallback, values);
    }

    @Nullable
    @Override
    public String fallback() {
        return this.fallback;
    }

    @NotNull
    @Override
    public TranslatableComponent fallback(@Nullable String text) {
        return create(this.children, this.style, this.key, text, this.args);
    }

    @NotNull
    public TranslatableComponent children(@NotNull List<? extends ComponentLike> values) {
        return create(values, this.style, this.key, this.fallback, this.args);
    }

    @NotNull
    public TranslatableComponent style(@NotNull Style style2) {
        return create(this.children, style2, this.key, this.fallback, this.args);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof TranslatableComponent)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        TranslatableComponent translatableComponent = (TranslatableComponent) result;
        return Objects.equals(this.key, translatableComponent.key())
                && Objects.equals(this.fallback, translatableComponent.fallback())
                && Objects.equals(this.args, translatableComponent.arguments());
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        value = 31 * value + this.key.hashCode();
        value = 31 * value + Objects.hashCode(this.fallback);
        return 31 * value + this.args.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    public TranslatableComponent.Builder toBuilder() {
        return new TranslatableComponentImpl.BuilderImpl(this);
    }

    static List<TranslationArgument> asArguments(@NotNull List<? extends ComponentLike> values) {
        if (values.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument> values2 =
                new ArrayList<>(values.size());
        for (int index = 0; index < values.size(); index++) {
            ComponentLike componentLike;
            if ((componentLike = (ComponentLike) values.get(index)) == null) {
                throw new NullPointerException("likes[" + index + "]");
            }
            if (componentLike instanceof TranslationArgument) {
                values2.add((TranslationArgument) componentLike);
            } else if (componentLike instanceof TranslationArgumentLike) {
                values2.add(
                        Objects.requireNonNull(
                                ((TranslationArgumentLike) componentLike).asTranslationArgument(),
                                "likes[" + index + "].asTranslationArgument()"));
            } else {
                values2.add(TranslationArgument.component(componentLike));
            }
        }
        return Collections.unmodifiableList(values2);
    }

    static final class BuilderImpl
            extends AbstractComponentBuilder<TranslatableComponent, TranslatableComponent.Builder>
            implements TranslatableComponent.Builder {

        @Nullable private String key;

        @Nullable private String fallback;

        private List<TranslationArgument> args = Collections.emptyList();

        BuilderImpl() {}

        BuilderImpl(@NotNull TranslatableComponent translatableComponent) {
            super(translatableComponent);
            this.key = translatableComponent.key();
            this.args = translatableComponent.arguments();
            this.fallback = translatableComponent.fallback();
        }

        @NotNull
        @Override
        public TranslatableComponent.Builder key(@NotNull String key) {
            this.key = key;
            return this;
        }

        @NotNull
        @Override
        public TranslatableComponent.Builder arguments(@NotNull ComponentLike... componentLike) {
            Objects.requireNonNull(componentLike, "args");
            return componentLike.length == 0
                    ? this.arguments(Collections.emptyList())
                    : this.arguments(Arrays.asList(componentLike));
        }

        @NotNull
        @Override
        public TranslatableComponent.Builder arguments(
                @NotNull List<? extends ComponentLike> values) {
            this.args =
                    TranslatableComponentImpl.asArguments(Objects.requireNonNull(values, "args"));
            return this;
        }

        @NotNull
        @Override
        public TranslatableComponent.Builder fallback(@Nullable String fallback) {
            this.fallback = fallback;
            return this;
        }

        @NotNull
        public TranslatableComponent build() {
            if (this.key == null) {
                throw new IllegalStateException("key must be set");
            } else {
                return TranslatableComponentImpl.create(
                        this.children, this.buildStyle(), this.key, this.fallback, this.args);
            }
        }
    }
}
