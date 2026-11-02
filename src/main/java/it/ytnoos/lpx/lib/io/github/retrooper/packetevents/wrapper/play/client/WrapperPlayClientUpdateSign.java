package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientUpdateSign extends PacketWrapper<WrapperPlayClientUpdateSign> {

    private Vector3i blockPosition;

    private String[] textLines;

    private boolean isFrontText;

    public WrapperPlayClientUpdateSign(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientUpdateSign(Vector3i vector3i, String[] texts, boolean isFrontText) {
        super(PacketType.Play.Client.UPDATE_SIGN);
        this.blockPosition = vector3i;
        this.textLines = texts;
        this.isFrontText = isFrontText;
    }

    @Override
    public void read() {
        int maxUpdateSignLineLength =
                PacketEvents.getAPI().getSettings().getMaxUpdateSignLineLength();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.blockPosition = new Vector3i(this.readLong(), this.serverVersion);
        } else {
            int value = this.readInt();
            short value2 = this.readShort();
            int value3 = this.readInt();
            this.blockPosition = new Vector3i(value, value2, value3);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)
                && this.serverVersion.isOlderThan(ServerVersion.V_26_3)) {
            this.isFrontText = this.readBoolean();
        } else {
            this.isFrontText = true;
        }
        this.textLines = new String[4];
        for (int index = 0; index < 4; index++) {
            this.textLines[index] = this.readString(maxUpdateSignLineLength);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.isFrontText = this.readBoolean();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            long serializedPosition = this.blockPosition.getSerializedPosition(this.serverVersion);
            this.writeLong(serializedPosition);
        } else {
            this.writeInt(this.blockPosition.x);
            this.writeShort(this.blockPosition.y);
            this.writeInt(this.blockPosition.z);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20)
                && this.serverVersion.isOlderThan(ServerVersion.V_26_3)) {
            this.writeBoolean(this.isFrontText);
        }
        for (int index = 0; index < 4; index++) {
            this.writeString(this.textLines[index]);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.writeBoolean(this.isFrontText);
        }
    }

    public void copy(WrapperPlayClientUpdateSign wrapperPlayClientUpdateSign) {
        this.blockPosition = wrapperPlayClientUpdateSign.blockPosition;
        this.isFrontText = wrapperPlayClientUpdateSign.isFrontText;
        this.textLines = wrapperPlayClientUpdateSign.textLines;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public String[] getTextLines() {
        return this.textLines;
    }

    public void setTextLines(String[] texts) {
        this.textLines = texts;
    }

    public boolean isFrontText() {
        return this.isFrontText;
    }

    public void setFrontText(boolean isFrontText) {
        this.isFrontText = isFrontText;
    }
}
