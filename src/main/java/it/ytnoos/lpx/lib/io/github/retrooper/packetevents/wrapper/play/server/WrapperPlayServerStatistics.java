package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Map;

public class WrapperPlayServerStatistics extends PacketWrapper<WrapperPlayServerStatistics> {

    private Map<String, Integer> statistics;

    public WrapperPlayServerStatistics(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerStatistics(Map<String, Integer> entries) {
        super(PacketType.Play.Server.STATISTICS);
        this.statistics = entries;
    }

    @Override
    public void read() {
        this.statistics = this.readMap(PacketWrapper::readString, PacketWrapper::readVarInt);
    }

    @Override
    public void write() {
        this.writeMap(this.statistics, PacketWrapper::writeString, PacketWrapper::writeVarInt);
    }

    public void copy(WrapperPlayServerStatistics wrapperPlayServerStatistics) {
        this.statistics = wrapperPlayServerStatistics.statistics;
    }

    public Map<String, Integer> getStatistics() {
        return this.statistics;
    }

    public void setStatistics(Map<String, Integer> entries) {
        this.statistics = entries;
    }
}
