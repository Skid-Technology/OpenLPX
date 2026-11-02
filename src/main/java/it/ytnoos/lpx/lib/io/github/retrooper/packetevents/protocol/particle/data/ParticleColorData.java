package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleColorData extends ParticleData {

    private AlphaColor color;

    public ParticleColorData(int value) {
        this(new AlphaColor(value));
    }

    public ParticleColorData(AlphaColor alphaColor) {
        this.color = alphaColor;
    }

    public static ParticleColorData read(PacketWrapper<?> packetWrapper) {
        int value =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)
                        ? packetWrapper.readInt()
                        : 0;
        return new ParticleColorData(value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ParticleColorData particleColorData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            packetWrapper.writeInt(particleColorData.color.asRGB());
        }
    }

    public static ParticleColorData decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        AlphaColor alphaColor;
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            alphaColor = AlphaColor.decode(nBTCompound.getTagOrThrow("color"), clientVersion);
        } else {
            alphaColor = AlphaColor.WHITE;
        }
        return new ParticleColorData(alphaColor);
    }

    public static void encode(
            ParticleColorData particleColorData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            nBTCompound.setTag("color", AlphaColor.encode(particleColorData.color, clientVersion));
        }
    }

    public int getColor() {
        return this.color.asRGB();
    }

    public void setColor(int value) {
        this.color = new AlphaColor(value);
    }

    public void setAlphaColor(AlphaColor alphaColor) {
        this.color = alphaColor;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleColorData particleColorData = (ParticleColorData) result;
            return this.color.equals(particleColorData.color);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.color);
    }
}
