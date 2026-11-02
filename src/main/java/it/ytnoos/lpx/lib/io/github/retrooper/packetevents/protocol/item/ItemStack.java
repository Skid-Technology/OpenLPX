package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.PatchableComponentMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemEnchantments;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.Enchantment;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.type.EnchantmentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.type.EnchantmentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTShort;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

@NullMarked
public class ItemStack {

    public static final ItemStack EMPTY = builder().nbt(new NBTCompound()).build();

    private final ClientVersion version;

    private final IRegistryHolder registryHolder;

    private final ItemType type;

    private int amount;

    @Obsolete @Nullable private NBTCompound nbt;

    @Nullable private PatchableComponentMap components;

    @Obsolete private int legacyData;

    private int decodedSizeBytes = -1;

    private ItemStack(
            ItemType itemType,
            int amount,
            @Nullable NBTCompound nBTCompound,
            @Nullable PatchableComponentMap patchableComponentMap,
            int legacyData,
            ClientVersion clientVersion,
            IRegistryHolder registryHolder2) {
        this.type = itemType == null ? ItemTypes.AIR : itemType;
        this.amount = amount;
        this.nbt = nBTCompound;
        this.components = patchableComponentMap;
        this.legacyData = legacyData;
        this.version = clientVersion;
        this.registryHolder = registryHolder2;
    }

