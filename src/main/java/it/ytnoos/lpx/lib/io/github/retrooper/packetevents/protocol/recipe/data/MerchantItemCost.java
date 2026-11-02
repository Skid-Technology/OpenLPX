package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class MerchantItemCost {

    private ItemType item;

    private int count;

    private ComponentPredicate predicate;

    public MerchantItemCost(ItemType itemType) {
        this(itemType, 1);
    }

    public MerchantItemCost(ItemType itemType, int value) {
        this(itemType, value, ComponentPredicate.emptyPredicate());
    }

    public MerchantItemCost(ItemType itemType, int count, ComponentPredicate componentPredicate) {
        this.item = itemType;
        this.count = count;
        this.predicate = componentPredicate;
    }

    @Contract("null -> null; !null -> !null")
    @Nullable
    public static MerchantItemCost ofItem(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        if (itemStack.isEmpty()) {
            return emptyCost();
        }
        ComponentPredicate componentPredicate =
                ComponentPredicate.fromPatches(itemStack.getComponents());
        return new MerchantItemCost(itemStack.getType(), itemStack.getAmount(), componentPredicate);
    }

    public static ItemStack readItem(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)
                ? packetWrapper.readItemStack()
                : read(packetWrapper).asItem();
    }

    public static MerchantItemCost read(PacketWrapper<?> packetWrapper) {
        ItemType itemType = packetWrapper.readMappedEntity(ItemTypes::getById);
        int value = packetWrapper.readVarInt();
        ComponentPredicate componentPredicate = ComponentPredicate.read(packetWrapper);
        return new MerchantItemCost(itemType, value, componentPredicate);
    }

    public static void writeItem(PacketWrapper<?> packetWrapper, ItemStack itemStack) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_5)) {
            packetWrapper.writeItemStack(itemStack);
        } else {
            write(packetWrapper, ofItem(itemStack));
        }
    }

    public static void write(PacketWrapper<?> packetWrapper, MerchantItemCost merchantItemCost) {
        packetWrapper.writeMappedEntity(merchantItemCost.item);
        packetWrapper.writeVarInt(merchantItemCost.count);
        ComponentPredicate.write(packetWrapper, merchantItemCost.predicate);
    }

    public static MerchantItemCost emptyCost() {
        return new MerchantItemCost(ItemTypes.AIR, 0);
    }

    public ItemStack asItem() {
        return ItemStack.builder()
                .type(this.item)
                .amount(this.count)
                .components(this.predicate.asPatches(this.item.getComponents()))
                .build();
    }

    public ItemType getItem() {
        return this.item;
    }

    public void setItem(ItemType itemType) {
        this.item = itemType;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ComponentPredicate getPredicate() {
        return this.predicate;
    }

    public void setPredicate(ComponentPredicate componentPredicate) {
        this.predicate = componentPredicate;
    }
}
