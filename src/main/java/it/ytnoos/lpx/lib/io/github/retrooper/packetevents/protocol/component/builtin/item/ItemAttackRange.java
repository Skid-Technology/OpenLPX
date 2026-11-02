package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemAttackRange {

    private float minRange;

    private float maxRange;

    private float minCreativeRange;

    private float maxCreativeRange;

    private float hitboxMargin;

    private float mobFactor;

    public ItemAttackRange(
            float minRange,
            float maxRange,
            float minCreativeRange,
            float maxCreativeRange,
            float hitboxMargin,
            float mobFactor) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.minCreativeRange = minCreativeRange;
        this.maxCreativeRange = maxCreativeRange;
        this.hitboxMargin = hitboxMargin;
        this.mobFactor = mobFactor;
    }

    public static ItemAttackRange read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        float value2 = packetWrapper.readFloat();
        float value3 = packetWrapper.readFloat();
        float value4 = packetWrapper.readFloat();
        float value5 = packetWrapper.readFloat();
        float value6 = packetWrapper.readFloat();
        return new ItemAttackRange(value, value2, value3, value4, value5, value6);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemAttackRange itemAttackRange) {
        packetWrapper.writeFloat(itemAttackRange.minRange);
        packetWrapper.writeFloat(itemAttackRange.maxRange);
        packetWrapper.writeFloat(itemAttackRange.minCreativeRange);
        packetWrapper.writeFloat(itemAttackRange.maxCreativeRange);
        packetWrapper.writeFloat(itemAttackRange.hitboxMargin);
        packetWrapper.writeFloat(itemAttackRange.mobFactor);
    }

    public float getMinRange() {
        return this.minRange;
    }

    public void setMinRange(float minRange) {
        this.minRange = minRange;
    }

    public float getMaxRange() {
        return this.maxRange;
    }

    public void setMaxRange(float maxRange) {
        this.maxRange = maxRange;
    }

    public float getMinCreativeRange() {
        return this.minCreativeRange;
    }

    public void setMinCreativeRange(float minCreativeRange) {
        this.minCreativeRange = minCreativeRange;
    }

    public float getMaxCreativeRange() {
        return this.maxCreativeRange;
    }

    public void setMaxCreativeRange(float maxCreativeRange) {
        this.maxCreativeRange = maxCreativeRange;
    }

    public float getHitboxMargin() {
        return this.hitboxMargin;
    }

    public void setHitboxMargin(float hitboxMargin) {
        this.hitboxMargin = hitboxMargin;
    }

    public float getMobFactor() {
        return this.mobFactor;
    }

    public void setMobFactor(float mobFactor) {
        this.mobFactor = mobFactor;
    }

    @Override
    public boolean equals(Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else {
            ItemAttackRange itemAttackRange;
            if (Float.compare((itemAttackRange = (ItemAttackRange) result).minRange, this.minRange)
                    != 0) {
                return false;
            } else if (Float.compare(itemAttackRange.maxRange, this.maxRange) != 0) {
                return false;
            } else if (Float.compare(itemAttackRange.minCreativeRange, this.minCreativeRange)
                    != 0) {
                return false;
            } else if (Float.compare(itemAttackRange.maxCreativeRange, this.maxCreativeRange)
                    != 0) {
                return false;
            } else {
                return Float.compare(itemAttackRange.hitboxMargin, this.hitboxMargin) != 0
                        ? false
                        : Float.compare(itemAttackRange.mobFactor, this.mobFactor) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        Object[] results = new Object[6];
        results[0] = this.minRange;
        results[1] = this.maxRange;
        results[2] = this.minCreativeRange;
        results[3] = this.maxCreativeRange;
        results[4] = this.hitboxMargin;
        results[5] = this.mobFactor;
        return Objects.hash(results);
    }
}
