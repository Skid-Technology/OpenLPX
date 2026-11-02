package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientKeepAlive extends PacketWrapper<WrapperPlayClientKeepAlive> {

    private long id;

    public WrapperPlayClientKeepAlive(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientKeepAlive(long id) {
        super(PacketType.Play.Client.KEEP_ALIVE);
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
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_12_2)) {
            this.writeLong(this.id);
        } else if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt((int) this.id);
        } else {
            this.writeInt((int) this.id);
        }
    }

    public void copy(WrapperPlayClientKeepAlive wrapperPlayClientKeepAlive) {
        this.id = wrapperPlayClientKeepAlive.id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
