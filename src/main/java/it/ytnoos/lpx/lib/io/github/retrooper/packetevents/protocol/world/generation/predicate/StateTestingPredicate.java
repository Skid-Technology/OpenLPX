package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.predicate;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.BlockPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class StateTestingPredicate implements BlockPredicate {

    protected static final NbtMapCodec<Vector3i> OFFSET_CODEC =
            new NbtMapCodec<Vector3i>() {

                public Vector3i decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return nBTCompound.getOr(
                            "offset", Vector3i.CODEC, Vector3i.zero(), packetWrapper);
                }

                public void encode(
                        NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, Vector3i vector3i)
                        throws NbtCodecException {
                    if (vector3i.getX() != 0 || vector3i.getY() != 0 || vector3i.getZ() != 0) {
                        nBTCompound.set("offset", vector3i, Vector3i.CODEC, packetWrapper);
                    }
                }
            };

    protected final Vector3i offset;

    protected StateTestingPredicate(Vector3i vector3i) {
        this.offset = vector3i;
    }

    public Vector3i getOffset() {
        return this.offset;
    }
}
