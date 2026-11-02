package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerClearTitles extends PacketWrapper<WrapperPlayServerClearTitles> {

    private boolean reset;

    public WrapperPlayServerClearTitles(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerClearTitles(boolean reset) {
        super(PacketType.Play.Server.CLEAR_TITLES);
        this.reset = reset;
    }

    @Override
    public void read() {
        this.reset = this.readBoolean();
    }

    public void copy(WrapperPlayServerClearTitles wrapperPlayServerClearTitles) {
        this.reset = wrapperPlayServerClearTitles.reset;
    }

    @Override
    public void write() {
        this.writeBoolean(this.reset);
    }

    public boolean getReset() {
        return this.reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }
}
