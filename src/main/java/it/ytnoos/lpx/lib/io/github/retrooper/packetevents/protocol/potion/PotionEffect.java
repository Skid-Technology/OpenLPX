package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public class PotionEffect {

    private final PotionType type;

    private final PotionEffect.Properties properties;

    public PotionEffect(
            PotionType potionType,
            int value,
            int value2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            @Nullable PotionEffect.Properties properties) {
        this(
                potionType,
                new PotionEffect.Properties(
                        value, value2, matches, matches2, matches3, properties));
    }

    public PotionEffect(PotionType potionType, PotionEffect.Properties properties2) {
        this.type = potionType;
        this.properties = properties2;
    }

    public static PotionEffect read(PacketWrapper<?> packetWrapper) {
        PotionType potionType = packetWrapper.readMappedEntity(PotionTypes::getById);
        PotionEffect.Properties properties = PotionEffect.Properties.read(packetWrapper);
        return new PotionEffect(potionType, properties);
    }

    public static void write(PacketWrapper<?> packetWrapper, PotionEffect potionEffect) {
        packetWrapper.writeMappedEntity(potionEffect.type);
        PotionEffect.Properties.write(packetWrapper, potionEffect.properties);
    }

    public static class Properties {

        private final int amplifier;

        private final int duration;

        private final boolean ambient;

        private final boolean showParticles;

        private final boolean showIcon;

        @Nullable private final PotionEffect.Properties hiddenEffect;

        public Properties(
                int amplifier,
                int duration,
                boolean ambient,
                boolean showParticles,
                boolean showIcon,
                @Nullable PotionEffect.Properties properties) {
            this.amplifier = amplifier;
            this.duration = duration;
            this.ambient = ambient;
            this.showParticles = showParticles;
            this.showIcon = showIcon;
            this.hiddenEffect = properties;
        }

        public static PotionEffect.Properties read(PacketWrapper<?> packetWrapper) {
            int value = packetWrapper.readVarInt();
            int value2 = packetWrapper.readVarInt();
            boolean matches = packetWrapper.readBoolean();
            boolean matches2 = packetWrapper.readBoolean();
            boolean matches3 = packetWrapper.readBoolean();
            PotionEffect.Properties properties =
                    packetWrapper.readOptional(PotionEffect.Properties::read);
            return new PotionEffect.Properties(
                    value, value2, matches, matches2, matches3, properties);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, PotionEffect.Properties properties) {
            packetWrapper.writeVarInt(properties.amplifier);
            packetWrapper.writeVarInt(properties.duration);
            packetWrapper.writeBoolean(properties.ambient);
            packetWrapper.writeBoolean(properties.showParticles);
            packetWrapper.writeBoolean(properties.showIcon);
            packetWrapper.writeOptional(properties.hiddenEffect, PotionEffect.Properties::write);
        }
    }
}
