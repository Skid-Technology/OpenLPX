package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;

public class WrapperPlayClientPlayerPosition extends WrapperPlayClientPlayerFlying {

    public WrapperPlayClientPlayerPosition(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerPosition(Vector3d vector3d, boolean matches) {
        super(true, false, matches, new Location(vector3d, 0.0F, 0.0F));
    }

    public Vector3d getPosition() {
        return this.getLocation().getPosition();
    }

    public void setPosition(Vector3d vector3d) {
        this.getLocation().setPosition(vector3d);
    }
}
