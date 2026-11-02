package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public abstract class AbstractMappedEntity implements MappedEntity {

    @Nullable protected final TypesBuilderData data;

    public AbstractMappedEntity(@Nullable TypesBuilderData typesBuilderData) {
        this.data = typesBuilderData;
    }

    @Nullable
    public TypesBuilderData getRegistryData() {
        return this.data;
    }

    @Override
    public ResourceLocation getName() {
        if (this.data != null) {
            return this.data.getName();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public int getId(ClientVersion clientVersion) {
        if (this.data != null) {
            return this.data.getId(clientVersion);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean isRegistered() {
        return this.data != null;
    }

    @Override
    public boolean equals(Object result) {
        if (result == null || this.getClass() != result.getClass()) {
            return false;
        } else if (this == result) {
            return true;
        } else {
            AbstractMappedEntity abstractMappedEntity = (AbstractMappedEntity) result;
            if (this.data != null && abstractMappedEntity.data != null) {
                return this.data.getName().equals(abstractMappedEntity.data.getName());
            } else {
                return this instanceof DeepComparableEntity
                        ? ((DeepComparableEntity) this).deepEquals(result)
                        : false;
            }
        }
    }

    @Override
    public int hashCode() {
        if (this.data != null) {
            return Objects.hash(this.getClass(), this.data.getName());
        } else {
            return this instanceof DeepComparableEntity
                    ? ((DeepComparableEntity) this).deepHashCode()
                    : System.identityHashCode(this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "["
                + (this.data == null ? this.hashCode() : this.data.getName())
                + 93;
    }
}
