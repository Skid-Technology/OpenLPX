package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.TileEntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.BlockEntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.BlockEntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerBlockEntityData
        extends PacketWrapper<WrapperPlayServerBlockEntityData> {

    private Vector3i position;

    private BlockEntityType type;

    private NBTCompound nbt;

    public WrapperPlayServerBlockEntityData(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerBlockEntityData(
            Vector3i vector3i, TileEntityType tileEntityType, NBTCompound nBTCompound) {
        this(vector3i, tileEntityType.getId(), nBTCompound);
    }

    @Deprecated
    public WrapperPlayServerBlockEntityData(Vector3i vector3i, int value, NBTCompound nBTCompound) {
        this(
                vector3i,
                BlockEntityTypes.getById(
                        PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                        value),
                nBTCompound);
    }

    public WrapperPlayServerBlockEntityData(
            Vector3i vector3i, BlockEntityType blockEntityType, NBTCompound nBTCompound) {
        super(PacketType.Play.Server.BLOCK_ENTITY_DATA);
        this.position = vector3i;
        this.type = blockEntityType;
        this.nbt = nBTCompound;
    }

    @Override
    public void read() {
        this.position = this.readBlockPosition();
        int value =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)
                        ? this.readVarInt()
                        : this.readUnsignedByte();
        this.type = BlockEntityTypes.getById(this.serverVersion.toClientVersion(), value);
        this.nbt = this.readNBT();
    }

    @Override
    public void write() {
        this.writeBlockPosition(this.position);
        int id = this.type.getId(this.serverVersion.toClientVersion());
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.writeVarInt(id);
        } else {
            this.writeByte(id);
        }
        this.writeNBT(this.nbt);
    }

    public void copy(WrapperPlayServerBlockEntityData wrapperPlayServerBlockEntityData) {
        this.position = wrapperPlayServerBlockEntityData.position;
        this.type = wrapperPlayServerBlockEntityData.type;
        this.nbt = wrapperPlayServerBlockEntityData.nbt;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    @Deprecated
    public int getType() {
        return this.type.getId(this.serverVersion.toClientVersion());
    }

    public BlockEntityType getBlockEntityType() {
        return this.type;
    }

    @Deprecated
    public TileEntityType getAsTileType() {
        return TileEntityType.getById(this.getType());
    }

    @Deprecated
    public void setType(int value) {
        this.setType(BlockEntityTypes.getById(this.serverVersion.toClientVersion(), value));
    }

    public void setType(BlockEntityType blockEntityType) {
        this.type = blockEntityType;
    }

    @Deprecated
    public void setType(TileEntityType tileEntityType) {
        this.setType(tileEntityType.getId());
    }

    public NBTCompound getNBT() {
        return this.nbt;
    }

    public void setNBT(NBTCompound nBTCompound) {
        this.nbt = nBTCompound;
    }
}
