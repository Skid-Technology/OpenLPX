package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUpdateEntityNBT
        extends PacketWrapper<WrapperPlayServerUpdateEntityNBT> {

    private int entityId;

    private NBTCompound nbtCompound;

    public WrapperPlayServerUpdateEntityNBT(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateEntityNBT(int entityId, NBTCompound nBTCompound) {
        super(PacketType.Play.Server.UPDATE_ENTITY_NBT);
        this.entityId = entityId;
        this.nbtCompound = nBTCompound;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.nbtCompound = this.readNBT();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeNBT(this.nbtCompound);
    }

    public void copy(WrapperPlayServerUpdateEntityNBT wrapperPlayServerUpdateEntityNBT) {
        this.entityId = wrapperPlayServerUpdateEntityNBT.entityId;
        this.nbtCompound = wrapperPlayServerUpdateEntityNBT.nbtCompound;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public NBTCompound getNBTCompound() {
        return this.nbtCompound;
    }

    public void setNBTCompound(NBTCompound nBTCompound) {
        this.nbtCompound = nBTCompound;
    }
}
