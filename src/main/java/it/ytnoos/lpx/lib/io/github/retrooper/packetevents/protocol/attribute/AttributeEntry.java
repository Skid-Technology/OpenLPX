package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public final class AttributeEntry {

    public static final NbtCodec<AttributeEntry> CODEC =
            (new NbtMapCodec<AttributeEntry>() {

                        public AttributeEntry decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            Attribute orThrow =
                                    nBTCompound.getOrThrow(
                                            "attribute", Attribute.CODEC, packetWrapper);
                            AttributeModifier attributeModifier =
                                    AttributeModifier.MAP_CODEC.decode(nBTCompound, packetWrapper);
                            return new AttributeEntry(orThrow, attributeModifier);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                AttributeEntry attributeEntry)
                                throws NbtCodecException {
                            nBTCompound.set(
                                    "attribute",
                                    attributeEntry.attribute,
                                    Attribute.CODEC,
                                    packetWrapper);
                            AttributeModifier.MAP_CODEC.encode(
                                    nBTCompound, packetWrapper, attributeEntry.modifier);
                        }
                    })
                    .codec();

    private final Attribute attribute;

    private final AttributeModifier modifier;

    public AttributeEntry(Attribute attribute2, AttributeModifier attributeModifier) {
        this.attribute = attribute2;
        this.modifier = attributeModifier;
    }

    public static AttributeEntry read(PacketWrapper<?> packetWrapper) {
        Attribute attribute = packetWrapper.readMappedEntity(Attributes.getRegistry());
        AttributeModifier attributeModifier = AttributeModifier.read(packetWrapper);
        return new AttributeEntry(attribute, attributeModifier);
    }

    public static void write(PacketWrapper<?> packetWrapper, AttributeEntry attributeEntry) {
        packetWrapper.writeMappedEntity(attributeEntry.attribute);
        AttributeModifier.write(packetWrapper, attributeEntry.modifier);
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public AttributeModifier getModifier() {
        return this.modifier;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            AttributeEntry attributeEntry = (AttributeEntry) result;
            return !this.attribute.equals(attributeEntry.attribute)
                    ? false
                    : this.modifier.equals(attributeEntry.modifier);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.attribute, this.modifier);
    }
}
