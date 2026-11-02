package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class BlockBoundingBox {

    private final int minX;

    private final int minY;

    private final int minZ;

    private final int maxX;

    private final int maxY;

    private final int maxZ;

    public BlockBoundingBox(int value, int value2, int value3, int value4, int value5, int value6) {
        this.minX = Math.min(value, value4);
        this.minY = Math.min(value2, value5);
        this.minZ = Math.min(value3, value6);
        this.maxX = Math.max(value4, value);
        this.maxY = Math.max(value5, value2);
        this.maxZ = Math.max(value6, value3);
    }

    public static BlockBoundingBox read(PacketWrapper<?> packetWrapper) {
        Vector3i vector3i = packetWrapper.readBlockPosition();
        Vector3i vector3i2 = packetWrapper.readBlockPosition();
        return new BlockBoundingBox(
                vector3i.x, vector3i.y, vector3i.z, vector3i2.x, vector3i2.y, vector3i2.z);
    }

    public static void write(PacketWrapper<?> packetWrapper, BlockBoundingBox blockBoundingBox) {
        packetWrapper.writeBlockPosition(
                new Vector3i(blockBoundingBox.minX, blockBoundingBox.minY, blockBoundingBox.minZ));
        packetWrapper.writeBlockPosition(
                new Vector3i(blockBoundingBox.maxX, blockBoundingBox.maxY, blockBoundingBox.maxZ));
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMinZ() {
        return this.minZ;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getMaxZ() {
        return this.maxZ;
    }
}
