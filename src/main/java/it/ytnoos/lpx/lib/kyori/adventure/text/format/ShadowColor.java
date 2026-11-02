package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.util.ARGBLike;
import it.ytnoos.lpx.lib.kyori.adventure.util.RGBLike;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface ShadowColor extends StyleBuilderApplicable, ARGBLike {

    @NotNull
    static ShadowColor lerp(float value, @NotNull ARGBLike aRGBLike, @NotNull ARGBLike aRGBLike2) {
        float value2 = Math.min(1.0F, Math.max(0.0F, value));
        int value3 = aRGBLike.red();
        int value4 = aRGBLike2.red();
        int value5 = aRGBLike.green();
        int value6 = aRGBLike2.green();
        int value7 = aRGBLike.blue();
        int value8 = aRGBLike2.blue();
        int value9 = aRGBLike.alpha();
        int value10 = aRGBLike2.alpha();
        return shadowColor(
                Math.round(value3 + value2 * (value4 - value3)),
                Math.round(value5 + value2 * (value6 - value5)),
                Math.round(value7 + value2 * (value8 - value7)),
                Math.round(value9 + value2 * (value10 - value9)));
    }

    @NotNull
    static ShadowColor none() {
        return ShadowColorImpl.NONE;
    }

    @Contract(pure = true)
    @NotNull
    static ShadowColor shadowColor(int value) {
        return value == 0 ? none() : new ShadowColorImpl(value);
    }

    @Contract(pure = true)
    @NotNull
    static ShadowColor shadowColor(
            @Range(from = 0L, to = 255L) int value,
            @Range(from = 0L, to = 255L) int value2,
            @Range(from = 0L, to = 255L) int value3,
            @Range(from = 0L, to = 255L) int value4) {
        int value5;
        return (value5 =
                                (value4 & 0xFF) << 24
                                        | (value & 0xFF) << 16
                                        | (value2 & 0xFF) << 8
                                        | value3 & 0xFF)
                        == 0
                ? none()
                : new ShadowColorImpl(value5);
    }

    @Contract(pure = true)
    @NotNull
    static ShadowColor shadowColor(
            @NotNull RGBLike rGBLike, @Range(from = 0L, to = 255L) int value) {
        return shadowColor(rGBLike.red(), rGBLike.green(), rGBLike.blue(), value);
    }

    @NotNull
    static ShadowColor shadowColor(@NotNull ARGBLike aRGBLike) {
        return aRGBLike instanceof ShadowColor
                ? (ShadowColor) aRGBLike
                : shadowColor(aRGBLike.red(), aRGBLike.green(), aRGBLike.blue(), aRGBLike.alpha());
    }

    @Contract(pure = true)
    @Nullable
    static ShadowColor fromHexString(@Pattern("#[0-9a-fA-F]{8}") @NotNull String text) {
        if (text.length() != 9) {
            return null;
        }
        if (!text.startsWith("#")) {
            return null;
        }
        try {
            int value = Integer.parseInt(text.substring(1, 3), 16);
            int value2 = Integer.parseInt(text.substring(3, 5), 16);
            int value3 = Integer.parseInt(text.substring(5, 7), 16);
            int value4 = Integer.parseInt(text.substring(7, 9), 16);
            return new ShadowColorImpl(value4 << 24 | value << 16 | value2 << 8 | value3);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @NotNull
    default String asHexString() {
        int value2;
        int value3 = (value2 = this.value()) >> 24 & 0xFF;
        int value4 = value2 >> 16 & 0xFF;
        int value5 = value2 >> 8 & 0xFF;
        int value6 = value2 & 0xFF;
        return String.format("#%02X%02X%02X%02X", value4, value5, value6, value3);
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

    @Override
    @Range(from = 0L, to = 255L)
    default int alpha() {
        return this.value() >> 24 & 0xFF;
    }

    int value();

    @Override
    default void styleApply(Style.@NotNull Builder builder) {
        builder.shadowColor(this);
    }
}
