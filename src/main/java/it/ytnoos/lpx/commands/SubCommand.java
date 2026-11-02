package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;
import it.ytnoos.lpx.utils.ColorUtils;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public abstract class SubCommand {

    protected final LPXBukkit lpx;

    private final String name;

    private final String permission;

    private final String description;

    private final boolean enabled;

    private final String arguments;

    protected SubCommand(
            LPXBukkit lPXBukkit, String text, String text2, String text3, boolean matches) {
        this(lPXBukkit, text, text2, text3, matches, "");
    }

    protected SubCommand(
            LPXBukkit lPXBukkit,
            String name,
            String permission,
            String description,
            boolean enabled,
            String arguments) {
        this.lpx = lPXBukkit;
        this.name = name;
        this.permission = permission;
        this.description = description;
        this.enabled = enabled;
        this.arguments = arguments;
    }

    public abstract void execute(Object result, String[] texts);

    public List createValues(CommandSender sender, String[] texts) {
        return Collections.emptyList();
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getArguments() {
        return this.arguments;
    }

    public void handleText(CommandSender sender, String text) {
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
        String text2 = messages.getPrefix() + " " + text;
        ColorUtils.send(sender, text2);
    }
}
