package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemMinimumAttackCharge {

    private float value;

    public ItemMinimumAttackCharge(float value) {
        this.value = value;
    }

    public static ItemMinimumAttackCharge read(PacketWrapper<?> packetWrapper) {
        return new ItemMinimumAttackCharge(packetWrapper.readFloat());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemMinimumAttackCharge itemMinimumAttackCharge) {
        packetWrapper.writeFloat(itemMinimumAttackCharge.value);
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object result) {
        return result != null && this.getClass() == result.getClass()
                ? Float.compare(((ItemMinimumAttackCharge) result).value, this.value) == 0
                : false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
