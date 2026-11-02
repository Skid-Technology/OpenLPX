package it.ytnoos.lpx.api.protocol;

import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public interface PlayerRegistry {

    Optional<ProtocolPlayer> getPlayer(User user);

    Map<User, ProtocolPlayer> getPlayers();

    void uninjectAll();

    void uninject(User user);

    Optional<ProtocolPlayer> getPlayer(Player player);

    @Nullable
    ProtocolPlayer addPlayer(User user, ProtocolPlayer protocolPlayer);
}
