package it.ytnoos.lpx.api;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

public interface LPXPlugin extends Plugin {

    Logger getLogger();

    File getDataFolder();

    File getConfigFile();

    InputStream getResource(String text);

    PluginDescriptionFile getDescription();

    Server getServer();

    FileConfiguration getConfig();

    void reload();

    void saveDefaultConfig();

    void saveConfig();
}
