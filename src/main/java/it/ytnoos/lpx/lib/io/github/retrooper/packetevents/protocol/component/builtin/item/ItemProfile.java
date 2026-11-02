package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.PlayerModelType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

@NullMarked
public final class ItemProfile {

    @Nullable private String name;

    @Nullable private UUID id;

    private List<ItemProfile.Property> properties;

    private ItemProfile.SkinPatch skinPatch;

    public ItemProfile(
            @Nullable String text, @Nullable UUID uUID, List<ItemProfile.Property> values) {
        this(text, uUID, values, ItemProfile.SkinPatch.EMPTY);
    }

    public ItemProfile(
            @Nullable String name,
            @Nullable UUID id,
            List<ItemProfile.Property> properties,
            ItemProfile.SkinPatch skinPatch2) {
        this.name = name;
        this.id = id;
        this.properties = properties;
        this.skinPatch = skinPatch2;
    }

    public static ItemProfile read(PacketWrapper<?> packetWrapper) {
        String text;
        UUID uUID;
        if (!packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_9)
                && packetWrapper.readBoolean()) {
            uUID = packetWrapper.readUUID();
            text = packetWrapper.readString(16);
        } else {
            text = packetWrapper.readOptional(result -> result.readString(16));
            uUID = packetWrapper.readOptional(PacketWrapper::readUUID);
        }
        List values = packetWrapper.readList(ItemProfile.Property::read);
        ItemProfile.SkinPatch skinPatch =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)
                        ? ItemProfile.SkinPatch.read(packetWrapper)
                        : ItemProfile.SkinPatch.EMPTY;
        return new ItemProfile(text, uUID, values, skinPatch);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemProfile itemProfile) {
        boolean matches;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            matches = itemProfile.name == null || itemProfile.id == null;
            packetWrapper.writeBoolean(!matches);
        } else {
            matches = true;
        }
        if (!matches) {
            packetWrapper.writeUUID(itemProfile.id);
            packetWrapper.writeString(itemProfile.name, 16);
        } else {
            packetWrapper.writeOptional(
                    itemProfile.name, (result, result2) -> result.writeString(result2, 16));
            packetWrapper.writeOptional(itemProfile.id, PacketWrapper::writeUUID);
        }
        packetWrapper.writeList(itemProfile.properties, ItemProfile.Property::write);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            ItemProfile.SkinPatch.write(packetWrapper, itemProfile.skinPatch);
        }
    }

    public static ItemProfile decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        if (nBT instanceof NBTString) {
            String value = ((NBTString) nBT).getValue();
            return new ItemProfile(value, null, new ArrayList<>());
        } else {
            NBTCompound nBTCompound;
            UUID orNull =
                    (nBTCompound = (NBTCompound) nBT)
                            .getOrNull("id", NbtCodecs.UUID, packetWrapper);
            String stringTagValueOrNull = nBTCompound.getStringTagValueOrNull("name");
            List orSupply =
                    nBTCompound.getOrSupply(
                            "properties",
                            ItemProfile.Property.PROPERTY_MAP,
                            ArrayList::new,
                            packetWrapper);
            ItemProfile.SkinPatch skinPatch =
                    ItemProfile.SkinPatch.decode(nBTCompound, packetWrapper);
            return new ItemProfile(stringTagValueOrNull, orNull, orSupply, skinPatch);
        }
    }

    public static NBT encode(PacketWrapper<?> packetWrapper, ItemProfile itemProfile) {
        NBTCompound nBTCompound = new NBTCompound();
        if (itemProfile.id != null) {
            nBTCompound.set("id", itemProfile.id, NbtCodecs.UUID, packetWrapper);
        }
        if (itemProfile.name != null) {
            nBTCompound.setTag("name", new NBTString(itemProfile.name));
        }
        if (!itemProfile.properties.isEmpty()) {
            nBTCompound.set(
                    "properties",
                    itemProfile.properties,
                    ItemProfile.Property.PROPERTY_MAP,
                    packetWrapper);
        }
        ItemProfile.SkinPatch.encode(nBTCompound, packetWrapper, itemProfile.skinPatch);
        return nBTCompound;
    }

    public static ItemProfile fromAdventure(PlayerHeadObjectContents playerHeadObjectContents) {
        List<PlayerHeadObjectContents.ProfileProperty> values =
                playerHeadObjectContents.profileProperties();
        ArrayList<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                .builtin.item.ItemProfile.Property>
                values2 = new ArrayList<>(values.size());
        for (PlayerHeadObjectContents.ProfileProperty profileProperty : values) {
            values2.add(ItemProfile.Property.fromAdventure(profileProperty));
        }
        return new ItemProfile(
                playerHeadObjectContents.name(), playerHeadObjectContents.id(), values2);
    }

    public List<PlayerHeadObjectContents.ProfileProperty> getAdventureProperties() {
        if (this.properties.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<PlayerHeadObjectContents.ProfileProperty> values =
                new ArrayList<>(this.properties.size());
        for (ItemProfile.Property property : this.properties) {
            values.add(property.asAdventure());
        }
        return Collections.unmodifiableList(values);
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public UUID getId() {
        return this.id;
    }

    public void setId(@Nullable UUID id) {
        this.id = id;
    }

    public void addProperty(ItemProfile.Property property) {
        this.properties.add(property);
    }

    public List<ItemProfile.Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<ItemProfile.Property> properties) {
        this.properties = properties;
    }

    public ItemProfile.SkinPatch getSkinPatch() {
        return this.skinPatch;
    }

    public void setSkinPatch(ItemProfile.SkinPatch skinPatch2) {
        this.skinPatch = skinPatch2;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ItemProfile)) {
            return false;
        } else {
            ItemProfile itemProfile = (ItemProfile) result;
            if (!Objects.equals(this.name, itemProfile.name)) {
                return false;
            } else if (!Objects.equals(this.id, itemProfile.id)) {
                return false;
            } else {
                return !this.properties.equals(itemProfile.properties)
                        ? false
                        : this.skinPatch.equals(itemProfile.skinPatch);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id, this.properties, this.skinPatch);
    }

    public static class Property {

        public static final NbtCodec<ItemProfile.Property> CODEC =
                (new NbtMapCodec<ItemProfile.Property>() {

                            public ItemProfile.Property decode(
                                    NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                    throws NbtCodecException {
                                String stringTagValueOrThrow =
                                        nBTCompound.getStringTagValueOrThrow("name");
                                String stringTagValueOrThrow2 =
                                        nBTCompound.getStringTagValueOrThrow("value");
                                String stringTagValueOrNull =
                                        nBTCompound.getStringTagValueOrNull("signature");
                                return new ItemProfile.Property(
                                        stringTagValueOrThrow,
                                        stringTagValueOrThrow2,
                                        stringTagValueOrNull);
                            }

                            public void encode(
                                    NBTCompound nBTCompound,
                                    PacketWrapper<?> packetWrapper,
                                    ItemProfile.Property property)
                                    throws NbtCodecException {
                                nBTCompound.setTag("name", new NBTString(property.getName()));
                                nBTCompound.setTag("value", new NBTString(property.getValue()));
                                if (property.getSignature() != null) {
                                    nBTCompound.setTag(
                                            "signature", new NBTString(property.getSignature()));
                                }
                            }
                        })
                        .codec();

        public static final NbtCodec<List<ItemProfile.Property>> PROPERTY_MAP =
                new NbtCodec<List<ItemProfile.Property>>() {

                    private final NbtCodec<List<ItemProfile.Property>> propertyList =
                            ItemProfile.Property.CODEC.applyList();

                    public List<ItemProfile.Property> decode(
                            NBT nBT, PacketWrapper<?> packetWrapper) {
                        if (!(nBT instanceof NBTCompound)) {
                            return this.propertyList.decode(nBT, packetWrapper);
                        }
                        Map<String, NBT> tags = ((NBTCompound) nBT).getTags();
                        ArrayList<ItemProfile.Property> values = new ArrayList<>(tags.size());
                        for (Entry<String, NBT> entry : tags.entrySet()) {
                            for (String text :
                                    NbtCodecs.STRING_LIST.decode(
                                            (NBT) entry.getValue(), packetWrapper)) {
                                values.add(
                                        new ItemProfile.Property(
                                                (String) entry.getKey(), text, null));
                            }
                        }
                        return values;
                    }

                    public NBT encode(
                            PacketWrapper<?> packetWrapper, List<ItemProfile.Property> values) {
                        return this.propertyList.encode(packetWrapper, values);
                    }
                };

        private String name;

        private String value;

        @Nullable private String signature;

        public Property(String name, String value, @Nullable String signature) {
            this.name = name;
            this.value = value;
            this.signature = signature;
        }

        public static ItemProfile.Property read(PacketWrapper<?> packetWrapper) {
            String text = packetWrapper.readString(64);
            String text2 = packetWrapper.readString(32767);
            String text3 = packetWrapper.readOptional(result -> result.readString(1024));
            return new ItemProfile.Property(text, text2, text3);
        }

        public static void write(PacketWrapper<?> packetWrapper, ItemProfile.Property property) {
            packetWrapper.writeString(property.name, 64);
            packetWrapper.writeString(property.value, 32767);
            packetWrapper.writeOptional(
                    property.signature, (result, result2) -> result.writeString(result2, 1024));
        }

        public static ItemProfile.Property fromAdventure(
                PlayerHeadObjectContents.ProfileProperty profileProperty) {
            return new ItemProfile.Property(
                    profileProperty.name(), profileProperty.value(), profileProperty.signature());
        }

        public PlayerHeadObjectContents.ProfileProperty asAdventure() {
            return PlayerHeadObjectContents.property(this.name, this.value, this.signature);
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Nullable
        public String getSignature() {
            return this.signature;
        }

        public void setSignature(@Nullable String signature) {
            this.signature = signature;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemProfile.Property)) {
                return false;
            } else {
                ItemProfile.Property property = (ItemProfile.Property) result;
                if (!this.name.equals(property.name)) {
                    return false;
                } else {
                    return !this.value.equals(property.value)
                            ? false
                            : Objects.equals(this.signature, property.signature);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.name, this.value, this.signature);
        }
    }

    public static class SkinPatch {

        public static final ItemProfile.SkinPatch EMPTY =
                new ItemProfile.SkinPatch(null, null, null, null);

        @Nullable private final ResourceLocation body;

        @Nullable private final ResourceLocation cape;

        @Nullable private final ResourceLocation elytra;

        @Nullable private final PlayerModelType model;

        public SkinPatch(
                @Nullable ResourceLocation resourceLocation,
                @Nullable ResourceLocation resourceLocation2,
                @Nullable ResourceLocation resourceLocation3,
                @Nullable PlayerModelType playerModelType) {
            this.body = resourceLocation;
            this.cape = resourceLocation2;
            this.elytra = resourceLocation3;
            this.model = playerModelType;
        }

        public static ItemProfile.SkinPatch read(PacketWrapper<?> packetWrapper) {
            ResourceLocation resourceLocation = packetWrapper.readOptional(ResourceLocation::read);
            ResourceLocation resourceLocation2 = packetWrapper.readOptional(ResourceLocation::read);
            ResourceLocation resourceLocation3 = packetWrapper.readOptional(ResourceLocation::read);
            PlayerModelType playerModelType = packetWrapper.readOptional(PlayerModelType::read);
            return new ItemProfile.SkinPatch(
                    resourceLocation, resourceLocation2, resourceLocation3, playerModelType);
        }

        public static void write(PacketWrapper<?> packetWrapper, ItemProfile.SkinPatch skinPatch) {
            packetWrapper.writeOptional(skinPatch.body, ResourceLocation::write);
            packetWrapper.writeOptional(skinPatch.cape, ResourceLocation::write);
            packetWrapper.writeOptional(skinPatch.elytra, ResourceLocation::write);
            packetWrapper.writeOptional(skinPatch.model, PlayerModelType::write);
        }

        public static ItemProfile.SkinPatch decode(
                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
            ResourceLocation orNull =
                    nBTCompound.getOrNull("texture", ResourceLocation.CODEC, packetWrapper);
            ResourceLocation orNull2 =
                    nBTCompound.getOrNull("cape", ResourceLocation.CODEC, packetWrapper);
            ResourceLocation orNull3 =
                    nBTCompound.getOrNull("elytra", ResourceLocation.CODEC, packetWrapper);
            PlayerModelType orNull4 =
                    nBTCompound.getOrNull("model", PlayerModelType.CODEC, packetWrapper);
            return new ItemProfile.SkinPatch(orNull, orNull2, orNull3, orNull4);
        }

        public static void encode(
                NBTCompound nBTCompound,
                PacketWrapper<?> packetWrapper,
                ItemProfile.SkinPatch skinPatch) {
            if (skinPatch.body != null) {
                nBTCompound.set("texture", skinPatch.body, ResourceLocation.CODEC, packetWrapper);
            }
            if (skinPatch.cape != null) {
                nBTCompound.set("cape", skinPatch.cape, ResourceLocation.CODEC, packetWrapper);
            }
            if (skinPatch.elytra != null) {
                nBTCompound.set("elytra", skinPatch.elytra, ResourceLocation.CODEC, packetWrapper);
            }
            if (skinPatch.model != null) {
                nBTCompound.set("model", skinPatch.model, PlayerModelType.CODEC, packetWrapper);
            }
        }

        @Nullable
        public ResourceLocation getBody() {
            return this.body;
        }

        @Nullable
        public ResourceLocation getCape() {
            return this.cape;
        }

        @Nullable
        public ResourceLocation getElytra() {
            return this.elytra;
        }

        @Nullable
        public PlayerModelType getModel() {
            return this.model;
        }

        @Override
        public boolean equals(Object result) {
            if (result != null && this.getClass() == result.getClass()) {
                ItemProfile.SkinPatch skinPatch = (ItemProfile.SkinPatch) result;
                if (!Objects.equals(this.body, skinPatch.body)) {
                    return false;
                } else if (!Objects.equals(this.cape, skinPatch.cape)) {
                    return false;
                } else {
                    return !Objects.equals(this.elytra, skinPatch.elytra)
                            ? false
                            : this.model == skinPatch.model;
                }
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.body, this.cape, this.elytra, this.model);
        }
    }
}
