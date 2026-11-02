package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

public class MerchantOffer implements RecipeData {

    private ItemStack firstInputItem;

    @Nullable private ItemStack secondInputItem;

    private ItemStack outputItem;

    private int uses;

    private int maxUses;

    private int xp;

    private int specialPrice;

    private float priceMultiplier;

    private int demand;

    private MerchantOffer(
            MerchantItemCost merchantItemCost,
            @Nullable MerchantItemCost merchantItemCost2,
            ItemStack itemStack,
            int value,
            int value2,
            int value3,
            int value4,
            float value5,
            int value6) {
        this(
                merchantItemCost.asItem(),
                merchantItemCost2 == null ? null : merchantItemCost2.asItem(),
                itemStack,
                value,
                value2,
                value3,
                value4,
                value5,
                value6);
    }

    private MerchantOffer(
            ItemStack itemStack,
            @Nullable ItemStack itemStack2,
            ItemStack itemStack3,
            int uses,
            int maxUses,
            int xp,
            int specialPrice,
            float priceMultiplier,
            int demand) {
        this.firstInputItem = itemStack;
        this.secondInputItem = itemStack2;
        this.outputItem = itemStack3;
        this.uses = uses;
        this.maxUses = maxUses;
        this.xp = xp;
        this.priceMultiplier = priceMultiplier;
        this.demand = demand;
        this.specialPrice = specialPrice;
    }

    public static MerchantOffer of(
            ItemStack itemStack,
            @Nullable ItemStack itemStack2,
            ItemStack itemStack3,
            int value,
            int value2,
            int value3,
            int value4,
            float value5,
            int value6) {
        return new MerchantOffer(
                itemStack, itemStack2, itemStack3, value, value2, value3, value4, value5, value6);
    }

    public static MerchantOffer of(
            ItemStack itemStack,
            ItemStack itemStack2,
            int value,
            int value2,
            int value3,
            int value4,
            float value5,
            int value6) {
        return new MerchantOffer(
                itemStack, null, itemStack2, value, value2, value3, value4, value5, value6);
    }

    public static MerchantOffer of(
            ItemStack itemStack,
            ItemStack itemStack2,
            int value,
            int value2,
            int value3,
            float value4,
            int value5) {
        return new MerchantOffer(
                itemStack, null, itemStack2, value, value2, value3, 0, value4, value5);
    }

    public static MerchantOffer of(
            MerchantItemCost merchantItemCost,
            @Nullable MerchantItemCost merchantItemCost2,
            ItemStack itemStack,
            int value,
            int value2,
            int value3,
            int value4,
            float value5,
            int value6) {
        return new MerchantOffer(
                merchantItemCost,
                merchantItemCost2,
                itemStack,
                value,
                value2,
                value3,
                value4,
                value5,
                value6);
    }

    public static MerchantOffer of(
            MerchantItemCost merchantItemCost,
            ItemStack itemStack,
            int value,
            int value2,
            int value3,
            int value4,
            float value5,
            int value6) {
        return new MerchantOffer(
                merchantItemCost, null, itemStack, value, value2, value3, value4, value5, value6);
    }

    public static MerchantOffer of(
            MerchantItemCost merchantItemCost,
            ItemStack itemStack,
            int value,
            int value2,
            int value3,
            float value4,
            int value5) {
        return new MerchantOffer(
                merchantItemCost, null, itemStack, value, value2, value3, 0, value4, value5);
    }

    public MerchantItemCost getFirstInputCost() {
        return MerchantItemCost.ofItem(this.firstInputItem);
    }

    public void setFirstInputCost(MerchantItemCost merchantItemCost) {
        this.firstInputItem = merchantItemCost.asItem();
    }

    @Obsolete
    public ItemStack getFirstInputItem() {
        return this.firstInputItem;
    }

    @Obsolete
    public void setFirstInputItem(ItemStack itemStack) {
        this.firstInputItem = itemStack;
    }

    @Nullable
    public MerchantItemCost getSecondInputCost() {
        return MerchantItemCost.ofItem(this.firstInputItem);
    }

    public void setSecondInputCost(@Nullable MerchantItemCost merchantItemCost) {
        this.secondInputItem = merchantItemCost == null ? null : merchantItemCost.asItem();
    }

    @Obsolete
    @Nullable
    public ItemStack getSecondInputItem() {
        return this.secondInputItem;
    }

    @Obsolete
    public void setSecondInputItem(@Nullable ItemStack itemStack) {
        this.secondInputItem = itemStack;
    }

    public ItemStack getOutputItem() {
        return this.outputItem;
    }

    public void setOutputItem(ItemStack itemStack) {
        this.outputItem = itemStack;
    }

    public int getUses() {
        return this.uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public float getPriceMultiplier() {
        return this.priceMultiplier;
    }

    public void setPriceMultiplier(float priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public int getDemand() {
        return this.demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public int getSpecialPrice() {
        return this.specialPrice;
    }

    public void setSpecialPrice(int specialPrice) {
        this.specialPrice = specialPrice;
    }

    public boolean isOutOfStock() {
        return this.uses >= this.maxUses;
    }
}
