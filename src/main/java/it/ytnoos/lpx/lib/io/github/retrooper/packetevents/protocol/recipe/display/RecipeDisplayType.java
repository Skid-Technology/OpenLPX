package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public interface RecipeDisplayType<T extends RecipeDisplay<?>> extends MappedEntity {

    T read(PacketWrapper<?> packetWrapper);

    void write(PacketWrapper<?> packetWrapper, T t);
}
