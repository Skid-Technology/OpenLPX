package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class Vector3f {

    private static final Vector3f ZERO = new Vector3f(0.0F, 0.0F, 0.0F);

    public final float x;

    public final float y;

    public final float z;

    public Vector3f() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3d vector3d) {
        this((float) vector3d.x, (float) vector3d.y, (float) vector3d.z);
    }

    public Vector3f(float[] values) {
        if (values.length > 0) {
            this.x = values[0];
            if (values.length > 1) {
                this.y = values[1];
                if (values.length > 2) {
                    this.z = values[2];
                } else {
                    this.z = 0.0F;
                }
            } else {
                this.y = 0.0F;
                this.z = 0.0F;
            }
        } else {
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 0.0F;
        }
    }

    public static Vector3f read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        float value2 = packetWrapper.readFloat();
        float value3 = packetWrapper.readFloat();
        return new Vector3f(value, value2, value3);
    }

    public static void write(PacketWrapper<?> packetWrapper, Vector3f vector3f) {
        packetWrapper.writeFloat(vector3f.x);
        packetWrapper.writeFloat(vector3f.y);
        packetWrapper.writeFloat(vector3f.z);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    @Override
    public boolean equals(Object result) {
        if (result instanceof Vector3f) {
            Vector3f vector3f = (Vector3f) result;
            return this.x == vector3f.x && this.y == vector3f.y && this.z == vector3f.z;
        }
        if (result instanceof Vector3d) {
            Vector3d vector3d = (Vector3d) result;
            return this.x == vector3d.x && this.y == vector3d.y && this.z == vector3d.z;
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

    public Vector3f add(float value, float value2, float value3) {
        return new Vector3f(this.x + value, this.y + value2, this.z + value3);
    }

    public Vector3f add(Vector3f vector3f) {
        return this.add(vector3f.x, vector3f.y, vector3f.z);
    }

    public Vector3f offset(BlockFace blockFace) {
        return this.add(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
    }

    public Vector3f offset(BlockFace blockFace, int value) {
        return value == 0
                ? this
                : this.add(
                        blockFace.getModX() * value,
                        blockFace.getModY() * value,
                        blockFace.getModZ() * value);
    }

    public Vector3f subtract(float value, float value2, float value3) {
        return new Vector3f(this.x - value, this.y - value2, this.z - value3);
    }

    public Vector3f subtract(Vector3f vector3f) {
        return this.subtract(vector3f.x, vector3f.y, vector3f.z);
    }

    public Vector3f multiply(float value, float value2, float value3) {
        return new Vector3f(this.x * value, this.y * value2, this.z * value3);
    }

    public Vector3f multiply(Vector3f vector3f) {
        return this.multiply(vector3f.x, vector3f.y, vector3f.z);
    }

    public Vector3f multiply(float value) {
        return this.multiply(value, value, value);
    }

    public Vector3f crossProduct(Vector3f vector3f) {
        float value = this.y * vector3f.z - vector3f.y * this.z;
        float value2 = this.z * vector3f.x - vector3f.z * this.x;
        float value3 = this.x * vector3f.y - vector3f.x * this.y;
        return new Vector3f(value, value2, value3);
    }

    public float dot(Vector3f vector3f) {
        return this.x * vector3f.x + this.y * vector3f.y + this.z * vector3f.z;
    }

    public Vector3f with(Float value, Float value2, Float value3) {
        return new Vector3f(
                value == null ? this.x : value,
                value2 == null ? this.y : value2,
                value3 == null ? this.z : value3);
    }

    public Vector3f withX(float value) {
        return new Vector3f(value, this.y, this.z);
    }

    public Vector3f withY(float value) {
        return new Vector3f(this.x, value, this.z);
    }

    public Vector3f withZ(float value) {
        return new Vector3f(this.x, this.y, value);
    }

    @Override
    public String toString() {
        return "X: " + this.x + ", Y: " + this.y + ", Z: " + this.z;
    }

    public static Vector3f zero() {
        return ZERO;
    }
}
