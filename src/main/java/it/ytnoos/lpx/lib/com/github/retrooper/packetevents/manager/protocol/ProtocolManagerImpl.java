package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.protocol;

import io.netty.buffer.ByteBuf;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.protocolsupport.ProtocolSupportUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol.ProtocolManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ProtocolVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import java.util.List;

public class ProtocolManagerImpl implements ProtocolManager {

    private ProtocolVersion platformVersion;

    private ProtocolVersion resolveVersionNoCache() {
        return ProtocolVersion.UNKNOWN;
    }

    @Override
    public ProtocolVersion getPlatformVersion() {
        if (this.platformVersion == null) {
            this.platformVersion = this.resolveVersionNoCache();
        }
        return this.platformVersion;
    }

    @Override
    public void sendPacket(Object result, Object result2) {
        if (ChannelHelper.isOpen(result)) {
            if (ProtocolSupportUtil.isAvailable() && result2 instanceof ByteBuf) {
                ((ByteBuf) result2).retain();
            }
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
            if (ProtocolSupportUtil.isAvailable() && result2 instanceof ByteBuf) {
                ((ByteBuf) result2).retain();
            }
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
            List<String> values;
            if ((values = ChannelHelper.pipelineHandlerNames(result)).contains("via-encoder")) {
                ChannelHelper.fireChannelReadInContext(result, "via-decoder", result2);
            } else if (values.contains("ps_decoder_transformer")) {
                ChannelHelper.fireChannelReadInContext(result, "ps_decoder_transformer", result2);
            } else if (values.contains("decompress")) {
                ChannelHelper.fireChannelReadInContext(result, "decompress", result2);
            } else if (values.contains("decrypt")) {
                ChannelHelper.fireChannelReadInContext(result, "decrypt", result2);
            } else {
                ChannelHelper.fireChannelReadInContext(result, "splitter", result2);
            }
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
        User user;
        return (user = this.getUser(result)).getClientVersion() == null
                ? ClientVersion.UNKNOWN
                : user.getClientVersion();
    }
}
