package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientChatPreview extends PacketWrapper<WrapperPlayClientChatPreview> {

    private int queryId;

    private String message;

    public WrapperPlayClientChatPreview(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChatPreview(int queryId, String message) {
        super(PacketType.Play.Client.CHAT_PREVIEW);
        this.queryId = queryId;
        this.message = message;
    }

    @Override
    public void read() {
        this.queryId = this.readInt();
        this.message = this.readString(256);
    }

    @Override
    public void write() {
        this.writeInt(this.queryId);
        this.writeString(this.message, 256);
    }

    public void copy(WrapperPlayClientChatPreview wrapperPlayClientChatPreview) {
        this.queryId = wrapperPlayClientChatPreview.queryId;
        this.message = wrapperPlayClientChatPreview.message;
    }

    public int getQueryId() {
        return this.queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
