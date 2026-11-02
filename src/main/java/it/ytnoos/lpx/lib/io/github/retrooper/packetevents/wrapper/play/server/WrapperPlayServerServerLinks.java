package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerServerLinks;

import java.util.List;

public class WrapperPlayServerServerLinks
        extends WrapperCommonServerServerLinks<WrapperPlayServerServerLinks> {

    public WrapperPlayServerServerLinks(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerServerLinks(List<WrapperCommonServerServerLinks.ServerLink> values) {
        super(PacketType.Play.Server.SERVER_LINKS, values);
    }
}
