package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.HashedComponentPatchMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public final class HashedStack {

    private final ItemType item;

    private final int count;

    private final HashedComponentPatchMap components;

    public HashedStack(
            ItemType itemType, int count, HashedComponentPatchMap hashedComponentPatchMap) {
        this.item = itemType;
        this.count = count;
        this.components = hashedComponentPatchMap;
    }

    public static Optional<HashedStack> readOptional(PacketWrapper<?> packetWrapper) {
        return Optional.ofNullable(read(packetWrapper));
    }

    public static Optional<HashedStack> toOptionalFromItemStack(ItemStack itemStack) {
        return Optional.ofNullable(fromItemStack(itemStack));
    }

    @Nullable
    public static HashedStack read(PacketWrapper<?> packetWrapper) {
        if (!packetWrapper.readBoolean()) {
            return null;
        }
        ItemType itemType = packetWrapper.readMappedEntity(ItemTypes.getRegistry());
        int value = packetWrapper.readVarInt();
        HashedComponentPatchMap hashedComponentPatchMap =
                HashedComponentPatchMap.read(packetWrapper);
        return new HashedStack(itemType, value, hashedComponentPatchMap);
    }

    public static void writeOptional(
            PacketWrapper<?> packetWrapper, Optional<HashedStack> optional) {
        write(packetWrapper, (HashedStack) optional.orElse(null));
    }

    public static void write(PacketWrapper<?> packetWrapper, HashedStack hashedStack) {
        if (hashedStack == null) {
            packetWrapper.writeBoolean(false);
        } else {
            packetWrapper.writeBoolean(true);
            packetWrapper.writeMappedEntity(hashedStack.item);
            packetWrapper.writeVarInt(hashedStack.count);
            HashedComponentPatchMap.write(packetWrapper, hashedStack.components);
        }
    }

    public static HashedStack fromItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        Map<ComponentType<?>, Optional<?>> patches = itemStack.getComponents().getPatches();
        HashMap<ComponentType<?>, Integer> entries = new HashMap<>(patches.size());
        HashSet<ComponentType<?>> values = new HashSet<>(patches.size());
        Iterator iterator2 = patches.entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            if (((Optional) (entry = (Entry) iterator2.next()).getValue()).isPresent()) {
                entries.put((ComponentType) entry.getKey(), 0);
            } else {
                values.add((ComponentType) entry.getKey());
            }
        }
        HashedComponentPatchMap hashedComponentPatchMap =
                new HashedComponentPatchMap(entries, values);
        return new HashedStack(itemStack.getType(), itemStack.getAmount(), hashedComponentPatchMap);
    }

    public ItemStack asItemStack() {
        ItemStack itemStack = ItemStack.builder().type(this.item).amount(this.count).build();
        for (ComponentType<?> componentType : this.components.getRemovedComponents()) {
            itemStack.unsetComponent(componentType);
        }
        return itemStack;
    }

    public ItemType getItem() {
        return this.item;
    }

    public int getCount() {
        return this.count;
    }

    public HashedComponentPatchMap getComponents() {
        return this.components;
    }

    @Override
    public String toString() {
        return "HashedStack["
                + this.count
                + "x "
                + this.item.getName()
                + ", "
                + this.components
                + "]";
    }
}
