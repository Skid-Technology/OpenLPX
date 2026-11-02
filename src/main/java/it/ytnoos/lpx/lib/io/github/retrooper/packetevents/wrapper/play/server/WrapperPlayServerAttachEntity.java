package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerAttachEntity extends PacketWrapper<WrapperPlayServerAttachEntity> {

    private int attachedId;

    private int holdingId;

    private boolean leash;

    public WrapperPlayServerAttachEntity(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerAttachEntity(int attachedId, int holdingId, boolean leash) {
        super(PacketType.Play.Server.ATTACH_ENTITY);
        this.attachedId = attachedId;
        this.holdingId = holdingId;
        this.leash = leash;
    }

    @Override
    public void read() {
        this.attachedId = this.readInt();
        this.holdingId = this.readInt();
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
            this.leash = this.readUnsignedByte() == 1;
        } else {
            this.leash = true;
        }
    }

    @Override
    public void write() {
        this.writeInt(this.attachedId);
        this.writeInt(this.holdingId);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
            this.writeByte(this.leash ? 1 : 0);
        }
    }

    public void copy(WrapperPlayServerAttachEntity wrapperPlayServerAttachEntity) {
        this.attachedId = wrapperPlayServerAttachEntity.attachedId;
        this.holdingId = wrapperPlayServerAttachEntity.holdingId;
        this.leash = wrapperPlayServerAttachEntity.leash;
    }

    public int getAttachedId() {
        return this.attachedId;
    }

    public void setAttachedId(int attachedId) {
        this.attachedId = attachedId;
    }

    public int getHoldingId() {
        return this.holdingId;
    }

    public void setHoldingId(int holdingId) {
        this.holdingId = holdingId;
    }

    public boolean isLeash() {
        return this.leash;
    }

    public void setLeash(boolean leash) {
        this.leash = leash;
    }
}
