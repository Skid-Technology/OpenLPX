package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ViaVersionAccessor {

    int UNKNOWN_PROTOCOL_VERSION = -1;

    int getProtocolVersion(Player player);

    int getProtocolVersion(User user);

    Class<?> getUserConnectionClass();

    Class<?> getBukkitDecodeHandlerClass();

    Class<?> getBukkitEncodeHandlerClass();
}
