package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Inserting;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Stream;

public final class TransitionTag implements Inserting, Examinable {

    public static final String TRANSITION = "transition";

    private final TextColor[] colors;

    private final float phase;

    private final boolean negativePhase;

    static final TagResolver RESOLVER = TagResolver.resolver("transition", TransitionTag::create);

    static Tag create(ArgumentQueue argumentQueue, Context context) {
        float value2 = 0.0F;
        List<TextColor> values;
        if (!argumentQueue.hasNext()) {
            values = Collections.emptyList();
        } else {
            values = new ArrayList();
            while (true) {
                if (argumentQueue.hasNext()) {
                    Tag.Argument argument;
                    String text2;
                    TextColor textColor;
                    if ((textColor =
                                    ColorTagResolver.resolveColorOrNull(
                                            text2 = (argument = argumentQueue.pop()).value()))
                            != null) {
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
                    if ((value2 = (float) optionalDouble.getAsDouble()) < -1.0F || value2 > 1.0F) {
                        throw context.newException(
                                String.format(
                                        "Gradient phase is out of range (%s). Must be in the range [-1.0f, 1.0f] (inclusive).",
                                        value2),
                                argumentQueue);
                    }
                }
                if (values.size() < 2) {
                    throw context.newException(
                            "Invalid transition, not enough colors. Transitions must have at least two colors.",
                            argumentQueue);
                }
                break;
            }
        }
        return new TransitionTag(value2, values);
    }

    private TransitionTag(float phase, List<TextColor> values) {
        if (phase < 0.0F) {
            this.negativePhase = true;
            this.phase = 1.0F + phase;
            Collections.reverse(values);
        } else {
            this.negativePhase = false;
            this.phase = phase;
        }
        if (values.isEmpty()) {
            this.colors = new TextColor[] {TextColor.color(16777215), TextColor.color(0)};
        } else {
            this.colors = values.toArray(new TextColor[0]);
        }
    }

    @NotNull
    @Override
    public Component value() {
        return Component.text("", this.color());
    }

    private TextColor color() {
        float value = 1.0F / (this.colors.length - 1);
        for (int index = 1; index < this.colors.length; index++) {
            float value2;
            if ((value2 = index * value) >= this.phase) {
                float value3 = 1.0F + (this.phase - value2) * (this.colors.length - 1);
                if (this.negativePhase) {
                    return TextColor.lerp(
                            1.0F - value3, this.colors[index], this.colors[index - 1]);
                }
                return TextColor.lerp(value3, this.colors[index - 1], this.colors[index]);
            }
        }
        return this.colors[0];
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("phase", this.phase),
                ExaminableProperty.of("colors", this.colors));
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            TransitionTag transitionTag = (TransitionTag) result;
            return this.phase == transitionTag.phase
                    && Arrays.equals(this.colors, transitionTag.colors);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = Objects.hash(this.phase);
        return 31 * value + Arrays.hashCode(this.colors);
    }
}
