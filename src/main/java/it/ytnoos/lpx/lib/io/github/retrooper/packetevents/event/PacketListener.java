package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

public interface PacketListener {

    default PacketListenerAbstract asAbstract(PacketListenerPriority result) {
        return new PacketListenerAbstract(result) {

            @Override
            public void onUserConnect(UserConnectEvent result) {
                PacketListener.this.onUserConnect(result);
            }

            @Override
            public void onUserLogin(UserLoginEvent result) {
                PacketListener.this.onUserLogin(result);
            }

            @Override
            public void onUserDisconnect(UserDisconnectEvent result) {
                PacketListener.this.onUserDisconnect(result);
            }

            @Override
            public void onPacketReceive(PacketReceiveEvent result) {
                PacketListener.this.onPacketReceive(result);
            }

            @Override
            public void onPacketSend(PacketSendEvent result) {
                PacketListener.this.onPacketSend(result);
            }

            @Override
            public void onPacketEventExternal(PacketEvent result) {
                PacketListener.this.onPacketEventExternal(result);
            }
        };
    }

    default void onUserConnect(UserConnectEvent userConnectEvent) {}

    default void onUserLogin(UserLoginEvent userLoginEvent) {}

    default void onUserDisconnect(UserDisconnectEvent userDisconnectEvent) {}

    default void onPacketReceive(PacketReceiveEvent packetReceiveEvent) {}

    default void onPacketSend(PacketSendEvent packetSendEvent) {}

    default void onPacketEventExternal(PacketEvent event) {}
}
