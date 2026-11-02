package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class WrapperPlayServerDisguisedChat extends PacketWrapper<WrapperPlayServerDisguisedChat> {

    private Component message;

    private ChatType.Bound chatFormatting;

    public WrapperPlayServerDisguisedChat(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDisguisedChat(Component component, ChatType.Bound bound) {
        super(PacketType.Play.Server.DISGUISED_CHAT);
        this.message = component;
        this.chatFormatting = bound;
    }

    @Override
    public void read() {
        this.message = this.readComponent();
        this.chatFormatting = this.readChatTypeBoundNetwork();
    }

    @Override
    public void write() {
        this.writeComponent(this.message);
        this.writeChatTypeBoundNetwork(this.chatFormatting);
    }

    public void copy(WrapperPlayServerDisguisedChat wrapperPlayServerDisguisedChat) {
        this.message = wrapperPlayServerDisguisedChat.message;
        this.chatFormatting = wrapperPlayServerDisguisedChat.chatFormatting;
    }

    public Component getMessage() {
        return this.message;
    }

    public void setMessage(Component component) {
        this.message = component;
    }

    public ChatType.Bound getChatFormatting() {
        return this.chatFormatting;
    }

    public void setChatFormatting(ChatType.Bound bound) {
        this.chatFormatting = bound;
    }

    @Deprecated
    public ChatType.Bound getChatType() {
        return this.chatFormatting;
    }

    @Deprecated
    public void setChatType(ChatType.Bound bound) {
        this.chatFormatting = bound;
    }
}
