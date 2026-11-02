package it.ytnoos.lpx.listeners;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.commands.LPXCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandVisibilityListener implements Listener {

    private final LPXBukkit lpx;

    public CommandVisibilityListener(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    @EventHandler
    public void commandSend(PlayerCommandSendEvent playerCommandSendEvent) {
        LPXBukkit lPXBukkit = this.lpx;
        LPXCommand command = lPXBukkit.getCommand();
        Player player = playerCommandSendEvent.getPlayer();
        CommandSender sender = (CommandSender) player;
        if (!command.testCommandSender(sender)) {
            playerCommandSendEvent.getCommands().remove("lpx");
            playerCommandSendEvent.getCommands().remove("lpx:lpx");
        }
    }
}
