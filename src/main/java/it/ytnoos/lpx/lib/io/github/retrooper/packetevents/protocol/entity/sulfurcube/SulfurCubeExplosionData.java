package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class SulfurCubeExplosionData {

    public static final NbtCodec<SulfurCubeExplosionData> CODEC =
            (new NbtMapCodec<SulfurCubeExplosionData>() {

                        public SulfurCubeExplosionData decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            int value = nBTCompound.getNumberTagValueOrThrow("power").intValue();
                            boolean booleanOrThrow = nBTCompound.getBooleanOrThrow("causes_fire");
                            int value2 = nBTCompound.getNumberTagValueOrThrow("fuse").intValue();
                            return new SulfurCubeExplosionData(value, booleanOrThrow, value2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                SulfurCubeExplosionData sulfurCubeExplosionData)
                                throws NbtCodecException {
                            nBTCompound.setTag("power", new NBTInt(sulfurCubeExplosionData.power));
                            nBTCompound.setTag(
                                    "causes_fire", new NBTByte(sulfurCubeExplosionData.causesFire));
                            nBTCompound.setTag("fuse", new NBTInt(sulfurCubeExplosionData.fuse));
                        }
                    })
                    .codec();

    private final int power;

    private final boolean causesFire;

    private final int fuse;

    public SulfurCubeExplosionData(int power, boolean causesFire, int fuse) {
        this.power = power;
        this.causesFire = causesFire;
        this.fuse = fuse;
    }

    public int getPower() {
        return this.power;
    }

    public boolean isCausesFire() {
        return this.causesFire;
    }

    public int getFuse() {
        return this.fuse;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            SulfurCubeExplosionData sulfurCubeExplosionData = (SulfurCubeExplosionData) result;
            if (this.power != sulfurCubeExplosionData.power) {
                return false;
            } else {
                return this.causesFire != sulfurCubeExplosionData.causesFire
                        ? false
                        : this.fuse == sulfurCubeExplosionData.fuse;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.power, this.causesFire, this.fuse);
    }
}
