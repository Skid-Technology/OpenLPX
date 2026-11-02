package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Experimental
public final class MaybeMappedEntity<T extends MappedEntity> {

    @Nullable private final T entity;

    @Nullable private final ResourceLocation name;

    @Nullable private final IRegistry<T> registry;

    public MaybeMappedEntity(T t) {
        this((T) t, null, null);
    }

    public MaybeMappedEntity(ResourceLocation resourceLocation) {
        this(resourceLocation, null);
    }

    public MaybeMappedEntity(ResourceLocation resourceLocation, @Nullable IRegistry<T> registry) {
        this(null, resourceLocation, registry);
    }

    public MaybeMappedEntity(@Nullable T t, @Nullable ResourceLocation resourceLocation) {
        this((T) t, resourceLocation, null);
    }

    public MaybeMappedEntity(
            @Nullable T t,
            @Nullable ResourceLocation resourceLocation,
            @Nullable IRegistry<T> registry2) {
        if (t == null && resourceLocation == null) {
            throw new IllegalArgumentException("Only one of entity and name is allowed to be null");
        }
        this.entity = (T) t;
        this.name = resourceLocation;
        this.registry = registry2;
    }

    public static <T extends MappedEntity> MaybeMappedEntity<T> read(
            PacketWrapper<?> packetWrapper, IRegistry<T> registry, PacketWrapper.Reader<T> reader) {
        if (packetWrapper.readBoolean()) {
            return new MaybeMappedEntity<>((T) reader.apply(packetWrapper));
        }
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        IRegistry registryOr =
                packetWrapper.getRegistryHolder().getRegistryOr(registry, clientVersion);
        return new MaybeMappedEntity<>(packetWrapper.readIdentifier(), registryOr);
    }

    public static <T extends MappedEntity> void write(
            PacketWrapper<?> packetWrapper,
            MaybeMappedEntity<T> maybeMappedEntity,
            PacketWrapper.Writer<T> writer) {
        if (maybeMappedEntity.entity != null) {
            packetWrapper.writeBoolean(true);
            writer.accept(packetWrapper, maybeMappedEntity.entity);
        } else {
            packetWrapper.writeBoolean(false);
            packetWrapper.writeIdentifier(maybeMappedEntity.name);
        }
    }

    public T getValueOrThrow() {
        MappedEntity mappedEntity;
        if ((mappedEntity = this.getValue()) == null) {
            throw new IllegalStateException("Can't resolve entity by name " + this.name);
        } else {
            return (T) mappedEntity;
        }
    }

    @Nullable
    public T getValue() {
        if (this.entity != null) {
            return this.entity;
        } else {
            return this.registry != null && this.name != null
                    ? this.registry.getByName(this.name)
                    : null;
        }
    }

    public ResourceLocation getName() {
        if (this.name != null) {
            return this.name;
        } else if (this.entity != null) {
            return this.entity.getName();
        } else {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof MaybeMappedEntity)) {
            return false;
        }
        MaybeMappedEntity maybeMappedEntity = (MaybeMappedEntity) result;
        return !Objects.equals(this.entity, maybeMappedEntity.entity)
                ? false
                : Objects.equals(this.name, maybeMappedEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entity, this.name);
    }
}
