package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class SulfurCubeKnockbackModifiers {

    public static final NbtCodec<SulfurCubeKnockbackModifiers> CODEC =
            (new NbtMapCodec<SulfurCubeKnockbackModifiers>() {

                        public SulfurCubeKnockbackModifiers decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            float value =
                                    nBTCompound
                                            .getNumberTagValueOrThrow("horizontal_power")
                                            .floatValue();
                            float value2 =
                                    nBTCompound
                                            .getNumberTagValueOrThrow("vertical_power")
                                            .floatValue();
                            return new SulfurCubeKnockbackModifiers(value, value2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                SulfurCubeKnockbackModifiers sulfurCubeKnockbackModifiers)
                                throws NbtCodecException {
                            nBTCompound.setTag(
                                    "horizontal_power",
                                    new NBTFloat(sulfurCubeKnockbackModifiers.horizontalPower));
                            nBTCompound.setTag(
                                    "vertical_power",
                                    new NBTFloat(sulfurCubeKnockbackModifiers.verticalPower));
                        }
                    })
                    .codec();

    private final float horizontalPower;

    private final float verticalPower;

    public SulfurCubeKnockbackModifiers(float horizontalPower, float verticalPower) {
        this.horizontalPower = horizontalPower;
        this.verticalPower = verticalPower;
    }

    public float getHorizontalPower() {
        return this.horizontalPower;
    }

    public float getVerticalPower() {
        return this.verticalPower;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        }
        SulfurCubeKnockbackModifiers sulfurCubeKnockbackModifiers;
        return Float.compare(
                                (sulfurCubeKnockbackModifiers =
                                                (SulfurCubeKnockbackModifiers) result)
                                        .horizontalPower,
                                this.horizontalPower)
                        != 0
                ? false
                : Float.compare(sulfurCubeKnockbackModifiers.verticalPower, this.verticalPower)
                        == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.horizontalPower, this.verticalPower);
    }
}
