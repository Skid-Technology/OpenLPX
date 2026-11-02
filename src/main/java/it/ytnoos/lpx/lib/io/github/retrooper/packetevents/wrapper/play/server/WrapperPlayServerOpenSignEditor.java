package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerOpenSignEditor
        extends PacketWrapper<WrapperPlayServerOpenSignEditor> {

    private Vector3i position;

    private boolean isFrontText;

    public WrapperPlayServerOpenSignEditor(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerOpenSignEditor(Vector3i vector3i, boolean isFrontText) {
        super(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        this.position = vector3i;
        this.isFrontText = isFrontText;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.position = new Vector3i(this.readLong(), this.serverVersion);
        } else {
            int value = this.readInt();
            int value2 = this.readInt();
            int value3 = this.readInt();
            this.position = new Vector3i(value, value2, value3);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)) {
            this.isFrontText = this.readBoolean();
        } else {
            this.isFrontText = true;
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            long serializedPosition = this.position.getSerializedPosition(this.serverVersion);
            this.writeLong(serializedPosition);
        } else {
            this.writeInt(this.position.x);
            this.writeInt(this.position.y);
            this.writeInt(this.position.z);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)) {
            this.writeBoolean(this.isFrontText);
        }
    }

    public void copy(WrapperPlayServerOpenSignEditor wrapperPlayServerOpenSignEditor) {
        this.position = wrapperPlayServerOpenSignEditor.position;
        this.isFrontText = wrapperPlayServerOpenSignEditor.isFrontText;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public boolean isFrontText() {
        return this.isFrontText;
    }

    public void setFrontText(boolean isFrontText) {
        this.isFrontText = isFrontText;
    }
}
