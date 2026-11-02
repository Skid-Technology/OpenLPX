package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientClickWindowButton
        extends PacketWrapper<WrapperPlayClientClickWindowButton> {

    private int windowID;

    private int buttonID;

    public WrapperPlayClientClickWindowButton(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientClickWindowButton(int windowID, int buttonID) {
        super(PacketType.Play.Client.CLICK_WINDOW_BUTTON);
        this.windowID = windowID;
        this.buttonID = buttonID;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.windowID = this.readContainerId();
            this.buttonID = this.readVarInt();
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.windowID = this.readVarInt();
            this.buttonID = this.readVarInt();
        } else {
            this.windowID = this.readByte();
            this.buttonID = this.readByte();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeContainerId(this.windowID);
            this.writeVarInt(this.buttonID);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeVarInt(this.windowID);
            this.writeVarInt(this.buttonID);
        } else {
            this.writeByte(this.windowID);
            this.writeByte(this.buttonID);
        }
    }

    public void copy(WrapperPlayClientClickWindowButton wrapperPlayClientClickWindowButton) {
        this.windowID = wrapperPlayClientClickWindowButton.windowID;
        this.buttonID = wrapperPlayClientClickWindowButton.buttonID;
    }

    public int getWindowId() {
        return this.windowID;
    }

    public void setWindowId(int windowID) {
        this.windowID = windowID;
    }

    public int getButtonId() {
        return this.buttonID;
    }

    public void setButtonId(int buttonID) {
        this.buttonID = buttonID;
    }
}
