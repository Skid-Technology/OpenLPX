package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class WrapperPlayServerActionBar extends PacketWrapper<WrapperPlayServerActionBar> {

    private Component actionBarText;

    public WrapperPlayServerActionBar(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerActionBar(Component component) {
        super(PacketType.Play.Server.ACTION_BAR);
        this.actionBarText = component;
    }

    @Override
    public void read() {
        this.actionBarText = this.readComponent();
    }

    @Override
    public void write() {
        this.writeComponent(this.actionBarText);
    }

    public void copy(WrapperPlayServerActionBar wrapperPlayServerActionBar) {
        this.actionBarText = wrapperPlayServerActionBar.actionBarText;
    }

    public Component getActionBarText() {
        return this.actionBarText;
    }

    public void setActionBarText(Component component) {
        this.actionBarText = component;
    }
}
