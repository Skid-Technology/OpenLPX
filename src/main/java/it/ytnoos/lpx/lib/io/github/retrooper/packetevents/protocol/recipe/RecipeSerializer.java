package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.RecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public interface RecipeSerializer<T extends RecipeData> extends MappedEntity {

    @Deprecated
    RecipeType getLegacyType();

    T read(PacketWrapper<?> packetWrapper);

    void write(PacketWrapper<?> packetWrapper, T t);
}
