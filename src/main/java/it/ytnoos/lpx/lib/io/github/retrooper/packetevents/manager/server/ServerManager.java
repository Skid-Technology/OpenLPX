package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jetbrains.annotations.Nullable;

public interface ServerManager {

    ServerVersion getVersion();

    default SystemOS getOS() {
        return SystemOS.getOS();
    }

    @Nullable
    default Object getRegistryCacheKey(User user, ClientVersion clientVersion) {
        return null;
    }
}
