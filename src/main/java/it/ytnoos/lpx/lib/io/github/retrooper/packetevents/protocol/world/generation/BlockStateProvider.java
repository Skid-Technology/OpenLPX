package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.SimpleStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.BlockStateCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface BlockStateProvider
        extends MappedEntity, CopyableEntity<BlockStateProvider>, DeepComparableEntity {

    NbtCodec<BlockStateProvider> TYPED_CODEC =
            (new NbtMapCodec<BlockStateProvider>() {

                        public BlockStateProvider decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            return (BlockStateProvider)
                                    nBTCompound
                                            .getOrThrow(
                                                    "type",
                                                    BlockStateProviderType.CODEC,
                                                    packetWrapper)
                                            .getCodec()
                                            .decode(nBTCompound, packetWrapper);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                BlockStateProvider blockStateProvider)
                                throws NbtCodecException {
                            ((NbtMapCodec<BlockStateProvider>)
                                            blockStateProvider.getType().getCodec())
                                    .encode(nBTCompound, packetWrapper, blockStateProvider);
                            nBTCompound.set(
                                    "type",
                                    blockStateProvider.getType(),
                                    BlockStateProviderType.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    NbtCodec<BlockStateProvider> DIRECT_CODEC =
            new NbtCodec<BlockStateProvider>() {

                public BlockStateProvider decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    try {
                        return BlockStateProvider.TYPED_CODEC.decode(nBT, packetWrapper);
                    } catch (NbtCodecException nbtCodecException) {
                        WrappedBlockState wrappedBlockState =
                                BlockStateCodec.FULL_CODEC.decode(nBT, packetWrapper);
                        return new SimpleStateProvider(wrappedBlockState);
                    }
                }

                public NBT encode(
                        PacketWrapper<?> packetWrapper, BlockStateProvider blockStateProvider)
                        throws NbtCodecException {
                    if (blockStateProvider instanceof SimpleStateProvider) {
                        WrappedBlockState state =
                                ((SimpleStateProvider) blockStateProvider).getState();
                        return BlockStateCodec.FULL_CODEC.encode(packetWrapper, state);
                    } else {
                        return BlockStateProvider.TYPED_CODEC.encode(
                                packetWrapper, blockStateProvider);
                    }
                }
            };

    NbtCodec<BlockStateProvider> CODEC =
            new NbtCodec<BlockStateProvider>() {

                @MonotonicNonNull private NbtCodec<BlockStateProvider> registryCodec;

                private NbtCodec<BlockStateProvider> registryCodec() {
                    NbtCodec<BlockStateProvider> nbtCodec = this.registryCodec;
                    if (this.registryCodec == null) {
                        nbtCodec = NbtCodecs.forRegistry(BlockStateProviders.getRegistry());
                        this.registryCodec = nbtCodec;
                    }
                    return nbtCodec;
                }

                public BlockStateProvider decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return nBT instanceof NBTString
                            ? this.registryCodec().decode(nBT, packetWrapper)
                            : BlockStateProvider.DIRECT_CODEC.decode(nBT, packetWrapper);
                }

                public NBT encode(
                        PacketWrapper<?> packetWrapper, BlockStateProvider blockStateProvider)
                        throws NbtCodecException {
                    return blockStateProvider.isRegistered()
                            ? this.registryCodec().encode(packetWrapper, blockStateProvider)
                            : BlockStateProvider.DIRECT_CODEC.encode(
                                    packetWrapper, blockStateProvider);
                }
            };

    BlockStateProviderType<?> getType();
}
