package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class ChatMessage_v1_19_3 extends ChatMessage_v1_16 {

    int index;

    byte[] signature;

    String plainContent;

    Instant timestamp;

    long salt;

    LastSeenMessages.Packed lastSeenMessagesPacked;

    @Nullable Component unsignedChatContent;

    FilterMask filterMask;

    ChatType.Bound chatFormatting;

    public ChatMessage_v1_19_3(
            UUID uUID,
            int index,
            byte[] signature,
            String plainContent,
            Instant instant,
            long salt,
            LastSeenMessages.Packed packed,
            @Nullable Component component,
            FilterMask filterMask2,
            ChatType.Bound bound) {
        super(Component.text(plainContent), bound.getType(), uUID);
        this.index = index;
        this.signature = signature;
        this.plainContent = plainContent;
        this.timestamp = instant;
        this.salt = salt;
        this.lastSeenMessagesPacked = packed;
        this.unsignedChatContent = component;
        this.filterMask = filterMask2;
        this.chatFormatting = bound;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    @Override
    public Component getChatContent() {
        return Component.text(this.plainContent);
    }

    @Deprecated
    @Override
    public void setChatContent(Component component) {
        throw new UnsupportedOperationException(
                "PacketEvents is not able to serialize components to plain-text. Please use the #setPlainContent instead to update the content.");
    }

    public String getPlainContent() {
        return this.plainContent;
    }

    public void setPlainContent(String plainContent) {
        this.plainContent = plainContent;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Instant instant) {
        this.timestamp = instant;
    }

    public long getSalt() {
        return this.salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public LastSeenMessages.Packed getLastSeenMessagesPacked() {
        return this.lastSeenMessagesPacked;
    }

    public void setLastSeenMessagesPacked(LastSeenMessages.Packed packed) {
        this.lastSeenMessagesPacked = packed;
    }

    public Optional<Component> getUnsignedChatContent() {
        return Optional.ofNullable(this.unsignedChatContent);
    }

    public void setUnsignedChatContent(@Nullable Component component) {
        this.unsignedChatContent = component;
    }

    public FilterMask getFilterMask() {
        return this.filterMask;
    }

    public void setFilterMask(FilterMask filterMask2) {
        this.filterMask = filterMask2;
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
