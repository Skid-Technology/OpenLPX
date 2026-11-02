package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.easing;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class EasingFunctions {

    private EasingFunctions() {}

    public static float constant(float value) {
        return 0.0F;
    }

    public static float linear(float value) {
        return value;
    }

    public static float inBack(float value) {
        float value2 = 1.70158F + 1.0F;
        return value * value * (value2 * value - (float) 1.70158F);
    }

    public static float inBounce(float value) {
        return 1.0F - outBounce(1.0F - value);
    }

    public static float inCubic(float value) {
        return value * value * value;
    }

    public static float inElastic(float value) {
        if (value == 0.0F) {
            return 0.0F;
        } else {
            return value == 1.0F
                    ? 1.0F
                    : (float)
                            (-Math.pow(2.0, 10.0 * value - 10.0)
                                    * Math.sin(
                                            (value * 10.0 - 10.75)
                                                    * (float) (Math.PI * 2.0 / 3.0)));
        }
    }

    public static float inExpo(float value) {
        return value == 0.0F ? 0.0F : (float) Math.pow(2.0, 10.0 * value - 10.0);
    }

    public static float inQuart(float value) {
        return value * value * (value * value);
    }

    public static float inQuint(float value) {
        return value * value * (value * value) * value;
    }

    public static float inSine(float value) {
        return 1.0F - (float) Math.cos(value * (float) (Math.PI / 2));
    }

    public static float inOutBounce(float value) {
        return value < 0.5F
                ? (1.0F - outBounce(1.0F - 2.0F * value)) / 2.0F
                : (1.0F + outBounce(2.0F * value - 1.0F)) / 2.0F;
    }

    public static float inOutCirc(float value) {
        return value < 0.5F
                ? (float) ((1.0 - Math.sqrt(1.0 - Math.pow(2.0 * value, 2.0))) / 2.0)
                : (float) ((Math.sqrt(1.0 - Math.pow(-2.0 * value + 2.0, 2.0)) + 1.0) / 2.0);
    }

    public static float inOutCubic(float value) {
        return value < 0.5F
                ? 4.0F * (value * value * value)
                : (float) (1.0 - Math.pow(-2.0 * value + 2.0, 3.0) / 2.0);
    }

    public static float inOutQuad(float value) {
        return value < 0.5F
                ? 2.0F * (value * value)
                : (float) (1.0 - Math.pow(-2.0 * value + 2.0, 2.0) / 2.0);
    }

    public static float inOutQuart(float value) {
        return value < 0.5F
                ? 8.0F * (value * value * (value * value))
                : (float) (1.0 - Math.pow(-2.0 * value + 2.0, 4.0) / 2.0);
    }

    public static float inOutQuint(float value) {
        return value < 0.5
                ? 16.0F * value * value * value * value * value
                : (float) (1.0 - Math.pow(-2.0 * value + 2.0, 5.0) / 2.0);
    }

    public static float outBounce(float value) {
        float value2 = 7.5625F;
        float value3 = 2.75F;
        if (value < 1.0F / value3) {
            return value2 * (value * value);
        } else if (value < 2.0F / value3) {
            return value2 * MathUtil.square(value - 1.5F / value3) + 0.75F;
        } else {
            return value < 2.5F / value3
                    ? value2 * MathUtil.square(value - 2.25F / value3) + 0.9375F
                    : value2 * MathUtil.square(value - 2.625F / value3) + 0.984375F;
        }
    }

    public static float outElastic(float value) {
        if (value == 0.0F) {
            return 0.0F;
        } else {
            return value == 1.0F
                    ? 1.0F
                    : (float)
                            (Math.pow(2.0, -10.0 * value)
                                            * Math.sin(
                                                    (value * 10.0 - 0.75)
                                                            * (float) (Math.PI * 2.0 / 3.0))
                                    + 1.0);
        }
    }

    public static float outExpo(float value) {
        return value == 1.0F ? 1.0F : 1.0F - (float) Math.pow(2.0, -10.0 * value);
    }

    public static float outQuad(float value) {
        return 1.0F - MathUtil.square(1.0F - value);
    }

    public static float outQuint(float value) {
        return 1.0F - (float) Math.pow(1.0 - value, 5.0);
    }

    public static float outSine(float value) {
        return (float) Math.sin(value * (float) (Math.PI / 2));
    }

    public static float inOutSine(float value) {
        return -((float) Math.cos((float) Math.PI * value) - 1.0F) / 2.0F;
    }

    public static float outBack(float value) {
        return 1.0F
                + (float) 2.70158F * MathUtil.cube(value - 1.0F)
                + (float) 1.70158F * MathUtil.square(value - 1.0F);
    }

    public static float outQuart(float value) {
        return 1.0F - MathUtil.square(MathUtil.square(1.0F - value));
    }

    public static float outCubic(float value) {
        return 1.0F - MathUtil.cube(1.0F - value);
    }

    public static float inOutExpo(float value) {
        if (value == 0.0F) {
            return 0.0F;
        } else if (value == 1.0F) {
            return 1.0F;
        } else {
            return value < 0.5F
                    ? (float) (Math.pow(2.0, 20.0 * value - 10.0) / 2.0)
                    : (float) ((2.0 - Math.pow(2.0, -20.0 * value + 10.0)) / 2.0);
        }
    }

    public static float inQuad(float value) {
        return value * value;
    }

    public static float outCirc(float value) {
        return (float) Math.sqrt(1.0F - MathUtil.square(value - 1.0F));
    }

    public static float inOutElastic(float value) {
        if (value == 0.0F) {
            return 0.0F;
        }
        if (value == 1.0F) {
            return 1.0F;
        }
        double value2 = Math.sin((20.0 * value - 11.125) * (float) Math.PI * 4.0F / 9.0F);
        return value < 0.5F
                ? (float) (-(Math.pow(2.0, 20.0 * value - 10.0) * value2) / 2.0)
                : (float) (Math.pow(2.0, -20.0 * value + 10.0) * value2 / 2.0 + 1.0);
    }

    public static float inCirc(float value) {
        return (float) (-Math.sqrt(1.0F - value * value)) + 1.0F;
    }

    public static float inOutBack(float value) {
        float value2 = 1.70158F * 1.525F;
        if (value < 0.5F) {
            return 4.0F * value * value * (2.0F * (value2 + 1.0F) * value - value2) / 2.0F;
        }
        float value3;
        float value4 = value3 = 2.0F * value - 2.0F;
        return (value4 * value4 * ((value2 + 1.0F) * value3 + value2) + 2.0F) / 2.0F;
    }
}
