package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimpattern;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface TrimPattern
        extends MappedEntity, CopyableEntity<TrimPattern>, DeepComparableEntity {

    ResourceLocation getAssetId();

    @Obsolete
    ItemType getTemplateItem();

    Component getDescription();

    boolean isDecal();

    static TrimPattern read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(
                TrimPatterns.getRegistry(), TrimPattern::readDirect);
    }

    static TrimPattern readDirect(PacketWrapper<?> packetWrapper) {
        ResourceLocation resourceLocation = packetWrapper.readIdentifier();
        ItemType itemType =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        ? null
                        : packetWrapper.readMappedEntity(ItemTypes::getById);
        Component component = packetWrapper.readComponent();
        boolean matches = packetWrapper.readBoolean();
        return new StaticTrimPattern(resourceLocation, itemType, component, matches);
    }

    static void write(PacketWrapper<?> packetWrapper, TrimPattern trimPattern) {
        packetWrapper.writeMappedEntityOrDirect(trimPattern, TrimPattern::writeDirect);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, TrimPattern trimPattern) {
        packetWrapper.writeIdentifier(trimPattern.getAssetId());
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeMappedEntity(trimPattern.getTemplateItem());
        }
        packetWrapper.writeComponent(trimPattern.getDescription());
        packetWrapper.writeBoolean(trimPattern.isDecal());
    }

    @Deprecated
    static TrimPattern decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion), typesBuilderData);
    }

    static TrimPattern decode(
            NBT nBT, PacketWrapper<?> packetWrapper, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound = (NBTCompound) nBT;
        ResourceLocation resourceLocation =
                new ResourceLocation(nBTCompound.getStringTagValueOrThrow("asset_id"));
        ItemType itemType =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        ? null
                        : ItemTypes.getByName(
                                nBTCompound.getStringTagValueOrThrow("template_item"));
        Component orThrow =
                nBTCompound.getOrThrow(
                        "description", packetWrapper.getSerializers(), packetWrapper);
        boolean matches =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_2)
                        && nBTCompound.getBoolean("decal");
        return new StaticTrimPattern(
                typesBuilderData, resourceLocation, itemType, orThrow, matches);
    }

    @Deprecated
    static NBT encode(TrimPattern trimPattern, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), trimPattern);
    }

    static NBT encode(PacketWrapper<?> packetWrapper, TrimPattern trimPattern) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("asset_id", new NBTString(trimPattern.getAssetId().toString()));
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            nBTCompound.setTag(
                    "template_item",
                    new NBTString(trimPattern.getTemplateItem().getName().toString()));
        }
        nBTCompound.set(
                "description",
                trimPattern.getDescription(),
                packetWrapper.getSerializers(),
                packetWrapper);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            nBTCompound.setTag("decal", new NBTByte(trimPattern.isDecal()));
        }
        return nBTCompound;
    }
}
