package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.JoinConfiguration;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.TagPattern;

import org.jetbrains.annotations.NotNull;

import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Locale;

public final class Formatter {

    private Formatter() {}

    @NotNull
    public static TagResolver number(@TagPattern @NotNull String text, @NotNull Number number2) {
        return TagResolver.resolver(
                text,
                (result, result2) -> {
                    NumberFormat numberFormat;
                    if (result.hasNext()) {
                        String text2 = result.pop().value();
                        if (result.hasNext()) {
                            String text3 = result.pop().value();
                            numberFormat =
                                    new DecimalFormat(
                                            text3,
                                            new DecimalFormatSymbols(Locale.forLanguageTag(text2)));
                        } else if (text2.contains(".")) {
                            numberFormat =
                                    new DecimalFormat(text2, DecimalFormatSymbols.getInstance());
                        } else {
                            numberFormat = DecimalFormat.getInstance(Locale.forLanguageTag(text2));
                        }
                    } else {
                        numberFormat = DecimalFormat.getInstance();
                    }
                    return Tag.inserting(result2.deserialize(numberFormat.format(number2)));
                });
    }

    @NotNull
    public static TagResolver date(
            @TagPattern @NotNull String text, @NotNull TemporalAccessor temporalAccessor) {
        return TagResolver.resolver(
                text,
                (result, result2) -> {
                    String text2 = result.popOr("Format expected.").value();
                    return Tag.inserting(
                            result2.deserialize(
                                    DateTimeFormatter.ofPattern(text2).format(temporalAccessor)));
                });
    }

    @NotNull
    public static TagResolver choice(@TagPattern @NotNull String text, Number number) {
        return TagResolver.resolver(
                text,
                (result, result2) -> {
                    String text2 = result.popOr("Format expected.").value();
                    ChoiceFormat choiceFormat = new ChoiceFormat(text2);
                    return Tag.inserting(result2.deserialize(choiceFormat.format(number)));
                });
    }

    public static TagResolver booleanChoice(@TagPattern @NotNull String text, boolean matches) {
        return TagResolver.resolver(
                text,
                (result, result2) -> {
                    String text2 = result.popOr("True format expected.").value();
                    String text3 = result.popOr("False format expected.").value();
                    return Tag.inserting(result2.deserialize(matches ? text2 : text3));
                });
    }

    public static TagResolver joining(
            @TagPattern @NotNull String text, @NotNull Iterable<? extends ComponentLike> iterable) {
        return TagResolver.resolver(
                text,
                (result, result2) -> {
                    if (!result.hasNext()) {
                        return Tag.inserting(
                                Component.join(
                                        JoinConfiguration.noSeparators(), ((Iterable) iterable)));
                    }
                    String text2 = result.pop().value();
                    JoinConfiguration.Builder builder2 =
                            JoinConfiguration.builder().separator(result2.deserialize(text2));
                    if (result.hasNext()) {
                        String text3 = result.pop().value();
                        builder2.lastSeparator(result2.deserialize(text3));
                    }
                    if (result.hasNext()) {
                        String text4 = result.pop().value();
                        builder2.lastSeparatorIfSerial(result2.deserialize(text4));
                    }
                    return Tag.inserting(Component.join(builder2.build(), ((Iterable) iterable)));
                });
    }

    public static TagResolver joining(
            @TagPattern @NotNull String text, @NotNull ComponentLike... componentLike) {
        return joining(text, Arrays.asList(componentLike));
    }
}
