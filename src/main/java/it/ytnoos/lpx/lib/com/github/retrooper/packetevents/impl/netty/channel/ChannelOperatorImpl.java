package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.channel;

import io.netty.channel.Channel;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelOperator;

import java.net.SocketAddress;
import java.util.List;

public class ChannelOperatorImpl implements ChannelOperator {

    @Override
    public SocketAddress remoteAddress(Object result) {
        return ((Channel) result).remoteAddress();
    }

    @Override
    public SocketAddress localAddress(Object result) {
        return ((Channel) result).localAddress();
    }

    @Override
    public boolean isOpen(Object result) {
        return ((Channel) result).isOpen();
    }

    @Override
    public Object close(Object result) {
        return ((Channel) result).close();
    }

    @Override
    public Object write(Object result, Object result2) {
        return ((Channel) result).write(result2);
    }

    @Override
    public Object flush(Object result) {
        return ((Channel) result).flush();
    }

    @Override
    public Object writeAndFlush(Object result, Object result2) {
        return ((Channel) result).writeAndFlush(result2);
    }

    @Override
    public Object fireChannelRead(Object result, Object result2) {
        return ((Channel) result).pipeline().fireChannelRead(result2);
    }

    @Override
    public Object writeInContext(Object result, String text, Object result2) {
        return ((Channel) result).pipeline().context(text).write(result2);
    }

    @Override
    public Object flushInContext(Object result, String text) {
        return ((Channel) result).pipeline().context(text).flush();
    }

    @Override
    public Object writeAndFlushInContext(Object result, String text, Object result2) {
        return ((Channel) result).pipeline().context(text).writeAndFlush(result2);
    }

    @Override
    public Object fireChannelReadInContext(Object result, String text, Object result2) {
        return ((Channel) result).pipeline().context(text).fireChannelRead(result2);
    }

    @Override
    public List<String> pipelineHandlerNames(Object result) {
        return ((Channel) result).pipeline().names();
    }

    @Override
    public Object getPipelineHandler(Object result, String text) {
        return ((Channel) result).pipeline().get(text);
    }

    @Override
    public Object getPipelineContext(Object result, String text) {
        return ((Channel) result).pipeline().context(text);
    }

    @Override
    public Object getPipeline(Object result) {
        return ((Channel) result).pipeline();
    }

    @Override
    public void runInEventLoop(Object result, Runnable runnable) {
        ((Channel) result).eventLoop().execute(runnable);
    }

    @Override
    public Object pooledByteBuf(Object result) {
        return ((Channel) result).alloc().buffer();
    }
}
