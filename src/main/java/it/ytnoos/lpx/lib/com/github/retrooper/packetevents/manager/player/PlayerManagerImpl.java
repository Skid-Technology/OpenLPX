package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.player;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.PlayerPingAccessorModern;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.protocolsupport.ProtocolSupportUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion.ViaVersionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.player.PlayerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol.ProtocolManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManagerImpl implements PlayerManager {

    @Internal
    public final Map<UUID, WeakReference<Player>> joiningPlayers = new ConcurrentHashMap<>();

    @Override
    public int getPing(@NotNull Object result) {
        return SpigotReflectionUtil.V_1_17_OR_HIGHER
                ? PlayerPingAccessorModern.getPing((Player) result)
                : SpigotReflectionUtil.getPlayerPingLegacy((Player) result);
    }

    @NotNull
    @Override
    public ClientVersion getClientVersion(@NotNull Object result) {
        Player player = (Player) result;
        User user;
        if ((user = this.getUser(player)) == null) {
            return ClientVersion.UNKNOWN;
        }
        if (user.getClientVersion() == null) {
            int value;
            if (ProtocolSupportUtil.isAvailable()) {
                value = ProtocolSupportUtil.getProtocolVersion(user.getAddress());
                PacketEvents.getAPI()
                        .getLogManager()
                        .debug(
                                "Requested ProtocolSupport for user "
                                        + user.getName()
                                        + "'s protocol version. Protocol version: "
                                        + value);
            } else if (ViaVersionUtil.isAvailable()) {
                value = ViaVersionUtil.getProtocolVersion(player);
                PacketEvents.getAPI()
                        .getLogManager()
                        .debug(
                                "Requested ViaVersion for "
                                        + player.getName()
                                        + "'s protocol version. Protocol version: "
                                        + value);
            } else {
                value = PacketEvents.getAPI().getServerManager().getVersion().getProtocolVersion();
                PacketEvents.getAPI()
                        .getLogManager()
                        .debug(
                                "No protocol translation plugins are available. We will assume "
                                        + user.getName()
                                        + "'s protocol version is the same as the server's protocol version. Protocol version: "
                                        + value);
            }
            ClientVersion byId = ClientVersion.getById(value);
            user.setClientVersion(byId);
        }
        return user.getClientVersion();
    }

    @Override
    public Object getChannel(@NotNull Object result) {
        UUID uniqueId = ((Player) result).getUniqueId();
        ProtocolManager protocolManager;
        Object result2;
        if ((result2 =
                                (protocolManager = PacketEvents.getAPI().getProtocolManager())
                                        .getChannel(uniqueId))
                        == null
                && (result2 = SpigotReflectionUtil.getChannel((Player) result)) != null) {
            synchronized (result2) {
                if (ChannelHelper.isOpen(result2)) {
                    protocolManager.setChannel(uniqueId, result2);
                }
            }
        }
        return result2;
    }

    @Override
    public User getUser(@NotNull Object result) {
        Player player = (Player) result;
        Object result2;
        return (result2 = this.getChannel(player)) == null
                ? null
                : PacketEvents.getAPI().getProtocolManager().getUser(result2);
    }
}
