package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.util.HSVLike;
import it.ytnoos.lpx.lib.kyori.adventure.util.RGBLike;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public interface TextColor
        extends Comparable<TextColor>, Examinable, RGBLike, StyleBuilderApplicable, TextFormat {

    char HEX_CHARACTER = '#';

    String HEX_PREFIX = "#";

    @NotNull
    static TextColor color(int value) {
        int value2;
        NamedTextColor namedTextColor;
        return (namedTextColor = NamedTextColor.namedColor(value2 = value & 16777215)) != null
                ? namedTextColor
                : new TextColorImpl(value2);
    }

    @NotNull
    static TextColor color(@NotNull RGBLike rGBLike) {
        return rGBLike instanceof TextColor
                ? (TextColor) rGBLike
                : color(rGBLike.red(), rGBLike.green(), rGBLike.blue());
    }

    @NotNull
    static TextColor color(@NotNull HSVLike hSVLike) {
        float value = hSVLike.s();
        float value2 = hSVLike.v();
        if (value == 0.0F) {
            return color(value2, value2, value2);
        } else {
            float value3;
            int value4 = (int) Math.floor(value3 = hSVLike.h() * 6.0F);
            float value5 = value3 - value4;
            float value6 = value2 * (1.0F - value);
            float value7 = value2 * (1.0F - value * value5);
            float value8 = value2 * (1.0F - value * (1.0F - value5));
            if (value4 == 0) {
                return color(value2, value8, value6);
            } else if (value4 == 1) {
                return color(value7, value2, value6);
            } else if (value4 == 2) {
                return color(value6, value2, value8);
            } else if (value4 == 3) {
                return color(value6, value7, value2);
            } else {
                return value4 == 4 ? color(value8, value6, value2) : color(value2, value6, value7);
            }
        }
    }

    @NotNull
    static TextColor color(
            @Range(from = 0L, to = 255L) int value,
            @Range(from = 0L, to = 255L) int value2,
            @Range(from = 0L, to = 255L) int value3) {
        return color((value & 0xFF) << 16 | (value2 & 0xFF) << 8 | value3 & 0xFF);
    }

    @NotNull
    static TextColor color(float value, float value2, float value3) {
        return color((int) (value * 255.0F), (int) (value2 * 255.0F), (int) (value3 * 255.0F));
    }

    @Nullable
    static TextColor fromHexString(@NotNull String text) {
        if (text.startsWith("#")) {
            try {
                return color(Integer.parseInt(text.substring(1), 16));
            } catch (NumberFormatException numberFormatException) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Nullable
    static TextColor fromCSSHexString(@NotNull String text) {
        if (text.startsWith("#")) {
            String text2;
            if ((text2 = text.substring(1)).length() != 3 && text2.length() != 6) {
                return null;
            }
            int value;
            try {
                value = Integer.parseInt(text2, 16);
            } catch (NumberFormatException numberFormatException) {
                return null;
            }
            if (text2.length() == 6) {
                return color(value);
            }
            int value2 = (value & 3840) >> 8 | (value & 3840) >> 4;
            int value3 = (value & 240) >> 4 | value & 240;
            int value4 = (value & 15) << 4 | value & 15;
            return color(value2, value3, value4);
        } else {
            return null;
        }
    }

    int value();

    @NotNull
    default String asHexString() {
        StringBuilder stringBuilder;
        (stringBuilder = new StringBuilder()).append("#");
        String text = Integer.toHexString(this.value());
        for (int index = 0; index < 6 - text.length(); index++) {
            stringBuilder.append('0');
        }
        stringBuilder.append(text);
        return stringBuilder.toString().toUpperCase(Locale.ROOT);
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int red() {
        return this.value() >> 16 & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int green() {
        return this.value() >> 8 & 0xFF;
    }

    @Override
    @Range(from = 0L, to = 255L)
    default int blue() {
        return this.value() & 0xFF;
    }

    @NotNull
    static TextColor lerp(float value, @NotNull RGBLike rGBLike, @NotNull RGBLike rGBLike2) {
        float value2 = Math.min(1.0F, Math.max(0.0F, value));
        int value3 = rGBLike.red();
        int value4 = rGBLike2.red();
        int value5 = rGBLike.green();
        int value6 = rGBLike2.green();
        int value7 = rGBLike.blue();
        int value8 = rGBLike2.blue();
        return color(
                Math.round(value3 + value2 * (value4 - value3)),
                Math.round(value5 + value2 * (value6 - value5)),
                Math.round(value7 + value2 * (value8 - value7)));
    }

    @NotNull
    static <C extends TextColor> C nearestColorTo(
            @NotNull List<C> values, @NotNull TextColor textColor) {
        Objects.requireNonNull(textColor, "color");
        float value = Float.MAX_VALUE;
        TextColor textColor2 = (TextColor) values.get(0);
        int value2 = 0;
        for (int size2 = values.size(); value2 < size2; value2++) {
            TextColor textColor3 = (TextColor) values.get(value2);
            float value3;
            if ((value3 = TextColorImpl.distance(textColor.asHSV(), textColor3.asHSV())) < value) {
                textColor2 = textColor3;
                value = value3;
            }
            if (value3 == 0.0F) {
                break;
            }
        }
        return (C) textColor2;
    }

    @Override
    default void styleApply(Style.@NotNull Builder builder) {
        builder.color(this);
    }

    default int compareTo(TextColor textColor) {
        return Integer.compare(this.value(), textColor.value());
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.asHexString()));
    }
}
