package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticDebugSubscription<T> extends AbstractMappedEntity
        implements DebugSubscription<T> {

    private final PacketWrapper.@Nullable Reader<T> reader;

    private final PacketWrapper.@Nullable Writer<T> writer;

    @Internal
    public StaticDebugSubscription(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.@Nullable Reader<T> reader2,
            PacketWrapper.@Nullable Writer<T> writer2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
    }

    @Override
    public T read(PacketWrapper<?> packetWrapper) {
        if (this.reader == null) {
            throw new IllegalStateException(this + " doesn't support network reading");
        } else {
            return this.reader.apply(packetWrapper);
        }
    }

    @Override
    public void write(PacketWrapper<?> packetWrapper, T t) {
        if (this.writer == null) {
            throw new IllegalStateException(this + " doesn't support network writing");
        }
        this.writer.accept(packetWrapper, (T) t);
    }
}
