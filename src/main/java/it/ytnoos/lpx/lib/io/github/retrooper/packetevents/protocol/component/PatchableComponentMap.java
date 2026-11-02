package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

@NullMarked
public class PatchableComponentMap implements IComponentMap {

    public static final PatchableComponentMap EMPTY =
            new PatchableComponentMap(Collections.emptyMap(), Collections.emptyMap());

    private final Map<ComponentType<?>, ?> base;

    private final Map<ComponentType<?>, Optional<?>> patches;

    private final IRegistryHolder registries;

    public PatchableComponentMap(StaticComponentMap staticComponentMap) {
        this(staticComponentMap.delegate, new HashMap<>(), staticComponentMap.registries);
    }

    @Deprecated
    public PatchableComponentMap(Map<ComponentType<?>, ?> entries) {
        this(entries, new HashMap<>(), GlobalRegistryHolder.INSTANCE);
    }

    public PatchableComponentMap(
            StaticComponentMap staticComponentMap, Map<ComponentType<?>, Optional<?>> entries) {
        this(staticComponentMap.delegate, entries, staticComponentMap.registries);
    }

    @Deprecated
    public PatchableComponentMap(
            Map<ComponentType<?>, ?> entries, Map<ComponentType<?>, Optional<?>> entries2) {
        this(entries, entries2, GlobalRegistryHolder.INSTANCE);
    }

    public PatchableComponentMap(
            StaticComponentMap staticComponentMap, IRegistryHolder registryHolder) {
        this(staticComponentMap.delegate, new HashMap<>(), registryHolder);
    }

    public PatchableComponentMap(Map<ComponentType<?>, ?> entries, IRegistryHolder registryHolder) {
        this(entries, new HashMap<>(), registryHolder);
    }

    public PatchableComponentMap(
            StaticComponentMap staticComponentMap,
            Map<ComponentType<?>, Optional<?>> entries,
            IRegistryHolder registryHolder) {
        this(staticComponentMap.delegate, entries, registryHolder);
    }

    public PatchableComponentMap(
            Map<ComponentType<?>, ?> entries,
            Map<ComponentType<?>, Optional<?>> entries2,
            IRegistryHolder registryHolder) {
        this.base = Collections.unmodifiableMap(new HashMap(entries));
        this.patches = entries2;
        this.registries = registryHolder;
    }

    public static PatchableComponentMap read(
            PacketWrapper<?> packetWrapper, ItemType itemType, boolean matches) {
        return read(
                packetWrapper,
                itemType.getComponents(packetWrapper.getServerVersion().toClientVersion()),
                matches);
    }

