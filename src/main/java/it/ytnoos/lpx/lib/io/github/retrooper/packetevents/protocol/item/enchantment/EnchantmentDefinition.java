package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAttributeModifiers;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NullMarked
public final class EnchantmentDefinition {

    private final MappedEntitySet<ItemType> supportedItems;

    private final Optional<MappedEntitySet<ItemType>> primaryItems;

    private final int weight;

    private final int maxLevel;

    private final EnchantmentCost minCost;

    private final EnchantmentCost maxCost;

    private final int anvilCost;

    private final List<ItemAttributeModifiers.EquipmentSlotGroup> slots;

    public EnchantmentDefinition(
            MappedEntitySet<ItemType> mappedEntitySet,
            Optional<MappedEntitySet<ItemType>> optional,
            int weight,
            int maxLevel,
            EnchantmentCost enchantmentCost,
            EnchantmentCost enchantmentCost2,
            int anvilCost,
            List<ItemAttributeModifiers.EquipmentSlotGroup> slots) {
        this.supportedItems = mappedEntitySet;
        this.primaryItems = optional;
        this.weight = weight;
        this.maxLevel = maxLevel;
        this.minCost = enchantmentCost;
        this.maxCost = enchantmentCost2;
        this.anvilCost = anvilCost;
        this.slots = slots;
    }

    @Deprecated
    public static EnchantmentDefinition decode(NBT nBT, ClientVersion clientVersion) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion));
    }

    public static EnchantmentDefinition decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        MappedEntitySet orThrow =
                (nBTCompound = (NBTCompound) nBT)
                        .getOrThrow(
                                "supported_items",
                                (result, result2) ->
                                        MappedEntitySet.decode(
                                                result, result2, ItemTypes.getRegistry()),
                                packetWrapper);
        Optional optional =
                Optional.ofNullable(
                        nBTCompound.getOrNull(
                                "primary_items",
                                (result, result2) ->
                                        MappedEntitySet.decode(
                                                result, result2, ItemTypes.getRegistry()),
                                packetWrapper));
        int asInt = nBTCompound.getNumberTagOrThrow("weight").getAsInt();
        int asInt2 = nBTCompound.getNumberTagOrThrow("max_level").getAsInt();
        EnchantmentCost orThrow2 =
                nBTCompound.getOrThrow("min_cost", EnchantmentCost::decode, packetWrapper);
        EnchantmentCost orThrow3 =
                nBTCompound.getOrThrow("max_cost", EnchantmentCost::decode, packetWrapper);
        int asInt3 = nBTCompound.getNumberTagOrThrow("anvil_cost").getAsInt();
        NBT nBT2;
        List<ItemAttributeModifiers.EquipmentSlotGroup> values;
        if ((nBT2 = nBTCompound.getTagOrThrow("slots")) instanceof NBTList) {
            NBTList nBTList = (NBTList) nBT2;
            values = new ArrayList(nBTList.size());
            Iterator iterator2 = nBTList.getTags().iterator();
            while (iterator2.hasNext()) {
                String value = ((NBTString) ((NBT) iterator2.next())).getValue();
                values.add(
                        AdventureIndexUtil.indexValueOrThrow(
                                ItemAttributeModifiers.EquipmentSlotGroup.ID_INDEX, value));
            }
        } else {
            String value2 = ((NBTString) nBT2).getValue();
            values =
                    Collections.singletonList(
                            AdventureIndexUtil.indexValueOrThrow(
                                    ItemAttributeModifiers.EquipmentSlotGroup.ID_INDEX, value2));
        }
        return new EnchantmentDefinition(
                orThrow, optional, asInt, asInt2, orThrow2, orThrow3, asInt3, values);
    }

    @Deprecated
    public static NBT encode(
            EnchantmentDefinition enchantmentDefinition, ClientVersion clientVersion) {
        return encode(PacketWrapper.createDummyWrapper(clientVersion), enchantmentDefinition);
    }

    public static NBT encode(
            PacketWrapper<?> packetWrapper, EnchantmentDefinition enchantmentDefinition) {
        NBTCompound nBTCompound;
        encode(nBTCompound = new NBTCompound(), packetWrapper, enchantmentDefinition);
        return nBTCompound;
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            EnchantmentDefinition enchantmentDefinition) {
        NBTList nBTList = NBTList.createStringList();
        for (ItemAttributeModifiers.EquipmentSlotGroup equipmentSlotGroup :
                enchantmentDefinition.slots) {
            nBTList.addTag(new NBTString(equipmentSlotGroup.getId()));
        }
        nBTCompound.set(
                "supported_items",
                enchantmentDefinition.supportedItems,
                MappedEntitySet::encode,
                packetWrapper);
        enchantmentDefinition.primaryItems.ifPresent(
                result ->
                        nBTCompound.set(
                                "primary_items",
                                result,
                                MappedEntitySet::encode,
                                ((PacketWrapper) packetWrapper)));
        nBTCompound.setTag("weight", new NBTInt(enchantmentDefinition.weight));
        nBTCompound.setTag("max_level", new NBTInt(enchantmentDefinition.maxLevel));
        nBTCompound.set(
                "min_cost", enchantmentDefinition.minCost, EnchantmentCost::encode, packetWrapper);
        nBTCompound.set(
                "max_cost", enchantmentDefinition.maxCost, EnchantmentCost::encode, packetWrapper);
        nBTCompound.setTag("anvil_cost", new NBTInt(enchantmentDefinition.anvilCost));
        nBTCompound.setTag("slots", nBTList);
    }

    public MappedEntitySet<ItemType> getSupportedItems() {
        return this.supportedItems;
    }

    public Optional<MappedEntitySet<ItemType>> getPrimaryItems() {
        return this.primaryItems;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public EnchantmentCost getMinCost() {
        return this.minCost;
    }

    public EnchantmentCost getMaxCost() {
        return this.maxCost;
    }

    public int getAnvilCost() {
        return this.anvilCost;
    }

    public List<ItemAttributeModifiers.EquipmentSlotGroup> getSlots() {
        return this.slots;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof EnchantmentDefinition)) {
            return false;
        } else {
            EnchantmentDefinition enchantmentDefinition = (EnchantmentDefinition) result;
            if (this.weight != enchantmentDefinition.weight) {
                return false;
            } else if (this.maxLevel != enchantmentDefinition.maxLevel) {
                return false;
            } else if (this.anvilCost != enchantmentDefinition.anvilCost) {
                return false;
            } else if (!this.supportedItems.equals(enchantmentDefinition.supportedItems)) {
                return false;
            } else if (!this.primaryItems.equals(enchantmentDefinition.primaryItems)) {
                return false;
            } else if (!this.minCost.equals(enchantmentDefinition.minCost)) {
                return false;
            } else {
                return !this.maxCost.equals(enchantmentDefinition.maxCost)
                        ? false
                        : this.slots.equals(enchantmentDefinition.slots);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.supportedItems,
                this.primaryItems,
                this.weight,
                this.maxLevel,
                this.minCost,
                this.maxCost,
                this.anvilCost,
                this.slots);
    }

    @Override
    public String toString() {
        return "EnchantmentDefinition{supportedItems="
                + this.supportedItems
                + ", primaryItems="
                + this.primaryItems
                + ", weight="
                + this.weight
                + ", maxLevel="
                + this.maxLevel
                + ", minCost="
                + this.minCost
                + ", maxCost="
                + this.maxCost
                + ", anvilCost="
                + this.anvilCost
                + ", slots="
                + this.slots
                + '}';
    }
}
