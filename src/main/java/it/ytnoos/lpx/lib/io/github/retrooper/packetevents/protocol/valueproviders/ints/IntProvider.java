package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.ints;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Random;

@NullMarked
public interface IntProvider {

    NbtMapCodec<IntProvider> MAP_CODEC =
            new NbtMapCodec<IntProvider>() {

                public IntProvider decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return (IntProvider)
                            nBTCompound
                                    .getOrThrow("type", IntProviderType.CODEC, packetWrapper)
                                    .getCodec()
                                    .decode(nBTCompound, packetWrapper);
                }

                public void encode(
                        NBTCompound nBTCompound,
                        PacketWrapper<?> packetWrapper,
                        IntProvider intProvider)
                        throws NbtCodecException {
                    ((NbtMapCodec<IntProvider>) intProvider.getType().getCodec())
                            .encode(nBTCompound, packetWrapper, intProvider);
                    nBTCompound.set(
                            "type", intProvider.getType(), IntProviderType.CODEC, packetWrapper);
                }
            };

    NbtCodec<IntProvider> CODEC =
            new NbtCodec<IntProvider>() {

                private final NbtCodec<IntProvider> delegate = IntProvider.MAP_CODEC.codec();

                public IntProvider decode(NBT nBT, PacketWrapper<?> packetWrapper)
                        throws NbtCodecException {
                    return nBT instanceof NBTNumber
                            ? new ConstantInt(((NBTNumber) nBT).getAsInt())
                            : this.delegate.decode(nBT, packetWrapper);
                }

                public NBT encode(PacketWrapper<?> packetWrapper, IntProvider intProvider)
                        throws NbtCodecException {
                    return intProvider instanceof ConstantInt
                            ? new NBTInt(((ConstantInt) intProvider).getValue())
                            : this.delegate.encode(packetWrapper, intProvider);
                }
            };

    IntProviderType<?> getType();

    int getSample(Random random);

    int getMin();

    int getMax();
}
