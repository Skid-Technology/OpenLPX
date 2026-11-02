package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19_3;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.ChatMessageProcessor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public class ChatMessageProcessor_v1_19_3 implements ChatMessageProcessor {

    @Override
    public ChatMessage readChatMessage(@NotNull PacketWrapper<?> packetWrapper) {
        UUID uUID = packetWrapper.readUUID();
        int value = packetWrapper.readVarInt();
        byte[] values = packetWrapper.readOptional(result -> result.readBytes(256));
        String text = packetWrapper.readString(256);
        Instant instant = packetWrapper.readTimestamp();
        long value2 = packetWrapper.readLong();
        LastSeenMessages.Packed packed = packetWrapper.readLastSeenMessagesPacked();
        Component component = packetWrapper.readOptional(PacketWrapper::readComponent);
        FilterMask filterMask = packetWrapper.readFilterMask();
        ChatType.Bound bound = packetWrapper.readChatTypeBoundNetwork();
        return new ChatMessage_v1_19_3(
                uUID, value, values, text, instant, value2, packed, component, filterMask, bound);
    }

    @Override
    public void writeChatMessage(
            @NotNull PacketWrapper<?> packetWrapper, @NotNull ChatMessage chatMessage) {
        ChatMessage_v1_19_3 chatMessage_v1_19_3 = (ChatMessage_v1_19_3) chatMessage;
        packetWrapper.writeUUID(chatMessage_v1_19_3.getSenderUUID());
        packetWrapper.writeVarInt(chatMessage_v1_19_3.getIndex());
        packetWrapper.writeOptional(chatMessage_v1_19_3.getSignature(), PacketWrapper::writeBytes);
        packetWrapper.writeString(chatMessage_v1_19_3.getPlainContent());
        packetWrapper.writeTimestamp(chatMessage_v1_19_3.getTimestamp());
        packetWrapper.writeLong(chatMessage_v1_19_3.getSalt());
        packetWrapper.writeLastSeenMessagesPacked(chatMessage_v1_19_3.getLastSeenMessagesPacked());
        packetWrapper.writeOptional(
                chatMessage_v1_19_3.getUnsignedChatContent().orElse(null),
                PacketWrapper::writeComponent);
        packetWrapper.writeFilterMask(chatMessage_v1_19_3.getFilterMask());
        packetWrapper.writeChatTypeBoundNetwork(chatMessage_v1_19_3.getChatFormatting());
    }
}
