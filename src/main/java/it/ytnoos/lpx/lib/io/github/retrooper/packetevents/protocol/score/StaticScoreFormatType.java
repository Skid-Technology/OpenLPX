package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.score;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticScoreFormatType<T extends ScoreFormat> extends AbstractMappedEntity
        implements ScoreFormatType<T> {

    private final PacketWrapper.Reader<T> reader;

    private final PacketWrapper.Writer<T> writer;

    @Internal
    public StaticScoreFormatType(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.Reader<T> reader2,
            PacketWrapper.Writer<T> writer2) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
    }

    @Override
    public int getId() {
        ServerVersion version = PacketEvents.getAPI().getServerManager().getVersion();
        return this.getId(version.toClientVersion());
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
