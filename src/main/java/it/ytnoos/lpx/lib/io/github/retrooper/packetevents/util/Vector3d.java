package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class Vector3d {

    private static final Vector3d ZERO = new Vector3d();

    public final double x;

    public final double y;

    public final double z;

    public Vector3d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(Vector3f vector3f) {
        this(vector3f.x, vector3f.y, vector3f.z);
    }

    public Vector3d(double[] values) {
        if (values.length > 0) {
            this.x = values[0];
            if (values.length > 1) {
                this.y = values[1];
                if (values.length > 2) {
                    this.z = values[2];
                } else {
                    this.z = 0.0;
                }
            } else {
                this.y = 0.0;
                this.z = 0.0;
            }
        } else {
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
        }
    }

    public static Vector3d read(PacketWrapper<?> packetWrapper) {
        double value = packetWrapper.readDouble();
        double value2 = packetWrapper.readDouble();
        double value3 = packetWrapper.readDouble();
        return new Vector3d(value, value2, value3);
    }

    public static void write(PacketWrapper<?> packetWrapper, Vector3d vector3d) {
        packetWrapper.writeDouble(vector3d.x);
        packetWrapper.writeDouble(vector3d.y);
        packetWrapper.writeDouble(vector3d.z);
    }

    public static Vector3d decode(NBT nBT, ClientVersion clientVersion) {
        NBTList nBTList;
        double asDouble = ((NBTNumber) (nBTList = (NBTList) nBT).getTag(0)).getAsDouble();
        double asDouble2 = ((NBTNumber) nBTList.getTag(1)).getAsDouble();
        double asDouble3 = ((NBTNumber) nBTList.getTag(2)).getAsDouble();
        return new Vector3d(asDouble, asDouble2, asDouble3);
    }

    public static NBT encode(Vector3d vector3d, ClientVersion clientVersion) {
        NBTList nBTList;
        (nBTList = new NBTList<>(NBTType.DOUBLE, 3)).addTag(new NBTDouble(vector3d.x));
        nBTList.addTag(new NBTDouble(vector3d.y));
        nBTList.addTag(new NBTDouble(vector3d.z));
        return nBTList;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    @Override
    public boolean equals(Object result) {
        if (result instanceof Vector3d) {
            Vector3d vector3d = (Vector3d) result;
            return this.x == vector3d.x && this.y == vector3d.y && this.z == vector3d.z;
        }
        if (result instanceof Vector3f) {
            Vector3f vector3f = (Vector3f) result;
            return this.x == vector3f.x && this.y == vector3f.y && this.z == vector3f.z;
        }
        if (!(result instanceof Vector3i)) {
            return false;
        }
        Vector3i vector3i = (Vector3i) result;
        return this.x == vector3i.x && this.y == vector3i.y && this.z == vector3i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public Vector3d add(double value, double value2, double value3) {
        return new Vector3d(this.x + value, this.y + value2, this.z + value3);
    }

    public Vector3d add(Vector3d vector3d) {
        return this.add(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d offset(BlockFace blockFace) {
        return this.add(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
    }

    public Vector3d offset(BlockFace blockFace, int value) {
        return value == 0
                ? this
                : this.add(
                        blockFace.getModX() * value,
                        blockFace.getModY() * value,
                        blockFace.getModZ() * value);
    }

    public Vector3d subtract(double value, double value2, double value3) {
        return new Vector3d(this.x - value, this.y - value2, this.z - value3);
    }

    public Vector3d subtract(Vector3d vector3d) {
        return this.subtract(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d multiply(double value, double value2, double value3) {
        return new Vector3d(this.x * value, this.y * value2, this.z * value3);
    }

    public Vector3d multiply(Vector3d vector3d) {
        return this.multiply(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d multiply(double value) {
        return this.multiply(value, value, value);
    }

    public Vector3d crossProduct(Vector3d vector3d) {
        double value = this.y * vector3d.z - vector3d.y * this.z;
        double value2 = this.z * vector3d.x - vector3d.z * this.x;
        double value3 = this.x * vector3d.y - vector3d.x * this.y;
        return new Vector3d(value, value2, value3);
    }

    public double dot(Vector3d vector3d) {
        return this.x * vector3d.x + this.y * vector3d.y + this.z * vector3d.z;
    }

    public Vector3d with(Double value, Double value2, Double value3) {
        return new Vector3d(
                value == null ? this.x : value,
                value2 == null ? this.y : value2,
                value3 == null ? this.z : value3);
    }

    public Vector3d withX(double value) {
        return new Vector3d(value, this.y, this.z);
    }

    public Vector3d withY(double value) {
        return new Vector3d(this.x, value, this.z);
    }

    public Vector3d withZ(double value) {
        return new Vector3d(this.x, this.y, value);
    }

    public double distance(Vector3d vector3d) {
        return Math.sqrt(this.distanceSquared(vector3d));
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Vector3d normalize() {
        double size = this.length();
        return new Vector3d(this.x / size, this.y / size, this.z / size);
    }

    public double distanceSquared(Vector3d vector3d) {
        double value = (this.x - vector3d.x) * (this.x - vector3d.x);
        double value2 = (this.y - vector3d.y) * (this.y - vector3d.y);
        double value3 = (this.z - vector3d.z) * (this.z - vector3d.z);
        return value + value2 + value3;
    }

    public Vector3i toVector3i() {
        return new Vector3i((int) this.x, (int) this.y, (int) this.z);
    }

    @Override
    public String toString() {
        return "X: " + this.x + ", Y: " + this.y + ", Z: " + this.z;
    }

    public static Vector3d zero() {
        return ZERO;
    }
}
