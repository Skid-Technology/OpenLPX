package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.PreViaPipelineSupport;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection.ServerConnectionInitializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion.CustomPipelineUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.InvalidDisconnectPacketSend;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.ExceptionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.PacketEventsImplHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

@Internal
public class PacketEventsEncoder extends ChannelOutboundHandlerAdapter {

    public static final Object COMPRESSION_ENABLED_EVENT = paperCompressionEnabledEvent();

    private static final boolean NETTY_4_1_0;

    public User user;

    public Player player;

    public boolean handleCompression;

    public boolean handledCompression = COMPRESSION_ENABLED_EVENT != null;

    private ChannelPromise promise;

    private final Queue<PacketEventsEncoder.QueuedMessage> queuedMessages = new ArrayDeque<>();

    private boolean hold = false;

    private boolean preVia;

    public PacketEventsEncoder(User user2, boolean preVia) {
        this.user = user2;
        this.preVia = preVia;
    }

    public PacketEventsEncoder(ChannelHandler channelHandler) {
        this.user = ((PacketEventsEncoder) channelHandler).user;
        this.player = ((PacketEventsEncoder) channelHandler).player;
        this.handleCompression = ((PacketEventsEncoder) channelHandler).handleCompression;
        this.handledCompression = ((PacketEventsEncoder) channelHandler).handledCompression;
        this.promise = ((PacketEventsEncoder) channelHandler).promise;
        this.preVia = ((PacketEventsEncoder) channelHandler).preVia;
    }

    public void setHold(Channel channel, boolean hold) throws Exception {
        if (this.hold != hold) {
            this.hold = hold;
            if (!hold && !this.queuedMessages.isEmpty()) {
                ChannelHandlerContext channelHandlerContext = channel.pipeline().context(this);
                PacketEventsEncoder.QueuedMessage queuedMessage;
                while ((queuedMessage = this.queuedMessages.poll()) != null) {
                    this.write(channelHandlerContext, queuedMessage.message, queuedMessage.promise);
                }
            }
        }
    }

    @Nullable
    private PacketSendEvent handleClientBoundPacket(
            Channel channel,
            User user,
            Object result,
            ByteBuf byteBuf,
            ChannelPromise channelPromise,
            boolean matches,
            @Nullable ConnectionState connectionState)
            throws Exception {
        PacketSendEvent packetSendEvent;
        if ((packetSendEvent =
                                connectionState == null
                                        ? PacketEventsImplHelper.handleClientBoundPacket(
                                                channel, user, result, byteBuf, matches)
                                        : PacketEventsImplHelper.handleClientBoundPacket(
                                                channel,
                                                user,
                                                result,
                                                byteBuf,
                                                matches,
                                                connectionState))
                        != null
                && packetSendEvent.hasTasksAfterSend()) {
            PacketSendEvent packetSendEvent2 = packetSendEvent;
            channelPromise.addListener(
                    result2 -> {
                        Iterator<Runnable> iterator2 =
                                packetSendEvent2.getTasksAfterSend().iterator();
                        while (iterator2.hasNext()) {
                            ((Runnable) iterator2.next()).run();
                        }
                    });
        }
        return packetSendEvent;
    }

