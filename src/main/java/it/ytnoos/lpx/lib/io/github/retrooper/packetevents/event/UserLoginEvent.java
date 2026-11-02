package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

@NullMarked
public class UserLoginEvent extends PacketEvent implements CallableEvent, UserEvent, PlayerEvent {

    private final User user;

    @UnknownNullability private final Object player;

    public UserLoginEvent(User user2, Object player) {
        this.user = user2;
        this.player = player;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @NullUnmarked
    @Override
    @UnknownNullability
    public <T> T getPlayer() {
        return (T) this.player;
    }

    @Override
    public void call(PacketListenerCommon packetListenerCommon) {
        packetListenerCommon.onUserLogin(this);
    }
}
