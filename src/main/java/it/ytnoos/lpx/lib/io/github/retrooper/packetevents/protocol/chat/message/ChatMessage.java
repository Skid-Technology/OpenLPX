package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class ChatMessage {

    private Component chatContent;

    private ChatType type;

    protected ChatMessage(Component component, ChatType chatType) {
        this.chatContent = component;
        this.type = chatType;
    }

    public Component getChatContent() {
        return this.chatContent;
    }

    public String getChatContentJson(ClientVersion clientVersion) {
        return AdventureSerializer.serializer(clientVersion).asJson(this.getChatContent());
    }

    public void setChatContent(Component component) {
        this.chatContent = component;
    }

    public void setChatContentJson(ClientVersion clientVersion, String text) {
        this.setChatContent(AdventureSerializer.serializer(clientVersion).fromJson(text));
    }

    public ChatType getType() {
        return this.type;
    }

    public void setType(ChatType chatType) {
        this.type = chatType;
    }
}
