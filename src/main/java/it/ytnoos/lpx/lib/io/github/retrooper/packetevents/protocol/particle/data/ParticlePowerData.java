package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticlePowerData extends ParticleData {

    private float power;

    public ParticlePowerData(float power) {
        this.power = power;
    }

    public static ParticlePowerData read(PacketWrapper<?> packetWrapper) {
        float value =
                packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_9)
                        ? 1.0F
                        : packetWrapper.readFloat();
        return new ParticlePowerData(value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ParticlePowerData particlePowerData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            packetWrapper.writeFloat(particlePowerData.power);
        }
    }

    @Internal
    public static ParticlePowerData decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        float value = nBTCompound.getNumberTagValueOrDefault("power", 1.0F).floatValue();
        return new ParticlePowerData(value);
    }

    @Internal
    public static void encode(
            ParticlePowerData particlePowerData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_21_9)
                && particlePowerData.power != 1.0F) {
            nBTCompound.setTag("power", new NBTFloat(particlePowerData.power));
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public float getPower() {
        return this.power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object result) {
        return result != null && this.getClass() == result.getClass()
                ? Float.compare(((ParticlePowerData) result).power, this.power) == 0
                : false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.power);
    }
}
