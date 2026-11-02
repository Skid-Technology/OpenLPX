package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.ChatMessageProcessor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public class ChatMessageProcessor_v1_19 implements ChatMessageProcessor {

    @Override
    public ChatMessage readChatMessage(@NotNull PacketWrapper<?> packetWrapper) {
        Component component = packetWrapper.readComponent();
        Component component2 = packetWrapper.readOptional(PacketWrapper::readComponent);
        ChatType chatType = packetWrapper.readMappedEntity(ChatTypes.getRegistry());
        UUID uUID = packetWrapper.readUUID();
        Component component3 = packetWrapper.readComponent();
        Component component4 = packetWrapper.readOptional(PacketWrapper::readComponent);
        Instant instant = packetWrapper.readTimestamp();
        long value = packetWrapper.readLong();
        byte[] values = packetWrapper.readByteArray();
        return new ChatMessage_v1_19(
                component,
                component2,
                chatType,
                uUID,
                component3,
                component4,
                instant,
                value,
                values);
    }

    @Override
    public void writeChatMessage(
            @NotNull PacketWrapper<?> packetWrapper, @NotNull ChatMessage chatMessage) {
        ChatMessage_v1_19 chatMessage_v1_19 = (ChatMessage_v1_19) chatMessage;
        packetWrapper.writeComponent(chatMessage_v1_19.getChatContent());
        packetWrapper.writeOptional(
                chatMessage_v1_19.getUnsignedChatContent(), PacketWrapper::writeComponent);
        packetWrapper.writeMappedEntity(chatMessage_v1_19.getType());
        packetWrapper.writeUUID(chatMessage_v1_19.getSenderUUID());
        packetWrapper.writeComponent(chatMessage_v1_19.getSenderDisplayName());
        packetWrapper.writeOptional(chatMessage_v1_19.getTeamName(), PacketWrapper::writeComponent);
        packetWrapper.writeTimestamp(chatMessage_v1_19.getTimestamp());
        packetWrapper.writeLong(chatMessage_v1_19.getSalt());
        packetWrapper.writeByteArray(chatMessage_v1_19.getSignature());
    }
}
