package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.floats.FloatProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class SulfurCubeContactDamage {

    public static final NbtCodec<SulfurCubeContactDamage> CODEC =
            (new NbtMapCodec<SulfurCubeContactDamage>() {

                        public SulfurCubeContactDamage decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            DamageType orThrow =
                                    nBTCompound.getOrThrow(
                                            "damage_type", DamageType.CODEC, packetWrapper);
                            FloatProvider orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "amount", FloatProvider.CODEC, packetWrapper);
                            boolean booleanOrThrow =
                                    nBTCompound.getBooleanOrThrow("attribute_to_source");
                            return new SulfurCubeContactDamage(orThrow, orThrow2, booleanOrThrow);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                SulfurCubeContactDamage sulfurCubeContactDamage)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "damage_type",
                                    sulfurCubeContactDamage.damageType,
                                    DamageType.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "amount",
                                    sulfurCubeContactDamage.amount,
                                    FloatProvider.CODEC,
                                    packetWrapper);
                            nBTCompound.setTag(
                                    "attribute_to_source",
                                    new NBTByte(sulfurCubeContactDamage.attributeToSource));
                        }
                    })
                    .codec();

    private final DamageType damageType;

    private final FloatProvider amount;

    private final boolean attributeToSource;

    public SulfurCubeContactDamage(
            DamageType damageType2, FloatProvider floatProvider, boolean attributeToSource) {
        this.damageType = damageType2;
        this.amount = floatProvider;
        this.attributeToSource = attributeToSource;
    }

    public DamageType getDamageType() {
        return this.damageType;
    }

    public FloatProvider getAmount() {
        return this.amount;
    }

    public boolean isAttributeToSource() {
        return this.attributeToSource;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            SulfurCubeContactDamage sulfurCubeContactDamage = (SulfurCubeContactDamage) result;
            if (this.attributeToSource != sulfurCubeContactDamage.attributeToSource) {
                return false;
            } else {
                return !this.damageType.equals(sulfurCubeContactDamage.damageType)
                        ? false
                        : this.amount.equals(sulfurCubeContactDamage.amount);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.damageType, this.amount, this.attributeToSource);
    }
}
