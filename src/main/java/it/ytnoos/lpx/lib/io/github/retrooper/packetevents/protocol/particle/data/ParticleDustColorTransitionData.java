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
public class ParticleDustColorTransitionData extends ParticleData {

    private float scale;

    private Color start;

    private Color end;

    public ParticleDustColorTransitionData(
            float value,
            float value2,
            float value3,
            float value4,
            float value5,
            float value6,
            float value7) {
        this(value, new Color(value2, value3, value4), new Color(value5, value6, value7));
    }

    public ParticleDustColorTransitionData(float value, float[] values, float[] values2) {
        this(value, values[0], values[1], values[2], values2[0], values2[1], values2[2]);
    }

    public ParticleDustColorTransitionData(float value, Vector3f vector3f, Vector3f vector3f2) {
        this(
                value,
                vector3f.getX(),
                vector3f.getY(),
                vector3f.getZ(),
                vector3f2.getX(),
                vector3f2.getY(),
                vector3f2.getZ());
    }

    public ParticleDustColorTransitionData(float scale, Color color, Color color2) {
        this.scale = scale;
        this.start = color;
        this.end = color2;
    }

    public static ParticleDustColorTransitionData read(PacketWrapper<?> packetWrapper) {
        Color color;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            color = new Color(packetWrapper.readInt());
        } else {
            float value = packetWrapper.readFloat();
            float value2 = packetWrapper.readFloat();
            float value3 = packetWrapper.readFloat();
            color = new Color(value, value2, value3);
        }
        float value4 = 0.0F;
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)) {
            value4 = packetWrapper.readFloat();
        }
        Color color2;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            color2 = new Color(packetWrapper.readInt());
        } else {
            float value5 = packetWrapper.readFloat();
            float value6 = packetWrapper.readFloat();
            float value7 = packetWrapper.readFloat();
            color2 = new Color(value5, value6, value7);
        }
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            value4 = packetWrapper.readFloat();
        }
        return new ParticleDustColorTransitionData(value4, color, color2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            ParticleDustColorTransitionData particleDustColorTransitionData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeInt(particleDustColorTransitionData.getStart().asRGB());
        } else {
            packetWrapper.writeFloat(particleDustColorTransitionData.getStartRed());
            packetWrapper.writeFloat(particleDustColorTransitionData.getStartGreen());
            packetWrapper.writeFloat(particleDustColorTransitionData.getStartBlue());
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)) {
            packetWrapper.writeFloat(particleDustColorTransitionData.getScale());
        }
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeInt(particleDustColorTransitionData.getEnd().asRGB());
        } else {
            packetWrapper.writeFloat(particleDustColorTransitionData.getEndRed());
            packetWrapper.writeFloat(particleDustColorTransitionData.getEndGreen());
            packetWrapper.writeFloat(particleDustColorTransitionData.getEndBlue());
        }
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            packetWrapper.writeFloat(particleDustColorTransitionData.getScale());
        }
    }

    public static ParticleDustColorTransitionData decode(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        String text = "from_color";
        String text2 = "to_color";
        if (clientVersion.isOlderThan(ClientVersion.V_1_20_5)) {
            text = "fromColor";
            text2 = "toColor";
        }
        Color color = Color.decode(nBTCompound.getTagOrThrow(text), clientVersion);
        Color color2 = Color.decode(nBTCompound.getTagOrThrow(text2), clientVersion);
        float asFloat = nBTCompound.getNumberTagOrThrow("scale").getAsFloat();
        return new ParticleDustColorTransitionData(asFloat, color, color2);
    }

    public static void encode(
            ParticleDustColorTransitionData particleDustColorTransitionData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        String text = "from_color";
        String text2 = "to_color";
        if (clientVersion.isOlderThan(ClientVersion.V_1_20_5)) {
            text = "fromColor";
            text2 = "toColor";
        }
        nBTCompound.setTag(
                text, Color.encode(particleDustColorTransitionData.start, clientVersion));
        nBTCompound.setTag(text2, Color.encode(particleDustColorTransitionData.end, clientVersion));
        nBTCompound.setTag("scale", new NBTFloat(particleDustColorTransitionData.scale));
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public float getStartRed() {
        return this.start.red() / 255.0F;
    }

    public void setStartRed(float value) {
        this.start = new Color(value, this.getStartGreen(), this.getStartBlue());
    }

    public float getStartGreen() {
        return this.start.green() / 255.0F;
    }

    public void setStartGreen(float value) {
        this.start = new Color(this.getStartRed(), value, this.getStartBlue());
    }

    public float getStartBlue() {
        return this.start.blue() / 255.0F;
    }

    public void setStartBlue(float value) {
        this.start = new Color(this.getStartRed(), this.getStartGreen(), value);
    }

    public float getEndRed() {
        return this.end.red() / 255.0F;
    }

    public void setEndRed(float value) {
        this.end = new Color(value, this.getEndGreen(), this.getEndBlue());
    }

    public float getEndGreen() {
        return this.end.green() / 255.0F;
    }

    public void setEndGreen(float value) {
        this.end = new Color(this.getEndRed(), value, this.getEndBlue());
    }

    public float getEndBlue() {
        return this.end.blue() / 255.0F;
    }

    public void setEndBlue(float value) {
        this.end = new Color(this.getEndRed(), this.getEndGreen(), value);
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Color getStart() {
        return this.start;
    }

    public void setStart(Color color) {
        this.start = color;
    }

    public Color getEnd() {
        return this.end;
    }

    public void setEnd(Color color) {
        this.end = color;
    }

    @Override
    public boolean equals(Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            ParticleDustColorTransitionData particleDustColorTransitionData;
            if (Float.compare(
                            (particleDustColorTransitionData =
                                            (ParticleDustColorTransitionData) result)
                                    .scale,
                            this.scale)
                    != 0) {
                return false;
            } else {
                return !this.start.equals(particleDustColorTransitionData.start)
                        ? false
                        : this.end.equals(particleDustColorTransitionData.end);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scale, this.start, this.end);
    }
}
