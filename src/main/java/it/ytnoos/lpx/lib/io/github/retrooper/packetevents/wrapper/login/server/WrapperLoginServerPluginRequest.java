package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.login.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperLoginServerPluginRequest
        extends PacketWrapper<WrapperLoginServerPluginRequest> {

    private int messageID;

    private String channelName;

    private byte[] data;

    public WrapperLoginServerPluginRequest(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperLoginServerPluginRequest(int messageID, String channelName, byte[] data) {
        super(PacketType.Login.Server.LOGIN_PLUGIN_REQUEST);
        this.messageID = messageID;
        this.channelName = channelName;
        this.data = data;
    }

    @Override
    public void read() {
        this.messageID = this.readVarInt();
        this.channelName = this.readString();
        int value = ByteBufHelper.readableBytes(this.buffer);
        this.data = this.readBytes(value);
    }

    @Override
    public void write() {
        this.writeVarInt(this.messageID);
        this.writeString(this.channelName);
        this.writeBytes(this.data);
    }

    public void copy(WrapperLoginServerPluginRequest wrapperLoginServerPluginRequest) {
        this.messageID = wrapperLoginServerPluginRequest.messageID;
        this.channelName = wrapperLoginServerPluginRequest.channelName;
        this.data = wrapperLoginServerPluginRequest.data;
    }

    public int getMessageId() {
        return this.messageID;
    }

    public void setMessageId(int messageID) {
        this.messageID = messageID;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
