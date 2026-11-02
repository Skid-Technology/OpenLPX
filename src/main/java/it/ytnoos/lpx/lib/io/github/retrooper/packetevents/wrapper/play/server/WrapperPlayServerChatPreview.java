package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerChatPreview extends PacketWrapper<WrapperPlayServerChatPreview> {

    private int queryId;

    @Nullable private Component message;

    public WrapperPlayServerChatPreview(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChatPreview(int queryId, @Nullable Component component) {
        super(PacketType.Play.Server.CHAT_PREVIEW_PACKET);
        this.queryId = queryId;
        this.message = component;
    }

    @Override
    public void read() {
        this.queryId = this.readInt();
        this.message = this.readOptional(PacketWrapper::readComponent);
    }

    @Override
    public void write() {
        this.writeInt(this.queryId);
        this.writeOptional(this.message, PacketWrapper::writeComponent);
    }

    public void copy(WrapperPlayServerChatPreview wrapperPlayServerChatPreview) {
        this.queryId = wrapperPlayServerChatPreview.queryId;
        this.message = wrapperPlayServerChatPreview.message;
    }

    public int getQueryId() {
        return this.queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public Optional<Component> getMessage() {
        return Optional.ofNullable(this.message);
    }

    public void setMessage(@Nullable Component component) {
        this.message = component;
    }
}
