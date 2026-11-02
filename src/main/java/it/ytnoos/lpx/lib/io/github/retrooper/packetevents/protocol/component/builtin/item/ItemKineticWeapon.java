package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemKineticWeapon {

    private int contactCooldownTicks;

    private int delayTicks;

    private ItemKineticWeapon.@Nullable Condition dismountConditions;

    private ItemKineticWeapon.@Nullable Condition knockbackConditions;

    private ItemKineticWeapon.@Nullable Condition damageConditions;

    private float forwardMovement;

    private float damageMultiplier;

    @Nullable private Sound sound;

    @Nullable private Sound hitSound;

    public ItemKineticWeapon(
            int contactCooldownTicks,
            int delayTicks,
            ItemKineticWeapon.@Nullable Condition condition,
            ItemKineticWeapon.@Nullable Condition condition2,
            ItemKineticWeapon.@Nullable Condition condition3,
            float forwardMovement,
            float damageMultiplier,
            @Nullable Sound sound2,
            @Nullable Sound sound3) {
        this.contactCooldownTicks = contactCooldownTicks;
        this.delayTicks = delayTicks;
        this.dismountConditions = condition;
        this.knockbackConditions = condition2;
        this.damageConditions = condition3;
        this.forwardMovement = forwardMovement;
        this.damageMultiplier = damageMultiplier;
        this.sound = sound2;
        this.hitSound = sound3;
    }

    public static ItemKineticWeapon read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        int value2 = packetWrapper.readVarInt();
        ItemKineticWeapon.Condition condition =
                packetWrapper.readOptional(ItemKineticWeapon.Condition::read);
        ItemKineticWeapon.Condition condition2 =
                packetWrapper.readOptional(ItemKineticWeapon.Condition::read);
        ItemKineticWeapon.Condition condition3 =
                packetWrapper.readOptional(ItemKineticWeapon.Condition::read);
        float value3 = packetWrapper.readFloat();
        float value4 = packetWrapper.readFloat();
        Sound sound = packetWrapper.readOptional(Sound::read);
        Sound sound2 = packetWrapper.readOptional(Sound::read);
        return new ItemKineticWeapon(
                value, value2, condition, condition2, condition3, value3, value4, sound, sound2);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemKineticWeapon itemKineticWeapon) {
        packetWrapper.writeVarInt(itemKineticWeapon.contactCooldownTicks);
        packetWrapper.writeVarInt(itemKineticWeapon.delayTicks);
        packetWrapper.writeOptional(
                itemKineticWeapon.dismountConditions, ItemKineticWeapon.Condition::write);
        packetWrapper.writeOptional(
                itemKineticWeapon.knockbackConditions, ItemKineticWeapon.Condition::write);
        packetWrapper.writeOptional(
                itemKineticWeapon.damageConditions, ItemKineticWeapon.Condition::write);
        packetWrapper.writeFloat(itemKineticWeapon.forwardMovement);
        packetWrapper.writeFloat(itemKineticWeapon.damageMultiplier);
        packetWrapper.writeOptional(itemKineticWeapon.sound, Sound::write);
        packetWrapper.writeOptional(itemKineticWeapon.hitSound, Sound::write);
    }

    public int getContactCooldownTicks() {
        return this.contactCooldownTicks;
    }

    public void setContactCooldownTicks(int contactCooldownTicks) {
        this.contactCooldownTicks = contactCooldownTicks;
    }

    public int getDelayTicks() {
        return this.delayTicks;
    }

    public void setDelayTicks(int delayTicks) {
        this.delayTicks = delayTicks;
    }

    public ItemKineticWeapon.@Nullable Condition getDismountConditions() {
        return this.dismountConditions;
    }

    public void setDismountConditions(ItemKineticWeapon.@Nullable Condition condition) {
        this.dismountConditions = condition;
    }

    public ItemKineticWeapon.@Nullable Condition getKnockbackConditions() {
        return this.knockbackConditions;
    }

    public void setKnockbackConditions(ItemKineticWeapon.@Nullable Condition condition) {
        this.knockbackConditions = condition;
    }

    public ItemKineticWeapon.@Nullable Condition getDamageConditions() {
        return this.damageConditions;
    }

    public void setDamageConditions(ItemKineticWeapon.@Nullable Condition condition) {
        this.damageConditions = condition;
    }

    public float getForwardMovement() {
        return this.forwardMovement;
    }

    public void setForwardMovement(float forwardMovement) {
        this.forwardMovement = forwardMovement;
    }

    public float getDamageMultiplier() {
        return this.damageMultiplier;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    @Nullable
    public Sound getSound() {
        return this.sound;
    }

    public void setSound(@Nullable Sound sound2) {
        this.sound = sound2;
    }

    @Nullable
    public Sound getHitSound() {
        return this.hitSound;
    }

    public void setHitSound(@Nullable Sound sound) {
        this.hitSound = sound;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ItemKineticWeapon itemKineticWeapon = (ItemKineticWeapon) result;
            if (this.contactCooldownTicks != itemKineticWeapon.contactCooldownTicks) {
                return false;
            } else if (this.delayTicks != itemKineticWeapon.delayTicks) {
                return false;
            } else if (Float.compare(itemKineticWeapon.forwardMovement, this.forwardMovement)
                    != 0) {
                return false;
            } else if (Float.compare(itemKineticWeapon.damageMultiplier, this.damageMultiplier)
                    != 0) {
                return false;
            } else if (!Objects.equals(
                    this.dismountConditions, itemKineticWeapon.dismountConditions)) {
                return false;
            } else if (!Objects.equals(
                    this.knockbackConditions, itemKineticWeapon.knockbackConditions)) {
                return false;
            } else if (!Objects.equals(this.damageConditions, itemKineticWeapon.damageConditions)) {
                return false;
            } else {
                return !Objects.equals(this.sound, itemKineticWeapon.sound)
                        ? false
                        : Objects.equals(this.hitSound, itemKineticWeapon.hitSound);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.contactCooldownTicks,
                this.delayTicks,
                this.dismountConditions,
                this.knockbackConditions,
                this.damageConditions,
                this.forwardMovement,
                this.damageMultiplier,
                this.sound,
                this.hitSound);
    }

    public static class Condition {

        private int maxDurationTicks;

        private float minSpeed;

        private float minRelativeSpeed;

        public Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
            this.maxDurationTicks = maxDurationTicks;
            this.minSpeed = minSpeed;
            this.minRelativeSpeed = minRelativeSpeed;
        }

        public static ItemKineticWeapon.Condition read(PacketWrapper<?> packetWrapper) {
            int value = packetWrapper.readVarInt();
            float value2 = packetWrapper.readFloat();
            float value3 = packetWrapper.readFloat();
            return new ItemKineticWeapon.Condition(value, value2, value3);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, ItemKineticWeapon.Condition condition) {
            packetWrapper.writeVarInt(condition.maxDurationTicks);
            packetWrapper.writeFloat(condition.minSpeed);
            packetWrapper.writeFloat(condition.minRelativeSpeed);
        }

        public int getMaxDurationTicks() {
            return this.maxDurationTicks;
        }

        public void setMaxDurationTicks(int maxDurationTicks) {
            this.maxDurationTicks = maxDurationTicks;
        }

        public float getMinSpeed() {
            return this.minSpeed;
        }

        public void setMinSpeed(float minSpeed) {
            this.minSpeed = minSpeed;
        }

        public float getMinRelativeSpeed() {
            return this.minRelativeSpeed;
        }

        public void setMinRelativeSpeed(float minRelativeSpeed) {
            this.minRelativeSpeed = minRelativeSpeed;
        }

        @Override
        public boolean equals(Object result) {
            if (result != null && this.getClass() == result.getClass()) {
                ItemKineticWeapon.Condition condition = (ItemKineticWeapon.Condition) result;
                if (this.maxDurationTicks != condition.maxDurationTicks) {
                    return false;
                } else {
                    return Float.compare(condition.minSpeed, this.minSpeed) != 0
                            ? false
                            : Float.compare(condition.minRelativeSpeed, this.minRelativeSpeed) == 0;
                }
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.maxDurationTicks, this.minSpeed, this.minRelativeSpeed);
        }
    }
}
