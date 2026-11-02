package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.AsyncScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.netty.NettyWatchdog;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class WatchdogCommand extends SubCommand {

    public WatchdogCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "watchdog", "lpx.watchdog", "Toggle netty watchdog", false);
    }

    @Override
    public void execute(Object result, String[] texts) {
        CommandSender sender = (CommandSender) result;
        AsyncScheduler asyncScheduler = FoliaScheduler.getAsyncScheduler();
        LPXPlugin plugin = this.lpx.getPlugin();
        asyncScheduler.runNow(
                plugin,
                result2 -> {
                    LPXBukkit lPXBukkit = this.lpx;
                    NettyWatchdog nettyWatchdog = lPXBukkit.getNettyWatchdog();
                    if (nettyWatchdog.isRunning()) {
                        String text = ChatColor.YELLOW + "Stopping the watchdog thread...";
                        this.handleText(sender, text);
                    } else {
                        String text2 = ChatColor.YELLOW + "Starting the watchdog thread...";
                        this.handleText(sender, text2);
                        String text3 =
                                ChatColor.RED
                                        + "WARNING! Do not use this in production because it may cause TPS Drops!";
                        this.handleText(sender, text3);
                    }
                    lPXBukkit = this.lpx;
                    lPXBukkit.getNettyWatchdog().update();
                    String text4 =
                            nettyWatchdog.isRunning()
                                    ? ChatColor.GREEN + "Watchdog thread started!"
                                    : ChatColor.RED + "Watchdog thread stopped!";
                    this.handleText(sender, text4);
                });
    }
}
