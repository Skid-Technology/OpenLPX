package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleSpellData extends ParticleData {

    private Color color;

    private float power;

    public ParticleSpellData(Color color2, float power) {
        this.color = color2;
        this.power = power;
    }

    public static ParticleSpellData read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            Color color = Color.read(packetWrapper);
            float value = packetWrapper.readFloat();
            return new ParticleSpellData(color, value);
        } else {
            return new ParticleSpellData(Color.WHITE, 1.0F);
        }
    }

    public static void write(PacketWrapper<?> packetWrapper, ParticleSpellData particleSpellData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            Color.write(packetWrapper, particleSpellData.color);
            packetWrapper.writeFloat(particleSpellData.power);
        }
    }

    @Internal
    public static ParticleSpellData decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        Color or =
                nBTCompound.getOr(
                        "color",
                        Color.CODEC,
                        Color.WHITE,
                        PacketWrapper.createDummyWrapper(clientVersion));
        float value = nBTCompound.getNumberTagValueOrDefault("power", 1.0F).floatValue();
        return new ParticleSpellData(or, value);
    }

    @Internal
    public static void encode(
            ParticleSpellData particleSpellData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_21_9)) {
            if (!Color.WHITE.equals(particleSpellData.color)) {
                nBTCompound.set(
                        "color",
                        particleSpellData.color,
                        Color.CODEC,
                        PacketWrapper.createDummyWrapper(clientVersion));
            }
            if (particleSpellData.power != 1.0F) {
                nBTCompound.setTag("power", new NBTFloat(particleSpellData.power));
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color2) {
        this.color = color2;
    }

    public float getPower() {
        return this.power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        }
        ParticleSpellData particleSpellData;
        return Float.compare((particleSpellData = (ParticleSpellData) result).power, this.power)
                        != 0
                ? false
                : this.color.equals(particleSpellData.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.color, this.power);
    }
}
