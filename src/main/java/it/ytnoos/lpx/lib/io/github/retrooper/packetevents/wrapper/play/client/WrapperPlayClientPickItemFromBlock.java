package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientPickItemFromBlock
        extends PacketWrapper<WrapperPlayClientPickItemFromBlock> {

    private Vector3i blockPos;

    private boolean includeData;

    public WrapperPlayClientPickItemFromBlock(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPickItemFromBlock(Vector3i vector3i, boolean includeData) {
        super(PacketType.Play.Client.PICK_ITEM_FROM_BLOCK);
        this.blockPos = vector3i;
        this.includeData = includeData;
    }

    @Override
    public void read() {
        this.blockPos = this.readBlockPosition();
        this.includeData = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.blockPos);
        this.writeBoolean(this.includeData);
    }

    public void copy(WrapperPlayClientPickItemFromBlock wrapperPlayClientPickItemFromBlock) {
        this.blockPos = wrapperPlayClientPickItemFromBlock.blockPos;
        this.includeData = wrapperPlayClientPickItemFromBlock.includeData;
    }

    public Vector3i getBlockPos() {
        return this.blockPos;
    }

    public void setBlockPos(Vector3i vector3i) {
        this.blockPos = vector3i;
    }

    public boolean isIncludeData() {
        return this.includeData;
    }

    public void setIncludeData(boolean includeData) {
        this.includeData = includeData;
    }
}
