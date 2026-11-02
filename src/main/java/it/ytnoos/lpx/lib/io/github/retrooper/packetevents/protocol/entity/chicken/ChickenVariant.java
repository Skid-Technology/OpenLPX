package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

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
public interface ChickenVariant
        extends MappedEntity, CopyableEntity<ChickenVariant>, DeepComparableEntity {

    NbtCodec<ChickenVariant> CODEC =
            (new NbtMapCodec<ChickenVariant>() {

                        public ChickenVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            ChickenVariant.ModelType or =
                                    nBTCompound.getOr(
                                            "model",
                                            ChickenVariant.ModelType.CODEC,
                                            ChickenVariant.ModelType.NORMAL,
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
                            return new StaticChickenVariant(or, orThrow, resourceLocation);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                ChickenVariant chickenVariant)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "model",
                                    chickenVariant.getModelType(),
                                    ChickenVariant.ModelType.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "asset_id",
                                    chickenVariant.getAssetId(),
                                    ResourceLocation.CODEC,
                                    packetWrapper);
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThan(ServerVersion.V_26_1)) {
                                nBTCompound.set(
                                        "baby_asset_id",
                                        chickenVariant.getBabyAssetId(),
                                        ResourceLocation.CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    ChickenVariant.ModelType getModelType();

    ResourceLocation getAssetId();

    ResourceLocation getBabyAssetId();

    static ChickenVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(ChickenVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, ChickenVariant chickenVariant) {
        packetWrapper.writeMappedEntity(chickenVariant);
    }

    @Deprecated
    static ChickenVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(ChickenVariant chickenVariant, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), chickenVariant);
    }

    enum ModelType implements CodecNameable {
        NORMAL("normal"),
        COLD("cold");

        private static final Index<String, ChickenVariant.ModelType> NAME_INDEX =
                Index.create(ChickenVariant.ModelType.class, ChickenVariant.ModelType::getName);

        public static final NbtCodec<ChickenVariant.ModelType> CODEC = NbtCodecs.forEnum(values());

        private final String name;

        ModelType(String name) {
            this.name = name;
        }

        public static ChickenVariant.ModelType getByName(String text) {
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
