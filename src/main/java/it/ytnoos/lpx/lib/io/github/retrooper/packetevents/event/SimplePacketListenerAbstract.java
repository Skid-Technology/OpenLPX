package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketConfigReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketConfigSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketHandshakeReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketLoginReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketLoginSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketPlaySendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketStatusReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketStatusSendEvent;

public abstract class SimplePacketListenerAbstract extends PacketListenerCommon {

    public SimplePacketListenerAbstract(PacketListenerPriority packetListenerPriority) {
        super(packetListenerPriority);
    }

    public SimplePacketListenerAbstract() {}

    @Override
    void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {
        if (packetReceiveEvent instanceof PacketHandshakeReceiveEvent) {
            this.onPacketHandshakeReceive((PacketHandshakeReceiveEvent) packetReceiveEvent);
        } else if (packetReceiveEvent instanceof PacketStatusReceiveEvent) {
            this.onPacketStatusReceive((PacketStatusReceiveEvent) packetReceiveEvent);
        } else if (packetReceiveEvent instanceof PacketLoginReceiveEvent) {
            this.onPacketLoginReceive((PacketLoginReceiveEvent) packetReceiveEvent);
        } else if (packetReceiveEvent instanceof PacketConfigReceiveEvent) {
            this.onPacketConfigReceive((PacketConfigReceiveEvent) packetReceiveEvent);
        } else if (packetReceiveEvent instanceof PacketPlayReceiveEvent) {
            this.onPacketPlayReceive((PacketPlayReceiveEvent) packetReceiveEvent);
        }
    }

    @Override
    void onPacketSend(PacketSendEvent packetSendEvent) {
        if (packetSendEvent instanceof PacketStatusSendEvent) {
            this.onPacketStatusSend((PacketStatusSendEvent) packetSendEvent);
        } else if (packetSendEvent instanceof PacketLoginSendEvent) {
            this.onPacketLoginSend((PacketLoginSendEvent) packetSendEvent);
        } else if (packetSendEvent instanceof PacketConfigSendEvent) {
            this.onPacketConfigSend((PacketConfigSendEvent) packetSendEvent);
        } else if (packetSendEvent instanceof PacketPlaySendEvent) {
            this.onPacketPlaySend((PacketPlaySendEvent) packetSendEvent);
        }
    }

    public void onPacketHandshakeReceive(PacketHandshakeReceiveEvent packetHandshakeReceiveEvent) {}

    public void onPacketStatusReceive(PacketStatusReceiveEvent packetStatusReceiveEvent) {}

    public void onPacketStatusSend(PacketStatusSendEvent packetStatusSendEvent) {}

    public void onPacketLoginReceive(PacketLoginReceiveEvent packetLoginReceiveEvent) {}

    public void onPacketLoginSend(PacketLoginSendEvent packetLoginSendEvent) {}

    public void onPacketConfigReceive(PacketConfigReceiveEvent packetConfigReceiveEvent) {}

    public void onPacketConfigSend(PacketConfigSendEvent packetConfigSendEvent) {}

    public void onPacketPlayReceive(PacketPlayReceiveEvent packetPlayReceiveEvent) {}

    public void onPacketPlaySend(PacketPlaySendEvent packetPlaySendEvent) {}
}
