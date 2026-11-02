package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LPXCommand implements CommandExecutor, TabCompleter {

    private static final boolean ENABLED =
            PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_19_1);

    private final LPXBukkit lpx;

    private final PluginCommand command;

    private final Map<String, SubCommand> entries;

    public LPXCommand(LPXBukkit lPXBukkit) {
        LinkedHashMap entries2 = new LinkedHashMap();
        this.entries = entries2;
        this.lpx = lPXBukkit;
        this.command = ((JavaPlugin) lPXBukkit.getPlugin()).getCommand("lpx");
        this.command.setExecutor(this);
        this.command.setTabCompleter(this);
        ReloadCommand reloadCommand = new ReloadCommand(lPXBukkit);
        this.handleSubCommand(reloadCommand);
        GuiCommand guiCommand = new GuiCommand(lPXBukkit);
        this.handleSubCommand(guiCommand);
        AlertsCommand alertsCommand = new AlertsCommand(lPXBukkit);
        this.handleSubCommand(alertsCommand);
        KickCommand kickCommand = new KickCommand(lPXBukkit);
        this.handleSubCommand(kickCommand);
        MonitorCommand monitorCommand = new MonitorCommand(lPXBukkit);
        this.handleSubCommand(monitorCommand);
        HealthCommand healthCommand = new HealthCommand(lPXBukkit);
        this.handleSubCommand(healthCommand);
        WatchdogCommand watchdogCommand = new WatchdogCommand(lPXBukkit);
        this.handleSubCommand(watchdogCommand);
        this.update();
    }

    private void handleSubCommand(SubCommand subCommand) {
        Map<String, it.ytnoos.lpx.commands.SubCommand> entries2 = this.entries;
        entries2.put(subCommand.getName().toLowerCase(Locale.ROOT), subCommand);
    }

    public boolean onCommand(CommandSender sender, Command command, String text, String[] texts) {
        if (!this.testCommandSender(sender)) {
            String text2 = this.createText();
            ColorUtils.send(sender, text2);
            return true;
        }
        boolean matches = this.testCommandSender2(sender);
        if (texts.length != 0) {
            String text3 = texts[0];
            SubCommand subCommand = this.createSubCommand(text3);
            if (subCommand == null) {
                LPXBukkit lPXBukkit = this.lpx;
                ConfigManager configManager = lPXBukkit.getConfigManager();
                Messages messages = configManager.getMessages();
                String text32 = messages.getText3();
                ColorUtils.send(sender, text32);
                return true;
            } else {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    LPXBukkit lPXBukkit2 = this.lpx;
                    ConfigManager configManager2 = lPXBukkit2.getConfigManager();
                    Messages messages2 = configManager2.getMessages();
                    String text22 = messages2.getText2();
                    String text4 = text22.replace("%permission%", subCommand.getPermission());
                    ColorUtils.send(sender, text4);
                    return true;
                } else {
                    if (subCommand.isEnabled() && !(sender instanceof Player)) {
                        LPXBukkit lPXBukkit3 = this.lpx;
                        ConfigManager configManager3 = lPXBukkit3.getConfigManager();
                        Messages messages3 = configManager3.getMessages();
                        String text5 =
                                messages3.getPrefix()
                                        + ChatColor.RED
                                        + " You must be a player to execute this command!";
                        ColorUtils.send(sender, text5);
                        return true;
                    } else {
                        subCommand.execute(sender, texts);
                        return true;
                    }
                }
            }
        } else {
            LPXBukkit lPXBukkit4 = this.lpx;
            ConfigManager configManager4 = lPXBukkit4.getConfigManager();
            Messages messages4 = configManager4.getMessages();
            String mainColor = messages4.getMainColor();
            lPXBukkit4 = this.lpx;
            ConfigManager configManager5 = lPXBukkit4.getConfigManager();
            Messages messages5 = configManager5.getMessages();
            String secondColor = messages5.getSecondColor();
            String text6 = this.lpx.getPlugin().getDescription().getVersion().replace('-', ' ');
            String text7 =
                    mainColor
                            + "●<reset><gray> Running "
                            + mainColor
                            + "<bold>LPX "
                            + text6
                            + "<reset><gray> by <reset>"
                            + secondColor
                            + "Ytnoos";
            ColorUtils.send(sender, text7);
            if (matches) {
                sender.sendMessage("");
            }
            for (SubCommand subCommand2 : this.entries.values()) {
                if (sender.hasPermission(subCommand2.getPermission())) {
                    if (!subCommand2.isEnabled() || sender instanceof Player) {
                        String name = subCommand2.getName();
                        String arguments = subCommand2.getArguments();
                        String description = subCommand2.getDescription();
                        String text8 =
                                mainColor
                                        + "<bold>> <reset>"
                                        + secondColor
                                        + "/lpx "
                                        + name
                                        + " "
                                        + arguments
                                        + " <reset><gray>- <reset><white>"
                                        + description;
                        ColorUtils.send(sender, text8);
                    }
                }
            }
            return true;
        }
    }

    public List<String> onTabComplete(
            CommandSender sender, Command command, String text, String[] texts) {
        if (!this.testCommandSender(sender)) {
            return Collections.emptyList();
        }
        if (texts.length == 1) {
            ArrayList<String> values2 = new ArrayList<>();
            String text2 = texts[0];
            for (SubCommand subCommand : this.entries.values()) {
                if (this.testSubCommand(sender, subCommand)) {
                    if (subCommand.getName().regionMatches(true, 0, text2, 0, text2.length())) {
                        values2.add(subCommand.getName());
                    }
                }
            }
            return values2;
        } else {
            String text3 = texts[0];
            SubCommand subCommand2 = this.createSubCommand(text3);
            if (subCommand2 != null) {
                CommandSender sender2 = sender;
                CommandSender sender3 = sender2;
                if (this.testSubCommand(sender3, subCommand2)) {
                    sender2 = sender;
                    CommandSender sender4 = sender2;
                    return subCommand2.createValues(sender4, texts);
                }
            }
            return Collections.emptyList();
        }
    }

    private String createText() {
        String text;
        try {
            text =
                    this.lpx
                            .getPlugin()
                            .getServer()
                            .spigot()
                            .getSpigotConfig()
                            .getString("messages.unknown-command");
        } catch (Throwable throwable) {
            LPXBukkit lPXBukkit = this.lpx;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            Messages messages = configManager.getMessages();
            text = messages.getText7();
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private SubCommand createSubCommand(String text) {
        return this.entries.get(text.toLowerCase(Locale.ROOT));
    }

    public boolean testCommandSender(CommandSender sender) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isEnabled4()) {
            if (!this.testCommandSender2(sender)) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        boolean matches = options.isEnabled4() && ENABLED;
        this.command.setPermission(matches ? String.join(";", this.createValues()) : null);
        this.command.setPermissionMessage(matches ? "" : null);
    }

    private List<String> createValues() {
        ArrayList<String> values2 = new ArrayList<>();
        for (SubCommand subCommand : this.entries.values()) {
            if (!values2.contains(subCommand.getPermission())) {
                values2.add(subCommand.getPermission());
            }
        }
        return values2;
    }

    private boolean testCommandSender2(CommandSender sender) {
        for (SubCommand subCommand : this.entries.values()) {
            if (sender.hasPermission(subCommand.getPermission())) {
                return true;
            }
        }
        return false;
    }

    private boolean testSubCommand(CommandSender sender, SubCommand subCommand) {
        if (sender.hasPermission(subCommand.getPermission())) {
            if (!subCommand.isEnabled() || sender instanceof Player) {
                return true;
            }
        }
        return false;
    }
}
