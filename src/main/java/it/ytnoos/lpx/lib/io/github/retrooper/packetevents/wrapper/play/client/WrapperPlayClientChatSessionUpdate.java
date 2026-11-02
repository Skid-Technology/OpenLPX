package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientChatSessionUpdate
        extends PacketWrapper<WrapperPlayClientChatSessionUpdate> {

    private RemoteChatSession chatSession;

    public WrapperPlayClientChatSessionUpdate(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChatSessionUpdate(RemoteChatSession remoteChatSession) {
        super(PacketType.Play.Client.CHAT_SESSION_UPDATE);
        this.chatSession = remoteChatSession;
    }

    @Override
    public void read() {
        this.chatSession = this.readRemoteChatSession();
    }

    @Override
    public void write() {
        this.writeRemoteChatSession(this.chatSession);
    }

    public void copy(WrapperPlayClientChatSessionUpdate wrapperPlayClientChatSessionUpdate) {
        this.chatSession = wrapperPlayClientChatSessionUpdate.chatSession;
    }

    public RemoteChatSession getChatSession() {
        return this.chatSession;
    }

    public void setChatSession(RemoteChatSession remoteChatSession) {
        this.chatSession = remoteChatSession;
    }
}
