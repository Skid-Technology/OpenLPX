package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientGenerateStructure
        extends PacketWrapper<WrapperPlayClientGenerateStructure> {

    private Vector3i blockPosition;

    private int levels;

    private boolean keepJigsaws;

    public WrapperPlayClientGenerateStructure(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientGenerateStructure(Vector3i vector3i, int levels, boolean keepJigsaws) {
        super(PacketType.Play.Client.GENERATE_STRUCTURE);
        this.blockPosition = vector3i;
        this.levels = levels;
        this.keepJigsaws = keepJigsaws;
    }

    @Override
    public void read() {
        this.blockPosition = new Vector3i(this.readLong(), this.serverVersion);
        this.levels = this.readVarInt();
        this.keepJigsaws = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeLong(this.blockPosition.getSerializedPosition(this.serverVersion));
        this.writeVarInt(this.levels);
        this.writeBoolean(this.keepJigsaws);
    }

    public void copy(WrapperPlayClientGenerateStructure wrapperPlayClientGenerateStructure) {
        this.blockPosition = wrapperPlayClientGenerateStructure.blockPosition;
        this.levels = wrapperPlayClientGenerateStructure.levels;
        this.keepJigsaws = wrapperPlayClientGenerateStructure.keepJigsaws;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }

    public int getLevels() {
        return this.levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public boolean isKeepingJigsaws() {
        return this.keepJigsaws;
    }

    public void setKeepJigsaws(boolean keepJigsaws) {
        this.keepJigsaws = keepJigsaws;
    }
}
