package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class WrapperLoginServerDisconnect extends PacketWrapper<WrapperLoginServerDisconnect> {

    private Component reason;

    public WrapperLoginServerDisconnect(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginServerDisconnect(Component component) {
        super(PacketType.Login.Server.DISCONNECT);
        this.reason = component;
    }

    @Override
    public void read() {
        this.reason = this.readComponentAsJSON();
    }

    @Override
    public void write() {
        this.writeComponentAsJSON(this.reason);
    }

    public void copy(WrapperLoginServerDisconnect wrapperLoginServerDisconnect) {
        this.reason = wrapperLoginServerDisconnect.reason;
    }

    public Component getReason() {
        return this.reason;
    }

    public void setReason(Component component) {
        this.reason = component;
    }
}
