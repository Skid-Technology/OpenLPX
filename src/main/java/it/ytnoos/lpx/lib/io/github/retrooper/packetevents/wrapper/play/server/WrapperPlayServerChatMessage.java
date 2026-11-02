package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.ChatMessageProcessor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessorLegacy;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessor_v1_16;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessor_v1_19;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessor_v1_19_1;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessor_v1_19_3;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessor_v1_21_5;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.reader.impl.ChatMessageProcessor_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

public class WrapperPlayServerChatMessage extends PacketWrapper<WrapperPlayServerChatMessage> {

    private static final ChatMessageProcessor CHAT_V1_7_PROCESSOR = new ChatMessageProcessor_v1_7();

    private static final ChatMessageProcessor CHAT_V1_8_PROCESSOR =
            new ChatMessageProcessorLegacy();

    private static final ChatMessageProcessor CHAT_V1_16_PROCESSOR =
            new ChatMessageProcessor_v1_16();

    private static final ChatMessageProcessor CHAT_V1_19_PROCESSOR =
            new ChatMessageProcessor_v1_19();

    private static final ChatMessageProcessor CHAT_V1_19_1_PROCESSOR =
            new ChatMessageProcessor_v1_19_1();

    private static final ChatMessageProcessor CHAT_V1_19_3_PROCESSOR =
            new ChatMessageProcessor_v1_19_3();

    private static final ChatMessageProcessor CHAT_V1_21_5_PROCESSOR =
            new ChatMessageProcessor_v1_21_5();

    private ChatMessage message;

    public WrapperPlayServerChatMessage(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChatMessage(ChatMessage chatMessage) {
        super(PacketType.Play.Server.CHAT_MESSAGE);
        this.message = chatMessage;
    }

    @Override
    public void read() {
        this.message = this.getProcessor().readChatMessage(this);
    }

    @Override
    public void write() {
        this.getProcessor().writeChatMessage(this, this.message);
    }

    public void copy(WrapperPlayServerChatMessage wrapperPlayServerChatMessage) {
        this.message = wrapperPlayServerChatMessage.message;
    }

    public ChatMessage getMessage() {
        return this.message;
    }

    public void setMessage(ChatMessage chatMessage) {
        this.message = chatMessage;
    }

    @Internal
    protected ChatMessageProcessor getProcessor() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            return CHAT_V1_21_5_PROCESSOR;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            return CHAT_V1_19_3_PROCESSOR;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
            return CHAT_V1_19_1_PROCESSOR;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            return CHAT_V1_19_PROCESSOR;
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            return CHAT_V1_16_PROCESSOR;
        } else {
            return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)
                    ? CHAT_V1_8_PROCESSOR
                    : CHAT_V1_7_PROCESSOR;
        }
    }
}
