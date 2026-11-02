package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerSetCompression
        extends PacketWrapper<WrapperPlayServerSetCompression> {

    private int threshold;

    public WrapperPlayServerSetCompression(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSetCompression(int threshold) {
        super(PacketType.Play.Server.SET_COMPRESSION);
        this.threshold = threshold;
    }

    @Override
    public void read() {
        this.threshold = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.threshold);
    }

    public void copy(WrapperPlayServerSetCompression wrapperPlayServerSetCompression) {
        this.threshold = wrapperPlayServerSetCompression.threshold;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
