package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

public abstract class PacketListenerAbstract extends PacketListenerCommon {

    public PacketListenerAbstract(PacketListenerPriority packetListenerPriority) {
        super(packetListenerPriority);
    }

    public PacketListenerAbstract() {}

    @Override
    public void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {}

    @Override
    public void onPacketSend(PacketSendEvent packetSendEvent) {}
}
