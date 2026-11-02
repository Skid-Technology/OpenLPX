package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_16;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.ChatMessageProcessor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChatMessageProcessor_v1_16 implements ChatMessageProcessor {

    @Override
    public ChatMessage readChatMessage(@NotNull PacketWrapper<?> packetWrapper) {
        Component component = packetWrapper.readComponent();
        ChatType chatType = packetWrapper.readMappedEntity(ChatTypes.getRegistry());
        UUID uUID = packetWrapper.readUUID();
        return new ChatMessage_v1_16(component, chatType, uUID);
    }

    @Override
    public void writeChatMessage(
            @NotNull PacketWrapper<?> packetWrapper, @NotNull ChatMessage chatMessage) {
        packetWrapper.writeComponent(chatMessage.getChatContent());
        packetWrapper.writeMappedEntity(chatMessage.getType());
        packetWrapper.writeUUID(((ChatMessage_v1_16) chatMessage).getSenderUUID());
    }
}
