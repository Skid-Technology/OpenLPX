package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;

public class PreChannelInitializer_v1_12 extends ChannelInboundHandlerAdapter {

    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(ChannelInitializer.class);

    public void channelRegistered(ChannelHandlerContext channelHandlerContext) {
        try {
            ServerConnectionInitializer.initChannel(
                    channelHandlerContext.channel(), ConnectionState.HANDSHAKING);
        } catch (Throwable throwable) {
            this.exceptionCaught(channelHandlerContext, throwable);
        } finally {
            ChannelPipeline channelPipeline;
            if ((channelPipeline = channelHandlerContext.pipeline()).context(this) != null) {
                channelPipeline.remove(this);
            }
        }
        channelHandlerContext.pipeline().fireChannelRegistered();
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        logger.warn(
                "Failed to initialize a channel. Closing: " + channelHandlerContext.channel(),
                throwable);
        channelHandlerContext.close();
    }
}
