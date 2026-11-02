package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerEffect extends PacketWrapper<WrapperPlayServerEffect> {

    private int type;

    private Vector3i position;

    private int data;

    private boolean globalEvent;

    public WrapperPlayServerEffect(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEffect(int type, Vector3i vector3i, int data, boolean globalEvent) {
        super(PacketType.Play.Server.EFFECT);
        this.type = type;
        this.position = vector3i;
        this.data = data;
        this.globalEvent = globalEvent;
    }

    @Override
    public void read() {
        this.type = this.readInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.position = this.readBlockPosition();
        } else {
            this.position = new Vector3i(this.readInt(), this.readByte() & 255, this.readInt());
        }
        this.data = this.readInt();
        this.globalEvent = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeInt(this.type);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeBlockPosition(this.position);
        } else {
            this.writeInt(this.position.x);
            this.writeByte(this.position.y & 0xFF);
            this.writeInt(this.position.z);
        }
        this.writeInt(this.data);
        this.writeBoolean(this.globalEvent);
    }

    public void copy(WrapperPlayServerEffect wrapperPlayServerEffect) {
        this.type = wrapperPlayServerEffect.type;
        this.position = wrapperPlayServerEffect.position;
        this.data = wrapperPlayServerEffect.data;
        this.globalEvent = wrapperPlayServerEffect.globalEvent;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isGlobalEvent() {
        return this.globalEvent;
    }

    public void setGlobalEvent(boolean globalEvent) {
        this.globalEvent = globalEvent;
    }
}
