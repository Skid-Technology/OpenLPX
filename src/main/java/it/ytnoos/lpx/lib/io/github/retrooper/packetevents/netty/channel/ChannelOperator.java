package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel;

import java.net.SocketAddress;
import java.util.List;

public interface ChannelOperator {

    SocketAddress remoteAddress(Object result);

    SocketAddress localAddress(Object result);

    boolean isOpen(Object result);

    Object close(Object result);

    Object write(Object result, Object result2);

    Object flush(Object result);

    Object writeAndFlush(Object result, Object result2);

    Object fireChannelRead(Object result, Object result2);

    Object writeInContext(Object result, String text, Object result2);

    Object flushInContext(Object result, String text);

    Object writeAndFlushInContext(Object result, String text, Object result2);

    Object fireChannelReadInContext(Object result, String text, Object result2);

    List<String> pipelineHandlerNames(Object result);

    Object getPipelineHandler(Object result, String text);

    Object getPipelineContext(Object result, String text);

    Object getPipeline(Object result);

    void runInEventLoop(Object result, Runnable runnable);

    Object pooledByteBuf(Object result);
}
