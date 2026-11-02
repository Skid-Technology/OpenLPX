package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.AsyncScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.netty.NettyHealthMonitor;
import it.ytnoos.lpx.netty.NettyHealthReport;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HealthCommand extends SubCommand {

    private final NettyHealthMonitor monitor;

    private final NettyHealthReport nettyHealthReport;

    public HealthCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "health", "lpx.health", "View the netty health report", false);
        this.monitor = lPXBukkit.getNettyHealthMonitor();
        NettyHealthReport nettyHealthReport2 = new NettyHealthReport(lPXBukkit);
        this.nettyHealthReport = nettyHealthReport2;
    }

    @Override
    public void execute(Object result, String[] texts) {
        CommandSender sender = (CommandSender) result;
        NettyHealthMonitor nettyHealthMonitor = this.monitor;
        if (!nettyHealthMonitor.isRunning()) {
            String text = ChatColor.RED + "Monitor is not running. Enable it with /lpx monitor";
            ColorUtils.send(sender, text);
        } else {
            AsyncScheduler asyncScheduler = FoliaScheduler.getAsyncScheduler();
            LPXPlugin plugin = this.lpx.getPlugin();
            asyncScheduler.runNow(
                    plugin,
                    result2 -> {
                        NettyHealthMonitor nettyHealthMonitor2 = this.monitor;
                        String text2 = this.nettyHealthReport.createText(nettyHealthMonitor2);
                        ColorUtils.send(sender, text2);
                    });
        }
    }
}
