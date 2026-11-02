package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerServerLinks;

import java.util.List;

public class WrapperConfigServerServerLinks
        extends WrapperCommonServerServerLinks<WrapperConfigServerServerLinks> {

    public WrapperConfigServerServerLinks(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerServerLinks(List<WrapperCommonServerServerLinks.ServerLink> values) {
        super(PacketType.Configuration.Server.SERVER_LINKS, values);
    }
}
