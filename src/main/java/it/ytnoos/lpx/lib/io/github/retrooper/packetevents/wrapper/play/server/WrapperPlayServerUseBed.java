package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUseBed extends PacketWrapper<WrapperPlayServerUseBed> {

    private int entityId;

    private Vector3i position;

    public WrapperPlayServerUseBed(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUseBed(int entityId, Vector3i vector3i) {
        super(PacketType.Play.Server.USE_BED);
        this.entityId = entityId;
        this.position = vector3i;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.entityId = this.readVarInt();
            this.position = this.readBlockPosition();
        } else {
            this.entityId = this.readInt();
            int value = this.readInt();
            short value2 = this.readUnsignedByte();
            int value3 = this.readInt();
            this.position = new Vector3i(value, value2, value3);
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeVarInt(this.entityId);
            this.writeBlockPosition(this.position);
        } else {
            this.writeInt(this.entityId);
            this.writeInt(this.position.getX());
            this.writeByte(this.position.getY());
            this.writeInt(this.position.getZ());
        }
    }

    public void copy(WrapperPlayServerUseBed wrapperPlayServerUseBed) {
        this.entityId = wrapperPlayServerUseBed.entityId;
        this.position = wrapperPlayServerUseBed.position;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }
}
