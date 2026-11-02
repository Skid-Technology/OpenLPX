package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener implements Listener {

    private final LPXBukkit lpx;

    public PlayerLoginListener(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent playerLoginEvent) {
        if (playerLoginEvent.getAddress() == null) {
            playerLoginEvent.setResult(Result.KICK_OTHER);
            LPXBukkit lPXBukkit = this.lpx;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            Messages messages = configManager.getMessages();
            playerLoginEvent.setKickMessage(messages.getText6());
        }
    }
}
