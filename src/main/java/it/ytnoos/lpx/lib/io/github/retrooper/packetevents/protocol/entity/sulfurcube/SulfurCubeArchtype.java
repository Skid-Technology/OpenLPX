package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sulfurcube;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute.AttributeEntry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public interface SulfurCubeArchtype
        extends MappedEntity, CopyableEntity<SulfurCubeArchtype>, DeepComparableEntity {

    NbtCodec<SulfurCubeArchtype> DIRECT_CODEC =
            (new NbtMapCodec<SulfurCubeArchtype>() {

                        public SulfurCubeArchtype decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            MappedEntitySet<ItemType> orThrow =
                                    nBTCompound.getOrThrow(
                                            "items",
                                            MappedEntitySet.codec(ItemTypes.getRegistry()),
                                            packetWrapper);
                            List<AttributeEntry> listOrThrow =
                                    nBTCompound.getListOrThrow(
                                            "attribute_modifiers",
                                            AttributeEntry.CODEC,
                                            packetWrapper);
                            boolean booleanOr = nBTCompound.getBooleanOr("buoyant", false);
                            SulfurCubeExplosionData orNull =
                                    nBTCompound.getOrNull(
                                            "explosion",
                                            SulfurCubeExplosionData.CODEC,
                                            packetWrapper);
                            SulfurCubeContactDamage orNull2 =
                                    nBTCompound.getOrNull(
                                            "contact_damage",
                                            SulfurCubeContactDamage.CODEC,
                                            packetWrapper);
                            SulfurCubeKnockbackModifiers orThrow2 =
                                    nBTCompound.getOrThrow(
                                            "knockback_modifiers",
                                            SulfurCubeKnockbackModifiers.CODEC,
                                            packetWrapper);
                            SulfurCubeSoundSettings orThrow3 =
                                    nBTCompound.getOrThrow(
                                            "sound_settings",
                                            SulfurCubeSoundSettings.CODEC,
                                            packetWrapper);
                            return new StaticSulfurCubeArchtype(
                                    null,
                                    orThrow,
                                    listOrThrow,
                                    booleanOr,
                                    orNull,
                                    orNull2,
                                    orThrow2,
                                    orThrow3);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                SulfurCubeArchtype sulfurCubeArchtype)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "items",
                                    sulfurCubeArchtype.getItems(),
                                    MappedEntitySet.codec(ItemTypes.getRegistry()),
                                    packetWrapper);
                            nBTCompound.setList(
                                    "attribute_modifiers",
                                    sulfurCubeArchtype.getAttributeModifiers(),
                                    AttributeEntry.CODEC,
                                    packetWrapper);
                            if (sulfurCubeArchtype.isBuoyant()) {
                                nBTCompound.setTag("buoyant", new NBTByte(true));
                            }
                            if (sulfurCubeArchtype.getExplosion() != null) {
                                nBTCompound.set(
                                        "explosion",
                                        sulfurCubeArchtype.getExplosion(),
                                        SulfurCubeExplosionData.CODEC,
                                        packetWrapper);
                            }
                            if (sulfurCubeArchtype.getContactDamage() != null) {
                                nBTCompound.set(
                                        "contact_damage",
                                        sulfurCubeArchtype.getContactDamage(),
                                        SulfurCubeContactDamage.CODEC,
                                        packetWrapper);
                            }
                            nBTCompound.set(
                                    "knockback_modifiers",
                                    sulfurCubeArchtype.getKnockbackModifiers(),
                                    SulfurCubeKnockbackModifiers.CODEC,
                                    packetWrapper);
                            nBTCompound.set(
                                    "sound_settings",
                                    sulfurCubeArchtype.getSoundSettings(),
                                    SulfurCubeSoundSettings.CODEC,
                                    packetWrapper);
                        }
                    })
                    .codec();

    MappedEntitySet<ItemType> getItems();

    List<AttributeEntry> getAttributeModifiers();

    boolean isBuoyant();

    @Nullable SulfurCubeExplosionData getExplosion();

    @Nullable SulfurCubeContactDamage getContactDamage();

    SulfurCubeKnockbackModifiers getKnockbackModifiers();

    SulfurCubeSoundSettings getSoundSettings();
}
