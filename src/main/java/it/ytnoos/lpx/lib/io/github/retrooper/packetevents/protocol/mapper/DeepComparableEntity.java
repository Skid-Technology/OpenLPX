package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface DeepComparableEntity {

    boolean deepEquals(@Nullable Object result);

    int deepHashCode();
}
