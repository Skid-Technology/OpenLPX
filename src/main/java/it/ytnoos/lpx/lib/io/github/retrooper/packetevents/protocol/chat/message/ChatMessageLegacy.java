package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

public class ChatMessageLegacy extends ChatMessage {

    public ChatMessageLegacy(Component component, ChatType chatType) {
        super(component, chatType);
    }
}
