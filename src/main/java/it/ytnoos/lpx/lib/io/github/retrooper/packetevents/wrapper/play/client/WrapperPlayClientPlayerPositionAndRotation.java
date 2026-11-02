package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;

public class WrapperPlayClientPlayerPositionAndRotation extends WrapperPlayClientPlayerFlying {

    public WrapperPlayClientPlayerPositionAndRotation(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerPositionAndRotation(
            Vector3d vector3d, float value, float value2, boolean matches) {
        super(true, true, matches, new Location(vector3d, value, value2));
    }

    public WrapperPlayClientPlayerPositionAndRotation(Location location, boolean matches) {
        super(true, true, matches, location);
    }

    public Vector3d getPosition() {
        return this.getLocation().getPosition();
    }

    public void setPosition(Vector3d vector3d) {
        this.getLocation().setPosition(vector3d);
    }

    public float getYaw() {
        return this.getLocation().getYaw();
    }

    public void setYaw(float value) {
        this.getLocation().setYaw(value);
    }

    public float getPitch() {
        return this.getLocation().getPitch();
    }

    public void setPitch(float value) {
        this.getLocation().setPitch(value);
    }
}
