package it.ytnoos.lpx;

import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.config.ConfigFile;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.settings.PacketEventsSettings;
import it.ytnoos.lpx.utils.PacketExceptionHandler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class LPXBootstrap extends JavaPlugin implements LPXPlugin {

    private LPX lpx;

    private FileConfiguration config;

    private File file;

    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI()
                .getSettings()
                .reEncodeByDefault(false)
                .checkForUpdates(false)
                .fullStackTrace(false)
                .kickOnPacketException(true);
        PacketEvents.getAPI().load();
    }

    public void onEnable() {
        FileConfiguration config2 = super.getConfig();
        if (config2.getBoolean("options.external-config", false)) {
            LPXBootstrap lPXBootstrap = this;
            ConfigFile configFile = new ConfigFile(lPXBootstrap, (String) "config.yml");
            this.config = configFile.getConfiguration();
            this.file = configFile.getFile();
        } else {
            this.config = config2;
            this.file = null;
        }
        LPXBootstrap lPXBootstrap2 = this;
        LPXBukkit lPXBukkit = new LPXBukkit(lPXBootstrap2);
        this.lpx = lPXBukkit;
        PacketEventsSettings settings = PacketEvents.getAPI().getSettings();
        PacketExceptionHandler packetExceptionHandler2 =
                new PacketExceptionHandler((LPXBukkit) this.lpx);
        settings.packetExceptionHandler(packetExceptionHandler2);
    }

    public void onDisable() {
        PacketEvents.getAPI().getSettings().packetExceptionHandler(null);
        if (this.lpx != null) {
            this.lpx.disable(true);
        }
    }

    public File getConfigFile() {
        return this.file != null ? this.file : new File(this.getDataFolder(), "config.yml");
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        if (this.file != null) {
            try {
                this.config.save(this.file);
            } catch (IOException oException) {
                oException.printStackTrace();
            }
        } else {
            super.saveConfig();
        }
    }

    public void reload() {
        if (super.getConfig().getBoolean("options.external-config", false)) {
            LPXBootstrap lPXBootstrap = this;
            ConfigFile configFile = new ConfigFile(lPXBootstrap, (String) "config.yml");
            this.config = configFile.getConfiguration();
        } else {
            super.reloadConfig();
            this.config = super.getConfig();
        }
    }
}
