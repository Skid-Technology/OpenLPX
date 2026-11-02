package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientQueryBlockNBT extends PacketWrapper<WrapperPlayClientQueryBlockNBT> {

    private int transactionID;

    private Vector3i blockPosition;

    public WrapperPlayClientQueryBlockNBT(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientQueryBlockNBT(int transactionID, Vector3i vector3i) {
        super(PacketType.Play.Client.QUERY_BLOCK_NBT);
        this.transactionID = transactionID;
        this.blockPosition = vector3i;
    }

    @Override
    public void read() {
        this.transactionID = this.readVarInt();
        this.blockPosition = this.readBlockPosition();
    }

    @Override
    public void write() {
        this.writeVarInt(this.transactionID);
        this.writeBlockPosition(this.blockPosition);
    }

    public void copy(WrapperPlayClientQueryBlockNBT wrapperPlayClientQueryBlockNBT) {
        this.transactionID = wrapperPlayClientQueryBlockNBT.transactionID;
        this.blockPosition = wrapperPlayClientQueryBlockNBT.blockPosition;
    }

    public int getTransactionId() {
        return this.transactionID;
    }

    public void setTransactionId(int transactionID) {
        this.transactionID = transactionID;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }
}
