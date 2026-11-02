package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface CatVariant extends MappedEntity, CopyableEntity<CatVariant>, DeepComparableEntity {

    NbtCodec<CatVariant> CODEC =
            (new NbtMapCodec<CatVariant>() {

                        public CatVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            ResourceLocation orThrow =
                                    nBTCompound.getOrThrow(
                                            "asset_id", ResourceLocation.CODEC, packetWrapper);
                            ResourceLocation resourceLocation =
                                    packetWrapper
                                                    .getServerVersion()
                                                    .isOlderThan(ServerVersion.V_26_1)
                                            ? orThrow
                                            : nBTCompound.getOrThrow(
                                                    "baby_asset_id",
                                                    ResourceLocation.CODEC,
                                                    packetWrapper);
                            return new StaticCatVariant(orThrow, resourceLocation);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                CatVariant catVariant)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "asset_id",
                                    catVariant.getAssetId(),
                                    ResourceLocation.CODEC,
                                    packetWrapper);
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThan(ServerVersion.V_26_1)) {
                                nBTCompound.set(
                                        "baby_asset_id",
                                        catVariant.getBabyAssetId(),
                                        ResourceLocation.CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    ResourceLocation getAssetId();

    ResourceLocation getBabyAssetId();

    static CatVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(CatVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, CatVariant catVariant) {
        packetWrapper.writeMappedEntity(catVariant);
    }

    @Deprecated
    static CatVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(CatVariant catVariant, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), catVariant);
    }
}
