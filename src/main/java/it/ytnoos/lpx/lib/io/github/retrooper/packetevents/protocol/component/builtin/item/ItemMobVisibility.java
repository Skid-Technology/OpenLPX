package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class ItemMobVisibility {

    private final MappedEntitySet<EntityType> targetingEntityTypes;

    private final float visibility;

    public ItemMobVisibility(MappedEntitySet<EntityType> mappedEntitySet, float visibility) {
        this.targetingEntityTypes = mappedEntitySet;
        this.visibility = visibility;
    }

    public static ItemMobVisibility read(PacketWrapper<?> packetWrapper) {
        MappedEntitySet<EntityType> mappedEntitySet =
                MappedEntitySet.read(packetWrapper, EntityTypes.getRegistry());
        float value = packetWrapper.readFloat();
        return new ItemMobVisibility(mappedEntitySet, value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemMobVisibility itemMobVisibility) {
        MappedEntitySet.write(packetWrapper, itemMobVisibility.targetingEntityTypes);
        packetWrapper.writeFloat(itemMobVisibility.visibility);
    }

    public MappedEntitySet<EntityType> getTargetingEntityTypes() {
        return this.targetingEntityTypes;
    }

    public float getVisibility() {
        return this.visibility;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        }
        ItemMobVisibility itemMobVisibility;
        return Float.compare(
                                (itemMobVisibility = (ItemMobVisibility) result).visibility,
                                this.visibility)
                        != 0
                ? false
                : this.targetingEntityTypes.equals(itemMobVisibility.targetingEntityTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.targetingEntityTypes, this.visibility);
    }
}
