package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.easing;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.easing.CubicBezierControls;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.easing.CubicCurve;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class CubicBezierEasingType extends AbstractMappedEntity implements EasingType {

    public static final NbtCodec<CubicBezierEasingType> CODEC =
            (new NbtMapCodec<CubicBezierEasingType>() {

                        public CubicBezierEasingType decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            CubicBezierControls orThrow =
                                    nBTCompound.getOrThrow(
                                            "cubic_bezier",
                                            CubicBezierControls.CODEC,
                                            packetWrapper);
                            return new CubicBezierEasingType(orThrow);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                CubicBezierEasingType cubicBezierEasingType)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "cubic_bezier",
                                    cubicBezierEasingType.controls,
                                    CubicBezierControls.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    private final CubicBezierControls controls;

    private final CubicCurve curveX;

    private final CubicCurve curveY;

    public CubicBezierEasingType(CubicBezierControls cubicBezierControls) {
        this(null, cubicBezierControls);
    }

    @Internal
    public CubicBezierEasingType(
            @Nullable TypesBuilderData typesBuilderData, CubicBezierControls cubicBezierControls) {
        super(typesBuilderData);
        this.controls = cubicBezierControls;
        this.curveX = cubicBezierControls.calcCurveX();
        this.curveY = cubicBezierControls.calcCurveY();
    }

    @Override
    public float apply(float value) {
        float value2 = value;
        float value3;
        for (int index = 0;
                index < 4 && !((value3 = this.curveX.calcSampleGradient(value2)) < 1.0E-5F);
                index++) {
            float value4 = this.curveX.calcSample(value2) - value;
            value2 -= value4 / value3;
        }
        return this.curveY.calcSample(value2);
    }
}
