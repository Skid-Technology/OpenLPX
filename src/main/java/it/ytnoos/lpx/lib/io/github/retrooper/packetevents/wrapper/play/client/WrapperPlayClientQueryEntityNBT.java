package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientQueryEntityNBT
        extends PacketWrapper<WrapperPlayClientQueryEntityNBT> {

    private int transactionID;

    private int entityID;

    public WrapperPlayClientQueryEntityNBT(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientQueryEntityNBT(int transactionID, int entityID) {
        super(PacketType.Play.Client.QUERY_ENTITY_NBT);
        this.transactionID = transactionID;
        this.entityID = entityID;
    }

    @Override
    public void read() {
        this.transactionID = this.readVarInt();
        this.entityID = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.transactionID);
        this.writeVarInt(this.entityID);
    }

    public void copy(WrapperPlayClientQueryEntityNBT wrapperPlayClientQueryEntityNBT) {
        this.transactionID = wrapperPlayClientQueryEntityNBT.transactionID;
        this.entityID = wrapperPlayClientQueryEntityNBT.entityID;
    }

    public int getTransactionId() {
        return this.transactionID;
    }

    public void setTransactionId(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getEntityId() {
        return this.entityID;
    }

    public void setEntityId(int entityID) {
        this.entityID = entityID;
    }
}
