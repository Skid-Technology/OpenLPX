package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface CowVariant extends MappedEntity, CopyableEntity<CowVariant>, DeepComparableEntity {

    NbtCodec<CowVariant> CODEC =
            (new NbtMapCodec<CowVariant>() {

                        public CowVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            CowVariant.ModelType or =
                                    nBTCompound.getOr(
                                            "model",
                                            CowVariant.ModelType.CODEC,
                                            CowVariant.ModelType.NORMAL,
                                            packetWrapper);
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
                            return new StaticCowVariant(or, orThrow, resourceLocation);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                CowVariant cowVariant)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "model",
                                    cowVariant.getModelType(),
                                    CowVariant.ModelType.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "asset_id",
                                    cowVariant.getAssetId(),
                                    ResourceLocation.CODEC,
                                    packetWrapper);
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThan(ServerVersion.V_26_1)) {
                                nBTCompound.set(
                                        "baby_asset_id",
                                        cowVariant.getBabyAssetId(),
                                        ResourceLocation.CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    CowVariant.ModelType getModelType();

    ResourceLocation getAssetId();

    ResourceLocation getBabyAssetId();

    static CowVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(CowVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, CowVariant cowVariant) {
        packetWrapper.writeMappedEntity(cowVariant);
    }

    @Deprecated
    static CowVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(CowVariant cowVariant, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), cowVariant);
    }

    enum ModelType implements CodecNameable {
        NORMAL("normal"),
        COLD("cold"),
        WARM("warm");

        private static final Index<String, CowVariant.ModelType> NAME_INDEX =
                Index.create(CowVariant.ModelType.class, CowVariant.ModelType::getName);

        public static final NbtCodec<CowVariant.ModelType> CODEC = NbtCodecs.forEnum(values());

        private final String name;

        ModelType(String name) {
            this.name = name;
        }

        public static CowVariant.ModelType getByName(String text) {
            return AdventureIndexUtil.indexValueOrThrow(NAME_INDEX, text);
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String getCodecName() {
            return this.name;
        }
    }
}
