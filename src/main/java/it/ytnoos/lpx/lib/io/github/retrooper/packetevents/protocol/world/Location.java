package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class Location implements Cloneable {

    private Vector3d position;

    private float yaw;

    private float pitch;

    public Location(Vector3d vector3d, float yaw, float pitch) {
        this.position = vector3d;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(double value, double value2, double value3, float value4, float value5) {
        this(new Vector3d(value, value2, value3), value4, value5);
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public double getZ() {
        return this.position.getZ();
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Vector3f getDirection() {
        double yaw = this.getYaw();
        double value;
        float value2 = (float) (-Math.sin(Math.toRadians(value = this.getPitch())));
        double value3;
        float value4 =
                (float)
                        (-(value3 = Math.cos(Math.toRadians(value)))
                                * Math.sin(Math.toRadians(yaw)));
        float value5 = (float) (value3 * Math.cos(Math.toRadians(yaw)));
        return new Vector3f(value4, value2, value5);
    }

    public void setDirection(Vector3f vector3f) {
        double x = vector3f.getX();
        double z = vector3f.getZ();
        if (x == 0.0 && z == 0.0) {
            this.pitch = vector3f.getY() > 0.0 ? -90.0F : 90.0F;
        } else {
            double value = Math.atan2(-x, z);
            this.yaw = (float) Math.toDegrees((value + (Math.PI * 2)) % (Math.PI * 2));
            double value2 = x * x;
            double value3 = z * z;
            double value4 = Math.sqrt(value2 + value3);
            this.pitch = (float) Math.toDegrees(Math.atan(-vector3f.getY() / value4));
        }
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof Location)) {
            return false;
        }
        Location location = (Location) result;
        return Objects.equals(this.position, location.position)
                && this.yaw == location.yaw
                && this.pitch == location.pitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.yaw, this.pitch);
    }

    public Location clone() {
        return new Location(this.position, this.yaw, this.pitch);
    }

    @Override
    public String toString() {
        return "Location {["
                + this.position.toString()
                + "], yaw: "
                + this.yaw
                + ", pitch: "
                + this.pitch
                + "}";
    }
}
