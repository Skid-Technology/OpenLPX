package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;

public class TileEntity {

    byte packedByte;

    short y;

    int type;

    NBTCompound data;

    public TileEntity(NBTCompound nBTCompound) {
        this.data = nBTCompound;
    }

    public TileEntity(byte packedByte, short y, int type, NBTCompound nBTCompound) {
        this.packedByte = packedByte;
        this.y = y;
        this.type = type;
        this.data = nBTCompound;
    }

    public int getX() {
        return PacketEvents.getAPI()
                        .getServerManager()
                        .getVersion()
                        .isNewerThanOrEquals(ServerVersion.V_1_18)
                ? (this.packedByte & 240) >> 4
                : this.data.getTagOfTypeOrNull("x", NBTInt.class).getAsInt();
    }

    public int getZ() {
        return PacketEvents.getAPI()
                        .getServerManager()
                        .getVersion()
                        .isNewerThanOrEquals(ServerVersion.V_1_18)
                ? this.packedByte & 15
                : this.data.getTagOfTypeOrNull("z", NBTInt.class).getAsInt();
    }

    public int getY() {
        return PacketEvents.getAPI()
                        .getServerManager()
                        .getVersion()
                        .isNewerThanOrEquals(ServerVersion.V_1_18)
                ? this.y
                : this.data.getTagOfTypeOrNull("y", NBTInt.class).getAsInt();
    }

    public void setX(int value) {
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.packedByte = (byte) (this.packedByte & 15 | (value & 15) << 4);
        } else {
            this.data.setTag("x", new NBTInt(value));
        }
    }

    public void setY(int value) {
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.y = (short) value;
        } else {
            this.data.setTag("y", new NBTInt(value));
        }
    }

    public void setZ(int value) {
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isNewerThanOrEquals(ServerVersion.V_1_18)) {
            this.packedByte = (byte) (this.packedByte & 240 | value & 15);
        } else {
            this.data.setTag("z", new NBTInt(value));
        }
    }

    public int getType() {
        return this.type;
    }

    public byte getPackedByte() {
        return this.packedByte;
    }

    public short getYShort() {
        return this.y;
    }

    public NBTCompound getNBT() {
        return this.data;
    }
}
