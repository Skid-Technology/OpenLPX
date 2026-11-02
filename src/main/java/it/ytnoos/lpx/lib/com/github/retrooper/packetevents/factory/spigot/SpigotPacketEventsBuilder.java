package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.factory.spigot;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.bukkit.Metrics;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bstats.charts.SimplePie;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit.InternalBukkitListener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit.InternalBukkitLoginListener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit.InternalGlobalBukkitListener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit.InternalPaperJoinListener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit.InternalPaperListener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.SpigotChannelInjector;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.ServerConnectionInitializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.InternalBukkitPacketListener;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.player.PlayerManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.protocol.ProtocolManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.server.ServerManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.netty.NettyManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.BukkitLogManager;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.protocolsupport.ProtocolSupportUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion.CustomPipelineUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion.ViaVersionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.EventManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.injector.ChannelInjector;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.player.PlayerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol.ProtocolManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.NettyManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.settings.PacketEventsSettings;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.LogManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.PEVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.updatechecker.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class SpigotPacketEventsBuilder {

    private static PacketEventsAPI<Plugin> API_INSTANCE;

    public static void clearBuildCache() {
        API_INSTANCE = null;
    }

    public static PacketEventsAPI<Plugin> build(Plugin plugin) {
        if (API_INSTANCE == null) {
            API_INSTANCE = buildNoCache(plugin);
        }
        return API_INSTANCE;
    }

    public static PacketEventsAPI<Plugin> build(
            Plugin plugin, PacketEventsSettings packetEventsSettings) {
        if (API_INSTANCE == null) {
            API_INSTANCE = buildNoCache(plugin, packetEventsSettings);
        }
        return API_INSTANCE;
    }

    public static PacketEventsAPI<Plugin> buildNoCache(Plugin plugin) {
        return buildNoCache(plugin, new PacketEventsSettings());
    }

    public static PacketEventsAPI<Plugin> buildNoCache(
            final Plugin plugin, final PacketEventsSettings packetEventsSettings) {
        return new PacketEventsAPI<Plugin>(
                new EventManager(ServerConnectionInitializer.PRE_VIA_LISTENER_REGISTERED)) {

            private final PacketEventsSettings settings = packetEventsSettings;

            private final ProtocolManager protocolManager = new ProtocolManagerImpl();

            private final ServerManager serverManager = new ServerManagerImpl();

            private final PlayerManager playerManager = new PlayerManagerImpl();

            private final NettyManager nettyManager = new NettyManagerImpl();

            private final SpigotChannelInjector injector = new SpigotChannelInjector();

            private final LogManager logManager = new BukkitLogManager(this);

            private boolean loaded;

            private boolean initialized;

            private boolean lateBind = false;

            private boolean terminated = false;

            @Override
            public void load() {
                if (!this.loaded) {
                    String result4 = plugin.getName().toLowerCase(Locale.ROOT);
                    PacketEvents.IDENTIFIER = "pe-" + result4;
                    PacketEvents.ENCODER_NAME = "pe-encoder-" + result4;
                    PacketEvents.DECODER_NAME = "pe-decoder-" + result4;
                    PacketEvents.CONNECTION_HANDLER_NAME = "pe-connection-handler-" + result4;
                    PacketEvents.SERVER_CHANNEL_HANDLER_NAME =
                            "pe-connection-initializer-" + result4;
                    PacketEvents.TIMEOUT_HANDLER_NAME = "pe-timeout-handler-" + result4;
                    try {
                        SpigotReflectionUtil.init();
                        CustomPipelineUtil.init();
                    } catch (Exception result) {
                        throw new IllegalStateException(result);
                    }
                    super.load();
                    this.lateBind = !this.injector.isServerBound();
                    if (!this.lateBind) {
                        this.injector.inject();
                    }
                    this.loaded = true;
                    this.getLogManager()
                            .info(
                                    "Loaded packetevents v2.14.1-LPX+6aecc331c-SNAPSHOT"
                                            + ("packetevents".equals(result4)
                                                    ? ""
                                                    : " for " + plugin.getName()));
                }
            }

            @Override
            protected void registerInternalListener() {
                this.getEventManager().registerListeners(new InternalBukkitPacketListener());
            }

            @Override
            public boolean isLoaded() {
                return this.loaded;
            }

            private String getMinecraftVersion() {
                try {
                    return Bukkit.getMinecraftVersion();
                } catch (NoSuchMethodError plugin2) {
                    String result;
                    int result5;
                    return (result5 = (result = Bukkit.getBukkitVersion()).indexOf(45)) != -1
                            ? result.substring(0, result5)
                            : result;
                }
            }

            @Override
            public void init() {
                this.load();
                if (!this.initialized) {
                    Plugin result = (Plugin) PacketEvents.getAPI().getPlugin();
                    String result5 = this.getMinecraftVersion();
                    AtomicBoolean plugin2 = new AtomicBoolean(false);
                    BiConsumer result6 =
                            (result2, result3) -> {
                                if (result5.contains("Unknown")) {
                                    ServerVersion serverVersion = ServerVersion.V_1_8_8;
                                    String plugin3 =
                                            "Your server software is preventing us from checking the Minecraft Server version. This is what we found: "
                                                    + result5
                                                    + ". We will assume the Server version is "
                                                    + serverVersion.name()
                                                    + "... If you need assistance, join our Discord server: https://discord.gg/DVHxPPxHZc";
                                    this.getLogManager().warn(plugin3);
                                } else {
                                    PEVersion pEVersion = PEVersion.fromString(result5);
                                    PEVersion pEVersion2 =
                                            PEVersion.fromString(
                                                    ServerVersion.getLatest().getReleaseName());
                                    if (pEVersion.isNewerThan(pEVersion2)) {
                                        String text =
                                                result3
                                                                        != UpdateChecker
                                                                                .UpdateCheckerStatus
                                                                                .OUTDATED
                                                                && result3
                                                                        != UpdateChecker
                                                                                .UpdateCheckerStatus
                                                                                .FAILED
                                                                && result3 != null
                                                        ? (String)
                                                                "Please test the development builds, as they may already have support for your Minecraft version (hint: select the build that contains 'spigot'): https://ci.codemc.io/job/retrooper/job/packetevents"
                                                        : (String)
                                                                "Please test the latest stable release, as it should already have support for your Minecraft version: https://modrinth.com/plugin/packetevents";
                                        this.getLogManager()
                                                .warn(
                                                        "Your build of PacketEvents does not support the Minecraft version "
                                                                + pEVersion
                                                                + "! The latest Minecraft version supported by your build of PacketEvents is "
                                                                + pEVersion2
                                                                + ". "
                                                                + text
                                                                + " If you're in need of any help, join our Discord server: https://discord.gg/DVHxPPxHZc");
                                        if (pEVersion.major() > pEVersion2.major()
                                                || pEVersion.minor() > pEVersion2.minor()) {
                                            Bukkit.getPluginManager().disablePlugin(result);
                                            plugin2.set(true);
                                        }
                                    }
                                }
                            };
                    if (this.settings.shouldCheckForUpdates()) {
                        this.getUpdateChecker().handleUpdateCheck(result6);
                    } else {
                        result6.accept(null, null);
                    }
                    if (plugin2.get()) {
                        return;
                    }
                    Metrics metrics = new Metrics(result, 11327);
                    metrics.addCustomChart(
                            new SimplePie(
                                    "packetevents_version",
                                    () -> this.getVersion().toStringWithoutSnapshot()));
                    Bukkit.getPluginManager()
                            .registerEvents(new InternalGlobalBukkitListener(), result);
                    try {
                        Class.forName("io.papermc.paper.connection.PlayerConnection");
                        if (this.serverManager
                                .getVersion()
                                .isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
                            Bukkit.getPluginManager()
                                    .registerEvents(new InternalPaperJoinListener(result), result);
                        } else {
                            Bukkit.getPluginManager()
                                    .registerEvents(new InternalPaperListener(result), result);
                        }
                    } catch (ClassNotFoundException classNotFoundException) {
                        if (this.serverManager
                                .getVersion()
                                .isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                            Bukkit.getPluginManager()
                                    .registerEvents(new InternalBukkitLoginListener(), result);
                        } else {
                            Bukkit.getPluginManager()
                                    .registerEvents(new InternalBukkitListener(result), result);
                        }
                    }
                    if (this.lateBind) {
                        Runnable runnable =
                                () -> {
                                    if (this.injector.isServerBound()) {
                                        this.injector.inject();
                                    }
                                };
                        FoliaScheduler.runTaskOnInit(result, runnable);
                    }
                    if (!"true".equalsIgnoreCase(System.getenv("PE_IGNORE_INCOMPATIBILITY"))) {
                        this.checkCompatibility();
                    }
                    for (Player player2 : Bukkit.getOnlinePlayers()) {
                        User user = PacketEvents.getAPI().getPlayerManager().getUser(player2);
                        ((SpigotChannelInjector) PacketEvents.getAPI().getInjector())
                                .updatePlayer(user, player2);
                    }
                    this.initialized = true;
                }
            }

            private void checkCompatibility() {
                ViaVersionUtil.checkIfViaIsPresent();
                ProtocolSupportUtil.isAvailable();
                Plugin result4;
                if ((result4 = Bukkit.getPluginManager().getPlugin("ViaVersion")) != null) {
                    String[] texts;
                    int result =
                            Integer.parseInt(
                                    (texts = result4.getDescription().getVersion().split("\\.", 3))
                                            [0]);
                    int result5 = Integer.parseInt(texts[1]);
                    if (result < 4 || result == 4 && result5 < 5) {
                        PacketEvents.getAPI()
                                .getLogManager()
                                .severe(
                                        "You are attempting to combine 2.0 PacketEvents with a ViaVersion older than 4.5.0, please update your ViaVersion!");
                        Plugin plugin2 = this.getPlugin();
                        Bukkit.getPluginManager().disablePlugin(plugin2);
                        throw new IllegalStateException(
                                "ViaVersion incompatibility! Update to v4.5.0 or newer!");
                    }
                }
                Plugin result6;
                if ((result6 = Bukkit.getPluginManager().getPlugin("ProtocolLib")) != null
                        && Integer.parseInt(
                                        result6.getDescription().getVersion().split("\\.", 2)[0])
                                < 5) {
                    PacketEvents.getAPI()
                            .getLogManager()
                            .severe(
                                    "You are attempting to combine 2.0 PacketEvents with a ProtocolLib version older than v5.0.0. This is no longer works, please update to their dev builds. https://ci.dmulloy2.net/job/ProtocolLib/lastBuild/");
                    Plugin plugin3 = this.getPlugin();
                    Bukkit.getPluginManager().disablePlugin(plugin3);
                    throw new IllegalStateException(
                            "ProtocolLib incompatibility! Update to v5.0.0 or newer!");
                }
            }

            @Override
            public boolean isInitialized() {
                return this.initialized;
            }

            @Override
            public void terminate() {
                if (this.initialized) {
                    super.terminate();
                    Iterator<User> result4 = this.protocolManager.getUsers().iterator();
                    while (result4.hasNext()) {
                        ServerConnectionInitializer.destroyHandlers(
                                ((User) result4.next()).getChannel());
                    }
                    this.initialized = false;
                    this.terminated = true;
                }
            }

            @Override
            public boolean isTerminated() {
                return this.terminated;
            }

            public Plugin getPlugin() {
                return plugin;
            }

            @Override
            public ProtocolManager getProtocolManager() {
                return this.protocolManager;
            }

            @Override
            public ServerManager getServerManager() {
                return this.serverManager;
            }

            @Override
            public PlayerManager getPlayerManager() {
                return this.playerManager;
            }

            @Override
            public PacketEventsSettings getSettings() {
                return this.settings;
            }

            @Override
            public NettyManager getNettyManager() {
                return this.nettyManager;
            }

            @Override
            public ChannelInjector getInjector() {
                return this.injector;
            }

            @Override
            public LogManager getLogManager() {
                return this.logManager;
            }
        };
    }
}
