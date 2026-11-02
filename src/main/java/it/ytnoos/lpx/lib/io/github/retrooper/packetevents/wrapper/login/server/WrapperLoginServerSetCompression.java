package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperLoginServerSetCompression
        extends PacketWrapper<WrapperLoginServerSetCompression> {

    private int threshold;

    public WrapperLoginServerSetCompression(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginServerSetCompression(int threshold) {
        super(PacketType.Login.Server.SET_COMPRESSION);
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

    public void copy(WrapperLoginServerSetCompression wrapperLoginServerSetCompression) {
        this.threshold = wrapperLoginServerSetCompression.threshold;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
