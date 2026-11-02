package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleSculkChargeData extends ParticleData {

    private float roll;

    public ParticleSculkChargeData(float roll) {
        this.roll = roll;
    }

    public float getRoll() {
        return this.roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public static ParticleSculkChargeData read(PacketWrapper<?> packetWrapper) {
        return new ParticleSculkChargeData(packetWrapper.readFloat());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleSculkChargeData particleSculkChargeData) {
        packetWrapper.writeFloat(particleSculkChargeData.getRoll());
    }

    public static ParticleSculkChargeData decode(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        float asFloat = nBTCompound.getNumberTagOrThrow("roll").getAsFloat();
        return new ParticleSculkChargeData(asFloat);
    }

    public static void encode(
            ParticleSculkChargeData particleSculkChargeData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        nBTCompound.setTag("roll", new NBTFloat(particleSculkChargeData.roll));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object result) {
        return result != null && this.getClass() == result.getClass()
                ? Float.compare(((ParticleSculkChargeData) result).roll, this.roll) == 0
                : false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.roll);
    }
}
