package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerCamera extends PacketWrapper<WrapperPlayServerCamera> {

    private int cameraId;

    public WrapperPlayServerCamera(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCamera(int cameraId) {
        super(PacketType.Play.Server.CAMERA);
        this.cameraId = cameraId;
    }

    @Override
    public void read() {
        this.cameraId = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.cameraId);
    }

    public void copy(WrapperPlayServerCamera wrapperPlayServerCamera) {
        this.cameraId = wrapperPlayServerCamera.cameraId;
    }

    public int getCameraId() {
        return this.cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }
}
