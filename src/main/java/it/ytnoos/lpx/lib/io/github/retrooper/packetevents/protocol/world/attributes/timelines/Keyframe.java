package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class Keyframe<T> {

    private final int ticks;

    private final T value;

    public Keyframe(int ticks, T t) {
        this.ticks = ticks;
        this.value = (T) t;
    }

    public static <T> NbtCodec<Keyframe<T>> codec(final NbtCodec<T> nbtCodec) {
        return (new NbtMapCodec<Keyframe<T>>() {

                    public Keyframe<T> decode(
                            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                            throws NbtCodecException {
                        int asInt = nBTCompound.getNumberTagOrThrow("ticks").getAsInt();
                        T orThrow = nBTCompound.getOrThrow("value", nbtCodec, packetWrapper);
                        return new Keyframe<>(asInt, (T) orThrow);
                    }

                    public void encode(
                            NBTCompound nBTCompound,
                            PacketWrapper<?> packetWrapper,
                            Keyframe<T> asInt)
                            throws NbtCodecException {
                        nBTCompound.setTag("ticks", new NBTInt(asInt.ticks));
                        nBTCompound.set("value", asInt.value, nbtCodec, packetWrapper);
                    }
                })
                .codec();
    }

    public int getTicks() {
        return this.ticks;
    }

    public T getValue() {
        return this.value;
    }
}
