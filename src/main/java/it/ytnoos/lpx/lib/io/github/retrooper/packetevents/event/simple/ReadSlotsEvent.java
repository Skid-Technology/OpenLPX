package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.ProtocolPacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class ReadSlotsEvent extends PacketEvent {

    private final PacketWrapper<?> wrapper;

    private final ProtocolPacketEvent event;

    private int slotsCount;

    public ReadSlotsEvent(
            PacketWrapper<?> packetWrapper, ProtocolPacketEvent protocolPacketEvent, int slotsCount)
            throws PacketProcessException {
        this.wrapper = packetWrapper;
        this.event = protocolPacketEvent;
        this.slotsCount = slotsCount;
    }

    public PacketWrapper<?> getWrapper() {
        return this.wrapper;
    }

    public ProtocolPacketEvent getEvent() {
        return this.event;
    }

    public int getSlotsCount() {
        return this.slotsCount;
    }
}
