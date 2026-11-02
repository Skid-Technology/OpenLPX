package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.SpigotChannelInjector;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.PreViaPipelineSupport;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.ServerConnectionInitializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.ExceptionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.PacketEventsImplHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PacketEventsDecoder extends MessageToMessageDecoder<ByteBuf> {

    public User user;

    public Player player;

    public boolean hasBeenRelocated;

    public boolean preVia;

    public PacketEventsDecoder(User user2, boolean preVia) {
        this.user = user2;
        this.preVia = preVia;
    }

    public PacketEventsDecoder(PacketEventsDecoder packetEventsDecoder) {
        this.user = packetEventsDecoder.user;
        this.player = packetEventsDecoder.player;
        this.hasBeenRelocated = packetEventsDecoder.hasBeenRelocated;
        this.preVia = packetEventsDecoder.preVia;
    }

    public void read(
            ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> values)
            throws Exception {
        try {
            Object result = byteBuf;
            if (PreViaPipelineSupport.shouldDispatchFallbackPreViaEvents(this.preVia)) {
                result =
                        PacketEventsImplHelper.handleServerBoundPacket(
                                channelHandlerContext.channel(),
                                this.user,
                                this.player,
                                byteBuf,
                                this.preVia,
                                this.user.getPreViaDecoderState());
            }
            ConnectionState connectionState =
                    this.preVia ? this.user.getPreViaDecoderState() : this.user.getDecoderState();
            result =
                    PacketEventsImplHelper.handleServerBoundPacket(
                            channelHandlerContext.channel(),
                            this.user,
                            this.player,
                            result,
                            !this.preVia,
                            connectionState);
            values.add(ByteBufHelper.retain(result));
        } catch (Throwable throwable) {
            if (ExceptionUtil.isException(throwable, PacketProcessException.class)) {
                throw throwable;
            } else {
                throw new PacketProcessException(throwable);
            }
        }
    }

    public void decode(
            ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> values)
            throws Exception {
        if (byteBuf.isReadable()) {
            this.read(channelHandlerContext, byteBuf, values);
        }
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable)
            throws Exception {
        if (!ExceptionUtil.isException(throwable, PacketProcessException.class)) {
            super.exceptionCaught(channelHandlerContext, throwable);
        } else {
            if (PacketEvents.getAPI().getSettings().isDebugEnabled()
                    || SpigotReflectionUtil.isMinecraftServerInstanceDebugging()
                    || this.user != null
                            && this.user.getDecoderState() != ConnectionState.HANDSHAKING) {
                PacketEvents.getAPI()
                        .getLogManager()
                        .packetException(this.buildExceptionMessage(throwable), throwable);
            }
            if (PacketEvents.getAPI().getSettings().isKickOnPacketExceptionEnabled()) {
                try {
                    if (this.user != null) {
                        this.user.sendPacket(
                                new WrapperPlayServerDisconnect(Component.text("Invalid packet")));
                    }
                } catch (Exception exception) {
                }
                channelHandlerContext.channel().close();
                if (this.player != null) {
                    EntityScheduler entityScheduler = FoliaScheduler.getEntityScheduler();
                    Player player2 = this.player;
                    Plugin plugin = (Plugin) PacketEvents.getAPI().getPlugin();
                    entityScheduler.runDelayed(
                            player2,
                            plugin,
                            result -> this.player.kickPlayer("Invalid packet"),
                            null,
                            1L);
                }
                if (this.user != null && this.user.getProfile().getName() != null) {
                    PacketEvents.getAPI()
                            .getLogManager()
                            .warn(
                                    "Disconnected "
                                            + this.user.getProfile().getName()
                                            + " due to an invalid packet!");
                }
            }
        }
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object result)
            throws Exception {
        if (PacketEventsEncoder.COMPRESSION_ENABLED_EVENT != null
                && result == PacketEventsEncoder.COMPRESSION_ENABLED_EVENT) {
            if (!this.preVia) {
                ServerConnectionInitializer.relocateHandlers(
                        channelHandlerContext.channel(), this.user, false);
                if (((SpigotChannelInjector) PacketEvents.getAPI().getInjector())
                        .isPreViaRequested()) {
                    ServerConnectionInitializer.relocateHandlers(
                            channelHandlerContext.channel(), this.user, true);
                }
            }
            super.userEventTriggered(channelHandlerContext, result);
        } else {
            super.userEventTriggered(channelHandlerContext, result);
        }
    }

    private String buildExceptionMessage(Throwable throwable) {
        String text =
                this.user != null
                                && this.user.getProfile() != null
                                && this.user.getProfile().getName() != null
                        ? this.user.getProfile().getName()
                        : "unknown";
        String text2 = this.user != null ? this.user.getDecoderState().name() : "null";
        String text3 =
                this.user != null && this.user.getClientVersion() != null
                        ? this.user.getClientVersion().getReleaseName()
                        : "null";
        String causeMessage = getCauseMessage(throwable);
        return "An error occurred while processing a packet from "
                + text
                + " (state: "
                + text2
                + ", clientVersion: "
                + text3
                + ", serverVersion: "
                + PacketEvents.getAPI().getServerManager().getVersion().getReleaseName()
                + "): "
                + causeMessage;
    }

    private static String getCauseMessage(Throwable throwable) {
        Throwable throwable2 = throwable;
        while (throwable2.getCause() != null) {
            throwable2 = throwable2.getCause();
        }
        String text;
        return (text = throwable2.getMessage()) != null && !text.trim().isEmpty()
                ? text
                : throwable2.getClass().getSimpleName();
    }
}
