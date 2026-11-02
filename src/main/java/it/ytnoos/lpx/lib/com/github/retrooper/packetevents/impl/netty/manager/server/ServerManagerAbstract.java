package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.manager.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;

public abstract class ServerManagerAbstract implements ServerManager {

    @Override
    public abstract ServerVersion getVersion();
}
