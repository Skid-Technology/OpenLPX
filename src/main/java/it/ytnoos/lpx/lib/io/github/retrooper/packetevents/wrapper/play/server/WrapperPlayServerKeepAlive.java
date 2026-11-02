package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerKeepAlive extends PacketWrapper<WrapperPlayServerKeepAlive> {

    private long id;

    public WrapperPlayServerKeepAlive(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerKeepAlive(long id) {
        super(PacketType.Play.Server.KEEP_ALIVE);
        this.id = id;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_12_2)) {
            this.id = this.readLong();
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.id = this.readVarInt();
        } else {
            this.id = this.readInt();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_12)) {
            this.writeLong(this.id);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt((int) this.id);
        } else {
            this.writeInt((int) this.id);
        }
    }

    public void copy(WrapperPlayServerKeepAlive wrapperPlayServerKeepAlive) {
        this.id = wrapperPlayServerKeepAlive.id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
