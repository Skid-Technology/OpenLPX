package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.AsyncScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.netty.NettyHealthMonitor;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MonitorCommand extends SubCommand {

    public MonitorCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "monitor", "lpx.metrics", "Turn off/on the PTD Monitor", false);
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
                    NettyHealthMonitor nettyHealthMonitor = lPXBukkit.getNettyHealthMonitor();
                    if (nettyHealthMonitor.isRunning()) {
                        lPXBukkit = this.lpx;
                        lPXBukkit.getNettyHealthMonitor().stop();
                        String text = ChatColor.RED + "Monitor disabled.";
                        this.handleText(sender, text);
                    } else {
                        lPXBukkit = this.lpx;
                        lPXBukkit.getNettyHealthMonitor().start();
                        String text2 =
                                ChatColor.GREEN
                                        + "Monitor enabled, it's not recommended to keep it enabled forever!";
                        this.handleText(sender, text2);
                    }
                });
    }
}
