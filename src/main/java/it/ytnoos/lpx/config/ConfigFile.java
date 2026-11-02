package it.ytnoos.lpx.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigFile {

    private final File file;

    private final String name;

    private FileConfiguration configuration;

    public ConfigFile(JavaPlugin javaPlugin, String name) {
        if (!name.endsWith(".yml")) {
            name = name + ".yml";
        }
        this.name = name;
        String parent =
                Bukkit.getServer()
                        .getWorldContainer()
                        .getAbsoluteFile()
                        .getParentFile()
                        .getParent();
        File file2 = new File(parent + "/config/", name);
        this.file = file2;
        this.load(javaPlugin);
    }

    private void load(JavaPlugin javaPlugin) {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            javaPlugin.saveResource(this.name, false);
        }
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        this.configuration = yamlConfiguration;
        try {
            this.configuration.load(this.file);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void update() {
        try {
            this.configuration.save(this.file);
        } catch (IOException oException) {
            oException.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public int getInt(String text) {
        return this.configuration.getInt(text);
    }

    public int calculateLimit(String text, int value) {
        return this.configuration.getInt(text, value);
    }

    public String getString(String text) {
        return this.configuration.getString(text);
    }

    public String createText(String text, String text2) {
        return this.configuration.getString(text, text2);
    }

    public double calculateValue(String text) {
        return this.configuration.getDouble(text);
    }

    public double getDouble(String text, double value) {
        return this.configuration.getDouble(text, value);
    }

    public boolean testText(String text) {
        return this.configuration.getBoolean(text);
    }

    public boolean getBoolean(String text, boolean matches) {
        return this.configuration.getBoolean(text, matches);
    }

    public long calculateTime(String text) {
        return this.configuration.getLong(text);
    }

    public long getLong(String text, long value) {
        return this.configuration.getLong(text, value);
    }

    public List<?> createValues(String text) {
        return this.configuration.getList(text);
    }

    public List<?> getList(String text, List values) {
        return this.configuration.getList(text, values);
    }

    public List<String> createValues2(String text) {
        return this.configuration.getStringList(text);
    }

    public ConfigurationSection createConfiguration(String text) {
        return this.configuration.getConfigurationSection(text);
    }
}
