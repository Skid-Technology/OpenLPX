package it.ytnoos.lpx.lib.kyori.adventure.util;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.stream.Stream;

public interface HSVLike extends Examinable {

    @NotNull
    static HSVLike hsvLike(float value, float value2, float value3) {
        return new HSVLikeImpl(value, value2, value3);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static HSVLike of(float value, float value2, float value3) {
        return new HSVLikeImpl(value, value2, value3);
    }

    @NotNull
    static HSVLike fromRGB(
            @Range(from = 0L, to = 255L) int value,
            @Range(from = 0L, to = 255L) int value2,
            @Range(from = 0L, to = 255L) int value3) {
        float value4 = value / 255.0F;
        float value5 = value2 / 255.0F;
        float value6 = value3 / 255.0F;
        float value7 = Math.min(value4, Math.min(value5, value6));
        float value8;
        float value9 = (value8 = Math.max(value4, Math.max(value5, value6))) - value7;
        float value10;
        if (value8 != 0.0F) {
            value10 = value9 / value8;
        } else {
            value10 = 0.0F;
        }
        if (value10 == 0.0F) {
            return new HSVLikeImpl(0.0F, value10, value8);
        }
        float value11;
        if (value4 == value8) {
            value11 = (value5 - value6) / value9;
        } else if (value5 == value8) {
            value11 = 2.0F + (value6 - value4) / value9;
        } else {
            value11 = 4.0F + (value4 - value5) / value9;
        }
        if ((value11 = value11 * 60.0F) < 0.0F) {
            value11 += 360.0F;
        }
        return new HSVLikeImpl(value11 / 360.0F, value10, value8);
    }

    float h();

    float s();

    float v();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("h", this.h()),
                ExaminableProperty.of("s", this.s()),
                ExaminableProperty.of("v", this.v()));
    }
}
