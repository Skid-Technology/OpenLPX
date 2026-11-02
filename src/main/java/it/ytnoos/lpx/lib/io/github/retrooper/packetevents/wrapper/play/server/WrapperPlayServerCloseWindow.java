package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerCloseWindow extends PacketWrapper<WrapperPlayServerCloseWindow> {

    private int windowId;

    public WrapperPlayServerCloseWindow(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCloseWindow() {
        this(0);
    }

    public WrapperPlayServerCloseWindow(int windowId) {
        super(PacketType.Play.Server.CLOSE_WINDOW);
        this.windowId = windowId;
    }

    @Override
    public void read() {
        this.windowId = this.readContainerId();
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowId);
    }

    public void copy(WrapperPlayServerCloseWindow wrapperPlayServerCloseWindow) {
        this.windowId = wrapperPlayServerCloseWindow.windowId;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }
}
