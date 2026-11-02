package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class Vector3i {

    public static final NbtCodec<Vector3i> CODEC =
            new NbtCodec<Vector3i>() {

                public Vector3i decode(NBT nBT, PacketWrapper<?> packetWrapper) {
                    int[] values;
                    if ((values = NbtCodecs.INT_ARRAY.decode(nBT, packetWrapper)).length != 3) {
                        throw new NbtCodecException(
                                "Expected 3 vector components, but got " + values.length);
                    } else {
                        return new Vector3i(values[0], values[1], values[2]);
                    }
                }

                public NBT encode(PacketWrapper<?> packetWrapper, Vector3i vector3i) {
                    return new NBTIntArray(
                            new int[] {vector3i.getX(), vector3i.getY(), vector3i.getZ()});
                }
            };

    private static final Vector3i ZERO = new Vector3i(0, 0, 0);

    public final int x;

    public final int y;

    public final int z;

    public Vector3i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Deprecated
    public Vector3i(long value) {
        this(value, PacketEvents.getAPI().getServerManager().getVersion());
    }

    public Vector3i(long value, ServerVersion serverVersion) {
        int value2 = (int) (value >> 38);
        int value3;
        int value4;
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            value3 = (int) (value << 52 >> 52);
            value4 = (int) (value << 26 >> 38);
        } else {
            value3 = (int) (value << 26 >> 52);
            value4 = (int) (value << 38 >> 38);
        }
        this.x = value2;
        this.y = value3;
        this.z = value4;
    }

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(int[] values) {
        if (values.length > 0) {
            this.x = values[0];
            if (values.length > 1) {
                this.y = values[1];
                if (values.length > 2) {
                    this.z = values[2];
                } else {
                    this.z = 0;
                }
            } else {
                this.y = 0;
                this.z = 0;
            }
        } else {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }

    public static Vector3i read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        int value2 = packetWrapper.readVarInt();
        int value3 = packetWrapper.readVarInt();
        return new Vector3i(value, value2, value3);
    }

    public static void write(PacketWrapper<?> packetWrapper, Vector3i vector3i) {
        packetWrapper.writeVarInt(vector3i.x);
        packetWrapper.writeVarInt(vector3i.y);
        packetWrapper.writeVarInt(vector3i.z);
    }

    public long getSerializedPosition(ServerVersion serverVersion) {
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            long value = this.getX() & 67108863;
            long value2 = this.getY() & 4095;
            long value3 = this.getZ() & 67108863;
            return value << 38 | value3 << 12 | value2;
        } else {
            return serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)
                    ? (long) (this.getX() & 67108863) << 38
                            | (long) (this.getZ() & 67108863) << 12
                            | this.getY() & 4095
                    : (long) (this.getX() & 67108863) << 38
                            | (long) (this.getY() & 4095) << 26
                            | this.getZ() & 67108863;
        }
    }

    @Deprecated
    public long getSerializedPosition() {
        return this.getSerializedPosition(PacketEvents.getAPI().getServerManager().getVersion());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public boolean equals(Object result) {
        if (result instanceof Vector3i) {
            Vector3i vector3i = (Vector3i) result;
            return this.x == vector3i.x && this.y == vector3i.y && this.z == vector3i.z;
        }
        if (result instanceof Vector3d) {
            Vector3d vector3d = (Vector3d) result;
            return this.x == vector3d.x && this.y == vector3d.y && this.z == vector3d.z;
        }
        if (!(result instanceof Vector3f)) {
            return false;
        }
        Vector3f vector3f = (Vector3f) result;
        return this.x == vector3f.x && this.y == vector3f.y && this.z == vector3f.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public Vector3d toVector3d() {
        return new Vector3d(this.x, this.y, this.z);
    }

    public Vector3i add(int value, int value2, int value3) {
        return new Vector3i(this.x + value, this.y + value2, this.z + value3);
    }

    public Vector3i add(Vector3i vector3i) {
        return this.add(vector3i.x, vector3i.y, vector3i.z);
    }

    public Vector3i offset(BlockFace blockFace) {
        return this.add(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
    }

    public Vector3i offset(BlockFace blockFace, int value) {
        return value == 0
                ? this
                : this.add(
                        blockFace.getModX() * value,
                        blockFace.getModY() * value,
                        blockFace.getModZ() * value);
    }

    public Vector3i subtract(int value, int value2, int value3) {
        return new Vector3i(this.x - value, this.y - value2, this.z - value3);
    }

    public Vector3i subtract(Vector3i vector3i) {
        return this.subtract(vector3i.x, vector3i.y, vector3i.z);
    }

    public Vector3i multiply(int value, int value2, int value3) {
        return new Vector3i(this.x * value, this.y * value2, this.z * value3);
    }

    public Vector3i multiply(Vector3i vector3i) {
        return this.multiply(vector3i.x, vector3i.y, vector3i.z);
    }

    public Vector3i multiply(int value) {
        return this.multiply(value, value, value);
    }

    public Vector3i crossProduct(Vector3i vector3i) {
        int value = this.y * vector3i.z - vector3i.y * this.z;
        int value2 = this.z * vector3i.x - vector3i.z * this.x;
        int value3 = this.x * vector3i.y - vector3i.x * this.y;
        return new Vector3i(value, value2, value3);
    }

    public int dot(Vector3i vector3i) {
        return this.x * vector3i.x + this.y * vector3i.y + this.z * vector3i.z;
    }

    public Vector3i with(Integer value, Integer value2, Integer value3) {
        return new Vector3i(
                value == null ? this.x : value,
                value2 == null ? this.y : value2,
                value3 == null ? this.z : value3);
    }

    public Vector3i withX(int value) {
        return new Vector3i(value, this.y, this.z);
    }

    public Vector3i withY(int value) {
        return new Vector3i(this.x, value, this.z);
    }

    public Vector3i withZ(int value) {
        return new Vector3i(this.x, this.y, value);
    }

    @Override
    public String toString() {
        return "X: " + this.x + ", Y: " + this.y + ", Z: " + this.z;
    }

    public static Vector3i zero() {
        return ZERO;
    }
}
