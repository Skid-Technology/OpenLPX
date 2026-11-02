package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.adventure.util.HSVLike;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class RainbowTag extends AbstractColorChangingTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    "rainbow", RainbowTag::create, AbstractColorChangingTag::claimComponent);

    private final boolean reversed;

    private final double dividedPhase;

    private int colorIndex = 0;

    static Tag create(ArgumentQueue argumentQueue, Context context) {
        boolean matches = false;
        int value2 = 0;
        if (argumentQueue.hasNext()) {
            String text;
            if ((text = argumentQueue.pop().value()).startsWith("!")) {
                matches = true;
                text = text.substring("!".length());
            }
            if (text.length() > 0) {
                try {
                    value2 = Integer.parseInt(text);
                } catch (NumberFormatException numberFormatException) {
                    throw context.newException("Expected phase, got " + text);
                }
            }
        }
        return new RainbowTag(matches, value2, context);
    }

    private RainbowTag(boolean reversed, int value, Context context) {
        super(context);
        this.reversed = reversed;
        this.dividedPhase = value / 10.0;
    }

    @Override
    protected void init() {
        if (this.reversed) {
            this.colorIndex = this.size() - 1;
        }
    }

    @Override
    protected void advanceColor() {
        if (this.reversed) {
            if (this.colorIndex == 0) {
                this.colorIndex = this.size() - 1;
            } else {
                this.colorIndex--;
            }
        } else {
            this.colorIndex++;
        }
    }

    @Override
    protected TextColor color() {
        return TextColor.color(
                HSVLike.hsvLike(
                        (float) (((float) this.colorIndex / this.size() + this.dividedPhase) % 1.0),
                        1.0F,
                        1.0F));
    }

    @NotNull
    @Override
    protected Consumer<TokenEmitter> preserveData() {
        boolean matches = this.reversed;
        int value = (int) Math.round(this.dividedPhase * 10.0);
        return result -> {
            result.tag("rainbow");
            if (matches && value != 0) {
                result.argument("!" + value);
            } else if (matches) {
                result.argument("!");
            } else if (value != 0) {
                result.argument(Integer.toString(value));
            }
        };
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("phase", this.dividedPhase));
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            RainbowTag rainbowTag = (RainbowTag) result;
            return this.colorIndex == rainbowTag.colorIndex
                    && this.dividedPhase == rainbowTag.dividedPhase;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colorIndex, this.dividedPhase);
    }
}
