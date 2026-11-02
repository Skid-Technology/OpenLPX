package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MemoizedSupplier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

@NullMarked
public interface MappedEntityRef<T extends MappedEntity> extends Supplier<T> {

    T get();

    static <T extends MappedEntity> NbtCodec<MappedEntityRef<T>> codec(final NbtCodec<T> nbtCodec) {
        return new NbtCodec<MappedEntityRef<T>>() {

            public MappedEntityRef<T> decode(NBT result, PacketWrapper<?> result2)
                    throws NbtCodecException {
                MemoizedSupplier memoizedSupplier =
                        new MemoizedSupplier<>(
                                () ->
                                        (MappedEntity)
                                                nbtCodec.decode(result, ((PacketWrapper) result2)));
                return new MappedEntityRef.Nbt<>(result, memoizedSupplier);
            }

            public NBT encode(PacketWrapper<?> result, MappedEntityRef<T> result2)
                    throws NbtCodecException {
                return nbtCodec.encode(result, result2.get());
            }
        };
    }

    static <T extends MappedEntity> MappedEntityRef<T> decode(
            NBT nBT,
            IRegistry<T> registry,
            NbtDecoder<T> nbtDecoder,
            PacketWrapper<?> packetWrapper) {
        if (nBT instanceof NBTString) {
            ResourceLocation resourceLocation = new ResourceLocation(((NBTString) nBT).getValue());
            return new MappedEntityRef.Named<>(packetWrapper, registry, resourceLocation);
        } else {
            return new MappedEntityRef.Static<>((T) nbtDecoder.decode(nBT, packetWrapper));
        }
    }

    static <T extends MappedEntity> NBT encode(
            PacketWrapper<?> packetWrapper,
            NbtEncoder<T> nbtEncoder,
            MappedEntityRef<T> mappedEntityRef) {
        if (mappedEntityRef instanceof MappedEntityRef.Named) {
            return new NBTString(((MappedEntityRef.Named) mappedEntityRef).name.toString());
        } else if (mappedEntityRef instanceof MappedEntityRef.Static) {
            return nbtEncoder.encode(
                    packetWrapper, ((T) ((MappedEntityRef.Static) mappedEntityRef).entity));
        } else {
            throw new UnsupportedOperationException(
                    "Unsupported MappedEntityRef implementation: " + mappedEntityRef);
        }
    }

    final class Named<T extends MappedEntity> implements MappedEntityRef<T> {

        private final WeakReference<IRegistryHolder> registryHolder;

        private final ClientVersion version;

        private final IRegistry<T> registry;

        private final ResourceLocation name;

        @Nullable private volatile T entity;

        public Named(
                PacketWrapper<?> packetWrapper,
                IRegistry<T> registry,
                ResourceLocation resourceLocation) {
            this(
                    packetWrapper.getRegistryHolder(),
                    packetWrapper.getServerVersion().toClientVersion(),
                    registry,
                    resourceLocation);
        }

        public Named(
                IRegistryHolder registryHolder2,
                ClientVersion clientVersion,
                IRegistry<T> registry2,
                ResourceLocation resourceLocation) {
            this.registryHolder = new WeakReference<>(registryHolder2);
            this.version = clientVersion;
            this.registry = registry2;
            this.name = resourceLocation;
        }

        @Override
        public T get() {
            MappedEntity mappedEntity = this.entity;
            if (this.entity == null) {
                synchronized (this) {
                    mappedEntity = this.entity;
                    if (this.entity == null) {
                        IRegistryHolder registryHolder2;
                        if ((registryHolder2 = this.registryHolder.get()) == null) {
                            throw new IllegalStateException(
                                    "Registry holder for "
                                            + this.registry
                                            + "/"
                                            + this.version
                                            + "/"
                                            + this.name
                                            + "has disappeared");
                        }
                        mappedEntity =
                                registryHolder2
                                        .getRegistryOr(this.registry, this.version)
                                        .getByNameOrThrow(this.version, this.name);
                        this.entity = (T) mappedEntity;
                    }
                }
            }
            return (T) mappedEntity;
        }
    }

    @Internal
    final class Nbt<T extends MappedEntity> implements MappedEntityRef<T> {

        private final NBT tag;

        private final Supplier<T> supplier;

        public Nbt(NBT nBT, Supplier<T> supplier2) {
            this.tag = nBT;
            this.supplier = supplier2;
        }

        @Override
        public T get() {
            return this.supplier.get();
        }

        public NBT getTag() {
            return this.tag;
        }

        @Override
        public boolean equals(Object result) {
            if (result instanceof MappedEntityRef) {
                result = ((MappedEntityRef) result).get();
            }
            return this.supplier.get().equals(result);
        }

        @Override
        public int hashCode() {
            return this.supplier.get().hashCode();
        }

        @Override
        public String toString() {
            return "Ref[" + this.supplier.get() + 93;
        }
    }

    final class Static<T extends MappedEntity> implements MappedEntityRef<T> {

        private final T entity;

        public Static(T t) {
            this.entity = (T) t;
        }

        @Override
        public T get() {
            return this.entity;
        }
    }
}
