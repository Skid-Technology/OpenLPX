package it.ytnoos.lpx.activation;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.metrics.MetricsLite;
import it.ytnoos.lpx.commands.LPXCommand;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.AsyncScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.EventManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;
import it.ytnoos.lpx.listeners.CommandVisibilityListener;
import it.ytnoos.lpx.listeners.InventoryListener;
import it.ytnoos.lpx.listeners.PlayerLoginListener;
import it.ytnoos.lpx.listeners.PlayerStateListener;
import it.ytnoos.lpx.listeners.RedstoneListener;
import it.ytnoos.lpx.netty.PacketCheckListener;
import it.ytnoos.lpx.netty.PacketCheckProcessor;
import it.ytnoos.lpx.netty.PreViaPacketListener;
import it.ytnoos.lpx.scheduler.PlayerTickTask;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;

import java.util.function.Consumer;
import java.util.logging.Level;

public class ActivationState {

    private static boolean enabled;

    private static final String TEXT = "LPX enabled!";

    public static void enable(LPXBukkit lPXBukkit) {
        if (!enabled) {
            enabled = true;
            ConfigManager configManager = lPXBukkit.getConfigManager();
            LPXPlugin plugin = lPXBukkit.getPlugin();
            configManager.load(plugin);
            lPXBukkit.getAlertManager().load();
            lPXBukkit.getCheckManager().registerChecks();
            InventoryManager inventoryManager = lPXBukkit.getInventoryManager();
            inventoryManager.init();
            Listener[] listeners = new Listener[2];
            PlayerStateListener playerStateListener = new PlayerStateListener(lPXBukkit);
            listeners[0] = playerStateListener;
            InventoryListener inventoryListener = new InventoryListener(lPXBukkit);
            listeners[1] = inventoryListener;
            lPXBukkit.registerListeners(listeners);
            ServerVersion version = PacketEvents.getAPI().getServerManager().getVersion();
            if (version.is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_13)
                    && version.is(VersionComparison.OLDER_THAN, ServerVersion.V_1_19_4)) {
                RedstoneListener redstoneListener = new RedstoneListener(lPXBukkit);
                lPXBukkit.registerListener(redstoneListener);
            }
            if (version.is(VersionComparison.NEWER_THAN_OR_EQUALS, ServerVersion.V_1_13)) {
                CommandVisibilityListener commandVisibilityListener =
                        new CommandVisibilityListener(lPXBukkit);
                lPXBukkit.registerListener(commandVisibilityListener);
            }
            if (version.is(VersionComparison.OLDER_THAN_OR_EQUALS, ServerVersion.V_1_8_8)) {
                PlayerLoginListener playerLoginListener = new PlayerLoginListener(lPXBukkit);
                lPXBukkit.registerListener(playerLoginListener);
            }
            LPXCommand lPXCommand = new LPXCommand(lPXBukkit);
            lPXBukkit.setCommand(lPXCommand);
            configManager = lPXBukkit.getConfigManager();
            GeneralOptions options = configManager.getOptions();
            if (options.isEnabled8()) {
                AsyncScheduler asyncScheduler = FoliaScheduler.getAsyncScheduler();
                LPXPlugin plugin2 = lPXBukkit.getPlugin();
                Consumer consumer =
                        result -> {
                            PlayerTickTask playerTickTask = new PlayerTickTask(lPXBukkit);
                            playerTickTask.run();
                        };
                ConfigManager configManager2 = lPXBukkit.getConfigManager();
                GeneralOptions options2 = configManager2.getOptions();
                asyncScheduler.runAtFixedRate(plugin2, consumer, 1L, options2.getLimit() * 20L);
            }
            configManager = lPXBukkit.getConfigManager();
            GeneralOptions options3 = configManager.getOptions();
            if (options3.isBStats()) {
                MetricsLite metricsLite = new MetricsLite(lPXBukkit.getPlugin());
            }
            lPXBukkit.getLogger().log(Level.INFO, TEXT);
            PacketCheckProcessor packetCheckProcessor = new PacketCheckProcessor(lPXBukkit);
            EventManager eventManager = PacketEvents.getAPI().getEventManager();
            PreViaPacketListener preViaPacketListener =
                    new PreViaPacketListener(packetCheckProcessor);
            eventManager.registerListener(preViaPacketListener);
            EventManager eventManager2 = PacketEvents.getAPI().getEventManager();
            PacketCheckListener packetCheckListener =
                    new PacketCheckListener(lPXBukkit, packetCheckProcessor);
            eventManager2.registerListener(packetCheckListener);
            Bukkit.getServicesManager()
                    .register(LPX.class, lPXBukkit, lPXBukkit.getPlugin(), ServicePriority.Normal);
        }
    }
}
