package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

public class WrapperPlayServerTestInstanceBlockStatus
        extends PacketWrapper<WrapperPlayServerTestInstanceBlockStatus> {

    private Component status;

    @Nullable private Vector3i size;

    public WrapperPlayServerTestInstanceBlockStatus(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTestInstanceBlockStatus(
            Component component, @Nullable Vector3i vector3i) {
        super(PacketType.Play.Server.TEST_INSTANCE_BLOCK_STATUS);
        this.status = component;
        this.size = vector3i;
    }

    @Override
    public void read() {
        this.status = this.readComponent();
        this.size = this.readOptional(Vector3i::read);
    }

    @Override
    public void write() {
        this.writeComponent(this.status);
        this.writeOptional(this.size, Vector3i::write);
    }

    public void copy(
            WrapperPlayServerTestInstanceBlockStatus wrapperPlayServerTestInstanceBlockStatus) {
        this.status = wrapperPlayServerTestInstanceBlockStatus.status;
        this.size = wrapperPlayServerTestInstanceBlockStatus.size;
    }

    public Component getStatus() {
        return this.status;
    }

    public void setStatus(Component component) {
        this.status = component;
    }

    @Nullable
    public Vector3i getSize() {
        return this.size;
    }

    public void setSize(@Nullable Vector3i vector3i) {
        this.size = vector3i;
    }
}
