package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerCustomReportDetails;

import java.util.Map;

public class WrapperConfigServerCustomReportDetails
        extends WrapperCommonServerCustomReportDetails<WrapperConfigServerCustomReportDetails> {

    public WrapperConfigServerCustomReportDetails(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerCustomReportDetails(Map<String, String> entries) {
        super(PacketType.Configuration.Server.CUSTOM_REPORT_DETAILS, entries);
    }
}
