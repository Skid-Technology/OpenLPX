package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.predicates;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentPredicate;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComponentMatchers {

    private ComponentPredicate components;

    private List<TypedComponentPredicate<?>> predicates;

    public ComponentMatchers() {
        this(new ComponentPredicate(), new ArrayList<>());
    }

    public ComponentMatchers(
            ComponentPredicate componentPredicate, List<TypedComponentPredicate<?>> predicates) {
        this.components = componentPredicate;
        this.predicates = predicates;
    }

    public static ComponentMatchers read(PacketWrapper<?> packetWrapper) {
        ComponentPredicate componentPredicate = ComponentPredicate.read(packetWrapper);
        List values = packetWrapper.readList(TypedComponentPredicate::read);
        return new ComponentMatchers(componentPredicate, values);
    }

    public static void write(PacketWrapper<?> packetWrapper, ComponentMatchers componentMatchers) {
        ComponentPredicate.write(packetWrapper, componentMatchers.components);
        packetWrapper.writeList(componentMatchers.predicates, TypedComponentPredicate::write);
    }

    public ComponentPredicate getComponents() {
        return this.components;
    }

    public void setComponents(ComponentPredicate componentPredicate) {
        this.components = componentPredicate;
    }

    public List<TypedComponentPredicate<?>> getPredicates() {
        return this.predicates;
    }

    public void setPredicates(List<TypedComponentPredicate<?>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ComponentMatchers)) {
            return false;
        }
        ComponentMatchers componentMatchers = (ComponentMatchers) result;
        return !this.components.equals(componentMatchers.components)
                ? false
                : this.predicates.equals(componentMatchers.predicates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.components, this.predicates);
    }
}
