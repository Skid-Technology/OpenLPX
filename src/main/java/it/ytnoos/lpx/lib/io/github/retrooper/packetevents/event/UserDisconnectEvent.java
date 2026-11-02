package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class UserDisconnectEvent extends PacketEvent implements UserEvent {

    private final User user;

    public UserDisconnectEvent(User user2) {
        this.user = user2;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void call(PacketListenerCommon packetListenerCommon) {
        packetListenerCommon.onUserDisconnect(this);
    }
}
