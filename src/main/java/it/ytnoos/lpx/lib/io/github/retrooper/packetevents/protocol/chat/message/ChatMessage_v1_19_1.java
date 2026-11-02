package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public class ChatMessage_v1_19_1 extends ChatMessage_v1_16 {

    private String plainContent;

    @Nullable private Component unsignedChatContent;

    private ChatType.Bound chatFormatting;

    private byte @Nullable [] previousSignature;

    private byte[] signature;

    private Instant timestamp;

    private long salt;

    private LastSeenMessages lastSeenMessages;

    private FilterMask filterMask;

    public ChatMessage_v1_19_1(
            String plainContent,
            Component component,
            @Nullable Component component2,
            UUID uUID,
            ChatType.Bound bound,
            byte @Nullable [] previousSignature,
            byte[] signature,
            Instant instant,
            long salt,
            LastSeenMessages lastSeenMessages2,
            FilterMask filterMask2) {
        super(component, bound.getType(), uUID);
        this.plainContent = plainContent;
        this.unsignedChatContent = component2;
        this.chatFormatting = bound;
        this.previousSignature = previousSignature;
        this.signature = signature;
        this.timestamp = instant;
        this.salt = salt;
        this.lastSeenMessages = lastSeenMessages2;
        this.filterMask = filterMask2;
    }

    public String getPlainContent() {
        return this.plainContent;
    }

    public void setPlainContent(String plainContent) {
        this.plainContent = plainContent;
    }

    public boolean isChatContentDecorated() {
        return !this.getChatContent().equals(Component.text(this.plainContent));
    }

    @Nullable
    public Component getUnsignedChatContent() {
        return this.unsignedChatContent;
    }

    public void setUnsignedChatContent(@Nullable Component component) {
        this.unsignedChatContent = component;
    }

    @Override
    public ChatType getType() {
        return this.chatFormatting.getType();
    }

    @Override
    public void setType(ChatType chatType) {
        this.chatFormatting.setType(chatType);
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

    public byte @Nullable [] getPreviousSignature() {
        return this.previousSignature;
    }

    public void setPreviousSignature(byte @Nullable [] previousSignature) {
        this.previousSignature = previousSignature;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
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

    public LastSeenMessages getLastSeenMessages() {
        return this.lastSeenMessages;
    }

    public void setLastSeenMessages(LastSeenMessages lastSeenMessages2) {
        this.lastSeenMessages = lastSeenMessages2;
    }

    public FilterMask getFilterMask() {
        return this.filterMask;
    }

    public void setFilterMask(FilterMask filterMask2) {
        this.filterMask = filterMask2;
    }

    @Deprecated
    public static class ChatTypeBoundNetwork {

        private ChatType type;

        private Component name;

        @Nullable private Component targetName;

        public ChatTypeBoundNetwork(
                ChatType chatType, Component component, @Nullable Component component2) {
            this.type = chatType;
            this.name = component;
            this.targetName = component2;
        }

        public ChatType getType() {
            return this.type;
        }

        public void setType(ChatType chatType) {
            this.type = chatType;
        }

        public Component getName() {
            return this.name;
        }

        public void setName(Component component) {
            this.name = component;
        }

        @Nullable
        public Component getTargetName() {
            return this.targetName;
        }

        public void setTargetName(@Nullable Component component) {
            this.targetName = component;
        }
    }
}
