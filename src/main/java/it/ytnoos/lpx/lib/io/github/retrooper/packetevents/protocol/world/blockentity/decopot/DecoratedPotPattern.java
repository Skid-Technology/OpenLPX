package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.blockentity.decopot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface DecoratedPotPattern
        extends MappedEntity, CopyableEntity<DecoratedPotPattern>, DeepComparableEntity {

    NbtCodec<DecoratedPotPattern> CODEC =
            (new NbtMapCodec<DecoratedPotPattern>() {

                        public DecoratedPotPattern decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            ResourceLocation orThrow =
                                    nBTCompound.getOrThrow(
                                            "asset_id", ResourceLocation.CODEC, packetWrapper);
                            return new StaticDecoratedPotPattern(null, orThrow);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                DecoratedPotPattern decoratedPotPattern)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "asset_id",
                                    decoratedPotPattern.getAssetId(),
                                    ResourceLocation.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    ResourceLocation getAssetId();

    static DecoratedPotPattern read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(DecoratedPotPatterns.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, DecoratedPotPattern decoratedPotPattern) {
        packetWrapper.writeMappedEntity(decoratedPotPattern);
    }
}
