package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.consumables;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticConsumeEffectType<T extends ConsumeEffect<?>> extends AbstractMappedEntity
        implements ConsumeEffectType<T> {

    private final PacketWrapper.Reader<T> reader;

    private final PacketWrapper.Writer<T> writer;

    @Internal
    public StaticConsumeEffectType(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.Reader<T> reader2,
            PacketWrapper.Writer<T> writer2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
    }

    @Override
    public T read(PacketWrapper<?> packetWrapper) {
        return this.reader.apply(packetWrapper);
    }

    @Override
    public void write(PacketWrapper<?> packetWrapper, T t) {
        this.writer.accept(packetWrapper, (T) t);
    }
}
