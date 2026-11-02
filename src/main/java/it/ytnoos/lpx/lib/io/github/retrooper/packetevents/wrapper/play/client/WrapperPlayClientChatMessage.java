package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.MessageSignData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

public class WrapperPlayClientChatMessage extends PacketWrapper<WrapperPlayClientChatMessage> {

    private static final int LEGACY_MAX_LENGTH =
            Integer.getInteger("packetevents.legacy-chat-max-length", 100);

    private String message;

    private MessageSignData messageSignData;

    @Nullable private LastSeenMessages.Update lastSeenMessages;

    @Nullable private LastSeenMessages.LegacyUpdate legacyLastSeenMessages;

    public WrapperPlayClientChatMessage(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChatMessage(
            String message,
            MessageSignData messageSignData2,
            @Nullable LastSeenMessages.LegacyUpdate legacyUpdate) {
        super(PacketType.Play.Client.CHAT_MESSAGE);
        this.message = message;
        this.messageSignData = messageSignData2;
        this.legacyLastSeenMessages = legacyUpdate;
    }

    public WrapperPlayClientChatMessage(
            String message,
            MessageSignData messageSignData2,
            @Nullable LastSeenMessages.Update update) {
        super(PacketType.Play.Client.CHAT_MESSAGE);
        this.message = message;
        this.messageSignData = messageSignData2;
        this.lastSeenMessages = update;
    }

    @Override
    public void read() {
        int value =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)
                        ? 256
                        : LEGACY_MAX_LENGTH;
        this.message = this.readString(value);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            Instant instant = this.readTimestamp();
            this.messageSignData = new MessageSignData(this.readSaltSignature(), instant);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                this.lastSeenMessages = this.readLastSeenMessagesUpdate();
            } else {
                boolean matches = this.readBoolean();
                this.messageSignData.setSignedPreview(matches);
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
                    this.legacyLastSeenMessages = this.readLegacyLastSeenMessagesUpdate();
                }
            }
        }
    }

    @Override
    public void write() {
        int value =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)
                        ? 256
                        : LEGACY_MAX_LENGTH;
        this.writeString(this.message, value);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            this.writeTimestamp(this.messageSignData.getTimestamp());
            this.writeSaltSignature(this.messageSignData.getSaltSignature());
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                if (this.lastSeenMessages != null) {
                    this.writeLastSeenMessagesUpdate(this.lastSeenMessages);
                }
            } else {
                this.writeBoolean(this.messageSignData.isSignedPreview());
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)
                        && this.legacyLastSeenMessages != null) {
                    this.writeLegacyLastSeenMessagesUpdate(this.legacyLastSeenMessages);
                }
            }
        }
    }

    public void copy(WrapperPlayClientChatMessage wrapperPlayClientChatMessage) {
        this.message = wrapperPlayClientChatMessage.message;
        this.messageSignData = wrapperPlayClientChatMessage.messageSignData;
        this.lastSeenMessages = wrapperPlayClientChatMessage.lastSeenMessages;
        this.legacyLastSeenMessages = wrapperPlayClientChatMessage.legacyLastSeenMessages;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<MessageSignData> getMessageSignData() {
        return Optional.ofNullable(this.messageSignData);
    }

    public void setMessageSignData(@Nullable MessageSignData messageSignData2) {
        this.messageSignData = messageSignData2;
    }

    @Nullable
    public LastSeenMessages.Update getLastSeenMessages() {
        return this.lastSeenMessages;
    }

    public void setLastSeenMessages(LastSeenMessages.@Nullable Update update) {
        this.lastSeenMessages = update;
    }

    @Nullable
    public LastSeenMessages.LegacyUpdate getLegacyLastSeenMessages() {
        return this.legacyLastSeenMessages;
    }

    public void setLegacyLastSeenMessages(@Nullable LastSeenMessages.LegacyUpdate legacyUpdate) {
        this.legacyLastSeenMessages = legacyUpdate;
    }
}
