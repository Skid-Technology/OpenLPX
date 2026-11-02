package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.predicates;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class TypedComponentPredicate<T extends IComponentPredicate> {

    private final ComponentPredicateType<T> type;

    private final T predicate;

    public TypedComponentPredicate(ComponentPredicateType<T> componentPredicateType, T t) {
        this.type = componentPredicateType;
        this.predicate = (T) t;
    }

    public static TypedComponentPredicate<?> read(PacketWrapper<?> packetWrapper) {
        ComponentPredicateType componentPredicateType;
        IComponentPredicate componentPredicate =
                (componentPredicateType =
                                packetWrapper.readMappedEntity(
                                        ComponentPredicateTypes.getRegistry()))
                        .read(packetWrapper);
        return new TypedComponentPredicate<>(componentPredicateType, componentPredicate);
    }

    public static <T extends IComponentPredicate> void write(
            PacketWrapper<?> packetWrapper, TypedComponentPredicate<T> typedComponentPredicate) {
        packetWrapper.writeMappedEntity(typedComponentPredicate.type);
        typedComponentPredicate.type.write(packetWrapper, typedComponentPredicate.predicate);
    }

    public ComponentPredicateType<T> getType() {
        return this.type;
    }

    public T getPredicate() {
        return this.predicate;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof TypedComponentPredicate)) {
            return false;
        }
        TypedComponentPredicate typedComponentPredicate = (TypedComponentPredicate) result;
        return !this.type.equals(typedComponentPredicate.type)
                ? false
                : this.predicate.equals(typedComponentPredicate.predicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.predicate);
    }
}
