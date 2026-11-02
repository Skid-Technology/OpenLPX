package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ItemBlocksAttacks {

    private float blockDelaySeconds;

    private float disableCooldownScale;

    private List<ItemBlocksAttacks.DamageReduction> damageReductions;

    private ItemBlocksAttacks.ItemDamageFunction itemDamage;

    @Nullable private MappedEntitySet<DamageType> bypassedBy;

    @Nullable private Sound blockSound;

    @Nullable private Sound disableSound;

    public ItemBlocksAttacks(
            float value,
            float value2,
            List<ItemBlocksAttacks.DamageReduction> values,
            ItemBlocksAttacks.ItemDamageFunction itemDamageFunction,
            @Nullable ResourceLocation resourceLocation,
            @Nullable Sound sound,
            @Nullable Sound sound2) {
        this(
                value,
                value2,
                values,
                itemDamageFunction,
                resourceLocation != null ? new MappedEntitySet<>(resourceLocation) : null,
                sound,
                sound2);
    }

    public ItemBlocksAttacks(
            float blockDelaySeconds,
            float disableCooldownScale,
            List<ItemBlocksAttacks.DamageReduction> damageReductions,
            ItemBlocksAttacks.ItemDamageFunction itemDamageFunction,
            @Nullable MappedEntitySet<DamageType> mappedEntitySet,
            @Nullable Sound sound,
            @Nullable Sound sound2) {
        this.blockDelaySeconds = blockDelaySeconds;
        this.disableCooldownScale = disableCooldownScale;
        this.damageReductions = damageReductions;
        this.itemDamage = itemDamageFunction;
        this.bypassedBy = mappedEntitySet;
        this.blockSound = sound;
        this.disableSound = sound2;
    }

    public static ItemBlocksAttacks read(PacketWrapper<?> packetWrapper) {
        float value = packetWrapper.readFloat();
        float value2 = packetWrapper.readFloat();
        List values = packetWrapper.readList(ItemBlocksAttacks.DamageReduction::read);
        ItemBlocksAttacks.ItemDamageFunction itemDamageFunction =
                ItemBlocksAttacks.ItemDamageFunction.read(packetWrapper);
        MappedEntitySet mappedEntitySet;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            mappedEntitySet =
                    packetWrapper.readOptional(
                            result ->
                                    MappedEntitySet.read(
                                            ((PacketWrapper) packetWrapper),
                                            DamageTypes.getRegistry()));
        } else {
            mappedEntitySet =
                    packetWrapper.readOptional(
                            result2 -> new MappedEntitySet(result2.readIdentifier()));
        }
        MappedEntitySet mappedEntitySet2 = mappedEntitySet;
        Sound sound = packetWrapper.readOptional(Sound::read);
        Sound sound2 = packetWrapper.readOptional(Sound::read);
        return new ItemBlocksAttacks(
                value, value2, values, itemDamageFunction, mappedEntitySet2, sound, sound2);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemBlocksAttacks itemBlocksAttacks) {
        packetWrapper.writeFloat(itemBlocksAttacks.blockDelaySeconds);
        packetWrapper.writeFloat(itemBlocksAttacks.disableCooldownScale);
        packetWrapper.writeList(
                itemBlocksAttacks.damageReductions, ItemBlocksAttacks.DamageReduction::write);
        ItemBlocksAttacks.ItemDamageFunction.write(packetWrapper, itemBlocksAttacks.itemDamage);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            packetWrapper.writeOptional(itemBlocksAttacks.bypassedBy, MappedEntitySet::write);
        } else {
            packetWrapper.writeOptional(
                    itemBlocksAttacks.getBypassedBy(), PacketWrapper::writeIdentifier);
        }
        packetWrapper.writeOptional(itemBlocksAttacks.blockSound, Sound::write);
        packetWrapper.writeOptional(itemBlocksAttacks.disableSound, Sound::write);
    }

    public float getBlockDelaySeconds() {
        return this.blockDelaySeconds;
    }

    public void setBlockDelaySeconds(float blockDelaySeconds) {
        this.blockDelaySeconds = blockDelaySeconds;
    }

    public float getDisableCooldownScale() {
        return this.disableCooldownScale;
    }

    public void setDisableCooldownScale(float disableCooldownScale) {
        this.disableCooldownScale = disableCooldownScale;
    }

    public List<ItemBlocksAttacks.DamageReduction> getDamageReductions() {
        return this.damageReductions;
    }

    public void setDamageReductions(List<ItemBlocksAttacks.DamageReduction> damageReductions) {
        this.damageReductions = damageReductions;
    }

    public ItemBlocksAttacks.ItemDamageFunction getItemDamage() {
        return this.itemDamage;
    }

    public void setItemDamage(ItemBlocksAttacks.ItemDamageFunction itemDamageFunction) {
        this.itemDamage = itemDamageFunction;
    }

    @Nullable
    public MappedEntitySet<DamageType> getBypassedBySet() {
        return this.bypassedBy;
    }

    public void setBypassedBySet(@Nullable MappedEntitySet<DamageType> mappedEntitySet) {
        this.bypassedBy = mappedEntitySet;
    }

    @Nullable
    public ResourceLocation getBypassedBy() {
        return this.bypassedBy != null ? this.bypassedBy.getTagKey() : null;
    }

    public void setBypassedBy(@Nullable ResourceLocation resourceLocation) {
        this.bypassedBy = resourceLocation != null ? new MappedEntitySet<>(resourceLocation) : null;
    }

    @Nullable
    public Sound getBlockSound() {
        return this.blockSound;
    }

    public void setBlockSound(@Nullable Sound sound) {
        this.blockSound = sound;
    }

    @Nullable
    public Sound getDisableSound() {
        return this.disableSound;
    }

    public void setDisableSound(@Nullable Sound sound) {
        this.disableSound = sound;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemBlocksAttacks)) {
            return false;
        } else {
            ItemBlocksAttacks itemBlocksAttacks;
            if (Float.compare(
                            (itemBlocksAttacks = (ItemBlocksAttacks) result).blockDelaySeconds,
                            this.blockDelaySeconds)
                    != 0) {
                return false;
            } else if (Float.compare(
                            itemBlocksAttacks.disableCooldownScale, this.disableCooldownScale)
                    != 0) {
                return false;
            } else if (!this.damageReductions.equals(itemBlocksAttacks.damageReductions)) {
                return false;
            } else if (!this.itemDamage.equals(itemBlocksAttacks.itemDamage)) {
                return false;
            } else if (!Objects.equals(this.bypassedBy, itemBlocksAttacks.bypassedBy)) {
                return false;
            } else {
                return !Objects.equals(this.blockSound, itemBlocksAttacks.blockSound)
                        ? false
                        : Objects.equals(this.disableSound, itemBlocksAttacks.disableSound);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.blockDelaySeconds,
                this.disableCooldownScale,
                this.damageReductions,
                this.itemDamage,
                this.bypassedBy,
                this.blockSound,
                this.disableSound);
    }

    public static final class DamageReduction {

        private float horizontalBlockingAngle;

        @Nullable private MappedEntitySet<DamageType> type;

        private float base;

        private float factor;

        public DamageReduction(
                float horizontalBlockingAngle,
                @Nullable MappedEntitySet<DamageType> mappedEntitySet,
                float base,
                float factor) {
            this.horizontalBlockingAngle = horizontalBlockingAngle;
            this.type = mappedEntitySet;
            this.base = base;
            this.factor = factor;
        }

        public static ItemBlocksAttacks.DamageReduction read(PacketWrapper<?> packetWrapper) {
            float value = packetWrapper.readFloat();
            MappedEntitySet mappedEntitySet =
                    packetWrapper.readOptional(
                            result -> MappedEntitySet.read(result, DamageTypes.getRegistry()));
            float value2 = packetWrapper.readFloat();
            float value3 = packetWrapper.readFloat();
            return new ItemBlocksAttacks.DamageReduction(value, mappedEntitySet, value2, value3);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, ItemBlocksAttacks.DamageReduction damageReduction) {
            packetWrapper.writeFloat(damageReduction.horizontalBlockingAngle);
            packetWrapper.writeOptional(damageReduction.type, MappedEntitySet::write);
            packetWrapper.writeFloat(damageReduction.base);
            packetWrapper.writeFloat(damageReduction.factor);
        }

        public float getHorizontalBlockingAngle() {
            return this.horizontalBlockingAngle;
        }

        public void setHorizontalBlockingAngle(float horizontalBlockingAngle) {
            this.horizontalBlockingAngle = horizontalBlockingAngle;
        }

        @Nullable
        public MappedEntitySet<DamageType> getType() {
            return this.type;
        }

        public void setType(@Nullable MappedEntitySet<DamageType> mappedEntitySet) {
            this.type = mappedEntitySet;
        }

        public float getBase() {
            return this.base;
        }

        public void setBase(float base) {
            this.base = base;
        }

        public float getFactor() {
            return this.factor;
        }

        public void setFactor(float factor) {
            this.factor = factor;
        }

        @Override
        public boolean equals(Object result) {
            if (!(result instanceof ItemBlocksAttacks.DamageReduction)) {
                return false;
            } else {
                ItemBlocksAttacks.DamageReduction damageReduction;
                if (Float.compare(
                                (damageReduction = (ItemBlocksAttacks.DamageReduction) result)
                                        .horizontalBlockingAngle,
                                this.horizontalBlockingAngle)
                        != 0) {
                    return false;
                } else if (Float.compare(damageReduction.base, this.base) != 0) {
                    return false;
                } else {
                    return Float.compare(damageReduction.factor, this.factor) != 0
                            ? false
                            : Objects.equals(this.type, damageReduction.type);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.horizontalBlockingAngle, this.type, this.base, this.factor);
        }
    }

    public static final class ItemDamageFunction {

        private float threshold;

        private float base;

        private float factor;

        public ItemDamageFunction(float threshold, float base, float factor) {
            this.threshold = threshold;
            this.base = base;
            this.factor = factor;
        }

        public static ItemBlocksAttacks.ItemDamageFunction read(PacketWrapper<?> packetWrapper) {
            float value = packetWrapper.readFloat();
            float value2 = packetWrapper.readFloat();
            float value3 = packetWrapper.readFloat();
            return new ItemBlocksAttacks.ItemDamageFunction(value, value2, value3);
        }

        public static void write(
                PacketWrapper<?> packetWrapper,
                ItemBlocksAttacks.ItemDamageFunction itemDamageFunction) {
            packetWrapper.writeFloat(itemDamageFunction.threshold);
            packetWrapper.writeFloat(itemDamageFunction.base);
            packetWrapper.writeFloat(itemDamageFunction.factor);
        }

        public float getThreshold() {
            return this.threshold;
        }

        public void setThreshold(float threshold) {
            this.threshold = threshold;
        }

        public float getBase() {
            return this.base;
        }

        public void setBase(float base) {
            this.base = base;
        }

        public float getFactor() {
            return this.factor;
        }

        public void setFactor(float factor) {
            this.factor = factor;
        }

        @Override
        public boolean equals(Object result) {
            if (!(result instanceof ItemBlocksAttacks.ItemDamageFunction)) {
                return false;
            } else {
                ItemBlocksAttacks.ItemDamageFunction itemDamageFunction;
                if (Float.compare(
                                (itemDamageFunction = (ItemBlocksAttacks.ItemDamageFunction) result)
                                        .threshold,
                                this.threshold)
                        != 0) {
                    return false;
                } else {
                    return Float.compare(itemDamageFunction.base, this.base) != 0
                            ? false
                            : Float.compare(itemDamageFunction.factor, this.factor) == 0;
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.threshold, this.base, this.factor);
        }
    }
}
