package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientPlayerFlying extends PacketWrapper<WrapperPlayClientPlayerFlying> {

    private boolean positionChanged;

    private boolean rotationChanged;

    private Location location;

    private boolean onGround;

    private boolean horizontalCollision;

    public WrapperPlayClientPlayerFlying(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent, false);
        this.positionChanged =
                packetReceiveEvent.getPacketType() == PacketType.Play.Client.PLAYER_POSITION
                        || packetReceiveEvent.getPacketType()
                                == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION;
        this.rotationChanged =
                packetReceiveEvent.getPacketType() == PacketType.Play.Client.PLAYER_ROTATION
                        || packetReceiveEvent.getPacketType()
                                == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION;
        this.readEvent(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerFlying(
            boolean matches, boolean matches2, boolean matches3, Location location) {
        this(matches, matches2, matches3, false, location);
    }

    public WrapperPlayClientPlayerFlying(
            boolean positionChanged,
            boolean rotationChanged,
            boolean onGround,
            boolean horizontalCollision,
            Location location2) {
        super(
                positionChanged && rotationChanged
                        ? PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION
                        : (positionChanged
                                ? PacketType.Play.Client.PLAYER_POSITION
                                : (rotationChanged
                                        ? PacketType.Play.Client.PLAYER_ROTATION
                                        : PacketType.Play.Client.PLAYER_FLYING)));
        this.positionChanged = positionChanged;
        this.rotationChanged = rotationChanged;
        this.onGround = onGround;
        this.horizontalCollision = horizontalCollision;
        this.location = location2;
    }

    public static boolean isFlying(PacketTypeCommon packetTypeCommon) {
        return packetTypeCommon == PacketType.Play.Client.PLAYER_FLYING
                || packetTypeCommon == PacketType.Play.Client.PLAYER_POSITION
                || packetTypeCommon == PacketType.Play.Client.PLAYER_ROTATION
                || packetTypeCommon == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION;
    }

    @Override
    public void read() {
        Vector3d vector3d = new Vector3d();
        float value = 0.0F;
        float value2 = 0.0F;
        if (this.positionChanged) {
            double value3 = this.readDouble();
            double value4 = this.readDouble();
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                this.readDouble();
            }
            double value5 = this.readDouble();
            vector3d = new Vector3d(value3, value4, value5);
        }
        if (this.rotationChanged) {
            value = this.readFloat();
            value2 = this.readFloat();
        }
        this.location = new Location(vector3d, value, value2);
        byte value6 = this.readByte();
        this.onGround = (value6 & 1) == 1;
        this.horizontalCollision = (value6 & 2) == 2;
    }

    @Override
    public void write() {
        if (this.positionChanged) {
            this.writeDouble(this.location.getPosition().getX());
            if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
                this.writeDouble(this.location.getPosition().getY() + 1.62);
            }
            this.writeDouble(this.location.getPosition().getY());
            this.writeDouble(this.location.getPosition().getZ());
        }
        if (this.rotationChanged) {
            this.writeFloat(this.location.getYaw());
            this.writeFloat(this.location.getPitch());
        }
        this.writeByte((this.onGround ? 1 : 0) | (this.horizontalCollision ? 2 : 0));
    }

    public void copy(WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        this.positionChanged = wrapperPlayClientPlayerFlying.positionChanged;
        this.rotationChanged = wrapperPlayClientPlayerFlying.rotationChanged;
        this.location = wrapperPlayClientPlayerFlying.location;
        this.onGround = wrapperPlayClientPlayerFlying.onGround;
        this.horizontalCollision = wrapperPlayClientPlayerFlying.horizontalCollision;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location2) {
        this.location = location2;
    }

    public boolean hasPositionChanged() {
        return this.positionChanged;
    }

    public void setPositionChanged(boolean positionChanged) {
        this.positionChanged = positionChanged;
    }

    public boolean hasRotationChanged() {
        return this.rotationChanged;
    }

    public void setRotationChanged(boolean rotationChanged) {
        this.rotationChanged = rotationChanged;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isHorizontalCollision() {
        return this.horizontalCollision;
    }

    public void setHorizontalCollision(boolean horizontalCollision) {
        this.horizontalCollision = horizontalCollision;
    }
}
