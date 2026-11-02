package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class BlendToGrayArgument {

    public static final NbtCodec<BlendToGrayArgument> CODEC =
            (new NbtMapCodec<BlendToGrayArgument>() {

                        public BlendToGrayArgument decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            float value =
                                    nBTCompound.getNumberTagValueOrThrow("brightness").floatValue();
                            float value2 =
                                    nBTCompound.getNumberTagValueOrThrow("factor").floatValue();
                            return new BlendToGrayArgument(value, value2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                BlendToGrayArgument blendToGrayArgument)
                                throws NbtCodecException {
                            nBTCompound.setTag(
                                    "brightness", new NBTFloat(blendToGrayArgument.brightness));
                            nBTCompound.setTag("factor", new NBTFloat(blendToGrayArgument.factor));
                        }
                    })
                    .codec();

    private final float brightness;

    private final float factor;

    public BlendToGrayArgument(float brightness, float factor) {
        this.brightness = brightness;
        this.factor = factor;
    }

    public AlphaColor blend(AlphaColor alphaColor) {
        AlphaColor alphaColor2 = alphaColor.asGrayscale().scale(this.brightness);
        return alphaColor.lerpSrgb(alphaColor2, this.factor);
    }

    public Color blend(Color color) {
        Color color2 = color.asGrayscale().scale(this.brightness);
        return color.lerpSrgb(color2, this.factor);
    }

    public float getBrightness() {
        return this.brightness;
    }

    public float getFactor() {
        return this.factor;
    }
}
