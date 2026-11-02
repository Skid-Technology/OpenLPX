package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class ComponentPredicate implements Predicate<IComponentMap> {

    private List<ComponentValue<?>> requiredComponents;

    public ComponentPredicate() {
        this(new ArrayList<>());
    }

    public ComponentPredicate(List<ComponentValue<?>> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public static ComponentPredicate read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ComponentValue::read);
        return new ComponentPredicate(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ComponentPredicate componentPredicate) {
        packetWrapper.writeList(componentPredicate.requiredComponents, ComponentValue::write);
    }

    public static ComponentPredicate emptyPredicate() {
        return new ComponentPredicate(new ArrayList<>());
    }

    public static ComponentPredicate fromPatches(PatchableComponentMap patchableComponentMap) {
        Map<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                        .ComponentType<
                                ?>,
                        Optional<?>>
                patches = patchableComponentMap.getPatches();
        ArrayList<ComponentValue<?>> values = new ArrayList<>(patches.size());
        Iterator iterator2 = patches.entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            if (((Optional) (entry = (Entry) iterator2.next()).getValue()).isPresent()) {
                values.add(
                        new ComponentValue<>(
                                (ComponentType<Object>) entry.getKey(),
                                ((Optional) entry.getValue()).get()));
            }
        }
        return new ComponentPredicate(values);
    }

    public PatchableComponentMap asPatches(StaticComponentMap staticComponentMap) {
        PatchableComponentMap patchableComponentMap = new PatchableComponentMap(staticComponentMap);
        for (it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentValue<?>
                componentValue : this.requiredComponents) {
            patchableComponentMap.set(componentValue);
        }
        return patchableComponentMap;
    }

    public boolean test(IComponentMap componentMap) {
        for (it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentValue<?>
                componentValue : this.requiredComponents) {
            Optional<?> optional;
            if (!(optional = componentMap.getOptional(componentValue.getType())).isPresent()
                    || !componentValue.getValue().equals(optional.get())) {
                return false;
            }
        }
        return true;
    }

    public List<ComponentValue<?>> getRequiredComponents() {
        return this.requiredComponents;
    }

    public void setRequiredComponents(List<ComponentValue<?>> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ComponentPredicate)) {
            return false;
        }
        ComponentPredicate componentPredicate = (ComponentPredicate) result;
        return this.requiredComponents.equals(componentPredicate.requiredComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.requiredComponents);
    }

    @Override
    public String toString() {
        return "ComponentPredicate{requiredComponents=" + this.requiredComponents + 125;
    }
}
