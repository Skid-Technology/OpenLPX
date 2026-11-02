package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;

import org.bukkit.Bukkit;

public class ServerManagerImpl implements ServerManager {

    private ServerVersion serverVersion;

    private ServerVersion resolveVersionNoCache() {
        LogManager logManager = PacketEvents.getAPI().getLogManager();
        String bukkitVersion = Bukkit.getBukkitVersion();
        ServerVersion serverVersion = ServerVersion.V_1_8_8;
        String text =
                "Your server software is preventing us from checking the Minecraft Server version. This is what we found: "
                        + Bukkit.getBukkitVersion()
                        + ". We will assume the Server version is "
                        + serverVersion.name()
                        + "...\n If you need assistance, join our Discord server: https://discord.gg/DVHxPPxHZc";
        if (bukkitVersion.contains("Unknown")) {
            logManager.warn(text);
            return serverVersion;
        }
        ServerVersion[] serverVersions;
        int value = (serverVersions = ServerVersion.reversedValues()).length;
        for (int index = 0; index < value; index++) {
            ServerVersion serverVersion2 = serverVersions[index];
            if (bukkitVersion.contains(serverVersion2.getReleaseName())) {
                return serverVersion2;
            }
        }
        logManager.warn(text);
        return serverVersion;
    }

    @Override
    public ServerVersion getVersion() {
        if (this.serverVersion == null) {
            this.serverVersion = this.resolveVersionNoCache();
        }
        return this.serverVersion;
    }

    @Override
    public Object getRegistryCacheKey(User user, ClientVersion clientVersion) {
        return GlobalRegistryHolder.getGlobalRegistryCacheKey(user, clientVersion);
    }
}
