package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.factory;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.BuildData;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.NettyManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.manager.player.PlayerManagerAbstract;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.manager.server.ServerManagerAbstract;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.injector.ChannelInjector;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.player.PlayerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol.ProtocolManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.NettyManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.settings.PacketEventsSettings;

import java.util.Locale;

public class NettyPacketEventsBuilder {

    private static PacketEventsAPI<BuildData> INSTANCE;

    public static void clearBuildCache() {
        INSTANCE = null;
    }

    public static PacketEventsAPI<BuildData> build(
            BuildData buildData,
            ChannelInjector channelInjector,
            ProtocolManager protocolManager,
            ServerManagerAbstract serverManagerAbstract,
            PlayerManagerAbstract playerManagerAbstract) {
        if (INSTANCE == null) {
            INSTANCE =
                    buildNoCache(
                            buildData,
                            channelInjector,
                            protocolManager,
                            serverManagerAbstract,
                            playerManagerAbstract);
        }
        return INSTANCE;
    }

    public static PacketEventsAPI<BuildData> build(
            BuildData buildData,
            ChannelInjector channelInjector,
            ProtocolManager protocolManager,
            ServerManagerAbstract serverManagerAbstract,
            PlayerManagerAbstract playerManagerAbstract,
            PacketEventsSettings packetEventsSettings) {
        if (INSTANCE == null) {
            INSTANCE =
                    buildNoCache(
                            buildData,
                            channelInjector,
                            protocolManager,
                            serverManagerAbstract,
                            playerManagerAbstract,
                            packetEventsSettings);
        }
        return INSTANCE;
    }

    public static PacketEventsAPI<BuildData> buildNoCache(
            BuildData buildData,
            ChannelInjector channelInjector,
            ProtocolManager protocolManager,
            ServerManagerAbstract serverManagerAbstract,
            PlayerManagerAbstract playerManagerAbstract) {
        return buildNoCache(
                buildData,
                channelInjector,
                protocolManager,
                serverManagerAbstract,
                playerManagerAbstract,
                new PacketEventsSettings());
    }

    public static PacketEventsAPI<BuildData> buildNoCache(
            final BuildData buildData,
            final ChannelInjector channelInjector,
            final ProtocolManager protocolManager,
            final ServerManagerAbstract serverManagerAbstract,
            final PlayerManagerAbstract playerManagerAbstract,
            final PacketEventsSettings packetEventsSettings) {
        return new PacketEventsAPI<BuildData>() {

            private final PacketEventsSettings settings = packetEventsSettings;

            private final NettyManager nettyManager = new NettyManagerImpl();

            private boolean loaded;

            private boolean initialized;

            private boolean terminated;

            @Override
            public void load() {
                if (!this.loaded) {
                    String text = buildData.getName().toLowerCase(Locale.ROOT);
                    PacketEvents.IDENTIFIER = "pe-" + text;
                    PacketEvents.ENCODER_NAME = "pe-encoder-" + text;
                    PacketEvents.DECODER_NAME = "pe-decoder-" + text;
                    PacketEvents.CONNECTION_HANDLER_NAME = "pe-connection-handler-" + text;
                    PacketEvents.SERVER_CHANNEL_HANDLER_NAME = "pe-connection-initializer-" + text;
                    PacketEvents.TIMEOUT_HANDLER_NAME = "pe-timeout-handler-" + text;
                    super.load();
                    channelInjector.inject();
                    this.loaded = true;
                    this.getLogManager()
                            .info(
                                    "Loaded packetevents v2.14.1-LPX+6aecc331c-SNAPSHOT"
                                            + ("packetevents".equals(text)
                                                    ? ""
                                                    : " for " + buildData.getName()));
                }
            }

            @Override
            public boolean isLoaded() {
                return this.loaded;
            }

            @Override
            public void init() {
                this.load();
                if (!this.initialized) {
                    if (this.settings.shouldCheckForUpdates()) {
                        this.getUpdateChecker().handleUpdateCheck();
                    }
                    PacketType.Play.Client.load();
                    PacketType.Play.Server.load();
                    this.initialized = true;
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
                    this.initialized = false;
                    this.terminated = true;
                }
            }

            @Override
            public boolean isTerminated() {
                return this.terminated;
            }

            public BuildData getPlugin() {
                return buildData;
            }

            @Override
            public ProtocolManager getProtocolManager() {
                return protocolManager;
            }

            @Override
            public ServerManager getServerManager() {
                return serverManagerAbstract;
            }

            @Override
            public PlayerManager getPlayerManager() {
                return playerManagerAbstract;
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
                return channelInjector;
            }
        };
    }
}
