package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemAttributeModifiers;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemEnchantments;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemLore;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemRarity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@NullMarked
public final class StaticComponentMap implements IComponentMap {

    public static final StaticComponentMap EMPTY = new StaticComponentMap(Collections.emptyMap());

    @Obsolete
    public static final StaticComponentMap SHARED_ITEM_COMPONENTS =
            builder()
                    .set(ComponentTypes.MAX_STACK_SIZE, 64)
                    .set(ComponentTypes.LORE, ItemLore.EMPTY)
                    .set(ComponentTypes.ENCHANTMENTS, ItemEnchantments.EMPTY)
                    .set(ComponentTypes.REPAIR_COST, 0)
                    .set(ComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY)
                    .set(ComponentTypes.RARITY, ItemRarity.COMMON)
                    .build();

    private final boolean empty;

    final Map<ComponentType<?>, ?> delegate;

    final IRegistryHolder registries;

    @Deprecated
    public StaticComponentMap(Map<ComponentType<?>, ?> entries) {
        this(entries, GlobalRegistryHolder.INSTANCE);
    }

    public StaticComponentMap(Map<ComponentType<?>, ?> entries, IRegistryHolder registryHolder) {
        this.empty = entries.isEmpty();
        this.delegate =
                this.empty
                        ? Collections.emptyMap()
                        : Collections.unmodifiableMap(new HashMap(entries));
        this.registries = registryHolder;
    }

    public static StaticComponentMap.Builder builder() {
        return new StaticComponentMap.Builder();
    }

    @Override
    public boolean has(ComponentType<?> componentType) {
        return !this.empty && this.delegate.containsKey(componentType);
    }

    @Nullable
    @Override
    public <T> T get(ComponentType<T> componentType) {
        Object result;
        if ((result = this.delegate.get(componentType)) instanceof ComponentValueRef) {
            result = ((ComponentValueRef) result).resolve(this.registries);
        }
        return (T) result;
    }

    @Override
    public <T> void set(ComponentType<T> componentType, Optional<T> optional) {
        throw new UnsupportedOperationException();
    }

    public StaticComponentMap withRegistries(IRegistryHolder registryHolder) {
        return this.registries != registryHolder
                ? new StaticComponentMap(this.delegate, registryHolder)
                : this;
    }

    public StaticComponentMap merge(StaticComponentMap staticComponentMap) {
        return builder()
                .setAll(this)
                .setAll(staticComponentMap)
                .setRegistries(this.registries)
                .build();
    }

    public Set<ComponentType<?>> getKeys() {
        return this.delegate.keySet();
    }

    @Deprecated
    public Map<ComponentType<?>, ?> getDelegate() {
        return this.delegate;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof StaticComponentMap)) {
            return false;
        }
        StaticComponentMap staticComponentMap = (StaticComponentMap) result;
        return this.delegate.equals(staticComponentMap.delegate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate);
    }

    @Override
    public String toString() {
        return "Components" + this.delegate;
    }

    public static class Builder {

        private final Map<ComponentType<?>, Object> map = new HashMap<>();

        private IRegistryHolder registries = GlobalRegistryHolder.INSTANCE;

        public StaticComponentMap build() {
            return new StaticComponentMap(this.map, this.registries);
        }

        public StaticComponentMap.Builder setRegistries(IRegistryHolder registryHolder) {
            this.registries = registryHolder;
            return this;
        }

        public StaticComponentMap.Builder setAll(StaticComponentMap.Builder builder) {
            return this.setAll(builder.map);
        }

        public StaticComponentMap.Builder setAll(StaticComponentMap staticComponentMap) {
            return this.setAll(staticComponentMap.delegate);
        }

        public StaticComponentMap.Builder setAll(Map<ComponentType<?>, ?> entries) {
            for (Entry<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                            .ComponentType<
                                    ?>,
                            ?>
                    entry : entries.entrySet()) {
                this.set((ComponentType<Object>) entry.getKey(), entry.getValue());
            }
            return this;
        }

        public <T> StaticComponentMap.Builder set(
                ComponentType<T> componentType, Optional<T> optional) {
            return this.set(componentType, optional.orElse(null));
        }

        public <T> StaticComponentMap.Builder set(ComponentType<T> componentType, @Nullable T t) {
            this.map.put(componentType, t);
            return this;
        }

        public <T> StaticComponentMap.Builder set(
                ComponentType<T> componentType, @Nullable ComponentValueRef<T> componentValueRef) {
            if (componentValueRef == null) {
                this.map.remove(componentType);
            } else {
                this.map.put(componentType, componentValueRef);
            }
            return this;
        }
    }
}
