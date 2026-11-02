package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;

public class Equipment {

    private EquipmentSlot slot;

    private ItemStack item;

    public Equipment(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        this.slot = equipmentSlot;
        this.item = itemStack;
    }

    public EquipmentSlot getSlot() {
        return this.slot;
    }

    public void setSlot(EquipmentSlot equipmentSlot) {
        this.slot = equipmentSlot;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack itemStack) {
        this.item = itemStack;
    }

    @Override
    public boolean equals(Object result) {
        Equipment equipment;
        return !(result instanceof Equipment)
                ? false
                : (equipment = (Equipment) result).getSlot() == this.slot
                        && equipment.getItem().equals(this.item);
    }
}
