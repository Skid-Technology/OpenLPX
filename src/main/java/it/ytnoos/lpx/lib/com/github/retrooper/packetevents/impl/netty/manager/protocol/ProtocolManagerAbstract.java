package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.manager.protocol;

import io.netty.buffer.ByteBuf;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol.ProtocolManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ProtocolVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

public abstract class ProtocolManagerAbstract implements ProtocolManager {

    @Override
    public abstract ProtocolVersion getPlatformVersion();

    @Override
    public void sendPacket(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            ChannelHelper.writeAndFlush(result, result2);
        } else {
            ((ByteBuf) result2).release();
        }
    }

    @Override
    public void sendPacketSilently(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            ChannelHelper.writeAndFlushInContext(result, PacketEvents.ENCODER_NAME, result2);
        } else {
            ((ByteBuf) result2).release();
        }
    }

    @Override
    public void writePacket(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            ChannelHelper.write(result, result2);
        } else {
            ((ByteBuf) result2).release();
        }
    }

    @Override
    public void writePacketSilently(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            ChannelHelper.writeInContext(result, PacketEvents.ENCODER_NAME, result2);
        } else {
            ((ByteBuf) result2).release();
        }
    }

    @Override
    public void receivePacket(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            ChannelHelper.fireChannelRead(result, result2);
        } else {
            ((ByteBuf) result2).release();
        }
    }

    @Override
    public void receivePacketSilently(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            ChannelHelper.fireChannelReadInContext(result, PacketEvents.DECODER_NAME, result2);
        } else {
            ((ByteBuf) result2).release();
        }
    }

    @Override
    public ClientVersion getClientVersion(Object result) {
        ClientVersion clientVersion;
        if ((clientVersion = this.getUser(result).getClientVersion()) == null) {
            clientVersion =
                    ClientVersion.getById(
                            PacketEvents.getAPI()
                                    .getServerManager()
                                    .getVersion()
                                    .getProtocolVersion());
        }
        return clientVersion;
    }
}
