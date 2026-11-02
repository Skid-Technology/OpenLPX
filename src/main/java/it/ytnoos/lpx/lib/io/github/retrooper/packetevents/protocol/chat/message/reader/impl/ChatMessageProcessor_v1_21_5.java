package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_21_5;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.ChatMessageProcessor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public class ChatMessageProcessor_v1_21_5 implements ChatMessageProcessor {

    @Override
    public ChatMessage readChatMessage(@NotNull PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        UUID uUID = packetWrapper.readUUID();
        int value2 = packetWrapper.readVarInt();
        byte[] values = packetWrapper.readOptional(result -> result.readBytes(256));
        String text = packetWrapper.readString(256);
        Instant instant = packetWrapper.readTimestamp();
        long value3 = packetWrapper.readLong();
        LastSeenMessages.Packed packed = packetWrapper.readLastSeenMessagesPacked();
        Component component = packetWrapper.readOptional(PacketWrapper::readComponent);
        FilterMask filterMask = packetWrapper.readFilterMask();
        ChatType.Bound bound = packetWrapper.readChatTypeBoundNetwork();
        return new ChatMessage_v1_21_5(
                value,
                uUID,
                value2,
                values,
                text,
                instant,
                value3,
                packed,
                component,
                filterMask,
                bound);
    }

    @Override
    public void writeChatMessage(
            @NotNull PacketWrapper<?> packetWrapper, @NotNull ChatMessage chatMessage) {
        ChatMessage_v1_21_5 chatMessage_v1_21_5 = (ChatMessage_v1_21_5) chatMessage;
        packetWrapper.writeVarInt(chatMessage_v1_21_5.getGlobalIndex());
        packetWrapper.writeUUID(chatMessage_v1_21_5.getSenderUUID());
        packetWrapper.writeVarInt(chatMessage_v1_21_5.getIndex());
        packetWrapper.writeOptional(chatMessage_v1_21_5.getSignature(), PacketWrapper::writeBytes);
        packetWrapper.writeString(chatMessage_v1_21_5.getPlainContent());
        packetWrapper.writeTimestamp(chatMessage_v1_21_5.getTimestamp());
        packetWrapper.writeLong(chatMessage_v1_21_5.getSalt());
        packetWrapper.writeLastSeenMessagesPacked(chatMessage_v1_21_5.getLastSeenMessagesPacked());
        packetWrapper.writeOptional(
                chatMessage_v1_21_5.getUnsignedChatContent().orElse(null),
                PacketWrapper::writeComponent);
        packetWrapper.writeFilterMask(chatMessage_v1_21_5.getFilterMask());
        packetWrapper.writeChatTypeBoundNetwork(chatMessage_v1_21_5.getChatFormatting());
    }
}
