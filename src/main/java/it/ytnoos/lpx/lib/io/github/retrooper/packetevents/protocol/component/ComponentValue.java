package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public final class ComponentValue<T> {

    private final ComponentType<T> type;

    private final T value;

    public ComponentValue(ComponentType<T> componentType, T t) {
        this.type = componentType;
        this.value = (T) t;
    }

    public static ComponentValue<?> read(PacketWrapper<?> packetWrapper) {
        ComponentType componentType = packetWrapper.readMappedEntity(ComponentTypes::getById);
        return read0(packetWrapper, componentType);
    }

    private static <T> ComponentValue<T> read0(
            PacketWrapper<?> packetWrapper, ComponentType<T> componentType) {
        return new ComponentValue<>(componentType, (T) componentType.read(packetWrapper));
    }

    public static <T> void write(PacketWrapper<?> packetWrapper, ComponentValue<T> componentValue) {
        packetWrapper.writeMappedEntity(componentValue.type);
        componentValue.type.write(packetWrapper, componentValue.value);
    }

    public ComponentType<T> getType() {
        return this.type;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ComponentValue)) {
            return false;
        }
        ComponentValue componentValue = (ComponentValue) result;
        return !this.type.equals(componentValue.type)
                ? false
                : this.value.equals(componentValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.value);
    }

    @Override
    public String toString() {
        return "ComponentValue{type=" + this.type + ", value=" + this.value + 125;
    }
}
