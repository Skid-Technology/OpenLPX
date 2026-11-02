package it.ytnoos.lpx.utils;

import com.google.common.collect.Lists;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack2) {
        this.itemStack = itemStack2;
    }

    public ItemBuilder(Material material, int value) {
        ItemStack itemStack2 = new ItemStack(material, value);
        this.itemStack = itemStack2;
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder hideAttributes() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            ItemFlag[] itemFlags = new ItemFlag[] {ItemFlag.HIDE_ATTRIBUTES};
            itemMeta.addItemFlags(itemFlags);
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setAmount(int value) {
        this.itemStack.setAmount(value);
        return this;
    }

    public ItemBuilder setName(String text) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(ColorUtils.createText2(text));
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int value) {
        this.itemStack.addUnsafeEnchantment(enchantment, value);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder setSkullOwner(String text) {
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(text);
        }
        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int value) {
        if (value == 0) {
            return this;
        }
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, value, true);
            this.itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder maxDurability() {
        this.itemStack.setDurability((short) 32767);
        return this;
    }

    public ItemBuilder setLore(String[] texts) {
        ArrayList<String> values = Lists.newArrayList(texts);
        return this.setLore(((List) values));
    }

    public ItemBuilder setLore(List<String> values) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        values.add(0, "");
        if (itemMeta != null) {
            itemMeta.setLore(
                    values.stream().map(ColorUtils::createText2).collect(Collectors.toList()));
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
