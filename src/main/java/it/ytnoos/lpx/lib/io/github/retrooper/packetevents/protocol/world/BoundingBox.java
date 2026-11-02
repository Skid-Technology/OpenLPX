package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class BoundingBox {

    private double minX;

    private double minY;

    private double minZ;

    private double maxX;

    private double maxY;

    private double maxZ;

    public BoundingBox() {
        this.resize(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public BoundingBox(
            double value,
            double value2,
            double value3,
            double value4,
            double value5,
            double value6) {
        this.resize(value, value2, value3, value4, value5, value6);
    }

    @NotNull
    public static BoundingBox of(@NotNull Vector3d vector3d, @NotNull Vector3d vector3d2) {
        return new BoundingBox(
                vector3d.getX(),
                vector3d.getY(),
                vector3d.getZ(),
                vector3d2.getX(),
                vector3d2.getY(),
                vector3d2.getZ());
    }

    @NotNull
    public static BoundingBox of(
            @NotNull Vector3d vector3d, double value, double value2, double value3) {
        return new BoundingBox(
                vector3d.getX() - value,
                vector3d.getY() - value2,
                vector3d.getZ() - value3,
                vector3d.getX() + value,
                vector3d.getY() + value2,
                vector3d.getZ() + value3);
    }

    @NotNull
    public static BoundingBox deserialize(@NotNull Map<String, Object> entries) {
        double value = 0.0;
        double value2 = 0.0;
        double value3 = 0.0;
        double value4 = 0.0;
        double value5 = 0.0;
        double value6 = 0.0;
        if (entries.containsKey("minX")) {
            value = ((Number) entries.get("minX")).doubleValue();
        }
        if (entries.containsKey("minY")) {
            value2 = ((Number) entries.get("minY")).doubleValue();
        }
        if (entries.containsKey("minZ")) {
            value3 = ((Number) entries.get("minZ")).doubleValue();
        }
        if (entries.containsKey("maxX")) {
            value4 = ((Number) entries.get("maxX")).doubleValue();
        }
        if (entries.containsKey("maxY")) {
            value5 = ((Number) entries.get("maxY")).doubleValue();
        }
        if (entries.containsKey("maxZ")) {
            value6 = ((Number) entries.get("maxZ")).doubleValue();
        }
        return new BoundingBox(value, value2, value3, value4, value5, value6);
    }

    @NotNull
    public BoundingBox resize(
            double value,
            double value2,
            double value3,
            double value4,
            double value5,
            double value6) {
        this.minX = Math.min(value, value4);
        this.minY = Math.min(value2, value5);
        this.minZ = Math.min(value3, value6);
        this.maxX = Math.max(value, value4);
        this.maxY = Math.max(value2, value5);
        this.maxZ = Math.max(value3, value6);
        return this;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMinZ() {
        return this.minZ;
    }

    @NotNull
    public Vector3d getMin() {
        return new Vector3d(this.minX, this.minY, this.minZ);
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    @NotNull
    public Vector3d getMax() {
        return new Vector3d(this.maxX, this.maxY, this.maxZ);
    }

    public double getWidthX() {
        return this.maxX - this.minX;
    }

    public double getWidthZ() {
        return this.maxZ - this.minZ;
    }

    public double getHeight() {
        return this.maxY - this.minY;
    }

    public double getVolume() {
        return this.getHeight() * this.getWidthX() * this.getWidthZ();
    }

    public double getCenterX() {
        return this.minX + this.getWidthX() * 0.5;
    }

    public double getCenterY() {
        return this.minY + this.getHeight() * 0.5;
    }

    public double getCenterZ() {
        return this.minZ + this.getWidthZ() * 0.5;
    }

    @NotNull
    public Vector3d getCenter() {
        return new Vector3d(this.getCenterX(), this.getCenterY(), this.getCenterZ());
    }

    @NotNull
    public BoundingBox copy(@NotNull BoundingBox box) {
        return this.resize(
                box.getMinX(),
                box.getMinY(),
                box.getMinZ(),
                box.getMaxX(),
                box.getMaxY(),
                box.getMaxZ());
    }

    @NotNull
    public BoundingBox expand(
            double value,
            double value2,
            double value3,
            double value4,
            double value5,
            double value6) {
        if (value == 0.0
                && value2 == 0.0
                && value3 == 0.0
                && value4 == 0.0
                && value5 == 0.0
                && value6 == 0.0) {
            return this;
        }
        double value7 = this.minX - value;
        double value8 = this.minY - value2;
        double value9 = this.minZ - value3;
        double value10 = this.maxX + value4;
        double value11 = this.maxY + value5;
        double value12 = this.maxZ + value6;
        if (value7 > value10) {
            double centerX = this.getCenterX();
            if (value10 >= centerX) {
                value7 = value10;
            } else if (value7 <= centerX) {
                value10 = value7;
            } else {
                value7 = centerX;
                value10 = centerX;
            }
        }
        if (value8 > value11) {
            double centerY = this.getCenterY();
            if (value11 >= centerY) {
                value8 = value11;
            } else if (value8 <= centerY) {
                value11 = value8;
            } else {
                value8 = centerY;
                value11 = centerY;
            }
        }
        if (value9 > value12) {
            double centerZ = this.getCenterZ();
            if (value12 >= centerZ) {
                value9 = value12;
            } else if (value9 <= centerZ) {
                value12 = value9;
            } else {
                value9 = centerZ;
                value12 = centerZ;
            }
        }
        return this.resize(value7, value8, value9, value10, value11, value12);
    }

    @NotNull
    public BoundingBox expand(double value, double value2, double value3) {
        return this.expand(value, value2, value3, value, value2, value3);
    }

    @NotNull
    public BoundingBox expand(@NotNull Vector3d vector3d) {
        double x = vector3d.getX();
        double y = vector3d.getY();
        double z = vector3d.getZ();
        return this.expand(x, y, z, x, y, z);
    }

    @NotNull
    public BoundingBox expand(double value) {
        return this.expand(value, value, value, value, value, value);
    }

    @NotNull
    public BoundingBox expand(double value, double value2, double value3, double value4) {
        if (value4 == 0.0) {
            return this;
        }
        if (value == 0.0 && value2 == 0.0 && value3 == 0.0) {
            return this;
        }
        double value5 = value < 0.0 ? -value * value4 : 0.0;
        double value6 = value2 < 0.0 ? -value2 * value4 : 0.0;
        double value7 = value3 < 0.0 ? -value3 * value4 : 0.0;
        double value8 = value > 0.0 ? value * value4 : 0.0;
        double value9 = value2 > 0.0 ? value2 * value4 : 0.0;
        double value10 = value3 > 0.0 ? value3 * value4 : 0.0;
        return this.expand(value5, value6, value7, value8, value9, value10);
    }

    @NotNull
    public BoundingBox expand(@NotNull Vector3d vector3d, double value) {
        return this.expand(vector3d.getX(), vector3d.getY(), vector3d.getZ(), value);
    }

    @NotNull
    public BoundingBox expandDirectional(double value, double value2, double value3) {
        return this.expand(value, value2, value3, 1.0);
    }

    @NotNull
    public BoundingBox expandDirectional(@NotNull Vector3d vector3d) {
        return this.expand(vector3d.getX(), vector3d.getY(), vector3d.getZ(), 1.0);
    }

    @NotNull
    public BoundingBox union(double value, double value2, double value3) {
        double value4 = Math.min(this.minX, value);
        double value5 = Math.min(this.minY, value2);
        double value6 = Math.min(this.minZ, value3);
        double value7 = Math.max(this.maxX, value);
        double value8 = Math.max(this.maxY, value2);
        double value9 = Math.max(this.maxZ, value3);
        return value4 == this.minX
                        && value5 == this.minY
                        && value6 == this.minZ
                        && value7 == this.maxX
                        && value8 == this.maxY
                        && value9 == this.maxZ
                ? this
                : this.resize(value4, value5, value6, value7, value8, value9);
    }

    @NotNull
    public BoundingBox union(@NotNull Vector3d vector3d) {
        return this.union(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }

    @NotNull
    public BoundingBox union(@NotNull BoundingBox box) {
        if (this.contains(box)) {
            return this;
        }
        double value = Math.min(this.minX, box.minX);
        double value2 = Math.min(this.minY, box.minY);
        double value3 = Math.min(this.minZ, box.minZ);
        double value4 = Math.max(this.maxX, box.maxX);
        double value5 = Math.max(this.maxY, box.maxY);
        double value6 = Math.max(this.maxZ, box.maxZ);
        return this.resize(value, value2, value3, value4, value5, value6);
    }

    @NotNull
    public BoundingBox intersection(@NotNull BoundingBox box) {
        double value = Math.max(this.minX, box.minX);
        double value2 = Math.max(this.minY, box.minY);
        double value3 = Math.max(this.minZ, box.minZ);
        double value4 = Math.min(this.maxX, box.maxX);
        double value5 = Math.min(this.maxY, box.maxY);
        double value6 = Math.min(this.maxZ, box.maxZ);
        return this.resize(value, value2, value3, value4, value5, value6);
    }

    @NotNull
    public BoundingBox shift(double value, double value2, double value3) {
        return value == 0.0 && value2 == 0.0 && value3 == 0.0
                ? this
                : this.resize(
                        this.minX + value,
                        this.minY + value2,
                        this.minZ + value3,
                        this.maxX + value,
                        this.maxY + value2,
                        this.maxZ + value3);
    }

    @NotNull
    public BoundingBox shift(@NotNull Vector3d vector3d) {
        return this.shift(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }

    private boolean overlaps(
            double value,
            double value2,
            double value3,
            double value4,
            double value5,
            double value6) {
        return this.minX < value4
                && this.maxX > value
                && this.minY < value5
                && this.maxY > value2
                && this.minZ < value6
                && this.maxZ > value3;
    }

    public boolean overlaps(@NotNull BoundingBox box) {
        return this.overlaps(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public boolean overlaps(@NotNull Vector3d vector3d, @NotNull Vector3d vector3d2) {
        double x = vector3d.getX();
        double y = vector3d.getY();
        double z = vector3d.getZ();
        double x2 = vector3d2.getX();
        double y2 = vector3d2.getY();
        double z2 = vector3d2.getZ();
        return this.overlaps(
                Math.min(x, x2),
                Math.min(y, y2),
                Math.min(z, z2),
                Math.max(x, x2),
                Math.max(y, y2),
                Math.max(z, z2));
    }

    public boolean contains(double value, double value2, double value3) {
        return value >= this.minX
                && value < this.maxX
                && value2 >= this.minY
                && value2 < this.maxY
                && value3 >= this.minZ
                && value3 < this.maxZ;
    }

    public boolean contains(@NotNull Vector3d vector3d) {
        return this.contains(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }

    private boolean contains(
            double value,
            double value2,
            double value3,
            double value4,
            double value5,
            double value6) {
        return this.minX <= value
                && this.maxX >= value4
                && this.minY <= value2
                && this.maxY >= value5
                && this.minZ <= value3
                && this.maxZ >= value6;
    }

    public boolean contains(@NotNull BoundingBox box) {
        return this.contains(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public boolean contains(@NotNull Vector3d vector3d, @NotNull Vector3d vector3d2) {
        double x = vector3d.getX();
        double y = vector3d.getY();
        double z = vector3d.getZ();
        double x2 = vector3d2.getX();
        double y2 = vector3d2.getY();
        double z2 = vector3d2.getZ();
        return this.contains(
                Math.min(x, x2),
                Math.min(y, y2),
                Math.min(z, z2),
                Math.max(x, x2),
                Math.max(y, y2),
                Math.max(z, z2));
    }

    @Override
    public int hashCode() {
        long value = Double.doubleToLongBits(this.maxX);
        int value2 = 31 + (int) (value ^ value >>> 32);
        value = Double.doubleToLongBits(this.maxY);
        value2 = 31 * value2 + (int) (value ^ value >>> 32);
        value = Double.doubleToLongBits(this.maxZ);
        value2 = 31 * value2 + (int) (value ^ value >>> 32);
        value = Double.doubleToLongBits(this.minX);
        value2 = 31 * value2 + (int) (value ^ value >>> 32);
        value = Double.doubleToLongBits(this.minY);
        value2 = 31 * value2 + (int) (value ^ value >>> 32);
        value = Double.doubleToLongBits(this.minZ);
        return 31 * value2 + (int) (value ^ value >>> 32);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof BoundingBox)) {
            return false;
        } else {
            BoundingBox box = (BoundingBox) result;
            if (Double.doubleToLongBits(this.maxX) != Double.doubleToLongBits(box.maxX)) {
                return false;
            } else if (Double.doubleToLongBits(this.maxY) != Double.doubleToLongBits(box.maxY)) {
                return false;
            } else if (Double.doubleToLongBits(this.maxZ) != Double.doubleToLongBits(box.maxZ)) {
                return false;
            } else if (Double.doubleToLongBits(this.minX) != Double.doubleToLongBits(box.minX)) {
                return false;
            } else {
                return Double.doubleToLongBits(this.minY) != Double.doubleToLongBits(box.minY)
                        ? false
                        : Double.doubleToLongBits(this.minZ) == Double.doubleToLongBits(box.minZ);
            }
        }
    }

    @Override
    public String toString() {
        return "BoundingBox [minX="
                + this.minX
                + ", minY="
                + this.minY
                + ", minZ="
                + this.minZ
                + ", maxX="
                + this.maxX
                + ", maxY="
                + this.maxY
                + ", maxZ="
                + this.maxZ
                + "]";
    }

    @NotNull
    public BoundingBox clone() {
        try {
            return (BoundingBox) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new Error(cloneNotSupportedException);
        }
    }

    @NotNull
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> entries;
        (entries = new LinkedHashMap()).put("minX", this.minX);
        entries.put("minY", this.minY);
        entries.put("minZ", this.minZ);
        entries.put("maxX", this.maxX);
        entries.put("maxY", this.maxY);
        entries.put("maxZ", this.maxZ);
        return entries;
    }
}
