package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.ServerChannelHandler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.ServerConnectionInitializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.handlers.PacketEventsDecoder;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.handlers.PacketEventsEncoder;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.InjectedList;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.injector.ChannelInjector;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.ReflectionObject;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Internal
public class SpigotChannelInjector implements ChannelInjector {

    public final Set<Channel> injectedConnectionChannels = new HashSet<>();

    public List<Object> networkManagers;

    private int connectionChannelsListIndex = -1;

    private boolean preViaRequested = false;

    private boolean preViaExistingChannelsCatchupDone = false;

    public void updatePlayer(User user, Object result) {
        Object result2;
        if ((result2 = user.getChannel()) == null) {
            result2 = PacketEvents.getAPI().getPlayerManager().getChannel(result);
        }
        this.setPlayer(result2, result);
    }

    @Override
    public boolean isPlayerSet(Object result) {
        if (result == null) {
            return false;
        }
        PacketEventsEncoder packetEventsEncoder;
        PacketEventsDecoder packetEventsDecoder;
        return (packetEventsEncoder = this.getEncoder((Channel) result)) != null
                        && packetEventsEncoder.player != null
                ? true
                : (packetEventsDecoder = this.getDecoder((Channel) result)) != null
                        && packetEventsDecoder.player != null;
    }

