package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class HashedComponentPatchMap {

    private final Map<ComponentType<?>, Integer> addedComponents;

    private final Set<ComponentType<?>> removedComponents;

    public HashedComponentPatchMap(
            Map<ComponentType<?>, Integer> entries, Set<ComponentType<?>> removedComponents) {
        this.addedComponents = entries;
        this.removedComponents = removedComponents;
    }

    public static HashedComponentPatchMap read(PacketWrapper<?> packetWrapper) {
        Map entries =
                packetWrapper.readMap(
                        result -> result.readMappedEntity(ComponentTypes.getRegistry()),
                        PacketWrapper::readInt);
        Set values =
                packetWrapper.readCollection(
                        HashSet::new,
                        result -> result.readMappedEntity(ComponentTypes.getRegistry()));
        return new HashedComponentPatchMap(entries, values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, HashedComponentPatchMap hashedComponentPatchMap) {
        packetWrapper.writeMap(
                hashedComponentPatchMap.addedComponents,
                PacketWrapper::writeMappedEntity,
                PacketWrapper::writeInt);
        packetWrapper.writeCollection(
                hashedComponentPatchMap.removedComponents, PacketWrapper::writeMappedEntity);
    }

    public Map<ComponentType<?>, Integer> getAddedComponents() {
        return this.addedComponents;
    }

    public Set<ComponentType<?>> getRemovedComponents() {
        return this.removedComponents;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("HashedComponentPatchMap[");
        if (!this.addedComponents.isEmpty()) {
            stringBuilder.append("added=");
            stringBuilder.append(
                    this.addedComponents.keySet().stream()
                            .map(result -> result.getName().toString())
                            .collect(Collectors.joining(", ", "[", "]")));
        }
        if (!this.removedComponents.isEmpty()) {
            if (!this.addedComponents.isEmpty()) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("removed=");
            stringBuilder.append(
                    this.removedComponents.stream()
                            .map(result -> result.getName().toString())
                            .collect(Collectors.joining(", ", "[", "]")));
        }
        if (this.addedComponents.isEmpty() && this.removedComponents.isEmpty()) {
            stringBuilder.append("EMPTY");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
