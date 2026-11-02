package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigServerConfigurationEnd
        extends PacketWrapper<WrapperConfigServerConfigurationEnd> {

    public WrapperConfigServerConfigurationEnd(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerConfigurationEnd() {
        super(PacketType.Configuration.Server.CONFIGURATION_END);
    }
}
