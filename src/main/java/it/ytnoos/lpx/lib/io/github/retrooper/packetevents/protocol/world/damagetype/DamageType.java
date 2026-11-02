package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface DamageType extends MappedEntity, CopyableEntity<DamageType>, DeepComparableEntity {

    NbtCodec<DamageType> DIRECT_CODEC =
            (new NbtMapCodec<DamageType>() {

                        public DamageType decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            String stringTagValueOrThrow =
                                    nBTCompound.getStringTagValueOrThrow("message_id");
                            DamageScaling orThrow =
                                    nBTCompound.getOrThrow(
                                            "scaling", DamageScaling.CODEC, packetWrapper);
                            float value =
                                    nBTCompound.getNumberTagValueOrThrow("exhaustion").floatValue();
                            DamageEffects or =
                                    nBTCompound.getOr(
                                            "effects",
                                            DamageEffects.CODEC,
                                            DamageEffects.HURT,
                                            packetWrapper);
                            DeathMessageType or2 =
                                    nBTCompound.getOr(
                                            "death_message_type",
                                            DeathMessageType.CODEC,
                                            DeathMessageType.DEFAULT,
                                            packetWrapper);
                            return new StaticDamageType(
                                    null, stringTagValueOrThrow, orThrow, value, or, or2);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                DamageType damageType)
                                throws NbtCodecException {
                            nBTCompound.setTag(
                                    "message_id", new NBTString(damageType.getMessageId()));
                            nBTCompound.set(
                                    "scaling",
                                    damageType.getScaling(),
                                    DamageScaling.CODEC,
                                    packetWrapper);
                            nBTCompound.setTag(
                                    "exhaustion", new NBTFloat(damageType.getExhaustion()));
                            if (damageType.getEffects() != DamageEffects.HURT) {
                                nBTCompound.set(
                                        "effects",
                                        damageType.getEffects(),
                                        DamageEffects.CODEC,
                                        packetWrapper);
                            }
                            if (damageType.getDeathMessageType() != DeathMessageType.DEFAULT) {
                                nBTCompound.set(
                                        "death_message_type",
                                        damageType.getDeathMessageType(),
                                        DeathMessageType.CODEC,
                                        packetWrapper);
                            }
                        }
                    })
                    .codec();

    NbtCodec<DamageType> CODEC = NbtCodecs.forRegistry(DamageTypes.getRegistry());

    String getMessageId();

    DamageScaling getScaling();

    float getExhaustion();

    DamageEffects getEffects();

    DeathMessageType getDeathMessageType();

    static DamageType read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(DamageTypes.getRegistry());
    }

    static void write(PacketWrapper<?> packetWrapper, DamageType damageType) {
        packetWrapper.writeMappedEntity(damageType);
    }

    @Deprecated
    static DamageType decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(DamageType damageType, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), damageType);
    }
}
