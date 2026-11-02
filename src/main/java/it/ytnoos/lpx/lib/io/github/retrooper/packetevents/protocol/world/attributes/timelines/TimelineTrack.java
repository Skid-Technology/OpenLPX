package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class TimelineTrack<T, A> {

    private final AttributeModifier<T, A> modifier;

    private final KeyframeTrack<A> argumentTrack;

    public TimelineTrack(
            AttributeModifier<T, A> attributeModifier, KeyframeTrack<A> keyframeTrack) {
        this.modifier = attributeModifier;
        this.argumentTrack = keyframeTrack;
    }

    public static <T> NbtCodec<TimelineTrack<T, ?>> codec(
            final EnvironmentAttribute<T> environmentAttribute) {
        final NbtCodec<AttributeModifier<T, ?>> modifierCodec =
                environmentAttribute.getType().getModifierCodec();
        return (new NbtMapCodec<TimelineTrack<T, ?>>() {

                    public TimelineTrack<T, ?> decode(
                            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                            throws NbtCodecException {
                        AttributeModifier<T, ?> or =
                                nBTCompound.getOr(
                                        "modifier",
                                        modifierCodec,
                                        AttributeModifier.override(),
                                        packetWrapper);
                        return ((TimelineTrack<T, ?>)
                                TimelineTrack.codec(environmentAttribute, or)
                                        .decode(nBTCompound, packetWrapper));
                    }

                    public void encode(
                            NBTCompound nBTCompound,
                            PacketWrapper<?> packetWrapper,
                            TimelineTrack<T, ?> or)
                            throws NbtCodecException {
                        this.encode0(nBTCompound, packetWrapper, or);
                    }

                    private <A> void encode0(
                            NBTCompound nBTCompound,
                            PacketWrapper<?> packetWrapper,
                            TimelineTrack<T, A> or)
                            throws NbtCodecException {
                        if (or.modifier != AttributeModifier.override()) {
                            nBTCompound.set("modifier", or.modifier, modifierCodec, packetWrapper);
                        }
                        TimelineTrack.codec(environmentAttribute, or.modifier)
                                .encode(nBTCompound, packetWrapper, or);
                    }
                })
                .codec();
    }

    public static <T, A> NbtMapCodec<TimelineTrack<T, A>> codec(
            EnvironmentAttribute<T> environmentAttribute,
            AttributeModifier<T, A> attributeModifier) {
        NbtMapCodec<KeyframeTrack<A>> nbtMapCodec =
                KeyframeTrack.mapCodec(attributeModifier.argumentCodec(environmentAttribute));
        return nbtMapCodec.apply(
                result -> new TimelineTrack<T, A>(attributeModifier, result),
                TimelineTrack::getArgumentTrack);
    }

    public AttributeModifier<T, A> getModifier() {
        return this.modifier;
    }

    public KeyframeTrack<A> getArgumentTrack() {
        return this.argumentTrack;
    }
}
