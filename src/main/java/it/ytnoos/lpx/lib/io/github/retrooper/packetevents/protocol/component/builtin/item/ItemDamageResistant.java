package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.damagetype.DamageTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class ItemDamageResistant {

    private MappedEntitySet<DamageType> types;

    public ItemDamageResistant(ResourceLocation resourceLocation) {
        this(new MappedEntitySet<>(resourceLocation));
    }

    public ItemDamageResistant(MappedEntitySet<DamageType> mappedEntitySet) {
        this.types = mappedEntitySet;
    }

    public static ItemDamageResistant read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)
                ? new ItemDamageResistant(
                        MappedEntitySet.read(packetWrapper, DamageTypes.getRegistry()))
                : new ItemDamageResistant(packetWrapper.readIdentifier());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemDamageResistant itemDamageResistant) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            MappedEntitySet.write(packetWrapper, itemDamageResistant.types);
        } else {
            packetWrapper.writeIdentifier(itemDamageResistant.getTypesTagKey());
        }
    }

    public MappedEntitySet<DamageType> getTypes() {
        return this.types;
    }

    public void setTypes(MappedEntitySet<DamageType> mappedEntitySet) {
        this.types = mappedEntitySet;
    }

    public ResourceLocation getTypesTagKey() {
        ResourceLocation resourceLocation;
        if ((resourceLocation = this.types.getTagKey()) == null) {
            throw new IllegalStateException("No tag key present");
        } else {
            return resourceLocation;
        }
    }

    public void setTypesTagKey(ResourceLocation resourceLocation) {
        this.types = new MappedEntitySet<>(resourceLocation);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemDamageResistant)) {
            return false;
        }
        ItemDamageResistant itemDamageResistant = (ItemDamageResistant) result;
        return this.types.equals(itemDamageResistant.types);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.types);
    }

    @Override
    public String toString() {
        return "ItemDamageResistant{types=" + this.types + 125;
    }
}
