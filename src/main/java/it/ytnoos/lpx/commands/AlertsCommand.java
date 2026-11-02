package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.config.AlertsOptions;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand extends SubCommand {

    public AlertsCommand(LPXBukkit lPXBukkit) {
        super(
                lPXBukkit,
                "alerts",
                (((lPXBukkit.getConfigManager()).getMessages()).getAlertsOptions()).getPermission(),
                "Toggle alerts",
                true);
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
    }

    @Override
    public void execute(Object result, String[] texts) {
        CommandSender sender = (CommandSender) result;
        LPXBukkit lPXBukkit = this.lpx;
        AlertManager alertManager = lPXBukkit.getAlertManager();
        Player player = (Player) sender;
        if (alertManager.testPlayer(player)) {
            LPXBukkit lPXBukkit2 = this.lpx;
            ConfigManager configManager = lPXBukkit2.getConfigManager();
            Messages messages = configManager.getMessages();
            AlertsOptions alertsOptions = messages.getAlertsOptions();
            String enabled = alertsOptions.getEnabled();
            ColorUtils.send(sender, enabled);
        } else {
            LPXBukkit lPXBukkit3 = this.lpx;
            ConfigManager configManager2 = lPXBukkit3.getConfigManager();
            Messages messages2 = configManager2.getMessages();
            AlertsOptions alertsOptions2 = messages2.getAlertsOptions();
            String disabled = alertsOptions2.getDisabled();
            ColorUtils.send(sender, disabled);
        }
    }
}
