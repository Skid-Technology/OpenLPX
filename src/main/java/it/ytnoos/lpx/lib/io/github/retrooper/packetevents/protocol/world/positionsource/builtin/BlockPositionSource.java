package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSourceTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class BlockPositionSource extends PositionSource {

    private Vector3i pos;

    public BlockPositionSource(Vector3i vector3i) {
        super(PositionSourceTypes.BLOCK);
        this.pos = vector3i;
    }

    public static BlockPositionSource read(PacketWrapper<?> packetWrapper) {
        return new BlockPositionSource(packetWrapper.readBlockPosition());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, BlockPositionSource blockPositionSource) {
        packetWrapper.writeBlockPosition(blockPositionSource.pos);
    }

    public static BlockPositionSource decodeSource(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        NBTIntArray tagOfTypeOrThrow = nBTCompound.getTagOfTypeOrThrow("pos", NBTIntArray.class);
        return new BlockPositionSource(new Vector3i(tagOfTypeOrThrow.getValue()));
    }

    public static void encodeSource(
            BlockPositionSource blockPositionSource,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        nBTCompound.setTag(
                "pos",
                new NBTIntArray(
                        new int[] {
                            blockPositionSource.pos.x,
                            blockPositionSource.pos.y,
                            blockPositionSource.pos.z
                        }));
    }

    public Vector3i getPos() {
        return this.pos;
    }

    public void setPos(Vector3i vector3i) {
        this.pos = vector3i;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            BlockPositionSource blockPositionSource = (BlockPositionSource) result;
            return this.pos.equals(blockPositionSource.pos);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.pos);
    }
}