    @Override
    public boolean isServerBound() {
        Object result;
        if ((result = SpigotReflectionUtil.getMinecraftServerConnectionInstance()) != null) {
            ReflectionObject reflectionObject = new ReflectionObject(result);
            for (int index = 0; index < 2; index++) {
                Iterator iterator2 = reflectionObject.readList(index).iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next() instanceof ChannelFuture) {
                        this.connectionChannelsListIndex = index;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void inject() {
        Object result2;
        if ((result2 = SpigotReflectionUtil.getMinecraftServerConnectionInstance()) != null) {
            ReflectionObject reflectionObject;
            List<ChannelFuture> values =
                    (reflectionObject = new ReflectionObject(result2))
                            .readList(this.connectionChannelsListIndex);
            InjectedList<ChannelFuture> injectedList =
                    new InjectedList<>(
                            values,
                            result -> {
                                Channel channel2 = result.channel();
                                this.injectServerChannel(channel2);
                                this.injectedConnectionChannels.add(channel2);
                            });
            reflectionObject.writeList(this.connectionChannelsListIndex, injectedList);
            this.injectNetworkManagers();
        }
    }

    public void injectNetworkManagers() {
        if (this.networkManagers == null) {
            this.networkManagers = SpigotReflectionUtil.getNetworkManagers();
        }
        synchronized (this.networkManagers) {
            if (!this.networkManagers.isEmpty()) {
                PacketEvents.getAPI()
                        .getLogManager()
                        .debug("Late bind not enabled, injecting into existing channel");
            }
            for (Object result : this.networkManagers) {
                Channel channel;
                if ((channel = new ReflectionObject(result).readObject(0, Channel.class)) != null) {
                    try {
                        User user;
                        if ((user = PacketEvents.getAPI().getProtocolManager().getUser(channel))
                                == null) {
                            ServerConnectionInitializer.initChannel(channel, ConnectionState.PLAY);
                        } else if (this.isPreViaRequested()) {
                            ServerConnectionInitializer.relocateHandlers(channel, user, true);
                        }
                    } catch (Exception exception) {
                        if (!PacketEvents.getAPI().getSettings().shouldIgnoreFailures()) {
                            PacketEvents.getAPI()
                                    .getLogManager()
                                    .severe(
                                            "PacketEvents Spigot injector failed to inject into an existing channel. If you need assistance, join our Discord server: https://discord.gg/DVHxPPxHZc",
                                            exception);
                        }
                    }
                }
            }
            if (this.isPreViaRequested()) {
                this.preViaExistingChannelsCatchupDone = true;
            }
        }
    }

    @Override
    public void uninject() {
        for (Channel channel : this.injectedConnectionChannels) {
            this.uninjectServerChannel(channel);
        }
        this.injectedConnectionChannels.clear();
        List values;
        Object result;
        ReflectionObject reflectionObject;
        if ((result = SpigotReflectionUtil.getMinecraftServerConnectionInstance()) != null
                && (values =
                                (reflectionObject = new ReflectionObject(result))
                                        .readList(this.connectionChannelsListIndex))
                        instanceof InjectedList) {
            reflectionObject.writeList(
                    this.connectionChannelsListIndex, ((InjectedList) values).originalList());
        }
    }

    private void injectServerChannel(Channel channel) {
        ChannelPipeline channelPipeline;
        if ((channelPipeline = channel.pipeline()).get(PacketEvents.CONNECTION_HANDLER_NAME)
                != null) {
            channelPipeline.remove(PacketEvents.CONNECTION_HANDLER_NAME);
        }
        if (channelPipeline.get("SpigotNettyServerChannelHandler#0") != null) {
            channelPipeline.addAfter(
                    "SpigotNettyServerChannelHandler#0",
                    PacketEvents.CONNECTION_HANDLER_NAME,
                    new ServerChannelHandler());
        } else if (channelPipeline.get("floodgate-init") != null) {
            channelPipeline.addAfter(
                    "floodgate-init",
                    PacketEvents.CONNECTION_HANDLER_NAME,
                    new ServerChannelHandler());
        } else if (channelPipeline.get("MinecraftPipeline#0") != null) {
            channelPipeline.addAfter(
                    "MinecraftPipeline#0",
                    PacketEvents.CONNECTION_HANDLER_NAME,
                    new ServerChannelHandler());
        } else {
            channelPipeline.addFirst(
                    PacketEvents.CONNECTION_HANDLER_NAME, new ServerChannelHandler());
        }
        if (this.networkManagers == null) {
            this.networkManagers = SpigotReflectionUtil.getNetworkManagers();
        }
        synchronized (this.networkManagers) {
            for (Object result : this.networkManagers) {
                Channel channel2;
                if ((channel2 = new ReflectionObject(result).readObject(0, Channel.class)) != null
                        && channel2.isOpen()
                        && channel2.localAddress().equals(channel.localAddress())) {
                    channel2.close();
                }
            }
        }
    }

    private void uninjectServerChannel(Channel channel) {
        if (channel.pipeline().get(PacketEvents.CONNECTION_HANDLER_NAME) != null) {
            channel.pipeline().remove(PacketEvents.CONNECTION_HANDLER_NAME);
        } else {
            PacketEvents.getAPI()
                    .getLogManager()
                    .warn("Failed to uninject server channel, handler not found");
        }
    }

    @Override
    public void updateUser(Object result, User user2) {
        PacketEventsEncoder packetEventsEncoder;
        if ((packetEventsEncoder = this.getEncoder((Channel) result)) != null) {
            packetEventsEncoder.user = user2;
        }
        PacketEventsDecoder packetEventsDecoder;
        if ((packetEventsDecoder = this.getDecoder((Channel) result)) != null) {
            packetEventsDecoder.user = user2;
        }
    }

    @Override
    public void setPlayer(Object result, Object result2) {
        PacketEventsEncoder packetEventsEncoder;
        if ((packetEventsEncoder = this.getEncoder((Channel) result)) != null) {
            packetEventsEncoder.player = (Player) result2;
        }
        PacketEventsDecoder packetEventsDecoder;
        if ((packetEventsDecoder = this.getDecoder((Channel) result)) != null) {
            packetEventsDecoder.player = (Player) result2;
            packetEventsDecoder.user.getProfile().setName(((Player) result2).getName());
            packetEventsDecoder.user.getProfile().setUUID(((Player) result2).getUniqueId());
        }
    }

    @Nullable
    public PacketEventsEncoder getEncoder(Channel channel) {
        return (PacketEventsEncoder) channel.pipeline().get(PacketEvents.ENCODER_NAME);
    }

    @Nullable
    public PacketEventsDecoder getDecoder(Channel channel) {
        return (PacketEventsDecoder) channel.pipeline().get(PacketEvents.DECODER_NAME);
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    public boolean isPreViaRequested() {
        return this.preViaRequested;
    }

    public void setPreViaRequested(boolean preViaRequested) {
        this.preViaRequested = preViaRequested;
    }

    public void catchUpPreViaOnExistingChannelsIfNeeded() {
        if (this.preViaRequested
                && !this.preViaExistingChannelsCatchupDone
                && this.isServerBound()) {
            this.injectNetworkManagers();
        }
    }
}
