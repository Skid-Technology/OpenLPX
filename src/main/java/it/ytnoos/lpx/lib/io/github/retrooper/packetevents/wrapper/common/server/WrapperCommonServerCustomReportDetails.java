package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Map;

public abstract class WrapperCommonServerCustomReportDetails<
                T extends WrapperCommonServerCustomReportDetails<T>>
        extends PacketWrapper<T> {

    private Map<String, String> details;

    public WrapperCommonServerCustomReportDetails(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperCommonServerCustomReportDetails(
            PacketTypeCommon packetTypeCommon, Map<String, String> entries) {
        super(packetTypeCommon);
        this.details = entries;
    }

    @Override
    public void read() {
        this.details =
                this.readMap(result -> result.readString(128), result -> result.readString(4096));
    }

    @Override
    public void write() {
        this.writeMap(
                this.details,
                (result, result2) -> result.writeString(result2, 128),
                (result, result2) -> result.writeString(result2, 4096));
    }

    public void copy(T t) {
        this.details = t.getDetails();
    }

    public Map<String, String> getDetails() {
        return this.details;
    }

    public void setDetails(Map<String, String> entries) {
        this.details = entries;
    }
}
