package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.status.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperStatusClientRequest extends PacketWrapper<WrapperStatusClientRequest> {

    public WrapperStatusClientRequest(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperStatusClientRequest() {
        super(PacketType.Status.Client.REQUEST);
    }

    @Override
    public void read() {}

    @Override
    public void write() {}

    public void copy(WrapperStatusClientRequest wrapperStatusClientRequest) {}
}
