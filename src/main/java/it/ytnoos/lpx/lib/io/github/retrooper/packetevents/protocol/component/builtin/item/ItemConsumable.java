package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables.ConsumeEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ItemConsumable {

    private float consumeSeconds;

    private ItemConsumable.Animation animation;

    private Sound sound;

    private boolean consumeParticles;

    private List<ConsumeEffect<?>> effects;

    public ItemConsumable(
            float consumeSeconds,
            ItemConsumable.Animation animation2,
            Sound sound2,
            boolean consumeParticles,
            List<ConsumeEffect<?>> effects) {
        this.consumeSeconds = consumeSeconds;
        this.animation = animation2;
        this.sound = sound2;
        this.consumeParticles = consumeParticles;
        this.effects = effects;
    }

    public static ItemConsumable read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        ItemConsumable.Animation animation =
                packetWrapper.readEnum(ItemConsumable.Animation.values());
        Sound sound = Sound.read(packetWrapper);
        boolean matches = packetWrapper.readBoolean();
        List values2 = packetWrapper.readList(ConsumeEffect::readFull);
        return new ItemConsumable(value, animation, sound, matches, values2);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemConsumable itemConsumable) {
        packetWrapper.writeFloat(itemConsumable.consumeSeconds);
        packetWrapper.writeEnum(itemConsumable.animation);
        Sound.write(packetWrapper, itemConsumable.sound);
        packetWrapper.writeBoolean(itemConsumable.consumeParticles);
        packetWrapper.writeList(itemConsumable.effects, ConsumeEffect::writeFull);
    }

    public float getConsumeSeconds() {
        return this.consumeSeconds;
    }

    public void setConsumeSeconds(float consumeSeconds) {
        this.consumeSeconds = consumeSeconds;
    }

    public ItemConsumable.Animation getAnimation() {
        return this.animation;
    }

    public void setAnimation(ItemConsumable.Animation animation2) {
        this.animation = animation2;
    }

    public Sound getSound() {
        return this.sound;
    }

    public void setSound(Sound sound2) {
        this.sound = sound2;
    }

    public boolean isConsumeParticles() {
        return this.consumeParticles;
    }

    public void setConsumeParticles(boolean consumeParticles) {
        this.consumeParticles = consumeParticles;
    }

    public List<ConsumeEffect<?>> getEffects() {
        return this.effects;
    }

    public void setEffects(List<ConsumeEffect<?>> effects) {
        this.effects = effects;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ItemConsumable)) {
            return false;
        } else {
            ItemConsumable itemConsumable;
            if (Float.compare(
                            (itemConsumable = (ItemConsumable) result).consumeSeconds,
                            this.consumeSeconds)
                    != 0) {
                return false;
            } else if (this.consumeParticles != itemConsumable.consumeParticles) {
                return false;
            } else if (this.animation != itemConsumable.animation) {
                return false;
            } else {
                return !this.sound.equals(itemConsumable.sound)
                        ? false
                        : this.effects.equals(itemConsumable.effects);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.consumeSeconds,
                this.animation,
                this.sound,
                this.consumeParticles,
                this.effects);
    }

    @Override
    public String toString() {
        return "ItemConsumable{consumeSeconds="
                + this.consumeSeconds
                + ", animation="
                + this.animation
                + ", sound="
                + this.sound
                + ", consumeParticles="
                + this.consumeParticles
                + ", effects="
                + this.effects
                + 125;
    }

    public enum Animation {
        NONE,
        EAT,
        DRINK,
        BLOCK,
        BOW,
        TRIDENT,
        CROSSBOW,
        SPYGLASS,
        TOOT_HORN,
        BRUSH,
        BUNDLE,
        SPEAR
    }
}
