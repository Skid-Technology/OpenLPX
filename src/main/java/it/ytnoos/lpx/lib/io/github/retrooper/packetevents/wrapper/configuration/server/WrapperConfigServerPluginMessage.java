package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerPluginMessage;

public class WrapperConfigServerPluginMessage
        extends WrapperCommonServerPluginMessage<WrapperConfigServerPluginMessage> {

    public WrapperConfigServerPluginMessage(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerPluginMessage(ResourceLocation resourceLocation, byte[] values) {
        this(resourceLocation.toString(), values);
    }

    public WrapperConfigServerPluginMessage(String text, byte[] values) {
        super(PacketType.Configuration.Server.PLUGIN_MESSAGE, text, values);
    }

    @Override
    public void read() {
        this.channelName = this.readString();
        if (ByteBufHelper.readableBytes(this.buffer) > 1048576) {
            throw new RuntimeException("Payload may not be larger than 1048576 bytes");
        }
        this.data = this.readRemainingBytes();
    }

    @Override
    public void write() {
        this.writeString(this.channelName);
        this.writeBytes(this.data);
    }
}
