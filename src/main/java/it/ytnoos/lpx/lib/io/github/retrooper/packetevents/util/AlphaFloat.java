package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class AlphaFloat {

    public static final NbtCodec<AlphaFloat> CODEC =
            new NbtCodec<AlphaFloat>() {

                public AlphaFloat decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    if (nBT instanceof NBTNumber) {
                        return new AlphaFloat(((NBTNumber) nBT).getAsFloat(), 1.0F);
                    }
                    NBTCompound nBTCompound;
                    float value =
                            (nBTCompound = nBT.castOrThrow(NBTCompound.class))
                                    .getNumberTagValueOrThrow("value")
                                    .floatValue();
                    float value2 =
                            nBTCompound.getNumberTagValueOrDefault("alpha", 1.0F).floatValue();
                    return new AlphaFloat(value, value2);
                }

                public NBT encode(PacketWrapper<?> packetWrapper, AlphaFloat alphaFloat)
                        throws NbtCodecException {
                    if (alphaFloat.alpha == 1.0F) {
                        return new NBTFloat(alphaFloat.value);
                    }
                    NBTCompound nBTCompound;
                    (nBTCompound = new NBTCompound())
                            .setTag("value", new NBTFloat(alphaFloat.value));
                    nBTCompound.setTag("alpha", new NBTFloat(alphaFloat.alpha));
                    return nBTCompound;
                }
            };

    private final float value;

    private final float alpha;

    public AlphaFloat(float value, float alpha) {
        this.value = value;
        this.alpha = alpha;
    }

    public float getValue() {
        return this.value;
    }

    public float getAlpha() {
        return this.alpha;
    }
}
