package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class CallbackStylingTagImpl extends AbstractTag implements Inserting {

    private final Consumer<Style.Builder> styles;

    CallbackStylingTagImpl(Consumer<Style.Builder> consumer) {
        this.styles = consumer;
    }

    @NotNull
    @Override
    public Component value() {
        return Component.text("", Style.style(this.styles));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.styles);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof CallbackStylingTagImpl)) {
            return false;
        }
        CallbackStylingTagImpl callbackStylingTagImpl = (CallbackStylingTagImpl) result;
        return Objects.equals(this.styles, callbackStylingTagImpl.styles);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("styles", this.styles));
    }
}
