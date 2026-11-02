package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.AlphaFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface FloatModifier<A> extends AttributeModifier<Float, A> {

    FloatModifier<AlphaFloat> ALPHA_BLEND =
            new FloatModifier<AlphaFloat>() {

                public Float apply(Float value, AlphaFloat alphaFloat) {
                    return MathUtil.lerp(alphaFloat.getAlpha(), value, alphaFloat.getValue());
                }

                @Override
                public NbtCodec<AlphaFloat> argumentCodec(
                        EnvironmentAttribute<Float> environmentAttribute) {
                    return AlphaFloat.CODEC;
                }
            };

    FloatModifier<Float> ADD =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .FloatModifier.Simple)
                    Float::sum);

    FloatModifier<Float> SUBTRACT = (FloatModifier.Simple) (result, result2) -> result - result2;

    FloatModifier<Float> MULTIPLY = (FloatModifier.Simple) (result, result2) -> result * result2;

    FloatModifier<Float> MINIMUM =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .FloatModifier.Simple)
                    Math::min);

    FloatModifier<Float> MAXIMUM =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .FloatModifier.Simple)
                    Math::max);

    @FunctionalInterface
    interface Simple extends FloatModifier<Float> {

        @Override
        default NbtCodec<Float> argumentCodec(EnvironmentAttribute<Float> environmentAttribute) {
            return NbtCodecs.FLOAT;
        }
    }
}
