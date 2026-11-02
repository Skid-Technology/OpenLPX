package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientChatCommandUnsigned
        extends PacketWrapper<WrapperPlayClientChatCommandUnsigned> {

    private String command;

    public WrapperPlayClientChatCommandUnsigned(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChatCommandUnsigned(String command) {
        super(PacketType.Play.Client.CHAT_COMMAND_UNSIGNED);
        this.command = command;
    }

    @Override
    public void read() {
        this.command =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)
                        ? this.readString()
                        : this.readString(256);
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeString(this.command);
        } else {
            this.writeString(this.command, 256);
        }
    }

    public void copy(WrapperPlayClientChatCommandUnsigned wrapperPlayClientChatCommandUnsigned) {
        this.command = wrapperPlayClientChatCommandUnsigned.command;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
