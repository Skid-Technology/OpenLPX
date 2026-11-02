package it.ytnoos.lpx.netty;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerAbstract;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;

public class PreViaPacketListener extends PacketListenerAbstract {

    private final PacketCheckProcessor listener;

    public PreViaPacketListener(PacketCheckProcessor packetCheckProcessor) {
        this.listener = packetCheckProcessor;
    }

    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        PacketCheckProcessor packetCheckProcessor = this.listener;
        Boolean matches = ((Boolean) (boolean) true);
        packetCheckProcessor.handlePacketReceive(packetReceiveEvent, ((boolean) matches));
    }

    public void onPacketEventExternal(PacketEvent event) {
        PacketCheckProcessor packetCheckProcessor = this.listener;
        Boolean matches = ((Boolean) (boolean) true);
        packetCheckProcessor.check(event, ((boolean) matches));
    }

    public boolean isPreVia() {
        return true;
    }
}
