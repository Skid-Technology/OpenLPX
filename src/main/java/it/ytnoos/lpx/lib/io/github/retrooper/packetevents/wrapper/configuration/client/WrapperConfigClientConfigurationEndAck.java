package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigClientConfigurationEndAck
        extends PacketWrapper<WrapperConfigClientConfigurationEndAck> {

    public WrapperConfigClientConfigurationEndAck(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientConfigurationEndAck() {
        super(PacketType.Configuration.Client.CONFIGURATION_END_ACK);
    }
}
