package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerNBTQueryResponse
        extends PacketWrapper<WrapperPlayServerNBTQueryResponse> {

    private int transactionId;

    private NBTCompound tag;

    public WrapperPlayServerNBTQueryResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerNBTQueryResponse(int transactionId, NBTCompound nBTCompound) {
        super(PacketType.Play.Server.NBT_QUERY_RESPONSE);
        this.transactionId = transactionId;
        this.tag = nBTCompound;
    }

    @Override
    public void read() {
        this.transactionId = this.readVarInt();
        this.tag = this.readNBT();
    }

    @Override
    public void write() {
        this.writeVarInt(this.transactionId);
        this.writeNBT(this.tag);
    }

    public void copy(WrapperPlayServerNBTQueryResponse wrapperPlayServerNBTQueryResponse) {
        this.transactionId = wrapperPlayServerNBTQueryResponse.transactionId;
        this.tag = wrapperPlayServerNBTQueryResponse.tag;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public NBTCompound getTag() {
        return this.tag;
    }

    public void setTag(NBTCompound nBTCompound) {
        this.tag = nBTCompound;
    }
}
