package it.ytnoos.lpx.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.handler.codec.MessageToMessageDecoder;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.ReceivedPacket;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.PacketLoggerOptions;
import it.ytnoos.lpx.logging.PacketLogger;

import java.util.List;

@Sharable
public class PacketDecoder extends MessageToMessageDecoder<Object> {

    private final LPXBukkit lpx;

    private final ProtocolPlayer player;

    public PacketDecoder(LPXBukkit lPXBukkit, ProtocolPlayer protocolPlayer) {
        this.lpx = lPXBukkit;
        this.player = protocolPlayer;
    }

    protected void decode(
            ChannelHandlerContext channelHandlerContext, Object result, List<Object> values) {
        ReceivedPacket lastReceivedPacket = this.player.getLastReceivedPacket();
        if (lastReceivedPacket != null) {
            this.player.setLastReceivedPacket(null);
            if (lastReceivedPacket.getPacket() == null) {
                lastReceivedPacket.setPacket(result);
            }
            LPXBukkit lPXBukkit = this.lpx;
            NettyHealthMonitor nettyHealthMonitor = lPXBukkit.getNettyHealthMonitor();
            LPXBukkit lPXBukkit2 = this.lpx;
            NettyHealthMonitor nettyHealthMonitor2 = lPXBukkit2.getNettyHealthMonitor();
            long value =
                    nettyHealthMonitor2.getTickScheduler() - lastReceivedPacket.getReceivedAtTick();
            EventLoop eventLoop2 = channelHandlerContext.channel().eventLoop();
            Long value2 = ((Long) value);
            long value3 = ((long) value2);
            nettyHealthMonitor.handleEventLoop(value3, eventLoop2);
            this.logPacket(lastReceivedPacket);
        }
        values.add(result);
    }

    private void logPacket(ReceivedPacket receivedPacket) {
        boolean matches;
        LPXBukkit lPXBukkit = this.lpx;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        PacketLoggerOptions logger = configManager.getLogger();
        String name = receivedPacket.getPacketType().getName();
        boolean matches2;
        if (logger.testValue(name)) {
            long elapsedTime = receivedPacket.getElapsedTime();
            Long value = ((Long) elapsedTime);
            matches2 = logger.testTime(name, ((long) value));
        } else {
            long elapsedTime2 = receivedPacket.getElapsedTime();
            Long value2 = ((Long) elapsedTime2);
            matches2 = logger.testTime(((long) value2));
        }
        label64:
        {
            if (matches2) {
                if (!logger.testValue2(name)) {
                    matches = true;
                    break label64;
                }
            }
            matches = false;
        }
        boolean matches3 = matches;
        if (logger.isEnabled() || matches3) {
            String playerName = receivedPacket.getPlayerName();
            if (logger.testText(playerName) || matches3) {
                if (!matches3 || this.player.getPacketCount() > 5L) {
                    if ((logger.testText2(name) || matches3 && this.lpx.canPacketLogHeavy())
                            && !this.player.isKicked()) {
                        lPXBukkit = this.lpx;
                        PacketLogger packetLogger = lPXBukkit.getPacketLogger();
                        packetLogger.logPacket(receivedPacket);
                    }
                }
            }
        }
    }
}
