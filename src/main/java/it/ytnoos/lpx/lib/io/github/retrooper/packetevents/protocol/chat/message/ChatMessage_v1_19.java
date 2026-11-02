package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public class ChatMessage_v1_19 extends ChatMessage_v1_16 {

    @Nullable private Component unsignedChatContent;

    private Component senderDisplayName;

    @Nullable private Component teamName;

    private Instant timestamp;

    private long salt;

    private byte[] signature;

    public ChatMessage_v1_19(
            Component component,
            @Nullable Component component2,
            ChatType chatType,
            UUID uUID,
            Component component3,
            @Nullable Component component4,
            Instant instant,
            long salt,
            byte[] signature) {
        super(component, chatType, uUID);
        this.unsignedChatContent = component2;
        this.senderDisplayName = component3;
        this.teamName = component4;
        this.timestamp = instant;
        this.salt = salt;
        this.signature = signature;
    }

    @Nullable
    public Component getUnsignedChatContent() {
        return this.unsignedChatContent;
    }

    public Component getSenderDisplayName() {
        return this.senderDisplayName;
    }

    @Nullable
    public Component getTeamName() {
        return this.teamName;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public long getSalt() {
        return this.salt;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setUnsignedChatContent(@Nullable Component component) {
        this.unsignedChatContent = component;
    }

    public void setSenderDisplayName(Component component) {
        this.senderDisplayName = component;
    }

    public void setTeamName(@Nullable Component component) {
        this.teamName = component;
    }

    public void setTimestamp(Instant instant) {
        this.timestamp = instant;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
