package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.easing;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Either;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface EasingType extends MappedEntity {

    NbtCodec<EasingType> CODEC =
            NbtCodecs.either(
                            NbtCodecs.forRegistry(EasingTypes.getRegistry()),
                            CubicBezierEasingType.CODEC)
                    .apply(
                            Either::unwrap,
                            result ->
                                    result instanceof CubicBezierEasingType
                                            ? Either.createRight((CubicBezierEasingType) result)
                                            : Either.createLeft(result));

    float apply(float value);
}
