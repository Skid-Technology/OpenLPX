package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.painting;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface PaintingVariant
        extends MappedEntity, CopyableEntity<PaintingVariant>, DeepComparableEntity {

    NbtCodec<PaintingVariant> CODEC =
            (new NbtMapCodec<PaintingVariant>() {

                        public PaintingVariant decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            int asInt = nBTCompound.getNumberTagOrThrow("width").getAsInt();
                            int asInt2 = nBTCompound.getNumberTagOrThrow("height").getAsInt();
                            ResourceLocation resourceLocation =
                                    new ResourceLocation(
                                            nBTCompound.getStringTagValueOrThrow("asset_id"));
                            Component orNull =
                                    nBTCompound.getOrNull(
                                            "title", packetWrapper.getSerializers(), packetWrapper);
                            Component orNull2 =
                                    nBTCompound.getOrNull(
                                            "author",
                                            packetWrapper.getSerializers(),
                                            packetWrapper);
                            return new StaticPaintingVariant(
                                    asInt, asInt2, resourceLocation, orNull, orNull2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                PaintingVariant paintingVariant)
                                throws NbtCodecException {
                            nBTCompound.setTag("width", new NBTInt(paintingVariant.getWidth()));
                            nBTCompound.setTag("height", new NBTInt(paintingVariant.getHeight()));
                            nBTCompound.setTag(
                                    "asset_id",
                                    new NBTString(paintingVariant.getAssetId().toString()));
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                                Component component;
                                if ((component = paintingVariant.getTitle()) != null) {
                                    nBTCompound.set(
                                            "title",
                                            component,
                                            packetWrapper.getSerializers(),
                                            packetWrapper);
                                }
                                Component component2;
                                if ((component2 = paintingVariant.getAuthor()) != null) {
                                    nBTCompound.set(
                                            "author",
                                            component2,
                                            packetWrapper.getSerializers(),
                                            packetWrapper);
                                }
                            }
                        }
                    })
                    .codec();

    @Contract(pure = true)
    int getWidth();

    @Contract(pure = true)
    int getHeight();

    @Contract(pure = true)
    ResourceLocation getAssetId();

    @Contract(pure = true)
    @Nullable Component getTitle();

    @Contract(pure = true)
    @Nullable Component getAuthor();

    static PaintingVariant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21)
                ? packetWrapper.readMappedEntityOrDirect(
                        PaintingVariants.getRegistry(), PaintingVariant::readDirect)
                : packetWrapper.readMappedEntity(PaintingVariants.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, PaintingVariant paintingVariant) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21)) {
            packetWrapper.writeMappedEntityOrDirect(paintingVariant, PaintingVariant::writeDirect);
        } else {
            packetWrapper.writeMappedEntity(paintingVariant);
        }
    }

    static PaintingVariant readDirect(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        int value2 = packetWrapper.readVarInt();
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        Component component = null;
        Component component2 = null;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            component = packetWrapper.readOptional(PacketWrapper::readComponent);
            component2 = packetWrapper.readOptional(PacketWrapper::readComponent);
        }
        return new StaticPaintingVariant(value, value2, resourceLocation, component, component2);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, PaintingVariant paintingVariant) {
        packetWrapper.writeVarInt(paintingVariant.getWidth());
        packetWrapper.writeVarInt(paintingVariant.getHeight());
        packetWrapper.writeIdentifier(paintingVariant.getAssetId());
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            packetWrapper.writeOptional(paintingVariant.getTitle(), PacketWrapper::writeComponent);
            packetWrapper.writeOptional(paintingVariant.getAuthor(), PacketWrapper::writeComponent);
        }
    }

    @Deprecated
    static PaintingVariant decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(PaintingVariant paintingVariant, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), paintingVariant);
    }
}
