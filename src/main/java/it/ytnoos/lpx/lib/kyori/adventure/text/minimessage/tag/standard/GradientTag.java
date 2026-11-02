package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.stream.Stream;

class GradientTag extends AbstractColorChangingTag {

    private static final TextColor DEFAULT_WHITE = TextColor.color(16777215);

    private static final TextColor DEFAULT_BLACK = TextColor.color(0);

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    "gradient", GradientTag::create, AbstractColorChangingTag::claimComponent);

    private int index = 0;

    private double multiplier = 1.0;

    private final TextColor[] colors;

    @Range(from = -1L, to = 1L)
    double phase;

    private final boolean negativePhase;

    static Tag create(ArgumentQueue argumentQueue, Context context) {
        double value2 = 0.0;
        List<TextColor> values;
        if (!argumentQueue.hasNext()) {
            values = Collections.emptyList();
        } else {
            values = new ArrayList();
            while (true) {
                if (argumentQueue.hasNext()) {
                    Tag.Argument argument;
                    String text2;
                    TextColor textColor =
                            ColorTagResolver.resolveColorOrNull(
                                    text2 = (argument = argumentQueue.pop()).value());
                    if (textColor != null) {
                        values.add(textColor);
                        continue;
                    }
                    OptionalDouble optionalDouble;
                    if (argumentQueue.hasNext()
                            || !(optionalDouble = argument.asDouble()).isPresent()) {
                        throw context.newException(
                                String.format(
                                        "Unable to parse a color from '%s'. Please use named colors or hex (#RRGGBB) colors.",
                                        text2),
                                argumentQueue);
                    }
                    if ((value2 = optionalDouble.getAsDouble()) < -1.0 || value2 > 1.0) {
                        throw context.newException(
                                String.format(
                                        "Gradient phase is out of range (%s). Must be in the range [-1.0, 1.0] (inclusive).",
                                        value2),
                                argumentQueue);
                    }
                }
                if (values.size() == 1) {
                    throw context.newException(
                            "Invalid gradient, not enough colors. Gradients must have at least two colors.",
                            argumentQueue);
                }
                break;
            }
        }
        return new GradientTag(value2, values, context);
    }

    GradientTag(double phase, List<TextColor> values, Context context) {
        super(context);
        if (values.isEmpty()) {
            this.colors = new TextColor[] {DEFAULT_WHITE, DEFAULT_BLACK};
        } else {
            this.colors = values.toArray(new TextColor[0]);
        }
        if (phase < 0.0) {
            this.negativePhase = true;
            this.phase = 1.0 + phase;
            Collections.reverse(Arrays.asList(this.colors));
        } else {
            this.negativePhase = false;
            this.phase = phase;
        }
    }

    @Override
    protected void init() {
        this.multiplier =
                this.size() == 1 ? 0.0 : (double) (this.colors.length - 1) / (this.size() - 1);
        this.phase = this.phase * (this.colors.length - 1);
        this.index = 0;
    }

    @Override
    protected void advanceColor() {
        this.index++;
    }

    @Override
    protected TextColor color() {
        double value;
        int value2 = (int) Math.floor(value = this.index * this.multiplier + this.phase);
        int value3 = (int) Math.ceil(value) % this.colors.length;
        int value4 = value2 % this.colors.length;
        return TextColor.lerp((float) value - value2, this.colors[value4], this.colors[value3]);
    }

    @NotNull
    @Override
    protected Consumer<TokenEmitter> preserveData() {
        TextColor[] textColors;
        double value;
        if (this.negativePhase) {
            Collections.reverse(
                    Arrays.asList(textColors = Arrays.copyOf(this.colors, this.colors.length)));
            value = this.phase - 1.0;
        } else {
            textColors = this.colors;
            value = this.phase;
        }
        double value2 = value;
        TextColor[] textColors2 = textColors;
        return result -> {
            result.tag("gradient");
            if (textColors2.length != 2
                    || !textColors2[0].equals(DEFAULT_WHITE)
                    || !textColors2[1].equals(DEFAULT_BLACK)) {
                int value3 = textColors2.length;
                for (int index = 0; index < value3; index++) {
                    TextColor textColor = textColors2[index];
                    if (textColor instanceof NamedTextColor) {
                        result.argument(
                                NamedTextColor.NAMES.keyOrThrow((NamedTextColor) textColor));
                    } else {
                        result.argument(textColor.asHexString());
                    }
                }
            }
            if (value2 != 0.0) {
                result.argument(Double.toString(value2));
            }
        };
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("phase", this.phase),
                ExaminableProperty.of("colors", this.colors));
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            GradientTag gradientTag = (GradientTag) result;
            return this.index == gradientTag.index
                    && this.phase == gradientTag.phase
                    && Arrays.equals(this.colors, gradientTag.colors);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = Objects.hash(this.index, this.phase);
        return 31 * value + Arrays.hashCode(this.colors);
    }
}
