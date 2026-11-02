package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException.Limit;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType.Configuration.Client;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientPluginMessage;

public class WrapperConfigClientPluginMessage
        extends WrapperCommonClientPluginMessage<WrapperConfigClientPluginMessage> {

    public WrapperConfigClientPluginMessage(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientPluginMessage(ResourceLocation resourceLocation, byte[] values) {
        this(resourceLocation.toString(), values);
    }

    public WrapperConfigClientPluginMessage(String text, byte[] values) {
        super(Client.PLUGIN_MESSAGE, text, values);
    }

    public void read() {
        this.channelName = this.readString();
        int value = 32767 * PacketEvents.getAPI().getSettings().getMaxPayloadMultiplier();
        int value2 = ByteBufHelper.readableBytes(this.buffer);
        if (value2 > value) {
            throw new PacketLimitException(Limit.PAYLOAD_SIZE, value2, value);
        }
        this.data = this.readRemainingBytes();
    }

    public void write() {
        this.writeString(this.channelName);
        this.writeBytes(this.data);
    }
}