    public void write(
            ChannelHandlerContext channelHandlerContext,
            Object result,
            ChannelPromise channelPromise)
            throws Exception {
        if (this.hold && result instanceof ByteBuf) {
            this.queuedMessages.add(new PacketEventsEncoder.QueuedMessage(result, channelPromise));
        } else {
            ChannelPromise channelPromise2 =
                    this.promise != null && !this.promise.isSuccess() ? this.promise : null;
            if (NETTY_4_1_0) {
                channelPromise = channelPromise.unvoid();
            }
            channelPromise.addListener(result2 -> this.promise = channelPromise2);
            this.promise = channelPromise;
            if (result instanceof ByteBuf) {
                boolean matches =
                        !this.handledCompression
                                && (this.preVia || this.handleCompression)
                                && this.handleCompression(channelHandlerContext, (ByteBuf) result);
                ConnectionState connectionState =
                        this.preVia
                                ? this.user.getPreViaEncoderState()
                                : this.user.getEncoderState();
                this.handleClientBoundPacket(
                        channelHandlerContext.channel(),
                        this.user,
                        this.player,
                        (ByteBuf) result,
                        this.promise,
                        !this.preVia,
                        connectionState);
                if (!((ByteBuf) result).isReadable()) {
                    ReferenceCountUtil.release(result);
                    channelPromise.trySuccess();
                    return;
                }
                if (PreViaPipelineSupport.shouldDispatchFallbackPreViaEvents(this.preVia)) {
                    this.handleClientBoundPacket(
                            channelHandlerContext.channel(),
                            this.user,
                            this.player,
                            (ByteBuf) result,
                            this.promise,
                            false,
                            this.user.getPreViaEncoderState());
                }
                if (matches) {
                    this.compress(channelHandlerContext, (ByteBuf) result);
                }
            }
            channelHandlerContext.write(result, channelPromise);
        }
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        PacketEventsEncoder.QueuedMessage queuedMessage;
        while ((queuedMessage = this.queuedMessages.poll()) != null) {
            ReferenceCountUtil.release(queuedMessage.message);
            if (NETTY_4_1_0 && queuedMessage.promise != null && !queuedMessage.promise.isVoid()) {
                queuedMessage.promise.setFailure(
                        new IllegalStateException(
                                this
                                        + " got dropped from pipeline "
                                        + channelHandlerContext.channel()));
            }
        }
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable)
            throws Exception {
        if (!ExceptionUtil.isException(throwable, InvalidDisconnectPacketSend.class)) {
            if (ExceptionUtil.isException(throwable, PacketProcessException.class)
                    && (this.user == null
                            || this.user.getEncoderState() != ConnectionState.HANDSHAKING)) {
                if (!SpigotReflectionUtil.isMinecraftServerInstanceDebugging()) {
                    PacketEvents.getAPI()
                            .getLogManager()
                            .packetException(this.buildExceptionMessage(throwable), throwable);
                }
                if (PacketEvents.getAPI().getSettings().isKickOnPacketExceptionEnabled()) {
                    try {
                        if (this.user != null) {
                            this.user.sendPacket(
                                    new WrapperPlayServerDisconnect(
                                            Component.text("Invalid packet")));
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
            super.exceptionCaught(channelHandlerContext, throwable);
        }
    }

    private static Object paperCompressionEnabledEvent() {
        try {
            return Class.forName("io.papermc.paper.network.ConnectionEvent")
                    .getDeclaredField("COMPRESSION_THRESHOLD_SET")
                    .get(null);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private void compress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
            throws InvocationTargetException {
        ChannelHandler channelHandler = channelHandlerContext.pipeline().get("compress");
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer();
        try {
            if (channelHandler != null) {
                CustomPipelineUtil.callEncode(
                        channelHandler, channelHandlerContext, byteBuf, byteBuf2);
            }
        } finally {
            byteBuf.clear().writeBytes(byteBuf2);
            byteBuf2.release();
        }
    }

    private void decompress(
            ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2)
            throws InvocationTargetException {
        ChannelHandler channelHandler;
        if ((channelHandler = channelHandlerContext.pipeline().get("decompress")) != null) {
            ByteBuf byteBuf3 =
                    (ByteBuf)
                            CustomPipelineUtil.callDecode(
                                            channelHandler, channelHandlerContext, byteBuf)
                                    .get(0);
            try {
                byteBuf2.clear().writeBytes(byteBuf3);
            } finally {
                byteBuf3.release();
            }
        }
    }

    private boolean handleCompression(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
            throws InvocationTargetException {
        List<String> values;
        int value;
        if ((value = (values = channelHandlerContext.pipeline().names()).indexOf("compress"))
                == -1) {
            return false;
        }
        this.handledCompression = true;
        int value2;
        if ((value2 = values.indexOf((this.preVia ? "pre-" : "") + PacketEvents.ENCODER_NAME))
                == -1) {
            return false;
        }
        if (value <= value2) {
            return false;
        }
        boolean matches = false;
        if (!this.preVia
                || !this.user.getClientVersion().isOlderThanOrEquals(ClientVersion.V_1_7_10)) {
            this.decompress(channelHandlerContext, byteBuf, byteBuf);
            matches = true;
        }
        ServerConnectionInitializer.relocateHandlers(
                channelHandlerContext.channel(), this.user, this.preVia);
        return matches;
    }

    private String buildExceptionMessage(Throwable throwable) {
        String text =
                this.user != null
                                && this.user.getProfile() != null
                                && this.user.getProfile().getName() != null
                        ? this.user.getProfile().getName()
                        : "unknown";
        String text2 = this.user != null ? this.user.getEncoderState().name() : "null";
        String text3 =
                this.user != null && this.user.getClientVersion() != null
                        ? this.user.getClientVersion().getReleaseName()
                        : "null";
        String causeMessage = getCauseMessage(throwable);
        return "An error occurred while sending a packet to "
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

    static {
        boolean matches = false;
        try {
            ChannelPromise.class.getDeclaredMethod("unvoid");
            matches = true;
        } catch (NoSuchMethodException noSuchMethodException) {
        }
        NETTY_4_1_0 = matches;
    }

    private static final class QueuedMessage {

        private final Object message;

        private final ChannelPromise promise;

        public QueuedMessage(Object message, ChannelPromise channelPromise) {
            this.message = message;
            this.promise = channelPromise;
        }
    }
}