    public static PatchableComponentMap read(
            PacketWrapper<?> packetWrapper,
            StaticComponentMap staticComponentMap,
            boolean matches) {
        int value = packetWrapper.readVarInt();
        int value2 = packetWrapper.readVarInt();
        if (value == 0 && value2 == 0) {
            return new PatchableComponentMap(staticComponentMap);
        }
        PatchableComponentMap patchableComponentMap =
                new PatchableComponentMap(
                        staticComponentMap,
                        new HashMap<>(value + value2),
                        packetWrapper.getRegistryHolder());
        for (int index = 0; index < value; index++) {
            ComponentType<?> componentType =
                    packetWrapper.readMappedEntity(ComponentTypes.getRegistry());
            int value3;
            if (matches) {
                int value4;
                if ((value4 = packetWrapper.readVarInt())
                        > ByteBufHelper.readableBytes(packetWrapper.buffer)) {
                    throw new RuntimeException(
                            "Component size "
                                    + value4
                                    + " for "
                                    + componentType.getName()
                                    + " out of bounds");
                }
                value3 = ByteBufHelper.readerIndex(packetWrapper.buffer) + value4;
            } else {
                value3 = -1;
            }
            Object result = componentType.read(packetWrapper);
            int value5;
            if (value3 != -1
                    && (value5 = ByteBufHelper.readerIndex(packetWrapper.buffer)) != value3) {
                throw new RuntimeException(
                        "Invalid component read for "
                                + componentType.getName()
                                + "; expected reader index "
                                + value3
                                + ", got reader index "
                                + value5);
            }
            patchableComponentMap.set((ComponentType) componentType, result);
        }
        for (int index2 = 0; index2 < value2; index2++) {
            patchableComponentMap.unset(
                    packetWrapper.readMappedEntity(ComponentTypes.getRegistry()));
        }
        return patchableComponentMap;
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemStack itemStack, boolean matches) {
        if (!itemStack.hasComponentPatches()) {
            packetWrapper.writeShort(0);
        } else {
            write(packetWrapper, itemStack.getComponents(), matches);
        }
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            PatchableComponentMap patchableComponentMap,
            boolean matches) {
        Map<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                        .ComponentType<
                                ?>,
                        Optional<?>>
                patches = patchableComponentMap.getPatches();
        int value = 0;
        int value2 = 0;
        Iterator iterator2 = patches.entrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Optional) ((Entry) iterator2.next()).getValue()).isPresent()) {
                value++;
            } else {
                value2++;
            }
        }
        packetWrapper.writeVarInt(value);
        packetWrapper.writeVarInt(value2);
        iterator2 = patches.entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            if (((Optional) (entry = (Entry) iterator2.next()).getValue()).isPresent()) {
                packetWrapper.writeVarInt(
                        ((ComponentType) entry.getKey())
                                .getId(packetWrapper.getServerVersion().toClientVersion()));
                if (matches) {
                    Object result = packetWrapper.buffer;
                    packetWrapper.buffer = ByteBufHelper.allocateNewBuffer(result);
                    ((ComponentType) entry.getKey())
                            .write(packetWrapper, ((Optional) entry.getValue()).get());
                    Object result2 = packetWrapper.buffer;
                    packetWrapper.buffer = result;
                    packetWrapper.writeVarInt(ByteBufHelper.readableBytes(result2));
                    ByteBufHelper.writeBytes(packetWrapper.buffer, result2);
                    ByteBufHelper.release(result2);
                } else {
                    ((ComponentType) entry.getKey())
                            .write(packetWrapper, ((Optional) entry.getValue()).get());
                }
            }
        }
        iterator2 = patches.entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry2;
            if (!((Optional) (entry2 = (Entry) iterator2.next()).getValue()).isPresent()) {
                packetWrapper.writeVarInt(
                        ((ComponentType) entry2.getKey())
                                .getId(packetWrapper.getServerVersion().toClientVersion()));
            }
        }
    }

    @Nullable
    @Override
    public <T> T get(ComponentType<T> componentType) {
        Optional<?> optional;
        Object result;
        if ((result =
                        (optional = this.patches.get(componentType)) != null
                                ? optional.orElse(null)
                                : this.base.get(componentType))
                instanceof ComponentValueRef) {
            result = ((ComponentValueRef) result).resolve(this.registries);
        }
        return (T) result;
    }

    @Override
    public <T> void set(ComponentType<T> componentType, Optional<T> optional) {
        Object result;
        if ((result = this.base.get(componentType)) instanceof ComponentValueRef) {
            result = ((ComponentValueRef) result).resolve(this.registries);
        }
        T t = optional.orElse(null);
        if (Objects.equals(result, t)) {
            this.patches.remove(componentType);
        } else {
            this.patches.put(componentType, optional);
        }
    }

    @Override
    public boolean has(ComponentType<?> componentType) {
        Optional<?> optional;
        return (optional = this.patches.get(componentType)) != null
                ? optional.isPresent()
                : this.base.containsKey(componentType);
    }

    public PatchableComponentMap withRegistries(IRegistryHolder registryHolder) {
        return this.registries != registryHolder
                ? new PatchableComponentMap(this.base, this.patches, this.registries)
                : this;
    }

    public PatchableComponentMap copy() {
        return new PatchableComponentMap(this.base, new HashMap<>(this.patches), this.registries);
    }

    @Deprecated
    public Map<ComponentType<?>, ?> getBase() {
        return this.base;
    }

    @Deprecated
    public Map<ComponentType<?>, Optional<?>> getPatches() {
        return this.patches;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof PatchableComponentMap)) {
            return false;
        }
        PatchableComponentMap patchableComponentMap = (PatchableComponentMap) result;
        return !this.base.equals(patchableComponentMap.base)
                ? false
                : this.patches.equals(patchableComponentMap.patches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.base, this.patches);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PatchableComponentMap[");
        if (!this.base.isEmpty()) {
            stringBuilder.append("base={");
            boolean matches = true;
            for (Entry<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                            .ComponentType<
                                    ?>,
                            ?>
                    entry : this.base.entrySet()) {
                if (!matches) {
                    stringBuilder.append(", ");
                }
                matches = false;
                stringBuilder
                        .append(((ComponentType) entry.getKey()).getName())
                        .append("=")
                        .append(entry.getValue());
            }
            stringBuilder.append("}");
        }
        if (!this.patches.isEmpty()) {
            if (!this.base.isEmpty()) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("patches={");
            boolean matches2 = true;
            for (Entry<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                            .ComponentType<
                                    ?>,
                            Optional<?>>
                    entry2 : this.patches.entrySet()) {
                if (!matches2) {
                    stringBuilder.append(", ");
                }
                matches2 = false;
                stringBuilder.append(((ComponentType) entry2.getKey()).getName()).append("=");
                if (((Optional) entry2.getValue()).isPresent()) {
                    stringBuilder.append(((Optional) entry2.getValue()).get());
                } else {
                    stringBuilder.append("REMOVED");
                }
            }
            stringBuilder.append("}");
        }
        if (this.base.isEmpty() && this.patches.isEmpty()) {
            stringBuilder.append("EMPTY");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
