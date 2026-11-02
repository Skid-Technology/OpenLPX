package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server.WrapperCommonServerPluginMessage;

public class WrapperPlayServerPluginMessage
        extends WrapperCommonServerPluginMessage<WrapperPlayServerPluginMessage> {

    public WrapperPlayServerPluginMessage(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPluginMessage(ResourceLocation resourceLocation, byte[] values) {
        this(resourceLocation.toString(), values);
    }

    public WrapperPlayServerPluginMessage(String text, byte[] values) {
        super(PacketType.Play.Server.PLUGIN_MESSAGE, text, values);
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
        if (ByteBufHelper.readableBytes(this.buffer) > 1048576) {
            throw new RuntimeException("Payload may not be larger than 1048576 bytes");
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
