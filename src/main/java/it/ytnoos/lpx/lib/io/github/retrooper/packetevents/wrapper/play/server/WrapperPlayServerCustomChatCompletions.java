package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatCompletionAction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperPlayServerCustomChatCompletions
        extends PacketWrapper<WrapperPlayServerCustomChatCompletions> {

    private ChatCompletionAction action;

    private List<String> entries;

    public WrapperPlayServerCustomChatCompletions(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCustomChatCompletions(
            ChatCompletionAction chatCompletionAction, List<String> entries) {
        super(PacketType.Play.Server.CUSTOM_CHAT_COMPLETIONS);
        this.action = chatCompletionAction;
        this.entries = entries;
    }

    @Override
    public void read() {
        this.action = ChatCompletionAction.fromId(this.readVarInt());
        this.entries = this.readList(PacketWrapper::readString);
    }

    @Override
    public void write() {
        this.writeVarInt(this.action.ordinal());
        this.writeList(this.entries, PacketWrapper::writeString);
    }

    public void copy(
            WrapperPlayServerCustomChatCompletions wrapperPlayServerCustomChatCompletions) {
        this.action = wrapperPlayServerCustomChatCompletions.action;
        this.entries = wrapperPlayServerCustomChatCompletions.entries;
    }

    public ChatCompletionAction getAction() {
        return this.action;
    }

    public void setAction(ChatCompletionAction chatCompletionAction) {
        this.action = chatCompletionAction;
    }

    public List<String> getEntries() {
        return this.entries;
    }

    public void setEntries(List<String> entries) {
        this.entries = entries;
    }
}
