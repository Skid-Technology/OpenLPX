package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biome;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biomes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface WolfVariant
        extends MappedEntity, CopyableEntity<WolfVariant>, DeepComparableEntity {

    NbtCodec<WolfVariant> CODEC =
            (new NbtMapCodec<WolfVariant>() {

                        public WolfVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            if (packetWrapper
                                    .getServerVersion()
                                    .isOlderThan(ServerVersion.V_1_21_5)) {
                                WolfAssetSet wolfAssetSet =
                                        WolfAssetSet.MAP_CODEC.decode(nBTCompound, packetWrapper);
                                MappedEntitySet orThrow =
                                        nBTCompound.getOrThrow(
                                                "biomes",
                                                (result, result2) ->
                                                        MappedEntitySet.decode(
                                                                result,
                                                                result2,
                                                                Biomes.getRegistry()),
                                                packetWrapper);
                                return new StaticWolfVariant(wolfAssetSet, orThrow);
                            } else {
                                WolfAssetSet orThrow2 =
                                        nBTCompound.getOrThrow(
                                                "assets", WolfAssetSet.CODEC, packetWrapper);
                                WolfAssetSet wolfAssetSet2 =
                                        packetWrapper
                                                        .getServerVersion()
                                                        .isOlderThan(ServerVersion.V_26_1)
                                                ? orThrow2
                                                : nBTCompound.getOrThrow(
                                                        "baby_assets",
                                                        WolfAssetSet.CODEC,
                                                        packetWrapper);
                                return new StaticWolfVariant(orThrow2, wolfAssetSet2);
                            }
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                WolfVariant wolfVariant)
                                throws NbtCodecException {
                            if (packetWrapper
                                    .getServerVersion()
                                    .isOlderThan(ServerVersion.V_1_21_5)) {
                                WolfAssetSet.MAP_CODEC.encode(
                                        nBTCompound, packetWrapper, wolfVariant.getAssets());
                                nBTCompound.set(
                                        "biomes",
                                        wolfVariant.getBiomes(),
                                        MappedEntitySet::encode,
                                        packetWrapper);
                            } else {
                                nBTCompound.set(
                                        "assets",
                                        wolfVariant.getAssets(),
                                        WolfAssetSet.CODEC,
                                        packetWrapper);
                                if (packetWrapper
                                        .getServerVersion()
                                        .isNewerThanOrEquals(ServerVersion.V_26_1)) {
                                    nBTCompound.set(
                                            "baby_assets",
                                            wolfVariant.getBabyAssets(),
                                            WolfAssetSet.CODEC,
                                            packetWrapper);
                                }
                            }
                        }
                    })
                    .codec();

    WolfAssetSet getAssets();

    WolfAssetSet getBabyAssets();

    @Obsolete
    MappedEntitySet<Biome> getBiomes();

    @Deprecated
    default ResourceLocation getWildTexture() {
        return this.getAssets().getWildId();
    }

    @Deprecated
    default ResourceLocation getTameTexture() {
        return this.getAssets().getTameId();
    }

    @Deprecated
    default ResourceLocation getAngryTexture() {
        return this.getAssets().getAngryId();
    }

    static WolfVariant read(PacketWrapper<?> packetWrapper) {
        return !packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        && !packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21)
                ? packetWrapper.readMappedEntityOrDirect(
                        WolfVariants.getRegistry(), WolfVariant::readDirect)
                : packetWrapper.readMappedEntity(WolfVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, WolfVariant wolfVariant) {
        if (!packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                && !packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21)) {
            packetWrapper.writeMappedEntityOrDirect(wolfVariant, WolfVariant::writeDirect);
        } else {
            packetWrapper.writeMappedEntity(wolfVariant);
        }
    }

    @Deprecated
    static WolfVariant readDirect(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        ResourceLocation resourceLocation2 = packetWrapper.readIdentifier();
        ResourceLocation resourceLocation3 = packetWrapper.readIdentifier();
        MappedEntitySet<Biome> mappedEntitySet =
                MappedEntitySet.read(packetWrapper, Biomes.getRegistry());
        return new StaticWolfVariant(
                resourceLocation, resourceLocation2, resourceLocation3, mappedEntitySet);
    }

    @Deprecated
    static void writeDirect(PacketWrapper<?> packetWrapper, WolfVariant wolfVariant) {
        packetWrapper.writeIdentifier(wolfVariant.getWildTexture());
        packetWrapper.writeIdentifier(wolfVariant.getTameTexture());
        packetWrapper.writeIdentifier(wolfVariant.getAngryTexture());
        MappedEntitySet.write(packetWrapper, wolfVariant.getBiomes());
    }

    @Deprecated
    static WolfVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion), typesBuilderData);
    }

    @Deprecated
    static WolfVariant decode(
            NBT nBT, PacketWrapper<?> packetWrapper, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, packetWrapper).copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(WolfVariant wolfVariant, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), wolfVariant);
    }

    @Deprecated
    static NBT encode(PacketWrapper<?> packetWrapper, WolfVariant wolfVariant) {
        return CODEC.encode(packetWrapper, wolfVariant);
    }
}
