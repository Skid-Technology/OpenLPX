package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerCollectItem extends PacketWrapper<WrapperPlayServerCollectItem> {

    private int collectedEntityId;

    private int collectorEntityId;

    private int pickupItemCount;

    public WrapperPlayServerCollectItem(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCollectItem(
            int collectedEntityId, int collectorEntityId, int pickupItemCount) {
        super(PacketType.Play.Server.COLLECT_ITEM);
        this.collectedEntityId = collectedEntityId;
        this.collectorEntityId = collectorEntityId;
        this.pickupItemCount = pickupItemCount;
    }

    @Override
    public void read() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.collectedEntityId = this.readInt();
            this.collectorEntityId = this.readInt();
        } else {
            this.collectedEntityId = this.readVarInt();
            this.collectorEntityId = this.readVarInt();
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)) {
                this.pickupItemCount = this.readVarInt();
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_7_10)) {
            this.writeInt(this.collectedEntityId);
            this.writeInt(this.collectorEntityId);
        } else {
            this.writeVarInt(this.collectedEntityId);
            this.writeVarInt(this.collectorEntityId);
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11)) {
                this.writeVarInt(this.pickupItemCount);
            }
        }
    }

    public void copy(WrapperPlayServerCollectItem wrapperPlayServerCollectItem) {
        this.collectedEntityId = wrapperPlayServerCollectItem.collectedEntityId;
        this.collectorEntityId = wrapperPlayServerCollectItem.collectorEntityId;
        this.pickupItemCount = wrapperPlayServerCollectItem.pickupItemCount;
    }

    public int getCollectedEntityId() {
        return this.collectedEntityId;
    }

    public void setCollectedEntityId(int collectedEntityId) {
        this.collectedEntityId = collectedEntityId;
    }

    public int getCollectorEntityId() {
        return this.collectorEntityId;
    }

    public void setCollectorEntityId(int collectorEntityId) {
        this.collectorEntityId = collectorEntityId;
    }

    public int getPickupItemCount() {
        return this.pickupItemCount;
    }

    public void setPickupItemCount(int pickupItemCount) {
        this.pickupItemCount = pickupItemCount;
    }
}
