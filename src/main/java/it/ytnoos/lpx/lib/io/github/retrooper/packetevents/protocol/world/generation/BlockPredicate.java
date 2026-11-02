package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface BlockPredicate {

    NbtCodec<BlockPredicate> CODEC =
            new NbtCodec<BlockPredicate>() {

                public BlockPredicate decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    NBTCompound nBTCompound;
                    return (BlockPredicate)
                            (nBTCompound = nBT.castOrThrow(NBTCompound.class))
                                    .getOrThrow("type", BlockPredicateType.CODEC, packetWrapper)
                                    .getCodec()
                                    .decode(nBTCompound, packetWrapper);
                }

                public NBT encode(PacketWrapper<?> packetWrapper, BlockPredicate blockPredicate)
                        throws NbtCodecException {
                    BlockPredicateType type = blockPredicate.getType();
                    NBTCompound nBTCompound = new NBTCompound();
                    type.getCodec().encode(nBTCompound, packetWrapper, blockPredicate);
                    nBTCompound.set(
                            "type",
                            blockPredicate.getType(),
                            BlockPredicateType.CODEC,
                            packetWrapper);
                    return nBTCompound;
                }
            };

    BlockPredicateType<?> getType();
}
