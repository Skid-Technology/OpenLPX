package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticEnvironmentAttribute<T> extends AbstractMappedEntity
        implements EnvironmentAttribute<T> {

    private final boolean isSynced;

    @Nullable private final AttributeType<T> attributeType;

    @Nullable private final T defaultValue;

    @Internal
    public StaticEnvironmentAttribute(
            @Nullable TypesBuilderData typesBuilderData,
            boolean isSynced,
            @Nullable AttributeType<T> attributeType2,
            @Nullable T t) {
        super(typesBuilderData);
        this.isSynced = isSynced;
        this.attributeType = attributeType2;
        this.defaultValue = (T) t;
    }

    @Override
    public boolean isSynced() {
        return this.isSynced;
    }

    @Override
    public AttributeType<T> getType() {
        if (this.attributeType == null) {
            throw new UnsupportedOperationException();
        } else {
            return this.attributeType;
        }
    }

    @Override
    public T getDefaultValue() {
        if (this.defaultValue == null) {
            throw new UnsupportedOperationException();
        } else {
            return this.defaultValue;
        }
    }
}
