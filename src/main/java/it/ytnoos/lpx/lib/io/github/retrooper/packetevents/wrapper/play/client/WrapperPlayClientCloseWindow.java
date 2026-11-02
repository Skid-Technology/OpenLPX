package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientCloseWindow extends PacketWrapper<WrapperPlayClientCloseWindow> {

    private int windowID;

    public WrapperPlayClientCloseWindow(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientCloseWindow(int windowID) {
        super(PacketType.Play.Client.CLOSE_WINDOW);
        this.windowID = windowID;
    }

    @Override
    public void read() {
        this.windowID = this.readContainerId();
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowID);
    }

    public void copy(WrapperPlayClientCloseWindow wrapperPlayClientCloseWindow) {
        this.windowID = wrapperPlayClientCloseWindow.windowID;
    }

    public int getWindowId() {
        return this.windowID;
    }

    public void setWindowId(int windowID) {
        this.windowID = windowID;
    }
}
