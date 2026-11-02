package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticWorldClock extends AbstractMappedEntity implements WorldClock {

    public StaticWorldClock() {
        this(null);
    }

    @Internal
    public StaticWorldClock(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }

    public WorldClock copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticWorldClock(typesBuilderData);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        return result instanceof StaticWorldClock;
    }

    @Override
    public int deepHashCode() {
        return Objects.hashCode(0);
    }
}
