package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.bukkit;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.MetricsBase;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts.CustomChart;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.json.JsonObjectBuilder;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;

public class Metrics {

    private final Plugin plugin;

    private final MetricsBase metricsBase;

    public Metrics(Plugin plugin2, int value) {
        this.plugin = plugin2;
        File file = new File(plugin2.getDataFolder().getParentFile(), "bStats");
        File file2;
        YamlConfiguration yamlConfiguration;
        if (!(yamlConfiguration =
                        YamlConfiguration.loadConfiguration(file2 = new File(file, "config.yml")))
                .isSet("serverUuid")) {
            yamlConfiguration.addDefault("enabled", true);
            yamlConfiguration.addDefault("serverUuid", UUID.randomUUID().toString());
            yamlConfiguration.addDefault("logFailedRequests", false);
            yamlConfiguration.addDefault("logSentData", false);
            yamlConfiguration.addDefault("logResponseStatusText", false);
            yamlConfiguration
                    .options()
                    .header(
                            "bStats (https://bStats.org) collects some basic information for plugin authors, like how\nmany people use their plugin and their total player count. It's recommended to keep bStats\nenabled, but if you're not comfortable with this, you can turn this setting off. There is no\nperformance penalty associated with having metrics enabled, and data sent to bStats is fully\nanonymous.")
                    .copyDefaults(true);
            try {
                yamlConfiguration.save(file2);
            } catch (IOException oException) {
            }
        }
        boolean booleanValue = yamlConfiguration.getBoolean("enabled", true);
        String string = yamlConfiguration.getString("serverUuid");
        boolean booleanValue2 = yamlConfiguration.getBoolean("logFailedRequests", false);
        boolean booleanValue3 = yamlConfiguration.getBoolean("logSentData", false);
        boolean booleanValue4 = yamlConfiguration.getBoolean("logResponseStatusText", false);
        boolean matches = false;
        try {
            matches = Class.forName("io.papermc.paper.threadedregions.RegionizedServer") != null;
        } catch (Exception exception) {
        }
        this.metricsBase =
                new MetricsBase(
                        "bukkit",
                        string,
                        value,
                        booleanValue,
                        this::appendPlatformData,
                        this::appendServiceData,
                        matches ? null : result -> Bukkit.getScheduler().runTask(plugin2, result),
                        plugin2::isEnabled,
                        (result, result2) ->
                                this.plugin.getLogger().log(Level.WARNING, result, result2),
                        result -> this.plugin.getLogger().log(Level.INFO, result),
                        booleanValue2,
                        booleanValue3,
                        booleanValue4,
                        false);
    }

    public void shutdown() {
        this.metricsBase.shutdown();
    }

    public void addCustomChart(CustomChart customChart) {
        this.metricsBase.addCustomChart(customChart);
    }

    private void appendPlatformData(JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.appendField("playerAmount", this.getPlayerAmount());
        jsonObjectBuilder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
        jsonObjectBuilder.appendField("bukkitVersion", Bukkit.getVersion());
        jsonObjectBuilder.appendField("bukkitName", Bukkit.getName());
        jsonObjectBuilder.appendField("javaVersion", System.getProperty("java.version"));
        jsonObjectBuilder.appendField("osName", System.getProperty("os.name"));
        jsonObjectBuilder.appendField("osArch", System.getProperty("os.arch"));
        jsonObjectBuilder.appendField("osVersion", System.getProperty("os.version"));
        jsonObjectBuilder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
    }

    private void appendServiceData(JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.appendField("pluginVersion", this.plugin.getDescription().getVersion());
    }

    private int getPlayerAmount() {
        try {
            Method method;
            return (method = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers"))
                            .getReturnType()
                            .equals(Collection.class)
                    ? ((Collection) method.invoke(Bukkit.getServer())).size()
                    : ((Player[]) method.invoke(Bukkit.getServer())).length;
        } catch (Exception exception) {
            return Bukkit.getOnlinePlayers().size();
        }
    }
}
