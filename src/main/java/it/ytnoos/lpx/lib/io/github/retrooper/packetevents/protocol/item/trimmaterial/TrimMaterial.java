package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimmaterial;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial.ArmorMaterial;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial.ArmorMaterials;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@NullMarked
public interface TrimMaterial
        extends MappedEntity, CopyableEntity<TrimMaterial>, DeepComparableEntity {

    NbtCodec<TrimMaterial> DIRECT_CODEC =
            (new NbtMapCodec<TrimMaterial>() {

                        public TrimMaterial decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            String text;
                            ResourceLocation resourceLocation;
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_26_3)) {
                                if ((text =
                                                (resourceLocation =
                                                                nBTCompound.getOrThrow(
                                                                        "palette_id",
                                                                        ResourceLocation.CODEC,
                                                                        packetWrapper))
                                                        .getKey())
                                        .startsWith("trim/")) {
                                    text = text.substring("trim/".length());
                                }
                            } else {
                                text = nBTCompound.getStringTagValueOrThrow("asset_name");
                                resourceLocation = ResourceLocation.minecraft("trim/" + text);
                            }
                            ItemType itemType =
                                    packetWrapper
                                                    .getServerVersion()
                                                    .isNewerThanOrEquals(ServerVersion.V_1_21_5)
                                            ? null
                                            : ItemTypes.getByName(
                                                    nBTCompound.getStringTagValueOrThrow(
                                                            "ingredient"));
                            float value =
                                    packetWrapper
                                                    .getServerVersion()
                                                    .isNewerThanOrEquals(ServerVersion.V_1_21_4)
                                            ? 0.0F
                                            : nBTCompound
                                                    .getNumberTagOrThrow("item_model_index")
                                                    .getAsFloat();
                            Map entries = Collections.emptyMap();
                            NBTCompound nBTCompound2;
                            if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)
                                    && (nBTCompound2 =
                                                    nBTCompound.getCompoundTagOrNull(
                                                            "override_armor_materials"))
                                            != null) {
                                entries = new HashMap();
                                Iterator<Entry<String, NBT>> iterator2 =
                                        nBTCompound2.getTags().entrySet().iterator();
                                while (iterator2.hasNext()) {
                                    Entry entry;
                                    ArmorMaterial byName =
                                            ArmorMaterials.getByName(
                                                    (String)
                                                            (entry = (Entry) iterator2.next())
                                                                    .getKey());
                                    String value2 = ((NBTString) entry.getValue()).getValue();
                                    entries.put(byName, value2);
                                }
                            }
                            Component orThrow =
                                    nBTCompound.getOrThrow(
                                            "description",
                                            packetWrapper.getSerializers(),
                                            packetWrapper);
                            return new StaticTrimMaterial(
                                    null,
                                    text,
                                    resourceLocation,
                                    itemType,
                                    value,
                                    entries,
                                    orThrow);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                TrimMaterial trimMaterial)
                                throws NbtCodecException {
                            NBTCompound nBTCompound2;
                            if (!trimMaterial.getOverrideArmorMaterials().isEmpty()) {
                                nBTCompound2 = new NBTCompound();
                                Iterator<Entry<ArmorMaterial, String>> iterator2 =
                                        trimMaterial
                                                .getOverrideArmorMaterials()
                                                .entrySet()
                                                .iterator();
                                while (iterator2.hasNext()) {
                                    Entry entry;
                                    String text =
                                            ((ArmorMaterial)
                                                            (entry = (Entry) iterator2.next())
                                                                    .getKey())
                                                    .getName()
                                                    .toString();
                                    NBTString nBTString = new NBTString((String) entry.getValue());
                                    nBTCompound2.setTag(text, nBTString);
                                }
                            } else {
                                nBTCompound2 = null;
                            }
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_26_3)) {
                                nBTCompound.set(
                                        "palette_id",
                                        trimMaterial.getPaletteId(),
                                        ResourceLocation.CODEC,
                                        packetWrapper);
                            } else {
                                nBTCompound.setTag(
                                        "asset_name", new NBTString(trimMaterial.getAssetName()));
                            }
                            if (packetWrapper
                                    .getServerVersion()
                                    .isOlderThan(ServerVersion.V_1_21_5)) {
                                nBTCompound.setTag(
                                        "ingredient",
                                        new NBTString(
                                                trimMaterial.getIngredient().getName().toString()));
                            }
                            if (packetWrapper
                                    .getServerVersion()
                                    .isOlderThan(ServerVersion.V_1_21_4)) {
                                nBTCompound.setTag(
                                        "item_model_index",
                                        new NBTFloat(trimMaterial.getItemModelIndex()));
                            }
                            if (nBTCompound2 != null
                                    && packetWrapper
                                            .getServerVersion()
                                            .isOlderThan(ServerVersion.V_26_3)) {
                                nBTCompound.setTag("override_armor_materials", nBTCompound2);
                            }
                            nBTCompound.set(
                                    "description",
                                    trimMaterial.getDescription(),
                                    packetWrapper.getSerializers(),
                                    packetWrapper);
                        }
                    })
                    .codec();

    float FALLBACK_ITEM_MODEL_INDEX = 0.0F;

    @Obsolete
    String getAssetName();

    ResourceLocation getPaletteId();

    @Obsolete
    ItemType getIngredient();

    @Obsolete
    float getItemModelIndex();

    @Obsolete
    @Nullable
    default String getArmorMaterialOverride(ArmorMaterial armorMaterial) {
        return this.getOverrideArmorMaterials().get(armorMaterial);
    }

    @Obsolete
    Map<ArmorMaterial, String> getOverrideArmorMaterials();

    Component getDescription();

    static TrimMaterial read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntityOrDirect(
                TrimMaterials.getRegistry(), TrimMaterial::readDirect);
    }

    static TrimMaterial readDirect(PacketWrapper<?> packetWrapper) {
        String text;
        ResourceLocation resourceLocation;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            if ((text = (resourceLocation = ResourceLocation.read(packetWrapper)).getKey())
                    .startsWith("trim/")) {
                text = text.substring("trim/".length());
            }
        } else {
            text = packetWrapper.readString();
            resourceLocation = ResourceLocation.minecraft("trim/" + text);
        }
        ItemType itemType =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        ? null
                        : packetWrapper.readMappedEntity(ItemTypes::getById);
        float value =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_4)
                        ? 0.0F
                        : packetWrapper.readFloat();
        Map entries = Collections.emptyMap();
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)) {
            entries =
                    packetWrapper.readMap(
                            result -> result.readMappedEntity(ArmorMaterials::getById),
                            PacketWrapper::readString);
        }
        Component component = packetWrapper.readComponent();
        return new StaticTrimMaterial(
                null, text, resourceLocation, itemType, value, entries, component);
    }

    static void write(PacketWrapper<?> packetWrapper, TrimMaterial trimMaterial) {
        packetWrapper.writeMappedEntityOrDirect(trimMaterial, TrimMaterial::writeDirect);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, TrimMaterial trimMaterial) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            ResourceLocation.write(packetWrapper, trimMaterial.getPaletteId());
        } else {
            packetWrapper.writeString(trimMaterial.getAssetName());
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_5)) {
            packetWrapper.writeMappedEntity(trimMaterial.getIngredient());
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_4)) {
            packetWrapper.writeFloat(trimMaterial.getItemModelIndex());
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_26_3)) {
            packetWrapper.writeMap(
                    trimMaterial.getOverrideArmorMaterials(),
                    PacketWrapper::writeMappedEntity,
                    PacketWrapper::writeString);
        }
        packetWrapper.writeComponent(trimMaterial.getDescription());
    }

    @Deprecated
    static TrimMaterial decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion), typesBuilderData);
    }

    @Deprecated
    static TrimMaterial decode(
            NBT nBT, PacketWrapper<?> packetWrapper, @Nullable TypesBuilderData typesBuilderData) {
        return DIRECT_CODEC.decode(nBT, packetWrapper).copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(TrimMaterial trimMaterial, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), trimMaterial);
    }

    @Deprecated
    static NBT encode(PacketWrapper<?> packetWrapper, TrimMaterial trimMaterial) {
        return DIRECT_CODEC.encode(packetWrapper, trimMaterial);
    }
}
