package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerDisplayScoreboard
        extends PacketWrapper<WrapperPlayServerDisplayScoreboard> {

    private int position;

    private String scoreName;

    public WrapperPlayServerDisplayScoreboard(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDisplayScoreboard(int position, String scoreName) {
        super(PacketType.Play.Server.DISPLAY_SCOREBOARD);
        this.position = position;
        this.scoreName = scoreName;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            this.position = this.readVarInt();
        } else {
            this.position = this.readByte();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.scoreName = this.readString();
        } else {
            this.scoreName = this.readString(16);
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            this.writeVarInt(this.position);
        } else {
            this.writeByte(this.position);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.writeString(this.scoreName);
        } else {
            this.writeString(this.scoreName, 16);
        }
    }

    public void copy(WrapperPlayServerDisplayScoreboard wrapperPlayServerDisplayScoreboard) {
        this.position = wrapperPlayServerDisplayScoreboard.position;
        this.scoreName = wrapperPlayServerDisplayScoreboard.scoreName;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getScoreName() {
        return this.scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }
}
