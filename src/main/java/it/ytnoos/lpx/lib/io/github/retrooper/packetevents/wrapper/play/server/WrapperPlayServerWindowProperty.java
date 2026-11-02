package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWindowProperty
        extends PacketWrapper<WrapperPlayServerWindowProperty> {

    private int windowId;

    private int id;

    private int value;

    public WrapperPlayServerWindowProperty(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWindowProperty(byte value, int value2, int value3) {
        this((int) value, value2, value3);
    }

    public WrapperPlayServerWindowProperty(int windowId, int id, int value) {
        super(PacketType.Play.Server.WINDOW_PROPERTY);
        this.windowId = windowId;
        this.id = id;
        this.value = value;
    }

    @Override
    public void read() {
        this.windowId = this.readContainerId();
        this.id = this.readShort();
        this.value = this.readShort();
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowId);
        this.writeShort(this.id);
        this.writeShort(this.value);
    }

    public void copy(WrapperPlayServerWindowProperty wrapperPlayServerWindowProperty) {
        this.windowId = wrapperPlayServerWindowProperty.windowId;
        this.id = wrapperPlayServerWindowProperty.id;
        this.value = wrapperPlayServerWindowProperty.value;
    }

    public int getContainerId() {
        return this.windowId;
    }

    public void setContainerId(int windowId) {
        this.windowId = windowId;
    }

    @Deprecated
    public byte getWindowIdB() {
        return (byte) this.getContainerId();
    }

    @Deprecated
    public void setWindowId(byte value) {
        this.setContainerId(value);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
