package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEnterCombatEvent
        extends PacketWrapper<WrapperPlayServerEnterCombatEvent> {

    public WrapperPlayServerEnterCombatEvent(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEnterCombatEvent() {
        super(PacketType.Play.Server.ENTER_COMBAT_EVENT);
    }

    @Override
    public void read() {}

    @Override
    public void write() {}

    public void copy(WrapperPlayServerEnterCombatEvent wrapperPlayServerEnterCombatEvent) {}
}
