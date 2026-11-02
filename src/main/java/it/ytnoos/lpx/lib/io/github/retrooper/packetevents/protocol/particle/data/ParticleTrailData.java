package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ParticleTrailData extends ParticleData {

    private Vector3d target;

    private Color color;

    private int duration;

    public ParticleTrailData(Vector3d vector3d, Color color) {
        this(vector3d, color, 25);
    }

    public ParticleTrailData(Vector3d vector3d, Color color2, int duration) {
        this.target = vector3d;
        this.color = color2;
        this.duration = duration;
    }

    public static ParticleTrailData read(PacketWrapper<?> packetWrapper) {
        Vector3d vector3d = Vector3d.read(packetWrapper);
        Color color = new Color(packetWrapper.readInt());
        int value =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_4)
                        ? packetWrapper.readVarInt()
                        : 25;
        return new ParticleTrailData(vector3d, color, value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ParticleTrailData particleTrailData) {
        Vector3d.write(packetWrapper, particleTrailData.target);
        packetWrapper.writeInt(particleTrailData.color.asRGB());
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            packetWrapper.writeVarInt(particleTrailData.duration);
        }
    }

    public static ParticleTrailData decode(NBTCompound nBTCompound, ClientVersion clientVersion) {
        Vector3d vector3d = Vector3d.decode(nBTCompound.getTagOrThrow("target"), clientVersion);
        Color color = Color.decode(nBTCompound.getTagOrThrow("color"), clientVersion);
        int value = 25;
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_21_4)) {
            value = nBTCompound.getNumberTagOrThrow("duration").getAsInt();
        }
        return new ParticleTrailData(vector3d, color, value);
    }

    public static void encode(
            ParticleTrailData particleTrailData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        nBTCompound.setTag("target", Vector3d.encode(particleTrailData.target, clientVersion));
        nBTCompound.setTag("color", Color.encode(particleTrailData.color, clientVersion));
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_21_4)) {
            nBTCompound.setTag("duration", new NBTInt(particleTrailData.duration));
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public Vector3d getTarget() {
        return this.target;
    }

    public void setTarget(Vector3d vector3d) {
        this.target = vector3d;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color2) {
        this.color = color2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleTrailData particleTrailData = (ParticleTrailData) result;
            if (this.duration != particleTrailData.duration) {
                return false;
            } else {
                return !this.target.equals(particleTrailData.target)
                        ? false
                        : this.color.equals(particleTrailData.color);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.target, this.color, this.duration);
    }
}
