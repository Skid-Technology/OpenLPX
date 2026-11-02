package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;

public class WrapperPlayClientPlayerRotation extends WrapperPlayClientPlayerFlying {

    public WrapperPlayClientPlayerRotation(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerRotation(float value, float value2, boolean matches) {
        super(false, true, matches, new Location(new Vector3d(), value, value2));
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
