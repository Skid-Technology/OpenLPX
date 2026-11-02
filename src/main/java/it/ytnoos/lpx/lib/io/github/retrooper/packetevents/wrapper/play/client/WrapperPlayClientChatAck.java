package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientChatAck extends PacketWrapper<WrapperPlayClientChatAck> {

    private LastSeenMessages.LegacyUpdate lastSeenMessages;

    private int offset;

    public WrapperPlayClientChatAck(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChatAck(LastSeenMessages.LegacyUpdate legacyUpdate) {
        super(PacketType.Play.Client.CHAT_ACK);
        this.lastSeenMessages = legacyUpdate;
    }

    public WrapperPlayClientChatAck(int offset) {
        super(PacketType.Play.Client.CHAT_ACK);
        this.offset = offset;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            this.offset = this.readVarInt();
        } else {
            this.lastSeenMessages = this.readLegacyLastSeenMessagesUpdate();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            this.writeVarInt(this.offset);
        } else {
            this.writeLegacyLastSeenMessagesUpdate(this.lastSeenMessages);
        }
    }

    public void copy(WrapperPlayClientChatAck wrapperPlayClientChatAck) {
        this.lastSeenMessages = wrapperPlayClientChatAck.lastSeenMessages;
        this.offset = wrapperPlayClientChatAck.offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public LastSeenMessages.LegacyUpdate getLastSeenMessages() {
        return this.lastSeenMessages;
    }

    public void setLastSeenMessages(LastSeenMessages.LegacyUpdate legacyUpdate) {
        this.lastSeenMessages = legacyUpdate;
    }
}
