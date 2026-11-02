package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Version;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.PEVersion;

import java.util.Map;

public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

    public static final PEVersion MODERN_NETTY_VERSION = new PEVersion(4, 1, 24);

    public static boolean CHECKED_NETTY_VERSION;

    public static PEVersion NETTY_VERSION;

    private static PEVersion resolveNettyVersion() {
        Map<String, Version> entries;
        Version version;
        if ((version =
                                (Version)
                                        (entries = Version.identify())
                                                .getOrDefault(
                                                        "netty-common",
                                                        (Version) entries.get("netty-all")))
                        == null
                && !entries.values().isEmpty()) {
            version = (Version) entries.values().iterator().next();
        }
        if (version != null) {
            String text;
            String[] texts;
            if ((texts = (text = version.artifactVersion().replaceAll("[^\\d.]", "")).split("\\."))
                            .length
                    > 3) {
                text = texts[0] + "." + texts[1] + "." + texts[2];
            }
            return PEVersion.fromString(
                    text.endsWith(".") ? text.substring(0, text.length() - 1) : text);
        } else {
            return null;
        }
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object result)
            throws Exception {
        if (result instanceof Channel) {
            Channel channel = (Channel) result;
            if (NETTY_VERSION == null && !CHECKED_NETTY_VERSION) {
                NETTY_VERSION = resolveNettyVersion();
                CHECKED_NETTY_VERSION = true;
            }
            if ((NETTY_VERSION == null || !NETTY_VERSION.isNewerThan(MODERN_NETTY_VERSION))
                    && !SpigotReflectionUtil.V_1_12_OR_HIGHER) {
                channel.pipeline()
                        .addFirst(
                                PacketEvents.SERVER_CHANNEL_HANDLER_NAME,
                                new PreChannelInitializer_v1_8());
            } else {
                channel.pipeline()
                        .addLast(
                                PacketEvents.SERVER_CHANNEL_HANDLER_NAME,
                                new PreChannelInitializer_v1_12());
            }
            super.channelRead(channelHandlerContext, result);
        }
    }
}
