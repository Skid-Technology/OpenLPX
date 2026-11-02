package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.UUID;

public class WrapperPlayClientSpectate extends PacketWrapper<WrapperPlayClientSpectate> {

    private UUID targetUUID;

    public WrapperPlayClientSpectate(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSpectate(UUID targetUUID) {
        super(PacketType.Play.Client.SPECTATE);
        this.targetUUID = targetUUID;
    }

    @Override
    public void read() {
        this.targetUUID = this.readUUID();
    }

    @Override
    public void write() {
        this.writeUUID(this.targetUUID);
    }

    public void copy(WrapperPlayClientSpectate wrapperPlayClientSpectate) {
        this.targetUUID = wrapperPlayClientSpectate.targetUUID;
    }

    public UUID getTargetUUID() {
        return this.targetUUID;
    }

    public void setTargetUUID(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }
}
