package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

final class StylingTagImpl extends AbstractTag implements Inserting {

    private final StyleBuilderApplicable[] styles;

    StylingTagImpl(StyleBuilderApplicable[] styleBuilderApplicables) {
        this.styles = styleBuilderApplicables;
    }

    @NotNull
    @Override
    public Component value() {
        return Component.text("", Style.style(this.styles));
    }

    @Override
    public int hashCode() {
        return 31 + Arrays.hashCode(this.styles);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof StylingTagImpl)) {
            return false;
        }
        StylingTagImpl stylingTagImpl = (StylingTagImpl) result;
        return Arrays.equals(this.styles, stylingTagImpl.styles);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("styles", this.styles));
    }
}
