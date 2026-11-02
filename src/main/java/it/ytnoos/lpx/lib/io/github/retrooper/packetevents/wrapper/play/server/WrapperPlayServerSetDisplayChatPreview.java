package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerSetDisplayChatPreview
        extends PacketWrapper<WrapperPlayServerSetDisplayChatPreview> {

    private boolean chatPreviewDisplay;

    public WrapperPlayServerSetDisplayChatPreview(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSetDisplayChatPreview(boolean chatPreviewDisplay) {
        super(PacketType.Play.Server.DISPLAY_CHAT_PREVIEW);
        this.chatPreviewDisplay = chatPreviewDisplay;
    }

    @Override
    public void read() {
        this.chatPreviewDisplay = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeBoolean(this.chatPreviewDisplay);
    }

    public void copy(
            WrapperPlayServerSetDisplayChatPreview wrapperPlayServerSetDisplayChatPreview) {
        this.chatPreviewDisplay = wrapperPlayServerSetDisplayChatPreview.chatPreviewDisplay;
    }

    public boolean isChatPreviewDisplay() {
        return this.chatPreviewDisplay;
    }

    public void setChatPreviewDisplay(boolean chatPreviewDisplay) {
        this.chatPreviewDisplay = chatPreviewDisplay;
    }
}
