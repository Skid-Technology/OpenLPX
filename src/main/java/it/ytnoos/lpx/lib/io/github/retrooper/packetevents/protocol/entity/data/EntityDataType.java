package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class EntityDataType<T> extends AbstractMappedEntity {

    private final PacketWrapper.Reader<? extends T> reader;

    private final PacketWrapper.Writer<T> writer;

    @Internal
    public EntityDataType(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.Reader<? extends T> reader2,
            PacketWrapper.Writer<T> writer2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
    }

    public T read(PacketWrapper<?> packetWrapper) {
        return (T) this.reader.apply(packetWrapper);
    }

    public void write(PacketWrapper<?> packetWrapper, T t) {
        this.writer.accept(packetWrapper, (T) t);
    }

    @Deprecated
    public Function<PacketWrapper<?>, T> getDataDeserializer() {
        PacketWrapper.Reader<? extends T> reader2 = this.reader;
        return reader2::apply;
    }

    @Deprecated
    public BiConsumer<PacketWrapper<?>, T> getDataSerializer() {
        return this.writer;
    }
}
