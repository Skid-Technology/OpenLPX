package it.ytnoos.lpx;

import io.netty.channel.Channel;

import it.ytnoos.lpx.activation.ActivationState;
import it.ytnoos.lpx.adapter.LegacyInventoryViewAdapter;
import it.ytnoos.lpx.adapter.ModernInventoryViewAdapter;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.alerts.ViolationRecord;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import it.ytnoos.lpx.check.BukkitCheckManager;
import it.ytnoos.lpx.commands.LPXCommand;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.inventories.MenuFactory;
import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.player.PlayerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import it.ytnoos.lpx.logging.PacketLogger;
import it.ytnoos.lpx.netty.NettyHealthMonitor;
import it.ytnoos.lpx.netty.NettyWatchdog;
import it.ytnoos.lpx.player.PlayerRegistryImpl;
import it.ytnoos.lpx.utils.ColorUtils;
import it.ytnoos.lpx.utils.PacketUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LPXBukkit implements LPX {

    private final LPXBootstrap plugin;

    private final PlayerRegistry registry;

    private final CheckManager checkManager;

    private final ConfigManager configManager;

    private final InventoryManager inventoryManager;

    private final MenuFactory menuFactory;

    private final PacketLogger packetLogger;

    private final AlertManager alertManager;

    private final ArrayDeque<ViolationRecord> values;

    private final NettyHealthMonitor nettyHealthMonitor;

    private final NettyWatchdog nettyWatchdog;

    private boolean updated = true;

    private boolean disabled = false;

    private long time;

    @Nullable private LPXCommand command;

    public LPXBukkit(LPXBootstrap lPXBootstrap) {
        this.plugin = lPXBootstrap;
        ArrayDeque values2 = new ArrayDeque();
        this.values = values2;
        ConfigManager configManager2 = new ConfigManager(lPXBootstrap);
        this.configManager = configManager2;
        LPXBukkit lPXBukkit = this;
        AlertManager alertManager2 = new AlertManager(lPXBukkit);
        this.alertManager = alertManager2;
        LPXBukkit lPXBukkit2 = this;
        PlayerRegistryImpl playerRegistryImpl = new PlayerRegistryImpl(lPXBukkit2);
        this.registry = playerRegistryImpl;
        InventoryManager inventoryManager2 = new InventoryManager(this);
        this.inventoryManager = inventoryManager2;
        MenuFactory menuFactory2 = new MenuFactory(this);
        this.menuFactory = menuFactory2;
        LPXBukkit lPXBukkit3 = this;
        PacketLogger packetLogger2 = new PacketLogger(lPXBukkit3);
        this.packetLogger = packetLogger2;
        BukkitCheckManager bukkitCheckManager = new BukkitCheckManager(this);
        this.checkManager = bukkitCheckManager;
        LPXBukkit lPXBukkit4 = this;
        NettyHealthMonitor nettyHealthMonitor2 = new NettyHealthMonitor(lPXBukkit4);
        this.nettyHealthMonitor = nettyHealthMonitor2;
        LPXBukkit lPXBukkit5 = this;
        NettyWatchdog nettyWatchdog2 = new NettyWatchdog(lPXBukkit5);
        this.nettyWatchdog = nettyWatchdog2;
        PacketEvents.getAPI().init();
        ActivationState.enable(this);
    }

    public void disable(boolean matches) {
        if (!this.disabled) {
            this.disabled = true;
            if (!matches) {
                this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            }
            if (this.registry != null) {
                this.registry.uninjectAll();
            }
            if (this.packetLogger != null) {
                this.packetLogger.shutdown();
            }
            if (this.nettyWatchdog != null) {
                this.nettyWatchdog.shutdown();
            }
            this.nettyHealthMonitor.stop();
            PacketEvents.getAPI().terminate();
            this.alertManager.disable();
        }
    }

    public void reloadConfig() {
        LPXBukkit lPXBukkit = this;
        this.configManager.reload(lPXBukkit);
        if (this.command != null) {
            this.command.update();
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_13)) {
                for (Player player : this.plugin.getServer().getOnlinePlayers()) {
                    EntityScheduler entityScheduler = FoliaScheduler.getEntityScheduler();
                    entityScheduler.run(
                            player, this.plugin, result -> player.updateCommands(), null);
                }
            }
        }
    }

    public LPXCommand getCommand() {
        return this.command;
    }

    public void setCommand(LPXCommand lPXCommand) {
        this.command = lPXCommand;
    }

    public InventoryViewAdapter getInventoryViewAdapter(Player player) {
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isNewerThanOrEquals(ServerVersion.V_1_21)) {
            return new LegacyInventoryViewAdapter(player);
        } else {
            return new ModernInventoryViewAdapter(player);
        }
    }

    public void registerListener(Listener listener) {
        this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }

    public void registerListeners(Listener[] listeners) {
        int value = listeners.length;
        for (int index = 0; index < value; index++) {
            Listener listener = listeners[index];
            this.registerListener(listener);
        }
    }

    public String getServer() {
        ConfigManager configManager2 = this.configManager;
        GeneralOptions options = configManager2.getOptions();
        return options.getServer();
    }

    public LPXPlugin getPlugin() {
        return this.plugin;
    }

    public PlayerRegistry getRegistry() {
        return this.registry;
    }

    public CheckManager getCheckManager() {
        return this.checkManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public AlertManager getAlertManager() {
        return this.alertManager;
    }

    public PacketLogger getPacketLogger() {
        return this.packetLogger;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public MenuFactory getMenuFactory() {
        return this.menuFactory;
    }

    public Deque getValues() {
        return this.values;
    }

    public NettyHealthMonitor getNettyHealthMonitor() {
        return this.nettyHealthMonitor;
    }

    public NettyWatchdog getNettyWatchdog() {
        return this.nettyWatchdog;
    }

    public long getFirstJoinTime() {
        return this.time;
    }

    public void setFirstJoinTime(long time) {
        this.time = time;
    }

    public boolean canPacketLogHeavy() {
        return this.time != 0L && System.currentTimeMillis() - this.time > 5000L;
    }

    public void kick(Player player2, String text) {
        Optional<ProtocolPlayer> player3 = this.registry.getPlayer(player2);
        player3.ifPresent(result -> result.kick(text));
    }

    public void forceKick(ProtocolPlayer protocolPlayer, String text) {
        User user = protocolPlayer.getUser();
        Optional<Player> player = protocolPlayer.getPlayer();
        player.ifPresent(
                result -> {
                    PlayerManager name = PacketEvents.getAPI().getPlayerManager();
                    WrapperPlayServerDisconnect wrapperPlayServerDisconnect =
                            new WrapperPlayServerDisconnect(ColorUtils.deserialize(text));
                    name.sendPacket(result, wrapperPlayServerDisconnect);
                });
        try {
            if (user != null) {
                Channel channel = (Channel) user.getChannel();
                PacketUtils.handleChannel(channel);
            }
        } catch (Exception exception) {
            Logger logger = this.getLogger();
            logger.log(
                    Level.SEVERE,
                    exception,
                    () -> {
                        String name = user.getName();
                        String text2 = name;
                        return (String) "Unable to force kick player " + text2;
                    });
        }
    }

    public void debug(Supplier supplier) {
        ConfigManager configManager2 = this.configManager;
        GeneralOptions options = configManager2.getOptions();
        if (options.isDebug()) {
            this.getLogger().info(supplier);
        }
    }
}
