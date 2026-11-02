package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19_1;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.ChatMessageProcessor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public class ChatMessageProcessor_v1_19_1 implements ChatMessageProcessor {

    @Override
    public ChatMessage readChatMessage(@NotNull PacketWrapper<?> packetWrapper) {
        byte[] values = packetWrapper.readOptional(PacketWrapper::readByteArray);
        UUID uUID = packetWrapper.readUUID();
        byte[] values2 = packetWrapper.readByteArray();
        String text2 = packetWrapper.readString(256);
        Component component;
        if ((component = packetWrapper.readOptional(PacketWrapper::readComponent)) == null
                && text2.isEmpty()) {
            component = Component.empty();
        } else if (component == null) {
            component = Component.text(text2);
        }
        Instant instant = packetWrapper.readTimestamp();
        long value = packetWrapper.readLong();
        LastSeenMessages lastSeenMessages = packetWrapper.readLastSeenMessages();
        Component component2 = packetWrapper.readOptional(PacketWrapper::readComponent);
        FilterMask filterMask = packetWrapper.readFilterMask();
        ChatType.Bound bound = packetWrapper.readChatTypeBoundNetwork();
        return new ChatMessage_v1_19_1(
                text2,
                component,
                component2,
                uUID,
                bound,
                values,
                values2,
                instant,
                value,
                lastSeenMessages,
                filterMask);
    }

    @Override
    public void writeChatMessage(
            @NotNull PacketWrapper<?> packetWrapper, @NotNull ChatMessage chatMessage) {
        ChatMessage_v1_19_1 chatMessage_v1_19_1 = (ChatMessage_v1_19_1) chatMessage;
        packetWrapper.writeOptional(
                chatMessage_v1_19_1.getPreviousSignature(), PacketWrapper::writeByteArray);
        packetWrapper.writeUUID(chatMessage_v1_19_1.getSenderUUID());
        packetWrapper.writeByteArray(chatMessage_v1_19_1.getSignature());
        packetWrapper.writeString(chatMessage_v1_19_1.getPlainContent(), 256);
        packetWrapper.writeOptional(
                chatMessage_v1_19_1.getChatContent(), PacketWrapper::writeComponent);
        packetWrapper.writeTimestamp(chatMessage_v1_19_1.getTimestamp());
        packetWrapper.writeLong(chatMessage_v1_19_1.getSalt());
        packetWrapper.writeLastSeenMessages(chatMessage_v1_19_1.getLastSeenMessages());
        packetWrapper.writeOptional(
                chatMessage_v1_19_1.getUnsignedChatContent(), PacketWrapper::writeComponent);
        packetWrapper.writeFilterMask(chatMessage_v1_19_1.getFilterMask());
        packetWrapper.writeChatTypeBoundNetwork(chatMessage_v1_19_1.getChatFormatting());
    }
}
