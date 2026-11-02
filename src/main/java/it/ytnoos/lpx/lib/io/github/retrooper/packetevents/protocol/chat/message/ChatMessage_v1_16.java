package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import java.util.UUID;

public class ChatMessage_v1_16 extends ChatMessage {

    private UUID senderUUID;

    public ChatMessage_v1_16(Component component, ChatType chatType, UUID senderUUID) {
        super(component, chatType);
        this.senderUUID = senderUUID;
    }

    public UUID getSenderUUID() {
        return this.senderUUID;
    }

    public void setSenderUUID(UUID senderUUID) {
        this.senderUUID = senderUUID;
    }
}
