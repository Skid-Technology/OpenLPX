package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ParticleGeyserBaseData extends ParticleData {

    public static final NbtMapCodec<ParticleGeyserBaseData> MAP_CODEC =
            new NbtMapCodec<ParticleGeyserBaseData>() {

                public ParticleGeyserBaseData decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int asInt = nBTCompound.getNumberTagOrThrow("water_blocks").getAsInt();
                    float asFloat =
                            nBTCompound.getNumberTagOrThrow("burst_impulse_base").getAsFloat();
                    return new ParticleGeyserBaseData(asInt, asFloat);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ParticleGeyserBaseData particleGeyserBaseData)
                        throws NbtCodecException {
                    nBTCompound.setTag(
                            "water_blocks", new NBTInt(particleGeyserBaseData.waterBlocks));
                    nBTCompound.setTag(
                            "burst_impulse_base",
                            new NBTFloat(particleGeyserBaseData.burstImpulseBase));
                }
            };

    private final int waterBlocks;

    private final float burstImpulseBase;

    public ParticleGeyserBaseData(int waterBlocks, float burstImpulseBase) {
        this.waterBlocks = waterBlocks;
        this.burstImpulseBase = burstImpulseBase;
    }

    public static ParticleGeyserBaseData read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readInt();
        float value2 = packetWrapper.readFloat();
        return new ParticleGeyserBaseData(value, value2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleGeyserBaseData particleGeyserBaseData) {
        packetWrapper.writeInt(particleGeyserBaseData.waterBlocks);
        packetWrapper.writeFloat(particleGeyserBaseData.burstImpulseBase);
    }

    public int getWaterBlocks() {
        return this.waterBlocks;
    }

    public float getBurstImpulseBase() {
        return this.burstImpulseBase;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleGeyserBaseData particleGeyserBaseData = (ParticleGeyserBaseData) result;
            return this.waterBlocks != particleGeyserBaseData.waterBlocks
                    ? false
                    : Float.compare(particleGeyserBaseData.burstImpulseBase, this.burstImpulseBase)
                            == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.waterBlocks, this.burstImpulseBase);
    }
}
