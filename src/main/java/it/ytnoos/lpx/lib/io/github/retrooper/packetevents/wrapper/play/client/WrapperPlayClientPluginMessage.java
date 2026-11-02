package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client.WrapperCommonClientPluginMessage;

public class WrapperPlayClientPluginMessage
        extends WrapperCommonClientPluginMessage<WrapperPlayClientPluginMessage> {

    public WrapperPlayClientPluginMessage(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPluginMessage(ResourceLocation resourceLocation, byte[] values) {
        this(resourceLocation.toString(), values);
    }

    public WrapperPlayClientPluginMessage(String text, byte[] values) {
        super(PacketType.Play.Client.PLUGIN_MESSAGE, text, values);
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.channelName = this.readString();
        } else {
            this.channelName = this.readString(20);
        }
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.readShort();
        }
        int value = 32767 * PacketEvents.getAPI().getSettings().getMaxPayloadMultiplier();
        int value2;
        if ((value2 = ByteBufHelper.readableBytes(this.buffer)) > value) {
            throw new PacketLimitException(PacketLimitException.Limit.PAYLOAD_SIZE, value2, value);
        }
        this.data = this.readRemainingBytes();
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.writeString(this.channelName);
        } else {
            this.writeString(this.channelName, 20);
        }
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeShort(this.data.length);
        }
        this.writeBytes(this.data);
    }
}
