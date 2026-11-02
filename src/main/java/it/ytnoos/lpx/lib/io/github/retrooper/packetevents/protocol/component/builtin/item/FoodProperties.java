package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionEffect;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FoodProperties {

    private int nutrition;

    private float saturation;

    private boolean canAlwaysEat;

    private float eatSeconds;

    private List<FoodProperties.PossibleEffect> effects;

    @Nullable private ItemStack usingConvertsTo;

    public FoodProperties(int value, float value2, boolean matches) {
        this(value, value2, matches, 1.6F, Collections.emptyList());
    }

    @Obsolete
    public FoodProperties(
            int value,
            float value2,
            boolean matches,
            float value3,
            List<FoodProperties.PossibleEffect> values) {
        this(value, value2, matches, value3, values, null);
    }

    @Obsolete
    public FoodProperties(
            int nutrition,
            float saturation,
            boolean canAlwaysEat,
            float eatSeconds,
            List<FoodProperties.PossibleEffect> effects,
            @Nullable ItemStack itemStack) {
        this.nutrition = nutrition;
        this.saturation = saturation;
        this.canAlwaysEat = canAlwaysEat;
        this.eatSeconds = eatSeconds;
        this.effects = effects;
        this.usingConvertsTo = itemStack;
    }

    public static FoodProperties read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        float value2 = packetWrapper.readFloat();
        boolean matches = packetWrapper.readBoolean();
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            return new FoodProperties(value, value2, matches);
        }
        float value3 = packetWrapper.readFloat();
        ItemStack itemStack =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21)
                        ? packetWrapper.readOptional(PacketWrapper::readItemStack)
                        : null;
        List values = packetWrapper.readList(FoodProperties.PossibleEffect::read);
        return new FoodProperties(value, value2, matches, value3, values, itemStack);
    }

    public static void write(PacketWrapper<?> packetWrapper, FoodProperties foodProperties) {
        packetWrapper.writeVarInt(foodProperties.nutrition);
        packetWrapper.writeFloat(foodProperties.saturation);
        packetWrapper.writeBoolean(foodProperties.canAlwaysEat);
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_2)) {
            packetWrapper.writeFloat(foodProperties.eatSeconds);
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21)) {
                packetWrapper.writeOptional(
                        foodProperties.usingConvertsTo, PacketWrapper::writeItemStack);
            }
            packetWrapper.writeList(foodProperties.effects, FoodProperties.PossibleEffect::write);
        }
    }

    public int getNutrition() {
        return this.nutrition;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public boolean isCanAlwaysEat() {
        return this.canAlwaysEat;
    }

    public void setCanAlwaysEat(boolean canAlwaysEat) {
        this.canAlwaysEat = canAlwaysEat;
    }

    @Obsolete
    public float getEatSeconds() {
        return this.eatSeconds;
    }

    @Obsolete
    public void setEatSeconds(float eatSeconds) {
        this.eatSeconds = eatSeconds;
    }

    @Obsolete
    public void addEffect(FoodProperties.PossibleEffect possibleEffect) {
        this.effects.add(possibleEffect);
    }

    @Obsolete
    public List<FoodProperties.PossibleEffect> getEffects() {
        return this.effects;
    }

    @Obsolete
    public void setEffects(List<FoodProperties.PossibleEffect> effects) {
        this.effects = effects;
    }

    @Obsolete
    @Nullable
    public ItemStack getUsingConvertsTo() {
        return this.usingConvertsTo;
    }

    @Obsolete
    public void setUsingConvertsTo(@Nullable ItemStack itemStack) {
        this.usingConvertsTo = itemStack;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof FoodProperties)) {
            return false;
        } else {
            FoodProperties foodProperties = (FoodProperties) result;
            if (this.nutrition != foodProperties.nutrition) {
                return false;
            } else if (Float.compare(foodProperties.saturation, this.saturation) != 0) {
                return false;
            } else if (this.canAlwaysEat != foodProperties.canAlwaysEat) {
                return false;
            } else if (Float.compare(foodProperties.eatSeconds, this.eatSeconds) != 0) {
                return false;
            } else {
                return !this.effects.equals(foodProperties.effects)
                        ? false
                        : Objects.equals(this.usingConvertsTo, foodProperties.usingConvertsTo);
            }
        }
    }

    @Override
    public int hashCode() {
        Object[] results = new Object[6];
        results[0] = this.nutrition;
        results[1] = this.saturation;
        results[2] = this.canAlwaysEat;
        results[3] = this.eatSeconds;
        results[4] = this.effects;
        results[5] = this.usingConvertsTo;
        return Objects.hash(results);
    }

    @Obsolete
    public static class PossibleEffect {

        private PotionEffect effect;

        private float probability;

        public PossibleEffect(PotionEffect potionEffect, float probability) {
            this.effect = potionEffect;
            this.probability = probability;
        }

        public static FoodProperties.PossibleEffect read(PacketWrapper<?> packetWrapper) {
            PotionEffect potionEffect = PotionEffect.read(packetWrapper);
            float value = packetWrapper.readFloat();
            return new FoodProperties.PossibleEffect(potionEffect, value);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, FoodProperties.PossibleEffect possibleEffect) {
            PotionEffect.write(packetWrapper, possibleEffect.effect);
            packetWrapper.writeFloat(possibleEffect.probability);
        }

        public PotionEffect getEffect() {
            return this.effect;
        }

        public void setEffect(PotionEffect potionEffect) {
            this.effect = potionEffect;
        }

        public float getProbability() {
            return this.probability;
        }

        public void setProbability(float probability) {
            this.probability = probability;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof FoodProperties.PossibleEffect)) {
                return false;
            }
            FoodProperties.PossibleEffect possibleEffect;
            return Float.compare(
                                    (possibleEffect = (FoodProperties.PossibleEffect) result)
                                            .probability,
                                    this.probability)
                            != 0
                    ? false
                    : this.effect.equals(possibleEffect.effect);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.effect, this.probability);
        }
    }
}
