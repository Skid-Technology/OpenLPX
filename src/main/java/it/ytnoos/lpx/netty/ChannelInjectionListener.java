package it.ytnoos.lpx.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.GeneralOptions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerAbstract;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.UserDisconnectEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Login.Server;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.player.ProtocolPlayerImpl;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ChannelInjectionListener extends PacketListenerAbstract {

    private final LPXBukkit lpx;

    private final PlayerRegistry registry;

    public ChannelInjectionListener(LPXBukkit lPXBukkit, PlayerRegistry playerRegistry) {
        this.lpx = lPXBukkit;
        this.registry = playerRegistry;
    }

    public void onUserDisconnect(UserDisconnectEvent userDisconnectEvent) {
        this.registry.uninject(userDisconnectEvent.getUser());
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        if (packetSendEvent.getPacketType() == Server.LOGIN_SUCCESS) {
            List<Runnable> tasksAfterSend = packetSendEvent.getTasksAfterSend();
            tasksAfterSend.add(
                    () -> {
                        User user = packetSendEvent.getUser();
                        Object channel = packetSendEvent.getChannel();
                        Channel channel2 = (Channel) channel;
                        User user2 = user;
                        user = user2;
                        User user3 = user;
                        this.setup(user3, channel2);
                    });
        }
    }

    private void setup(User user, Channel channel) {
        UUID uUID = user.getUUID();
        LPXBukkit lPXBukkit = this.lpx;
        Supplier supplier =
                () -> {
                    String name = Thread.currentThread().getName();
                    String text = String.valueOf(uUID);
                    return name + (String) " Setting up player with uuid " + text;
                };
        lPXBukkit.debug(supplier);
        LPXBukkit lPXBukkit2 = this.lpx;
        ProtocolPlayerImpl protocolPlayerImpl = new ProtocolPlayerImpl(lPXBukkit2, user);
        if (this.registry.addPlayer(user, protocolPlayerImpl) != null) {
            Logger logger = this.lpx.getLogger();
            String name2 = user.getName();
            logger.warning((String) "Player " + name2 + " already injected, removed old instance.");
        }
        lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        GeneralOptions options = configManager.getOptions();
        if (options.isGeyser() && uUID.getMostSignificantBits() == 0L) {
            protocolPlayerImpl.setBedrock(true);
        }
        this.inject(channel, user, ((ProtocolPlayer) protocolPlayerImpl));
    }

    public void inject(Channel channel, User user, ProtocolPlayer protocolPlayer) {
        LPXBukkit lPXBukkit = this.lpx;
        Supplier supplier =
                () -> {
                    String name = Thread.currentThread().getName();
                    String text = user.getName();
                    String text2 = String.valueOf(user.getUUID());
                    String text3 = text2;
                    String text4 = text;
                    String text5 = text3;
                    return name + (String) " Injecting player: " + text4 + (String) " " + text5;
                };
        lPXBukkit.debug(supplier);
        ChannelPipeline channelPipeline = channel.pipeline();
        ChannelHandler channelHandler = channelPipeline.get("lpx-handler");
        Object result = channelHandler;
        if (channelHandler != null) {
            channelPipeline.remove((ChannelHandler) result);
        } else {
            LPXBukkit lPXBukkit2 = this.lpx;
            PacketDecoder packetDecoder = new PacketDecoder(lPXBukkit2, protocolPlayer);
            result = packetDecoder;
        }
        channelPipeline.addAfter("decoder", "lpx-handler", (ChannelHandler) result);
        lPXBukkit = this.lpx;
        Supplier supplier2 =
                () -> {
                    String name = user.getName();
                    String text = String.valueOf(channelPipeline.toMap());
                    String text3 = " : ";
                    String text4 = text;
                    return name + text3 + text4;
                };
        lPXBukkit.debug(supplier2);
        lPXBukkit = this.lpx;
        NettyHealthMonitor nettyHealthMonitor = lPXBukkit.getNettyHealthMonitor();
        UUID uniqueId = protocolPlayer.getUniqueId();
        Thread thread = Thread.currentThread();
        EventLoop eventLoop2 = channel.eventLoop();
        Thread thread2 = thread;
        thread = thread2;
        Thread thread3 = thread;
        nettyHealthMonitor.handleEventLoop(uniqueId, thread3, eventLoop2);
    }
}
