package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class SuspiciousStewEffects {

    private List<SuspiciousStewEffects.EffectEntry> effects;

    public SuspiciousStewEffects(List<SuspiciousStewEffects.EffectEntry> effects) {
        this.effects = effects;
    }

    public static SuspiciousStewEffects read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(SuspiciousStewEffects.EffectEntry::read);
        return new SuspiciousStewEffects(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SuspiciousStewEffects suspiciousStewEffects) {
        packetWrapper.writeList(
                suspiciousStewEffects.effects, SuspiciousStewEffects.EffectEntry::write);
    }

    public void addEffect(SuspiciousStewEffects.EffectEntry effectEntry) {
        this.effects.add(effectEntry);
    }

    public List<SuspiciousStewEffects.EffectEntry> getEffects() {
        return this.effects;
    }

    public void setEffects(List<SuspiciousStewEffects.EffectEntry> effects) {
        this.effects = effects;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof SuspiciousStewEffects)) {
            return false;
        }
        SuspiciousStewEffects suspiciousStewEffects = (SuspiciousStewEffects) result;
        return this.effects.equals(suspiciousStewEffects.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.effects);
    }

    public static class EffectEntry {

        private PotionType type;

        private int duration;

        public EffectEntry(PotionType potionType, int duration) {
            this.type = potionType;
            this.duration = duration;
        }

        public static SuspiciousStewEffects.EffectEntry read(PacketWrapper<?> packetWrapper) {
            PotionType potionType = packetWrapper.readMappedEntity(PotionTypes::getById);
            int value = packetWrapper.readVarInt();
            return new SuspiciousStewEffects.EffectEntry(potionType, value);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, SuspiciousStewEffects.EffectEntry effectEntry) {
            packetWrapper.writeMappedEntity(effectEntry.type);
            packetWrapper.writeVarInt(effectEntry.duration);
        }

        public PotionType getType() {
            return this.type;
        }

        public void setType(PotionType potionType) {
            this.type = potionType;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof SuspiciousStewEffects.EffectEntry)) {
                return false;
            }
            SuspiciousStewEffects.EffectEntry effectEntry =
                    (SuspiciousStewEffects.EffectEntry) result;
            return this.duration != effectEntry.duration
                    ? false
                    : this.type.equals(effectEntry.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.duration);
        }
    }
}
