package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleShriekData extends ParticleData {

    private int delay;

    public ParticleShriekData(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public static ParticleShriekData read(PacketWrapper<?> packetWrapper) {
        return new ParticleShriekData(packetWrapper.readVarInt());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleShriekData particleShriekData) {
        packetWrapper.writeVarInt(particleShriekData.getDelay());
    }

    public static ParticleShriekData decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        int asInt = nBTCompound.getNumberTagOrThrow("delay").getAsInt();
        return new ParticleShriekData(asInt);
    }

    public static void encode(
            ParticleShriekData particleShriekData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        nBTCompound.setTag("delay", new NBTInt(particleShriekData.delay));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleShriekData particleShriekData = (ParticleShriekData) result;
            return this.delay == particleShriekData.delay;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delay);
    }
}
