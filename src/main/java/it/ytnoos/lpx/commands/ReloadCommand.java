package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "reload", "lpx.reload", "Reload configuration", false);
    }

    @Override
    public void execute(Object result, String[] texts) {
        CommandSender sender = (CommandSender) result;
        this.lpx.reloadConfig();
        String text = ChatColor.GREEN + "Config reloaded!";
        this.handleText(sender, text);
    }
}
