package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint.TrackedWaypoint;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWaypoint extends PacketWrapper<WrapperPlayServerWaypoint> {

    private WrapperPlayServerWaypoint.Operation operation;

    private TrackedWaypoint waypoint;

    public WrapperPlayServerWaypoint(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWaypoint(
            WrapperPlayServerWaypoint.Operation operation2, TrackedWaypoint trackedWaypoint) {
        super(PacketType.Play.Server.WAYPOINT);
        this.operation = operation2;
        this.waypoint = trackedWaypoint;
    }

    @Override
    public void read() {
        this.operation = this.readEnum(WrapperPlayServerWaypoint.Operation.class);
        this.waypoint = TrackedWaypoint.read(this);
    }

    @Override
    public void write() {
        this.writeEnum(this.operation);
        TrackedWaypoint.write(this, this.waypoint);
    }

    public void copy(WrapperPlayServerWaypoint wrapperPlayServerWaypoint) {
        this.operation = wrapperPlayServerWaypoint.operation;
        this.waypoint = wrapperPlayServerWaypoint.waypoint;
    }

    public WrapperPlayServerWaypoint.Operation getOperation() {
        return this.operation;
    }

    public void setOperation(WrapperPlayServerWaypoint.Operation operation2) {
        this.operation = operation2;
    }

    public TrackedWaypoint getWaypoint() {
        return this.waypoint;
    }

    public void setWaypoint(TrackedWaypoint trackedWaypoint) {
        this.waypoint = trackedWaypoint;
    }

    public enum Operation {
        TRACK,
        UNTRACK,
        UPDATE
    }
}
