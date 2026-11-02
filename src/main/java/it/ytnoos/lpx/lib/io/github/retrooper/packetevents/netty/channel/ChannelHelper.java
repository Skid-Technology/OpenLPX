package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

public class ChannelHelper {

    public static SocketAddress remoteAddress(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().remoteAddress(result);
    }

    public static SocketAddress localAddress(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().localAddress(result);
    }

    public static boolean isOpen(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().isOpen(result);
    }

    public static Object close(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().close(result);
    }

    public static Object write(Object result, Object result2) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().write(result, result2);
    }

    public static Object flush(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().flush(result);
    }

    public static Object writeAndFlush(Object result, Object result2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .writeAndFlush(result, result2);
    }

    public static Object fireChannelRead(Object result, Object result2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .fireChannelRead(result, result2);
    }

    public static Object writeInContext(Object result, String text, Object result2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .writeInContext(result, text, result2);
    }

    public static Object flushInContext(Object result, String text) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .flushInContext(result, text);
    }

    public static Object writeAndFlushInContext(Object result, String text, Object result2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .writeAndFlushInContext(result, text, result2);
    }

    public static Object fireChannelReadInContext(Object result, String text, Object result2) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .fireChannelReadInContext(result, text, result2);
    }

    public static List<String> pipelineHandlerNames(Object result) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .pipelineHandlerNames(result);
    }

    public static String pipelineHandlerNamesAsString(Object result) {
        return Arrays.toString(pipelineHandlerNames(result).toArray(new String[0]));
    }

    public static Object getPipeline(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().getPipeline(result);
    }

    public static Object getPipelineHandler(Object result, String text) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .getPipelineHandler(result, text);
    }

    public static Object getPipelineContext(Object result, String text) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .getPipelineContext(result, text);
    }

    public static Object pooledByteBuf(Object result) {
        return PacketEvents.getAPI().getNettyManager().getChannelOperator().pooledByteBuf(result);
    }

    public static void runInEventLoop(Object result, Runnable runnable) {
        PacketEvents.getAPI()
                .getNettyManager()
                .getChannelOperator()
                .runInEventLoop(result, runnable);
    }
}
