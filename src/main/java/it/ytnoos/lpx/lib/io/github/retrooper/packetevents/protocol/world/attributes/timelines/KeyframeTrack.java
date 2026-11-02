package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.easing.EasingType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.easing.EasingTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class KeyframeTrack<T> {

    private final List<Keyframe<T>> keyframes;

    private final EasingType easingType;

    public KeyframeTrack(List<Keyframe<T>> keyframes, EasingType easingType2) {
        this.keyframes = keyframes;
        this.easingType = easingType2;
    }

    public static <T> NbtMapCodec<KeyframeTrack<T>> mapCodec(NbtCodec<T> nbtCodec) {
        final NbtCodec<
                        List<
                                it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world
                                                .attributes.timelines.Keyframe<
                                        T>>>
                nbtCodec2 = Keyframe.codec(nbtCodec).applyList();
        return new NbtMapCodec<KeyframeTrack<T>>() {

            public KeyframeTrack<T> decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                    throws NbtCodecException {
                List result =
                        ((List) nBTCompound.getOrThrow("keyframes", nbtCodec2, packetWrapper));
                EasingType or =
                        nBTCompound.getOr(
                                "ease", EasingType.CODEC, EasingTypes.LINEAR, packetWrapper);
                return new KeyframeTrack<>(result, or);
            }

            public void encode(
                    NBTCompound nBTCompound,
                    PacketWrapper<?> packetWrapper,
                    KeyframeTrack<T> result)
                    throws NbtCodecException {
                nBTCompound.set("keyframes", result.keyframes, nbtCodec2, packetWrapper);
                if (result.easingType != EasingTypes.LINEAR) {
                    nBTCompound.set("ease", result.easingType, EasingType.CODEC, packetWrapper);
                }
            }
        };
    }

    public List<Keyframe<T>> getKeyframes() {
        return this.keyframes;
    }

    public EasingType getEasingType() {
        return this.easingType;
    }
}
