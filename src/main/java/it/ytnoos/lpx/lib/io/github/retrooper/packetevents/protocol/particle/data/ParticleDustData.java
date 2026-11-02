package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleDustData extends ParticleData {

    private float scale;

    private Color color;

    public ParticleDustData(float value, float value2, float value3, float value4) {
        this(value, new Color(value2, value3, value4));
    }

    public ParticleDustData(float value, float[] values) {
        this(value, values[0], values[1], values[2]);
    }

    public ParticleDustData(float value, Vector3f vector3f) {
        this(value, vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    public ParticleDustData(float value, int value2, int value3, int value4) {
        this(value, new Color(value2, value3, value4));
    }

    public ParticleDustData(float scale, Color color2) {
        this.scale = scale;
        this.color = color2;
    }

    public static ParticleDustData read(PacketWrapper<?> packetWrapper) {
        Color color;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            color = new Color(packetWrapper.readInt());
        } else {
            float value = packetWrapper.readFloat();
            float value2 = packetWrapper.readFloat();
            float value3 = packetWrapper.readFloat();
            color = new Color(value, value2, value3);
        }
        float value4 = packetWrapper.readFloat();
        return new ParticleDustData(value4, color);
    }

    public static void write(PacketWrapper<?> packetWrapper, ParticleDustData particleDustData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeInt(particleDustData.color.asRGB());
        } else {
            packetWrapper.writeFloat(particleDustData.getRed());
            packetWrapper.writeFloat(particleDustData.getGreen());
            packetWrapper.writeFloat(particleDustData.getBlue());
        }
        packetWrapper.writeFloat(particleDustData.scale);
    }

    public static ParticleDustData decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        Color color = Color.decode(nBTCompound.getTagOrThrow("color"), clientVersion);
        float asFloat = nBTCompound.getNumberTagOrThrow("scale").getAsFloat();
        return new ParticleDustData(asFloat, color);
    }

    public static void encode(
            ParticleDustData particleDustData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        nBTCompound.setTag("color", Color.encode(particleDustData.color, clientVersion));
        nBTCompound.setTag("scale", new NBTFloat(particleDustData.scale));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public float getRed() {
        return this.color.red() / 255.0F;
    }

    public void setRed(float value) {
        this.color = new Color(value, this.getGreen(), this.getBlue());
    }

    public float getGreen() {
        return this.color.green() / 255.0F;
    }

    public void setGreen(float value) {
        this.color = new Color(this.getRed(), value, this.getBlue());
    }

    public float getBlue() {
        return this.color.blue() / 255.0F;
    }

    public void setBlue(float value) {
        this.color = new Color(this.getRed(), this.getGreen(), value);
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color2) {
        this.color = color2;
    }

    @Override
    public boolean equals(Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        }
        ParticleDustData particleDustData;
        return Float.compare((particleDustData = (ParticleDustData) result).scale, this.scale) != 0
                ? false
                : this.color.equals(particleDustData.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scale, this.color);
    }
}
