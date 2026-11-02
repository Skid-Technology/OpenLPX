package it.ytnoos.lpx.alerts;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.check.options.PersonalCheckOptions;
import it.ytnoos.lpx.api.event.LPXAlertEvent;
import it.ytnoos.lpx.api.event.LPXPunishEvent;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.commands.KickCommand;
import it.ytnoos.lpx.config.AlertsOptions;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.AsyncScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.GlobalRegionScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;
import it.ytnoos.lpx.logging.FileLogger;
import it.ytnoos.lpx.utils.ColorUtils;
import it.ytnoos.lpx.utils.TimeUtils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertManager {

    private final LPXBukkit lpx;

    private final FileLogger fileLogger;

    private final DiscordAlertHandler discord;

    private final Set<UUID> values;

    private long time;

    public AlertManager(LPXBukkit lPXBukkit) {
        HashSet values2 = new HashSet();
        this.values = values2;
        this.time = 0L;
        this.lpx = lPXBukkit;
        FileLogger fileLogger2 = new FileLogger(lPXBukkit);
        this.fileLogger = fileLogger2;
        DiscordAlertHandler discordAlertHandler = new DiscordAlertHandler(lPXBukkit);
        this.discord = discordAlertHandler;
    }

    public void load() {
        this.fileLogger.load();
        this.discord.load();
    }

    public void disable() {
        this.fileLogger.close();
    }

    public void flag(ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfos) {
        if (!protocolPlayer.isKicked()) {
            this.fileLogger.writeFlag(protocolPlayer, check, flagInfos);
            LPXBukkit lPXBukkit = this.lpx;
            if (lPXBukkit.getValues().size() >= 360) {
                lPXBukkit = this.lpx;
                lPXBukkit.getValues().removeLast();
            }
            lPXBukkit = this.lpx;
            Deque values2 = lPXBukkit.getValues();
            String username = protocolPlayer.getUsername();
            PersonalCheckOptions options = check.getOptions();
            long now = System.currentTimeMillis();
            ViolationRecord violationRecord =
                    new ViolationRecord(username, options, now, flagInfos);
            values2.addFirst(violationRecord);
            Runnable runnable =
                    () -> {
                        PluginManager pluginManager = Bukkit.getPluginManager();
                        LPXAlertEvent result = new LPXAlertEvent(protocolPlayer, check, flagInfos);
                        pluginManager.callEvent(result);
                    };
            this.callEvent(runnable);
            int minVl = check.getOptions().getCheckOptions().getMinVl();
            if (minVl != -1 && check.getOptions().getVl() >= minVl) {
                lPXBukkit = this.lpx;
                ConfigManager configManager = lPXBukkit.getConfigManager();
                Messages messages = configManager.getMessages();
                AlertsOptions alertsOptions = messages.getAlertsOptions();
                String format = alertsOptions.getFormat();
                String text = this.replace(format, protocolPlayer, check, flagInfos);
                Logger logger = this.lpx.getLogger();
                logger.log(Level.INFO, ColorUtils.createText(text));
                long lastPunish = protocolPlayer.getLastPunish();
                Long value = ((Long) lastPunish);
                long value2 = TimeUtils.calculateTime(value);
                LPXBukkit lPXBukkit2 = this.lpx;
                ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                GeneralOptions options2 = configManager2.getOptions();
                if (value2 >= options2.getLimit6()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int value3 = 0;
                    while (true) {
                        if (value3 >= alertsOptions.getHover().size()) {
                            Action action = Action.SHOW_TEXT;
                            String text2 = stringBuilder.toString();
                            HoverEvent hoverEvent =
                                    new HoverEvent(action, ColorUtils.createBaseComponents(text2));
                            BaseComponent[] baseComponents = ColorUtils.createBaseComponents(text);
                            int value4 = baseComponents.length;
                            for (int index = 0; index < value4; index++) {
                                baseComponents[index].setHoverEvent(hoverEvent);
                            }
                            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                            onlinePlayers.forEach(
                                    result -> {
                                        Object[] result3 = new Object[0];
                                        if (result.hasPermission(alertsOptions.getPermission())
                                                && !this.values.contains(result.getUniqueId())) {
                                            result.getPlayer().spigot().sendMessage(baseComponents);
                                        }
                                    });
                            AsyncScheduler asyncScheduler = FoliaScheduler.getAsyncScheduler();
                            LPXPlugin plugin = this.lpx.getPlugin();
                            asyncScheduler.runNow(
                                    plugin,
                                    result2 -> {
                                        Check result3 = check;
                                        Check check2 = result3;
                                        this.discord.handleFlagInfos(
                                                protocolPlayer, check2, flagInfos);
                                    });
                            return;
                        }
                        String text3 = (String) alertsOptions.getHover().get(value3);
                        stringBuilder.append(this.replace(text3, protocolPlayer, check, flagInfos));
                        if (value3 != alertsOptions.getHover().size() - 1) {
                            stringBuilder.append('\n');
                        }
                        value3++;
                    }
                }
            }
        }
    }

    public void punish(ProtocolPlayer protocolPlayer, Check check) {
        long lastPunish = protocolPlayer.getLastPunish();
        Long value = ((Long) lastPunish);
        long value2 = TimeUtils.calculateTime(value);
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (value2 >= options.getLimit6()) {
            Runnable runnable =
                    () -> {
                        PluginManager player = Bukkit.getPluginManager();
                        LPXPunishEvent lPXPunishEvent = new LPXPunishEvent(protocolPlayer, check);
                        player.callEvent(lPXPunishEvent);
                    };
            this.callEvent(runnable);
            protocolPlayer.updateLastPunish();
            for (String text : check.getOptions().getCheckOptions().getPunishCommands()) {
                String text2 = text;
                String text3 = this.createText(text2, protocolPlayer);
                text = text3;
                if (text3.startsWith("lpx kick")) {
                    LPXBukkit lPXBukkit2 = this.lpx;
                    ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
                    String[] texts = text.substring(4).split(" ");
                    CommandSender sender = (CommandSender) consoleSender;
                    KickCommand.runCommand(lPXBukkit2, sender, texts);
                } else {
                    String text4 = text;
                    GlobalRegionScheduler globalRegionScheduler =
                            FoliaScheduler.getGlobalRegionScheduler();
                    LPXPlugin plugin = this.lpx.getPlugin();
                    globalRegionScheduler.execute(
                            plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text4));
                }
            }
            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
            onlinePlayers.forEach(
                    result -> {
                        LPXBukkit lPXBukkit3 = this.lpx;
                        ConfigManager configManager2 = lPXBukkit3.getConfigManager();
                        Messages messages = configManager2.getMessages();
                        AlertsOptions alertsOptions = messages.getAlertsOptions();
                        if (result.hasPermission(alertsOptions.getPermission())
                                && !this.values.contains(result.getUniqueId())) {
                            Player player = result.getPlayer();
                            LPXBukkit lPXBukkit4 = this.lpx;
                            ConfigManager configManager3 = lPXBukkit4.getConfigManager();
                            Messages messages2 = configManager3.getMessages();
                            String text5 = messages2.getText();
                            FlagInfo[] flagInfos = new FlagInfo[0];
                            String text6 = this.replace(text5, protocolPlayer, check, flagInfos);
                            Player player2 = player;
                            player = player2;
                            Player player3 = player;
                            ColorUtils.handleText(player3, text6);
                        }
                    });
        }
    }

    public String replace(
            String text, ProtocolPlayer protocolPlayer, Check check, FlagInfo[] flagInfos) {
        String text2 = this.createText(text);
        String text3 = this.createText(text2, protocolPlayer);
        String text4 = text3;
        text3 = text4;
        String text5 = text3;
        return this.createText(text5, check, flagInfos);
    }

    public String createText(String text, ProtocolPlayer protocolPlayer) {
        return text.replace("%player%", protocolPlayer.getUsername())
                .replace("%uuid%", protocolPlayer.getUniqueId().toString());
    }

    private String createText(String text, Check check, FlagInfo[] flagInfos) {
        StringBuilder stringBuilder = new StringBuilder(flagInfos.length == 0 ? ":(" : "\n");
        int value = flagInfos.length;
        for (int index = 0; index < value; index++) {
            FlagInfo flagInfo = flagInfos[index];
            LPXBukkit lPXBukkit = this.lpx;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            Messages messages = configManager.getMessages();
            StringBuilder stringBuilder2 =
                    stringBuilder
                            .append(messages.getSecondColor())
                            .append(flagInfo.getName())
                            .append(": ")
                            .append(ChatColor.GRAY);
            String text2 = String.valueOf(flagInfo.getValue());
            stringBuilder2.append(ColorUtils.escapeTags(text2)).append('\n');
        }
        return text.replace("%check%", check.getType().getCategory().getFormattedName())
                .replace("%type%", check.getType().getId().name())
                .replace("%vl%", check.getOptions().getVl() + "")
                .replace("%infos%", stringBuilder.toString())
                .replace("%description%", check.getType().getDescription())
                .replace("%max-vl%", check.getOptions().getCheckOptions().getMaxVL() + "");
    }

    private void callEvent(Runnable runnable) {
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_13)) {
            FoliaScheduler.getGlobalRegionScheduler().execute(this.lpx.getPlugin(), runnable);
        } else {
            runnable.run();
        }
    }

    private String createText(String text) {
        return text.replace("%server%", this.lpx.getServer());
    }

    public void sendPermissionAlert(String text, String text2) {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        onlinePlayers.forEach(
                result -> {
                    if (result.hasPermission(text2)
                            && !this.values.contains(result.getUniqueId())) {
                        Player player = ((Player) result);
                        ColorUtils.handleText(player, text);
                    }
                });
        Logger logger = this.lpx.getLogger();
        logger.log(Level.INFO, ColorUtils.createText(text));
    }

    public boolean testPlayer(Player player) {
        UUID uniqueId = player.getUniqueId();
        if (this.values.contains(uniqueId)) {
            this.values.remove(uniqueId);
            return true;
        } else {
            this.values.add(uniqueId);
            return false;
        }
    }

    public void handleValue(Object result) {
        this.values.remove(result);
    }

    public void reload() {
        this.discord.load();
    }

    public FileLogger getFileLogger() {
        return this.fileLogger;
    }

    public Set<UUID> getValues() {
        return this.values;
    }
}
