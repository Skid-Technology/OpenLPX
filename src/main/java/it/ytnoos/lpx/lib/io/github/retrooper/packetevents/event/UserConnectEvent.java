package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class UserConnectEvent extends PacketEvent implements CancellableEvent, UserEvent {

    private final User user;

    private boolean cancelled;

    public UserConnectEvent(User user2) {
        this.user = user2;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public void call(PacketListenerCommon packetListenerCommon) {
        packetListenerCommon.onUserConnect(this);
    }
}
