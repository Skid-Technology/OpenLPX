package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.RecipeData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticRecipeSerializer<T extends RecipeData> extends AbstractMappedEntity
        implements RecipeSerializer<T> {

    private final PacketWrapper.Reader<T> reader;

    private final PacketWrapper.Writer<T> writer;

    @Nullable private final RecipeType legacyType;

    @Internal
    public StaticRecipeSerializer(
            @Nullable TypesBuilderData typesBuilderData,
            PacketWrapper.Reader<T> reader2,
            PacketWrapper.Writer<T> writer2,
            @Nullable RecipeType recipeType) {
        super(typesBuilderData);
        this.reader = reader2;
        this.writer = writer2;
        this.legacyType = recipeType;
    }

    @Override
    public RecipeType getLegacyType() {
        return this.legacyType;
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
