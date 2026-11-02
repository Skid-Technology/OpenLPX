package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemWeapon {

    private int itemDamagePerAttack;

    private float disableBlockingForSeconds;

    public ItemWeapon(int itemDamagePerAttack, float disableBlockingForSeconds) {
        this.itemDamagePerAttack = itemDamagePerAttack;
        this.disableBlockingForSeconds = disableBlockingForSeconds;
    }

    public static ItemWeapon read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        float value2 = packetWrapper.readFloat();
        return new ItemWeapon(value, value2);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemWeapon itemWeapon) {
        packetWrapper.writeVarInt(itemWeapon.itemDamagePerAttack);
        packetWrapper.writeFloat(itemWeapon.disableBlockingForSeconds);
    }

    public int getItemDamagePerAttack() {
        return this.itemDamagePerAttack;
    }

    public void setItemDamagePerAttack(int itemDamagePerAttack) {
        this.itemDamagePerAttack = itemDamagePerAttack;
    }

    public float getDisableBlockingForSeconds() {
        return this.disableBlockingForSeconds;
    }

    public void setDisableBlockingForSeconds(float disableBlockingForSeconds) {
        this.disableBlockingForSeconds = disableBlockingForSeconds;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemWeapon)) {
            return false;
        }
        ItemWeapon itemWeapon = (ItemWeapon) result;
        return this.itemDamagePerAttack != itemWeapon.itemDamagePerAttack
                ? false
                : Float.compare(
                                itemWeapon.disableBlockingForSeconds,
                                this.disableBlockingForSeconds)
                        == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.itemDamagePerAttack, this.disableBlockingForSeconds);
    }
}
