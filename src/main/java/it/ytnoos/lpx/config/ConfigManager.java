package it.ytnoos.lpx.config;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.check.RegisteredCheck;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;

import java.util.Iterator;
import java.util.List;

public class ConfigManager {

    private Messages messages;

    private GeneralOptions options;

    private DiscordOptions discord;

    private PacketLoggerOptions logger;

    public ConfigManager(LPXPlugin lPXPlugin) {
        lPXPlugin.saveDefaultConfig();
        ConfigUpdater.update(lPXPlugin);
    }

    public void load(LPXPlugin lPXPlugin) {
        Messages messages2 = new Messages(lPXPlugin);
        this.messages = messages2;
        Messages messages3 = this.messages;
        GeneralOptions generalOptions = new GeneralOptions(messages3, lPXPlugin);
        this.options = generalOptions;
        Messages messages4 = this.messages;
        PacketLoggerOptions packetLoggerOptions = new PacketLoggerOptions(messages4, lPXPlugin);
        this.logger = packetLoggerOptions;
        Messages messages5 = this.messages;
        DiscordOptions discordOptions = new DiscordOptions(messages5, lPXPlugin);
        this.discord = discordOptions;
    }

    public void reload(LPXBukkit lPXBukkit) {
        lPXBukkit.getPlugin().reload();
        LPXPlugin plugin = lPXBukkit.getPlugin();
        this.load(plugin);
        Iterator iterator2 = lPXBukkit.getCheckManager().getChecks().values().iterator();
        while (iterator2.hasNext()) {
            Iterator iterator3 = ((List) iterator2.next()).iterator();
            while (iterator3.hasNext()) {
                ((RegisteredCheck) iterator3.next()).getOptions().reload();
            }
        }
        iterator2 = lPXBukkit.getRegistry().getPlayers().values().iterator();
        while (iterator2.hasNext()) {
            Iterator<CheckContainer<?>> iterator4 =
                    ((ProtocolPlayer) iterator2.next()).getChecks().values().iterator();
            while (iterator4.hasNext()) {
                Iterator iterator5 = ((CheckContainer) iterator4.next()).iterator();
                while (iterator5.hasNext()) {
                    ((Check) iterator5.next()).reload();
                }
            }
        }
        lPXBukkit.getAlertManager().reload();
    }

    public GeneralOptions getOptions() {
        return this.options;
    }

    public Messages getMessages() {
        return this.messages;
    }

    public PacketLoggerOptions getLogger() {
        return this.logger;
    }

    public DiscordOptions getDiscord() {
        return this.discord;
    }
}
