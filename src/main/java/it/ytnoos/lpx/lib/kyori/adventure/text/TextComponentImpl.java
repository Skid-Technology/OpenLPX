package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.internal.properties.AdventureProperties;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.util.Nag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

class TextComponentImpl extends AbstractComponent implements TextComponent {

    private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED =
            Boolean.TRUE.equals(
                    AdventureProperties.TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED.value());

    @VisibleForTesting static final char SECTION_CHAR = '§';

    static final TextComponent EMPTY = createDirect("");

    static final TextComponent NEWLINE = createDirect("\n");

    static final TextComponent SPACE = createDirect(" ");

    private final String content;

    static TextComponent create(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text2) {
        List<it.ytnoos.lpx.lib.kyori.adventure.text.Component> values2;
        return (values2 = ComponentLike.asComponents(values, IS_NOT_EMPTY)).isEmpty()
                        && style.isEmpty()
                        && text2.isEmpty()
                ? Component.empty()
                : new TextComponentImpl(
                        values2,
                        Objects.requireNonNull(style, "style"),
                        Objects.requireNonNull(text2, "content"));
    }

    TextComponent create0(
            @NotNull List<? extends ComponentLike> values,
            @NotNull Style style,
            @NotNull String text) {
        return create(values, style, text);
    }

    @NotNull
    private static TextComponent createDirect(@NotNull String text) {
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), text);
    }

    TextComponentImpl(
            @NotNull List<Component> values, @NotNull Style style, @NotNull String content) {
        super(values, style);
        this.content = content;
        LegacyFormattingDetected legacyFormattingDetected;
        if (WARN_WHEN_LEGACY_FORMATTING_DETECTED
                && (legacyFormattingDetected = this.warnWhenLegacyFormattingDetected()) != null) {
            Nag.print(legacyFormattingDetected);
        }
    }

    @VisibleForTesting
    @Nullable
    final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
        return this.content.indexOf(167) != -1 ? new LegacyFormattingDetected(this) : null;
    }

    @NotNull
    @Override
    public String content() {
        return this.content;
    }

    @NotNull
    @Override
    public TextComponent content(@NotNull String text) {
        return Objects.equals(this.content, text)
                ? this
                : this.create0(this.children, this.style, text);
    }

    @NotNull
    public TextComponent children(@NotNull List<? extends ComponentLike> values) {
        return this.create0(values, this.style, this.content);
    }

    @NotNull
    public TextComponent style(@NotNull Style style2) {
        return this.create0(this.children, style2, this.content);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof TextComponentImpl)) {
            return false;
        }
        if (!super.equals(result)) {
            return false;
        }
        TextComponentImpl textComponentImpl = (TextComponentImpl) result;
        return Objects.equals(this.content, textComponentImpl.content);
    }

    @Override
    public int hashCode() {
        int value = super.hashCode();
        return 31 * value + this.content.hashCode();
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    public TextComponent.Builder toBuilder() {
        return new TextComponentImpl.BuilderImpl(this);
    }

    static class BuilderImpl extends AbstractComponentBuilder<TextComponent, TextComponent.Builder>
            implements TextComponent.Builder {

        private String content = "";

        BuilderImpl() {}

        BuilderImpl(@NotNull TextComponent textComponent) {
            super(textComponent);
            this.content = textComponent.content();
        }

        @NotNull
        @Override
        public TextComponent.Builder content(@NotNull String text) {
            this.content = Objects.requireNonNull(text, "content");
            return this;
        }

        @NotNull
        @Override
        public String content() {
            return this.content;
        }

        @NotNull
        public TextComponent build() {
            return this.isEmpty()
                    ? Component.empty()
                    : TextComponentImpl.create(this.children, this.buildStyle(), this.content);
        }

        private boolean isEmpty() {
            return this.content.isEmpty() && this.children.isEmpty() && !this.hasStyle();
        }
    }
}
