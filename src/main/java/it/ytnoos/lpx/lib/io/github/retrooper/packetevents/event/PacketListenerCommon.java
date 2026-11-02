package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

public abstract class PacketListenerCommon {

    private final PacketListenerPriority priority;

    public PacketListenerCommon(PacketListenerPriority packetListenerPriority) {
        this.priority = packetListenerPriority;
    }

    public PacketListenerCommon() {
        this.priority = PacketListenerPriority.NORMAL;
    }

    public PacketListenerPriority getPriority() {
        return this.priority;
    }

    public void onUserConnect(UserConnectEvent userConnectEvent) {}

    public void onUserLogin(UserLoginEvent userLoginEvent) {}

    public void onUserDisconnect(UserDisconnectEvent userDisconnectEvent) {}

    void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {}

    void onPacketSend(PacketSendEvent packetSendEvent) {}

    public void onPacketEventExternal(PacketEvent event) {}

    public boolean isPreVia() {
        return false;
    }
}
