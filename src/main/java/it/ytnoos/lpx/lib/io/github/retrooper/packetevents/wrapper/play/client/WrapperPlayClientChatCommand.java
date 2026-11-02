package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.SignedCommandArgument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.MessageSignData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.SaltSignature;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;

public class WrapperPlayClientChatCommand extends PacketWrapper<WrapperPlayClientChatCommand> {

    private String command;

    private MessageSignData messageSignData;

    private List<SignedCommandArgument> signedArguments;

    @Nullable private LastSeenMessages.Update lastSeenMessages;

    @Nullable private LastSeenMessages.LegacyUpdate legacyLastSeenMessages;

    public WrapperPlayClientChatCommand(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChatCommand(
            String command,
            MessageSignData messageSignData2,
            List<SignedCommandArgument> signedArguments,
            @Nullable LastSeenMessages.LegacyUpdate legacyUpdate) {
        super(PacketType.Play.Client.CHAT_COMMAND);
        this.command = command;
        this.messageSignData = messageSignData2;
        this.signedArguments = signedArguments;
        this.legacyLastSeenMessages = legacyUpdate;
    }

    public WrapperPlayClientChatCommand(
            String command,
            MessageSignData messageSignData2,
            List<SignedCommandArgument> signedArguments,
            @Nullable LastSeenMessages.Update update) {
        super(PacketType.Play.Client.CHAT_COMMAND);
        this.command = command;
        this.messageSignData = messageSignData2;
        this.signedArguments = signedArguments;
        this.lastSeenMessages = update;
    }

    @Override
    public void read() {
        this.command =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)
                        ? this.readString()
                        : this.readString(256);
        Instant instant = this.readTimestamp();
        long value = this.readLong();
        this.messageSignData = new MessageSignData(new SaltSignature(value, new byte[0]), instant);
        this.signedArguments = this.readSignedCommandArguments();
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

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeString(this.command);
        } else {
            this.writeString(this.command, 256);
        }
        this.writeTimestamp(this.messageSignData.getTimestamp());
        this.writeLong(this.messageSignData.getSaltSignature().getSalt());
        this.writeSignedCommandArguments(this.signedArguments);
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

    public void copy(WrapperPlayClientChatCommand wrapperPlayClientChatCommand) {
        this.command = wrapperPlayClientChatCommand.command;
        this.messageSignData = wrapperPlayClientChatCommand.messageSignData;
        this.signedArguments = wrapperPlayClientChatCommand.signedArguments;
        this.lastSeenMessages = wrapperPlayClientChatCommand.lastSeenMessages;
        this.legacyLastSeenMessages = wrapperPlayClientChatCommand.legacyLastSeenMessages;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public MessageSignData getMessageSignData() {
        return this.messageSignData;
    }

    public void setMessageSignData(MessageSignData messageSignData2) {
        this.messageSignData = messageSignData2;
    }

    public List<SignedCommandArgument> getSignedArguments() {
        return this.signedArguments;
    }

    public void setSignedArguments(List<SignedCommandArgument> signedArguments) {
        this.signedArguments = signedArguments;
    }

    public LastSeenMessages.@Nullable Update getLastSeenMessages() {
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
