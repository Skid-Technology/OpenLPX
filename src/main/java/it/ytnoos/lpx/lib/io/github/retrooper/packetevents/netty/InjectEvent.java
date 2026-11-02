package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

public interface InjectEvent {

    void inject(Object result, User user, boolean matches);
}