    @Deprecated
    public static ItemStack decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return decode(nBT, packetWrapper.getServerVersion().toClientVersion());
    }

    @Deprecated
    public static ItemStack decode(NBT nBT, ClientVersion clientVersion) {
        if (nBT instanceof NBTString) {
            ResourceLocation resourceLocation = new ResourceLocation(((NBTString) nBT).getValue());
            return builder().type(ItemTypes.getByName(resourceLocation.toString())).build();
        } else {
            NBTCompound nBTCompound = (NBTCompound) nBT;
            ItemStack.Builder builder2 = builder();
            ResourceLocation itemId =
                    Optional.ofNullable(nBTCompound.getStringTagValueOrNull("id"))
                            .or(
                                    () ->
                                            Optional.ofNullable(
                                                    nBTCompound.getStringTagValueOrNull("item")))
                            .map(ResourceLocation::new)
                            .orElseThrow(
                                    () ->
                                            new IllegalArgumentException(
                                                    "No item type specified: "
                                                            + nBTCompound.getTags().keySet()));
            builder2.type(ItemTypes.getByName(itemId.toString()));
            builder2.nbt(nBTCompound.getCompoundTagOrNull("tag"));
            Optional.ofNullable(nBTCompound.getNumberTagOrNull("Count"))
                    .or(() -> Optional.ofNullable(nBTCompound.getNumberTagOrNull("count")))
                    .map(NBTNumber::getAsInt)
                    .ifPresent(builder2::amount);
            return builder2.build();
        }
    }

    @Deprecated
    public static NBT encode(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        return encodeForParticle(itemStack, packetWrapper.getServerVersion().toClientVersion());
    }

    @Deprecated
    public static NBT encodeForParticle(ItemStack itemStack, ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)
                && (itemStack.isEmpty()
                        || itemStack.components == null
                        || itemStack.components.getPatches().isEmpty())) {
            return new NBTString(itemStack.type.getName().toString());
        }
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("id", new NBTString(itemStack.type.getName().toString()));
        if (clientVersion.isOlderThan(ClientVersion.V_1_20_5)) {
            nBTCompound.setTag("Count", new NBTInt(itemStack.getAmount()));
            if (itemStack.nbt != null) {
                nBTCompound.setTag("tag", itemStack.nbt);
            }
        }
        return nBTCompound;
    }

    public int getMaxStackSize() {
        return this.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)
                ? this.getComponentOr(ComponentTypes.MAX_STACK_SIZE, 1)
                : this.getType().getMaxAmount();
    }

    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isDamageableItem() || !this.isDamaged());
    }

    public boolean isDamageableItem() {
        return this.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)
                ? this.hasComponent(ComponentTypes.MAX_DAMAGE)
                        && !this.hasComponent(ComponentTypes.UNBREAKABLE_MODERN)
                        && this.hasComponent(ComponentTypes.DAMAGE)
                : !this.isEmpty()
                        && this.getMaxDamage() > 0
                        && (this.nbt == null || !this.nbt.getBoolean("Unbreakable"));
    }

    public boolean isDamaged() {
        return this.isDamageableItem() && this.getDamageValue() > 0;
    }

    public int getDamageValue() {
        if (this.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            return MathUtil.clamp(
                    this.getComponentOr(ComponentTypes.DAMAGE, 0), 0, this.getMaxDamage());
        } else if (this.version.isNewerThanOrEquals(ClientVersion.V_1_13)) {
            NBTNumber nBTNumber;
            return (nBTNumber = this.nbt != null ? this.nbt.getNumberTagOrNull("Damage") : null)
                            == null
                    ? 0
                    : nBTNumber.getAsInt();
        } else {
            return Math.max(0, this.legacyData);
        }
    }

    public void setDamageValue(int value) {
        if (this.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            this.setComponent(ComponentTypes.DAMAGE, MathUtil.clamp(value, 0, this.getMaxDamage()));
        } else if (this.version.isNewerThanOrEquals(ClientVersion.V_1_13)) {
            this.getOrCreateTag().setTag("Damage", new NBTInt(Math.max(0, value)));
        } else {
            this.legacyData = Math.max(0, value);
        }
    }

    public int getMaxDamage() {
        return this.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)
                ? this.getComponentOr(ComponentTypes.MAX_DAMAGE, 0)
                : this.getType().getMaxDurability();
    }

    public NBTCompound getOrCreateTag() {
        if (this.nbt == null) {
            this.nbt = new NBTCompound();
        }
        return this.nbt;
    }

    public ItemType getType() {
        if (this.version.isNewerThanOrEquals(ClientVersion.V_1_11)) {
            return this.isEmpty() ? ItemTypes.AIR : this.type;
        } else {
            return this.type;
        }
    }

    public int getAmount() {
        if (this.version.isNewerThanOrEquals(ClientVersion.V_1_11)) {
            return this.isEmpty() ? 0 : this.amount;
        } else {
            return this.amount;
        }
    }

    public void shrink(int amount) {
        this.amount -= amount;
    }

    public void grow(int amount) {
        this.amount += amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ItemStack split(int value) {
        int value2 = Math.min(value, this.getAmount());
        ItemStack itemStack;
        (itemStack = this.copy()).setAmount(value2);
        this.shrink(value2);
        return itemStack;
    }

    public ItemStack copy() {
        if (this.isEmpty()) {
            return EMPTY;
        }
        ItemStack itemStack;
        (itemStack =
                                new ItemStack(
                                        this.type,
                                        this.amount,
                                        this.nbt == null ? null : this.nbt.copy(),
                                        this.components == null ? null : this.components.copy(),
                                        this.legacyData,
                                        this.version,
                                        this.registryHolder))
                        .decodedSizeBytes =
                this.decodedSizeBytes;
        return itemStack;
    }

    public int getDecodedSizeBytes() {
        return this.decodedSizeBytes;
    }

    public void setDecodedSizeBytes(int decodedSizeBytes) {
        this.decodedSizeBytes = decodedSizeBytes;
    }

    @Nullable
    public NBTCompound getNBT() {
        return this.nbt;
    }

    public void setNBT(NBTCompound nBTCompound) {
        this.nbt = nBTCompound;
    }

    public <T> T getComponentOr(ComponentType<T> componentType, T t) {
        return this.hasComponentPatches()
                ? this.getComponents().getOr(componentType, (T) t)
                : this.getType().getComponents(this.version).getOr(componentType, (T) t);
    }

    public <T> Optional<T> getComponent(ComponentType<T> componentType) {
        return this.hasComponentPatches()
                ? this.getComponents().getOptional(componentType)
                : this.getType().getComponents(this.version).getOptional(componentType);
    }

    public <T> void setComponent(ComponentType<T> componentType, T t) {
        this.getComponents().set(componentType, t);
    }

    public <T> void unsetComponent(ComponentType<T> componentType) {
        this.getComponents().unset(componentType);
    }

    public <T> void setComponent(ComponentType<T> componentType, Optional<T> optional) {
        this.getComponents().set(componentType, optional);
    }

    public boolean hasComponent(ComponentType<?> componentType) {
        return this.hasComponentPatches()
                ? this.getComponents().has(componentType)
                : this.getType().getComponents(this.version).has(componentType);
    }

    public boolean hasComponentPatches() {
        return this.components != null && !this.components.getPatches().isEmpty();
    }

    public PatchableComponentMap getComponents() {
        if (this.components == null) {
            this.components =
                    new PatchableComponentMap(
                            this.type.getComponents(this.version),
                            new HashMap<>(4),
                            this.registryHolder);
        }
        return this.components;
    }

    public void setComponents(@Nullable PatchableComponentMap patchableComponentMap) {
        this.components = patchableComponentMap;
    }

    public int getLegacyData() {
        return this.legacyData;
    }

    public void setLegacyData(int legacyData) {
        this.legacyData = legacyData;
    }

    public boolean isEnchantable() {
        return this.isEnchantable(this.version);
    }

    @Deprecated
    public boolean isEnchantable(ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            return this.hasComponent(ComponentTypes.ENCHANTABLE)
                    && !this.isEnchanted(clientVersion);
        } else if (this.type == ItemTypes.BOOK) {
            return this.getAmount() == 1;
        } else {
            return this.type == ItemTypes.ENCHANTED_BOOK
                    ? false
                    : this.getMaxStackSize() == 1
                            && this.canBeDepleted()
                            && !this.isEnchanted(clientVersion);
        }
    }

    public boolean isEnchanted() {
        return this.isEnchanted(this.version);
    }

    @Deprecated
    public boolean isEnchanted(ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            return !this.getComponentOr(ComponentTypes.ENCHANTMENTS, ItemEnchantments.EMPTY)
                            .isEmpty()
                    || !this.getComponentOr(
                                    ComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY)
                            .isEmpty();
        }
        if (this.nbt == null) {
            return false;
        }
        String enchantmentsTagName = this.getEnchantmentsTagName(clientVersion);
        NBTList nBTList;
        return (nBTList = this.nbt.getCompoundListTagOrNull(enchantmentsTagName)) != null
                && !nBTList.getTags().isEmpty();
    }

    public List<Enchantment> getEnchantments() {
        return this.getEnchantments(this.version);
    }

    @Deprecated
    public List<Enchantment> getEnchantments(ClientVersion clientVersion) {
        if (!clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            if (this.nbt != null) {
                String enchantmentsTagName = this.getEnchantmentsTagName(clientVersion);
                NBTList nBTList;
                if ((nBTList = this.nbt.getCompoundListTagOrNull(enchantmentsTagName)) != null) {
                    List tags = nBTList.getTags();
                    ArrayList<Enchantment> values = new ArrayList<>(tags.size());
                    Iterator iterator2 = tags.iterator();
                    while (iterator2.hasNext()) {
                        NBTCompound nBTCompound;
                        EnchantmentType enchantmentType;
                        NBTNumber nBTNumber;
                        if ((enchantmentType =
                                                getEnchantmentTypeFromTag(
                                                        nBTCompound =
                                                                (NBTCompound) iterator2.next(),
                                                        clientVersion))
                                        != null
                                && (nBTNumber = nBTCompound.getNumberTagOrNull("lvl")) != null) {
                            int asInt = nBTNumber.getAsInt();
                            Enchantment enchantment =
                                    Enchantment.builder()
                                            .type(enchantmentType)
                                            .level(asInt)
                                            .build();
                            values.add(enchantment);
                        }
                    }
                    return values;
                }
            }
            return new ArrayList<>(0);
        } else {
            ItemEnchantments componentOr =
                    this.getComponentOr(ComponentTypes.ENCHANTMENTS, ItemEnchantments.EMPTY);
            ItemEnchantments componentOr2 =
                    this.getComponentOr(ComponentTypes.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
            ArrayList<Enchantment> values2 =
                    new ArrayList<>(
                            componentOr.getEnchantmentCount() + componentOr2.getEnchantmentCount());
            for (Entry entry : (Iterable<Entry>) ((Iterable) componentOr)) {
                values2.add(
                        new Enchantment(
                                (EnchantmentType) entry.getKey(), (Integer) entry.getValue()));
            }
            for (Entry entry2 : (Iterable<Entry>) ((Iterable) componentOr2)) {
                values2.add(
                        new Enchantment(
                                (EnchantmentType) entry2.getKey(), (Integer) entry2.getValue()));
            }
            return values2;
        }
    }

    public int getEnchantmentLevel(EnchantmentType enchantmentType) {
        return this.getEnchantmentLevel(enchantmentType, this.version);
    }

    @Deprecated
    public int getEnchantmentLevel(EnchantmentType enchantmentType, ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            ItemEnchantments itemEnchantments;
            int value;
            if (!(itemEnchantments =
                                    this.getComponentOr(
                                            ComponentTypes.ENCHANTMENTS, ItemEnchantments.EMPTY))
                            .isEmpty()
                    && (value = itemEnchantments.getEnchantmentLevel(enchantmentType)) > 0) {
                return value;
            }
            ItemEnchantments itemEnchantments2;
            return !(itemEnchantments2 =
                                    this.getComponentOr(
                                            ComponentTypes.STORED_ENCHANTMENTS,
                                            ItemEnchantments.EMPTY))
                            .isEmpty()
                    ? itemEnchantments2.getEnchantmentLevel(enchantmentType)
                    : 0;
        } else {
            if (this.nbt != null) {
                String enchantmentsTagName = this.getEnchantmentsTagName(clientVersion);
                NBTList nBTList;
                if ((nBTList = this.nbt.getCompoundListTagOrNull(enchantmentsTagName)) != null) {
                    Iterator iterator2 = nBTList.getTags().iterator();
                    while (iterator2.hasNext()) {
                        NBTCompound nBTCompound;
                        if (Objects.equals(
                                getEnchantmentTypeFromTag(
                                        nBTCompound = (NBTCompound) iterator2.next(),
                                        clientVersion),
                                enchantmentType)) {
                            NBTNumber nBTNumber;
                            return (nBTNumber = nBTCompound.getNumberTagOrNull("lvl")) != null
                                    ? nBTNumber.getAsInt()
                                    : 0;
                        }
                    }
                }
            }
            return 0;
        }
    }

    @Nullable
    private static EnchantmentType getEnchantmentTypeFromTag(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_13)) {
            return EnchantmentTypes.getByName(nBTCompound.getStringTagValueOrNull("id"));
        }
        NBTShort nBTShort;
        return (nBTShort = nBTCompound.getTagOfTypeOrNull("id", NBTShort.class)) != null
                ? EnchantmentTypes.getById(clientVersion, nBTShort.getAsInt())
                : null;
    }

    public void setEnchantments(List<Enchantment> values) {
        this.setEnchantments(values, this.version);
    }

    @Deprecated
    public void setEnchantments(List<Enchantment> values, ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_5)) {
            HashMap entries = new HashMap(values.size());
            for (Enchantment enchantment : values) {
                entries.put(enchantment.getType(), enchantment.getLevel());
            }
            ComponentType<ItemEnchantments> componentType =
                    this.hasComponent(ComponentTypes.STORED_ENCHANTMENTS)
                            ? ComponentTypes.STORED_ENCHANTMENTS
                            : ComponentTypes.ENCHANTMENTS;
            boolean matches =
                    this.<ItemEnchantments>getComponent(componentType)
                            .map(ItemEnchantments::isShowInTooltip)
                            .orElse(true);
            this.setComponent(componentType, new ItemEnchantments(entries, matches));
        } else {
            String enchantmentsTagName = this.getEnchantmentsTagName(clientVersion);
            if (values.isEmpty()) {
                if (this.nbt != null && this.nbt.getTagOrNull(enchantmentsTagName) != null) {
                    this.nbt.removeTag(enchantmentsTagName);
                }
            } else {
                ArrayList<NBTCompound> values2 = new ArrayList<>();
                for (Enchantment enchantment2 : values) {
                    NBTCompound nBTCompound = new NBTCompound();
                    if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_13)) {
                        nBTCompound.setTag(
                                "id", new NBTString(enchantment2.getType().getName().toString()));
                    } else {
                        nBTCompound.setTag(
                                "id",
                                new NBTShort((short) enchantment2.getType().getId(clientVersion)));
                    }
                    nBTCompound.setTag("lvl", new NBTShort((short) enchantment2.getLevel()));
                    values2.add(nBTCompound);
                }
                this.getOrCreateTag()
                        .setTag(enchantmentsTagName, new NBTList<>(NBTType.COMPOUND, values2));
            }
        }
    }

    @Deprecated
    public String getEnchantmentsTagName(ClientVersion clientVersion) {
        String text =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_1_13) ? "Enchantments" : "ench";
        if (this.type == ItemTypes.ENCHANTED_BOOK) {
            text = "StoredEnchantments";
        }
        return text;
    }

    public boolean canBeDepleted() {
        return this.getMaxDamage() > 0;
    }

    public boolean is(ItemType itemType) {
        return this.getType() == itemType;
    }

    public static boolean isSameItemSameTags(ItemStack itemStack, ItemStack itemStack2) {
        return isSameItemSameComponents(itemStack, itemStack2);
    }

    public static boolean isSameItemSameComponents(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack.version != itemStack2.version) {
            throw new IllegalArgumentException(
                    "Can't compare two ItemStacks across versions: "
                            + itemStack.version
                            + " != "
                            + itemStack2.version);
        } else {
            return itemStack.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)
                    ? itemStack.is(itemStack2.getType())
                            && (itemStack.isEmpty() && itemStack2.isEmpty()
                                    || itemStack.getComponents().equals(itemStack2.getComponents()))
                    : itemStack.is(itemStack2.getType())
                            && (itemStack.isEmpty() && itemStack2.isEmpty()
                                    || Objects.equals(itemStack.nbt, itemStack2.nbt));
        }
    }

    public static boolean tagMatches(
            @Nullable ItemStack itemStack, @Nullable ItemStack itemStack2) {
        if (itemStack == itemStack2) {
            return true;
        } else if (itemStack == null) {
            return itemStack2.isEmpty();
        } else if (itemStack2 == null) {
            return itemStack.isEmpty();
        } else if (itemStack.version != itemStack2.version) {
            throw new IllegalArgumentException(
                    "Can't compare two ItemStacks across versions: "
                            + itemStack.version
                            + " != "
                            + itemStack2.version);
        } else {
            return itemStack.version.isNewerThanOrEquals(ClientVersion.V_1_20_5)
                    ? itemStack.getComponents().equals(itemStack2.getComponents())
                    : Objects.equals(itemStack.nbt, itemStack2.nbt);
        }
    }

    public boolean isEmpty() {
        boolean matches = this.type == ItemTypes.AIR || this.amount <= 0;
        return !this.version.isOlderThanOrEquals(ClientVersion.V_1_12_2)
                ? matches
                : matches || this.legacyData < -32768 || this.legacyData > 65536;
    }

    public ClientVersion getVersion() {
        return this.version;
    }

    public IRegistryHolder getRegistryHolder() {
        return this.registryHolder;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemStack)) {
            return false;
        }
        ItemStack itemStack = (ItemStack) result;
        return this.type.equals(itemStack.type)
                && this.amount == itemStack.amount
                && Objects.equals(this.nbt, itemStack.nbt)
                && Objects.equals(this.components, itemStack.components)
                && this.legacyData == itemStack.legacyData;
    }

    @Override
    public String toString() {
        return this.isEmpty()
                ? "ItemStack[EMPTY]"
                : "ItemStack["
                        + this.getAmount()
                        + "x/"
                        + this.getMaxStackSize()
                        + "x "
                        + this.type.getName()
                        + (this.nbt != null ? ", nbt tag names=" + this.nbt.getTagNames() : "")
                        + (this.legacyData != -1 ? ", legacy data=" + this.legacyData : "")
                        + (this.components != null ? ", components=" + this.components : "")
                        + "]";
    }

    public static ItemStack.Builder builder() {
        return new ItemStack.Builder();
    }

    public static class Builder {

        private ClientVersion version =
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion();

        private IRegistryHolder registryHolder = GlobalRegistryHolder.INSTANCE;

        private ItemType type = ItemTypes.AIR;

        private int amount = 1;

        @Nullable private NBTCompound nbt = null;

        @Nullable private PatchableComponentMap components = null;

        private int legacyData = -1;

        public ItemStack.Builder type(ItemType itemType) {
            this.type = itemType;
            return this;
        }

        public ItemStack.Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public ItemStack.Builder nbt(@Nullable NBTCompound nBTCompound) {
            this.nbt = nBTCompound;
            return this;
        }

        public ItemStack.Builder nbt(String text, NBT nBT) {
            if (this.nbt == null) {
                this.nbt = new NBTCompound();
            }
            this.nbt.setTag(text, nBT);
            return this;
        }

        public ItemStack.Builder components(@Nullable PatchableComponentMap patchableComponentMap) {
            this.components = patchableComponentMap;
            return this;
        }

        public <T> ItemStack.Builder component(ComponentType<T> componentType, @Nullable T t) {
            if (this.components == null) {
                this.components = new PatchableComponentMap(this.type.getComponents(this.version));
            }
            this.components.set(componentType, t);
            return this;
        }

        public ItemStack.Builder legacyData(int legacyData) {
            this.legacyData = legacyData;
            return this;
        }

        public ItemStack.Builder user(User user2) {
            return this.version(user2.getPacketVersion()).registryHolder(user2);
        }

        public ItemStack.Builder wrapper(PacketWrapper<?> packetWrapper) {
            ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
            return this.version(clientVersion).registryHolder(packetWrapper.getRegistryHolder());
        }

        public ItemStack.Builder version(ClientVersion clientVersion) {
            this.version = clientVersion;
            return this;
        }

        public ItemStack.Builder registryHolder(IRegistryHolder registryHolder2) {
            this.registryHolder = registryHolder2;
            return this;
        }

        public ItemStack build() {
            return new ItemStack(
                    this.type,
                    this.amount,
                    this.nbt,
                    this.components,
                    this.legacyData,
                    this.version,
                    this.registryHolder);
        }
    }
}
