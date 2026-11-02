package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ParticleGeyserData extends ParticleData {

    public static final NbtMapCodec<ParticleGeyserData> MAP_CODEC =
            new NbtMapCodec<ParticleGeyserData>() {

                public ParticleGeyserData decode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    int asInt = nBTCompound.getNumberTagOrThrow("water_blocks").getAsInt();
                    return new ParticleGeyserData(asInt);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        ParticleGeyserData particleGeyserData)
                        throws NbtCodecException {
                    nBTCompound.setTag("water_blocks", new NBTInt(particleGeyserData.waterBlocks));
                }
            };

    private final int waterBlocks;

    public ParticleGeyserData(int waterBlocks) {
        this.waterBlocks = waterBlocks;
    }

    public static ParticleGeyserData read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readInt();
        return new ParticleGeyserData(value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleGeyserData particleGeyserData) {
        packetWrapper.writeInt(particleGeyserData.waterBlocks);
    }

    public int getWaterBlocks() {
        return this.waterBlocks;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleGeyserData particleGeyserData = (ParticleGeyserData) result;
            return this.waterBlocks == particleGeyserData.waterBlocks;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.waterBlocks);
    }
}
