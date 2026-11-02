package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.legacy;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public final class LegacyFormat implements Examinable {

    static final LegacyFormat RESET = new LegacyFormat(true);

    @Nullable private final NamedTextColor color;

    @Nullable private final TextDecoration decoration;

    private final boolean reset;

    LegacyFormat(@Nullable NamedTextColor namedTextColor) {
        this.color = namedTextColor;
        this.decoration = null;
        this.reset = false;
    }

    LegacyFormat(@Nullable TextDecoration textDecoration) {
        this.color = null;
        this.decoration = textDecoration;
        this.reset = false;
    }

    private LegacyFormat(boolean reset) {
        this.color = null;
        this.decoration = null;
        this.reset = reset;
    }

    @Nullable
    public TextColor color() {
        return this.color;
    }

    @Nullable
    public TextDecoration decoration() {
        return this.decoration;
    }

    public boolean reset() {
        return this.reset;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            LegacyFormat legacyFormat = (LegacyFormat) result;
            return this.color == legacyFormat.color
                    && this.decoration == legacyFormat.decoration
                    && this.reset == legacyFormat.reset;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = Objects.hashCode(this.color);
        value = 31 * value + Objects.hashCode(this.decoration);
        return 31 * value + Boolean.hashCode(this.reset);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("color", this.color),
                ExaminableProperty.of("decoration", this.decoration),
                ExaminableProperty.of("reset", this.reset));
    }
}
