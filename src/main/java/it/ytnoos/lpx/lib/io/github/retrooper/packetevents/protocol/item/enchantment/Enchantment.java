package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.enchantment.type.EnchantmentType;

public class Enchantment {

    private EnchantmentType type;

    private int level;

    public Enchantment(EnchantmentType enchantmentType, int level) {
        this.type = enchantmentType;
        this.level = level;
    }

    public EnchantmentType getType() {
        return this.type;
    }

    public void setType(EnchantmentType enchantmentType) {
        this.type = enchantmentType;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static Enchantment.Builder builder() {
        return new Enchantment.Builder();
    }

    public static class Builder {

        private EnchantmentType type;

        private int level;

        public Enchantment.Builder type(EnchantmentType enchantmentType) {
            this.type = enchantmentType;
            return this;
        }

        public Enchantment.Builder level(int level) {
            this.level = level;
            return this;
        }

        public Enchantment build() {
            return new Enchantment(this.type, this.level);
        }
    }
}
