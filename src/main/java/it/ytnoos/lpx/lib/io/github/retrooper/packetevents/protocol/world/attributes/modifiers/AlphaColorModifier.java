package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface AlphaColorModifier<A> extends AttributeModifier<AlphaColor, A> {

    AlphaColorModifier<AlphaColor> ALPHA_BLEND =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .AlphaColorModifier.ArgbModifier)
                    Color::blendWith);

    AlphaColorModifier<Color> ADD =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .AlphaColorModifier.RgbModifier)
                    AlphaColor::plus);

    AlphaColorModifier<Color> SUBTRACT =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .AlphaColorModifier.RgbModifier)
                    AlphaColor::minus);

    AlphaColorModifier<AlphaColor> MULTIPLY =
            ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers
                            .AlphaColorModifier.ArgbModifier)
                    AlphaColor::times);

    AlphaColorModifier<BlendToGrayArgument> BLEND_TO_GRAY =
            new AlphaColorModifier<BlendToGrayArgument>() {

                public AlphaColor apply(
                        AlphaColor alphaColor, BlendToGrayArgument blendToGrayArgument) {
                    return blendToGrayArgument.blend(alphaColor);
                }

                @Override
                public NbtCodec<BlendToGrayArgument> argumentCodec(
                        EnvironmentAttribute<AlphaColor> environmentAttribute) {
                    return BlendToGrayArgument.CODEC;
                }
            };

    @FunctionalInterface
    interface ArgbModifier extends AlphaColorModifier<AlphaColor> {

        @Override
        default NbtCodec<AlphaColor> argumentCodec(
                EnvironmentAttribute<AlphaColor> environmentAttribute) {
            return NbtCodecs.ARGB_COLOR;
        }
    }

    @FunctionalInterface
    interface RgbModifier extends AlphaColorModifier<Color> {

        @Override
        default NbtCodec<Color> argumentCodec(
                EnvironmentAttribute<AlphaColor> environmentAttribute) {
            return NbtCodecs.RGB_COLOR;
        }
    }
}
