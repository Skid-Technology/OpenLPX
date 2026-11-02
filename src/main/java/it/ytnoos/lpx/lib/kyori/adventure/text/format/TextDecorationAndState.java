package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@NonExtendable
public interface TextDecorationAndState extends Examinable, StyleBuilderApplicable {

    @NotNull
    TextDecoration decoration();

    TextDecoration.@NotNull State state();

    @Override
    default void styleApply(Style.@NotNull Builder builder) {
        builder.decoration(this.decoration(), this.state());
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("decoration", this.decoration()),
                ExaminableProperty.of("state", this.state()));
    }
}
